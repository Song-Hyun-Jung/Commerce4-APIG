package com.digital4.interceptor;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.digital4.service.AuthInterceptService;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor{ //Authorize가 된 상태인지 확인-controller 실행전에 먼저 실행됨
	@Resource
	AuthInterceptService authInterceptSvc;
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String token = request.getHeader("Authorization");
        log.info("preHandle: " + token);
        
        try {
        	
	        if (token == null) { //로그인으로 토큰을 받아서 인증하지 않고 접근했을때
	        	response.setContentType("application/json");
	        	response.setCharacterEncoding("UTF-8");
	        	response.getWriter().write("{\"errorCode\":\"401\",\"errorMsg\":\"Authorization 헤더가 없거나 잘못된 인증입니다.\"}");
	        	return false;
	        }
	    
	    
	        if(authInterceptSvc.isValidToken(Long.parseLong(token)) == false ) { //로그인 만료 확인
	        	response.setContentType("application/json");
	        	response.setCharacterEncoding("UTF-8");
	        	response.getWriter().write("{\"errorCode\":\"500\",\"errorMsg\":\"로그인이 만료되었습니다.\"}");
	        	return false;
	        }
	    
	      //토큰 생성시간 변경
	        if(authInterceptSvc.updateValidTime(Long.parseLong(token)) == true){ 
		         //토큰 생성시간 변경
		        System.out.println("접속시간 갱신"); //변경된 토큰 생성시간
	        }
	        else {
	        	response.setContentType("application/json");
	        	response.setCharacterEncoding("UTF-8");
	        	response.getWriter().write("{\"errorCode\":\"500\",\"errorMsg\":\"접속시간 갱신 실패\"}");
	        	return false;
	        }
        }
        catch(Exception e) {
        	throw e;
        }
        return true;
    }
   
}