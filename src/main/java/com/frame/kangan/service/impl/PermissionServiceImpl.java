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
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frame.kangan.data.mapper.FrameUserMapper;
import com.frame.kangan.data.mapper.FrameUserPermissionMapper;
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

	@Autowired
	private FrameUserPermissionMapper frameUserPermissionMapper;
	
	@Autowired
	private FrameUserMapper frameUserMapper;
	
	@Override
	public Set<String> getUserPermissionCodeByUserId(int userId) {
		
		List<String> list = frameUserPermissionMapper.getPermissionCodeSetByUserId(userId);
		Set<String> set = new HashSet<String>();
		for(String permissionCode :list){
			set.add(permissionCode);
		}
		return set;
	}

	@Override
	public Set<String> getUserPermissionCodeByUserAccount(String account) {
		// TODO Auto-generated method stub
		int userId = frameUserMapper.getUserIdByAccount(account);
		if(userId == 0){
			return new HashSet<String>();
		}
		Set<String> set  = getUserPermissionCodeByUserId(userId);
		return set;
	}

}
