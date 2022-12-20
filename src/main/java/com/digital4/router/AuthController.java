package com.digital4.router;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.digital4.schema.Auth;
import com.digital4.schema.ErrorMsg;
import com.digital4.schema.Product;
import com.digital4.utils.ExceptionUtils;
import com.digital4.utils.HttpConnectionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "인증", description = "Auth Related API")
@RequestMapping(value = "/rest/auth")
public class AuthController {
	
	@RequestMapping(value = "/tokenValid", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "토큰검증", notes = "토큰의 유효성을 검증")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공", response = Auth.class),
		@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class)
	})
	public ResponseEntity<?> tokenValid (@Parameter(name = "토큰", required = false) @RequestBody Auth inputAuth) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		ErrorMsg errors = new ErrorMsg();

		Auth auth_res = new Auth();
		try {
			String url = "8081/rest/auth/tokenValid";
			String response = HttpConnectionUtils.postRequest(url, inputAuth);
			
			System.out.println("postRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			auth_res = objectMapper.readValue(response, Auth.class);
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}
		
		return new ResponseEntity<Auth>(auth_res, header, HttpStatus.valueOf(200));
	}

	@RequestMapping(value = "/generateToken", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "토큰생성", notes = "로그인 정보를 사용하여 토큰을 생성")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공", response = Auth.class),
		@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class)
	})
	public ResponseEntity<?> generateToken (@Parameter(name = "토큰", required = false) @RequestBody Auth inputAuth) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		ErrorMsg errors = new ErrorMsg();

		Auth auth_res = new Auth();
		try {
			String url = "8081/rest/auth/generateToken";
			String response = HttpConnectionUtils.postRequest(url, inputAuth);
			
			System.out.println("postRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			auth_res = objectMapper.readValue(response, Auth.class);
			return new ResponseEntity<Auth>(auth_res, header, HttpStatus.valueOf(200));
		
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}
	
	}
	
	@RequestMapping(value = "/personInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "사용자 ID 확인", notes = "토큰 정보를 이용하여 사용자 ID를 가져와 확인한다.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공", response = Auth.class),
		@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class)
	})
	public ResponseEntity<?> getPersonId (@Parameter(name = "사용자 ID", required = false) @RequestBody Auth inputAuth) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		ErrorMsg errors = new ErrorMsg();

		Auth auth_res = new Auth();
		try {
			String url = "8081/rest/auth/personInfo";
			String response = HttpConnectionUtils.postRequest(url, inputAuth);
			
			System.out.println("postRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			auth_res = objectMapper.readValue(response, Auth.class);
			return new ResponseEntity<Auth>(auth_res, header, HttpStatus.valueOf(200));
			
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}
	}
	

	@RequestMapping(value = "/updateToken", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "접속시간 갱신", notes = "토큰에 저장되어있는 접속시간을 갱신")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공", response = Auth.class),
		@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class)
	})
	public ResponseEntity<?> updateToken (@Parameter(name = "토큰", required = false) @RequestBody Auth inputAuth) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		ErrorMsg errors = new ErrorMsg();

		Auth auth_res = new Auth();
		try {
			String url = "8081/rest/auth/updateToken";
			String response = HttpConnectionUtils.postRequest(url, inputAuth);
			
			System.out.println("postRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			auth_res = objectMapper.readValue(response, Auth.class);
			
			return new ResponseEntity<Auth>(auth_res, header, HttpStatus.valueOf(200));
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}
		
	}
	
}
