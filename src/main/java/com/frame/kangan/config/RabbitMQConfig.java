package com.frame.kangan.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.StatefulRetryOperationsInterceptorFactoryBean;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.retry.interceptor.StatefulRetryOperationsInterceptor;
import org.springframework.util.DigestUtils;

/**
 * @ClassName: RabbitMQConfig
 * 
 * @Description: TODO(这里用一句话描述这个类的作用)
 * 
 * @author kang.an@ele.me
 * 
 * @date 2016年6月28日 下午6:25:15
 * 
 */
@SpringBootApplication
public class RabbitMQConfig {

	public static final String Frame_QUEUE = "frame.quene";
	public static final String Frame_EXCHANGE = "frame.exchange";
	public static final String Frame_ROUTING = "frame.routing";
	
	@Value("${rabbitmq.username}")
	private String userName;
	
	@Value("${rabbitmq.password}")
	private String password;
	
	@Value("${rabbitmq.host}")
	private String host;
	
	@Value("${rabbitmq.port}")
	private int port;
	
	@Value("${rabbitmq.virtual-host}")
	private String virtualHost;
	

	@Bean(name = Frame_EXCHANGE)
	public Exchange directExchange() {
		/**
		 * 交换器可以声明持久化，name是交换器名称，durable:true 是持久化  autoDelete：false使用完不删除
		 * Quene也可以声明durable:true 
		 */
		return new DirectExchange(Frame_EXCHANGE, true, false);
	}

	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory, Exchange exchange) {
		RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
		rabbitAdmin.setAutoStartup(true);
		Queue queue = new Queue(Frame_QUEUE, true);
		rabbitAdmin.declareExchange(directExchange());
		rabbitAdmin.declareQueue(queue);
		Binding binding = BindingBuilder.bind(queue).to(exchange).with(Frame_ROUTING).noargs();
		rabbitAdmin.declareBinding(binding);
		return rabbitAdmin;
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory factory = new CachingConnectionFactory();
		factory.setUsername(userName);
		factory.setPassword(password);
		factory.setHost(host);
		factory.setPort(port);
		factory.setVirtualHost(virtualHost);
		return factory;
	}

	@Bean
	public StatefulRetryOperationsInterceptor statefulRetryOperationsInterceptorFactoryBean(
			RabbitTemplate rabbitTemplate) {
		StatefulRetryOperationsInterceptorFactoryBean factoryBean = new StatefulRetryOperationsInterceptorFactoryBean();
		/**
		 * 根据官方文档，添加自定义的setMessageRecoverer，通过工厂方法生成的StatefulRetryOperationsInterceptor 可以返回自定义的bean
		 * The recoverer should be able to return an object of the same type as
		 * the target object because its return value will be used to return to
		 * the caller in the case of a recovery.
		 */
		factoryBean.setMessageRecoverer(new FrameMessageRecover(rabbitTemplate));
		/**
		 * message必须进行转码，否则会报非法错误。没有尝试用其他方式加码，理论上是可行的
		 */
		factoryBean.setMessageKeyGenerator(message -> DigestUtils.md5DigestAsHex(message.getBody()));
		return factoryBean.getObject();
	}

	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(MessageConverter messageConverter,
			StatefulRetryOperationsInterceptor interceptor) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setTaskExecutor(new SimpleAsyncTaskExecutor("frame"));
		factory.setConnectionFactory(connectionFactory());
		factory.setConcurrentConsumers(1);
		factory.setMaxConcurrentConsumers(1);
		factory.setMessageConverter(messageConverter);
		factory.setPrefetchCount(1);
		factory.setAdviceChain(interceptor);
		factory.setMissingQueuesFatal(false);
		return factory;
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory factory, MessageConverter messageConverter) {
		RabbitTemplate template = new RabbitTemplate(factory);
		template.setExchange(Frame_EXCHANGE);
		template.setQueue(Frame_QUEUE);
		template.setRoutingKey(Frame_ROUTING);
		template.setMessageConverter(messageConverter);
		return template;
	}

	@Bean
	public Jackson2JsonMessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	private static class FrameMessageRecover extends RejectAndDontRequeueRecoverer {
		private final RabbitTemplate rabbitTemplate;
		private static final Log logger = LogFactory.getLog(FrameMessageRecover.class);

		public FrameMessageRecover(RabbitTemplate rabbitTemplate) {
			this.rabbitTemplate = rabbitTemplate;
		}

		@Override
		public void recover(Message message, Throwable cause) {
			logger.error("消息消费失败，重新放入队列末尾", cause);
			rabbitTemplate.send(message);
			super.recover(message, cause);
		}
	}

}
