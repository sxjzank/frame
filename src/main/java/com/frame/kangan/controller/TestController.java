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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.frame.kangan.data.mapper.FrameImageMapper;
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
	
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	
	@Autowired
	private FrameImageMapper frameImageMapper;
	
	
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
		return g.toJson(frameImageMapper.selectById((long)1));
	}
	
	@RequestMapping("/test4")
	@ResponseBody
	public void testIndex(){
		
	}
	
	
	
	
}
