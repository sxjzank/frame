package com.frame.kangan.web.security;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.frame.kangan.data.po.FrameUser;
import com.frame.kangan.service.IUserService;
/**   
 *    
 * Project: shiro   
 * Class: RetryLimitHashedCredentialsMatcher   
 * Description: 
 * @author: Cheney   
 * @Date 2016年6月30日 下午9:35:50
 * @version 1.0
 *    
 */
public class FrameRetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

	@Autowired
	private IUserService userService;
	
    private Cache<String, AtomicInteger> passwordRetryCache;
    
    public FrameRetryLimitHashedCredentialsMatcher(EhCacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
    		FrameAuthenticationToken upToken = (FrameAuthenticationToken) token;
    		String account = upToken.getPrincipal();
		String password = upToken.getCredentials();
        //retry count + 1
        AtomicInteger retryCount = passwordRetryCache.get(account);
        if(retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(account, retryCount);
        }
        if(retryCount.incrementAndGet() > 5) {
            //if retry count > 5 throw
            throw new ExcessiveAttemptsException();
        }
        FrameUser user = userService.getUserByAccountAndPwd(account, password);
//        boolean matches = super.doCredentialsMatch(upToken, info);
        if(user!=null) {
            //clear retry count
            passwordRetryCache.remove(account);
            return true;
        }
        return false;
    }
}