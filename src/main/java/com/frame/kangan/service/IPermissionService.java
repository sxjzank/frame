/**   
* @Title: IPermissionService.java 
* @Package com.frame.kangan.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author kang.an@ele.me   
* @date 2016年6月22日 下午5:25:11 
* @version V1.0   
*/
package com.frame.kangan.service;

import java.util.Set;

/** 
* @ClassName: IPermissionService 

* @Description: TODO(这里用一句话描述这个类的作用) 

* @author kang.an@ele.me

* @date 2016年6月22日 下午5:25:11 
*  
*/
public interface IPermissionService {
	
	Set<Integer> getUserPermissionByUserId(int userId);

	Set<String> getUserPermissionByUserAccount(String account);
}
