package com.frame.kangan.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.frame.kangan.config.RabbitMQConfig;
import com.frame.kangan.data.po.FrameUser;

/** 
* @ClassName: test 

* @Description: TODO(这里用一句话描述这个类的作用) 

* @author kang.an@ele.me

* @date 2016年5月26日 上午10:53:26 
*  
*/
@Service
public class testMQ {

	@RabbitListener(queues = RabbitMQConfig.Frame_QUEUE)
	public void testMQsdf(FrameUser message) throws Exception{
		System.out.println(message);
	}
}
