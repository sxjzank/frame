/**   
* @Title: SecurityFilterBeanConfig.java 
* @Package com.frame.kangan.config 
* @Description: TODO(用一句话描述该文件做什么) 
* @author kang.an@ele.me   
* @date 2016年6月13日 下午1:54:24 
* @version V1.0   
*/
package com.frame.kangan.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.Filter;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.frame.kangan.web.security.FrameAuthenticatingFilter;
import com.frame.kangan.web.security.FrameRealm;

/** 
* @ClassName: SecurityFilterBeanConfig 

* @Description: TODO(这里用一句话描述这个类的作用) 

* @author kang.an@ele.me

* @date 2016年6月13日 下午1:54:24 
*  
*/
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class SecurityFilterBeanConfig {


	@Bean
	public FilterRegistrationBean delegatingFilterProxy() throws Exception {
		DelegatingFilterProxy filterProxy = new DelegatingFilterProxy();
		filterProxy.setTargetFilterLifecycle(true);
		filterProxy.setTargetBeanName("shiroFilter");
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.setFilter(filterProxy);
		return bean;
	}

	@Bean(name = "shiroFilter")
	public AbstractShiroFilter shiroFilter(SecurityManager securityManager) throws Exception {
	    ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
	    shiroFilter.setSecurityManager(securityManager);
	    FrameAuthenticatingFilter frameAuthenticatingFilter = new FrameAuthenticatingFilter();
	    frameAuthenticatingFilter.setLoginUrl("/userLogin.html");
	    shiroFilter.setLoginUrl("/userLogin.html");
	    shiroFilter.setSuccessUrl("/index");
//	    shiroFilter.setUnauthorizedUrl("/forbidden");
	    
	    Map<String, Filter> filters =shiroFilter.getFilters();
	    filters.put(DefaultFilter.authc.name(), frameAuthenticatingFilter);//鉴权
//	    filters.put("anon", new AnonymousFilter());//游客
//	    filters.put("logout", new LogoutFilter());
//	    filters.put("roles", new RolesAuthorizationFilter());//角色
//	    filters.put("user", new UserFilter());//用户
        filters.put(DefaultFilter.perms.name(), new HttpMethodPermissionFilter());

	    
	    Map<String, String> filterChainDefinitionMapping = new HashMap<String, String>();
	    try {
	    	InputStream inputStream = getClass().getResourceAsStream("/pathPermission.properties");
//			InputStream inputStream = ResourceUtils.getInputStreamForPath("pathPermission.properties");
			Properties prop = new Properties();
			prop.load(inputStream);
			for(Object name:prop.keySet().toArray()){  
				filterChainDefinitionMapping.put(name.toString(), prop.getProperty(name.toString()));  
            }  
		}catch (IOException e) {  
	        e.printStackTrace();  
	    }
//	    filterChainDefinitionMapping.put("/", "anon");
//	    filterChainDefinitionMapping.put("/test3", "authc,roles[guest]");
//	    filterChainDefinitionMapping.put("/userLogin.html", "anon");
//	    filterChainDefinitionMapping.put("/file.html", "authc,roles[admin]");
//	    filterChainDefinitionMapping.put("/admin", "authc,roles[admin]");
	    shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMapping);
	

	    return  (AbstractShiroFilter) shiroFilter.getObject();
	}

	@Bean(name = "securityManager")
	public SecurityManager securityManager(FrameRealm frameRealm) {
	    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
	    securityManager.setRealm(frameRealm);
	    return securityManager;
	}

	@Bean(name = "frameRealm")
	public FrameRealm FrameRealm() {
		FrameRealm realm = new FrameRealm();
	    return realm;
	}


	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
	    return new LifecycleBeanPostProcessor();
	}
	
	

}
