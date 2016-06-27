/**   
* @Title: IUserService.java 
* @Package com.frame.kangan.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author kang.an@ele.me   
* @date 2016年6月22日 下午5:25:20 
* @version V1.0   
*/
package com.frame.kangan.service;

import java.util.Set;

import com.frame.kangan.data.po.FrameUser;

/** 
* @ClassName: IUserService 

* @Description: TODO(这里用一句话描述这个类的作用) 

* @author kang.an@ele.me

* @date 2016年6月22日 下午5:25:20 
*  
*/
public interface IUserService {
	
	Set<String> getUserRolesByUserId(int userId); 
	
	Set<String> getUserRolesByUserAccount(String account); 
	
	FrameUser getUserByAccountAndPwd(String account,String password);
	
}
