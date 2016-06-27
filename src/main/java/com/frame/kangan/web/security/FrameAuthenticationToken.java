package com.frame.kangan.web.security;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

public class FrameAuthenticationToken implements AuthenticationToken, RememberMeAuthenticationToken {
    private static final long serialVersionUID = 6513052484070045160L;
//    private final Optional<String> coffeeToken;
    
    private String account;
    
    private String password;
    

    public FrameAuthenticationToken(String account,String password) {
        this.account = account;
        this.password = password;
    }

    @Override
    public String getPrincipal() {
        return account;
    }

    @Override
    public String getCredentials() {
    		return password;
    }

    @Override
    public boolean isRememberMe() {
        return true;
    }
    
    
    
    
}
