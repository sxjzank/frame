/**   
* @Title: TestController.java 
* @Package com.frame.kangan.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author kang.an@ele.me   
* @date 2016年5月31日 下午3:24:27 
* @version V1.0   
*/
package com.frame.kangan.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.frame.kangan.data.mapper.FrameImageMapper;
import com.frame.kangan.data.mapper.FrameUserMapper;
import com.frame.kangan.data.mapper.FrameUserPermissionMapper;
import com.frame.kangan.data.po.FrameUser;
import com.google.gson.Gson;

/** 
* @ClassName: TestController 

* @Description: TODO(这里用一句话描述这个类的作用) 

* @author kang.an@ele.me

* @date 2016年5月31日 下午3:24:27 
*  
*/
@RestController
@EnableCaching
public class TestController {
	
    private static final Log logger = LogFactory.getLog(TestController.class);
	
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	
	@Autowired
	private FrameImageMapper frameImageMapper;
	
	@Autowired
	private FrameUserMapper frameUserMapper;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private FrameUserPermissionMapper frameUserPermissionMapper;
	
	
	@RequestMapping("/test")
	@ResponseBody
	public String testRedis(){
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		redisTemplate.opsForValue().set(list, list);
		Gson g = new Gson();
		return g.toJson(redisTemplate.opsForValue().get(456456));
	}
	
	
	@RequestMapping("/test2")
	@ResponseBody
	@Cacheable(value = "cache",key="'hello'")  
	public String testRedis2(){
		String a ="1231ee";
		 System.out.println("无缓存的时候调用这里");  
		return a.length()+"";
	}
	
	@RequestMapping("/test3")
	@ResponseBody
	public String testMybatis(){
		Gson g = new Gson();
//		return g.toJson(frameImageMapper.selectTest());
		Subject subject = SecurityUtils.getSubject();
		System.out.println(subject.getPrincipal());
		return g.toJson(frameImageMapper.selectById((long)1));
	}
	
	@RequestMapping("/test4")
	@ResponseBody
	public void testIndex(){
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public void login(HttpServletRequest request,HttpServletResponse res) throws Exception{
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String error = null;
		Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
		try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            error = "用户名/密码错误";
        } catch (IncorrectCredentialsException e) {
            error = "用户名/密码错误";
        } catch (AuthenticationException e) {
            //其他错误，比如锁定，如果想单独处理请单独catch处理
            error = "其他错误：" + e.getMessage();
        }

        if(error != null) {//出错了，返回登录页面
        } else {//登录成功
        	Cookie cookie = new Cookie("account",account);
    		res.addCookie(cookie);
    		res.sendRedirect("/index");
        }
	}
	
	@RequestMapping("/logout")
	@ResponseBody
	public void logout(HttpServletRequest request,HttpServletResponse res) throws Exception{
		logger.debug("logout");
		SecurityUtils.getSubject().logout(); 
		res.sendRedirect("/userLogin.html");
	}
	
	
	@RequestMapping("/index")
	@ResponseBody
	public void index(String account,String password){
		logger.debug("woshi log");
		redisTemplate.opsForSet().add("ank","123","4456");
		redisTemplate.opsForSet().add("kang", "123","2222");
		Set<Object> set = redisTemplate.opsForSet().intersect("ank", "kang");
		FrameUser user = frameUserMapper.getUserByAccount("ankang","ankang2");
		System.out.println();
	}
	
	
	@RequestMapping("/testRabbitMq")
	@ResponseBody
	public void testRabbitMq(){
		FrameUser user = new FrameUser();
		user.setAccount("!23");
		user.setId(1);
		rabbitTemplate.convertAndSend(user);
	}
	
	@RequestMapping("/testPermission")
	@ResponseBody
	public void testPermission(){
		System.out.println(frameUserPermissionMapper.getPermissionCodeSetByUserId(1));
	}
	
}
