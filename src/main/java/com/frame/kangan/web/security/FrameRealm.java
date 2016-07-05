/**   
* @Title: FrameRealm.java 
* @Package com.frame.kangan.web.security 
* @Description: TODO(用一句话描述该文件做什么) 
* @author kang.an@ele.me   
* @date 2016年6月13日 下午3:57:25 
* @version V1.0   
*/
package com.frame.kangan.web.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.frame.kangan.data.po.FrameUser;
import com.frame.kangan.service.IPermissionService;
import com.frame.kangan.service.IUserService;

/** 
* @ClassName: FrameRealm 

* @Description: TODO(这里用一句话描述这个类的作用) 

* @author kang.an@ele.me

* @date 2016年6月13日 下午3:57:25 
*  
*/
public class FrameRealm  extends AuthorizingRealm{
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IPermissionService permissionService;
	

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	
	  private static final Log logger = LogFactory.getLog(AuthorizingRealm.class);
	
    @Override
    protected void onInit() {
        super.onInit();
        super.setCacheManager(new MemoryConstrainedCacheManager());
    }
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		String account = (String)principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setStringPermissions(permissionService.getUserPermissionByUserAccount(account));
		authorizationInfo.setRoles(userService.getUserRolesByUserAccount(account));
		return authorizationInfo;
		
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String account = (String)token.getPrincipal();
		String password = (String)token.getCredentials();
		FrameUser user = userService.getUserByAccountAndPwd(account, password);
		if (user == null) {
			logger.error("没找到帐号"+account);
			throw new UnknownAccountException("没找到帐号");// 没找到帐号
		}
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(account,password,getName());
		return authenticationInfo;
	}
	
	 @Override
	public CredentialsMatcher getCredentialsMatcher() {
		return (token, info) -> {
			FrameAuthenticationToken upToken = (FrameAuthenticationToken) token;
			String account = upToken.getPrincipal();
			String password = upToken.getCredentials();
			int times = 0;
			if (null != redisTemplate.opsForValue().get(account)) {
				times = (Integer) redisTemplate.opsForValue().get(account);
			}
			/**
			 * TODO 这里应该写一个checktoken的方法，理论上不应该使用账号密码，应该使用序列化的token
			 */
			FrameUser user = userService.getUserByAccountAndPwd(account, password);
			if (user == null) {
				logger.error("没找到帐号"+account);
				times = times +1;
			}else{
				//清除登录失败的次数
				times = 0;
			}
			if (times > 5) {
				logger.error(account + "尝试登录次数大于5次");
				 throw new ExcessiveAttemptsException();  
//				throw new AuthenticationException("10分钟内尝试登录次数最多为5次");
			}
			redisTemplate.opsForValue().set(account, times);
			return true;
		};
	}
	
	@Override
	public boolean supports(AuthenticationToken token) {
	    return token instanceof FrameAuthenticationToken;
	}

}
