/**   
* @Title: userServiceImpl.java 
* @Package com.frame.kangan.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author kang.an@ele.me   
* @date 2016年6月23日 上午11:00:54 
* @version V1.0   
*/
package com.frame.kangan.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frame.kangan.data.mapper.FrameUserMapper;
import com.frame.kangan.data.po.FrameUser;
import com.frame.kangan.service.IUserService;

/** 
* @ClassName: userServiceImpl 

* @Description: TODO(这里用一句话描述这个类的作用) 

* @author kang.an@ele.me

* @date 2016年6月23日 上午11:00:54 
*  
*/
@Service
public class UserServiceImpl implements IUserService{

	
	@Autowired
	private FrameUserMapper frameUserMapper;
	/* (非 Javadoc) 
	* <p>Title: getUserRolesByUserId</p> 
	* <p>Description: </p> 
	* @param userId
	* @return 
	* @see com.frame.kangan.service.IUserService#getUserRolesByUserId(int) 
	*/
	@Override
	public Set<String> getUserRolesByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (非 Javadoc) 
	* <p>Title: getUserRolesByUserAccount</p> 
	* <p>Description: </p> 
	* @param account
	* @return 
	* @see com.frame.kangan.service.IUserService#getUserRolesByUserAccount(java.lang.String) 
	*/
	@Override
	public Set<String> getUserRolesByUserAccount(String account) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (非 Javadoc) 
	* <p>Title: getUserByAccountAndPwd</p> 
	* <p>Description: </p> 
	* @param account
	* @param password
	* @return 
	* @see com.frame.kangan.service.IUserService#getUserByAccountAndPwd(java.lang.String, java.lang.String) 
	*/
	@Override
	public FrameUser getUserByAccountAndPwd(String account, String password) {
		return frameUserMapper.getUserByAccount(account, password);
	}

}
