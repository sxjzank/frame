/**   
* @Title: PermissionServiceImpl.java 
* @Package com.frame.kangan.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author kang.an@ele.me   
* @date 2016年6月23日 上午11:01:38 
* @version V1.0   
*/
package com.frame.kangan.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.frame.kangan.service.IPermissionService;

/** 
* @ClassName: PermissionServiceImpl 

* @Description: TODO(这里用一句话描述这个类的作用) 

* @author kang.an@ele.me

* @date 2016年6月23日 上午11:01:38 
*  
*/
@Service
public class PermissionServiceImpl implements IPermissionService{

	/* (非 Javadoc) 
	* <p>Title: getUserPermissionByUserId</p> 
	* <p>Description: </p> 
	* @param userId
	* @return 
	* @see com.frame.kangan.service.IPermissionService#getUserPermissionByUserId(int) 
	*/
	@Override
	public Set<Integer> getUserPermissionByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (非 Javadoc) 
	* <p>Title: getUserPermissionByUserAccount</p> 
	* <p>Description: </p> 
	* @param account
	* @return 
	* @see com.frame.kangan.service.IPermissionService#getUserPermissionByUserAccount(java.lang.String) 
	*/
	@Override
	public Set<String> getUserPermissionByUserAccount(String account) {
		// TODO Auto-generated method stub
		Set<String> set = new HashSet<>();
		set.add("ADMIN");
		
		return set;
	}

}
