package com.digital4.router;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digital4.schema.SuccessMsg;
import com.digital4.schema.Address;
import com.digital4.schema.Auth;
import com.digital4.schema.ErrorMsg;
import com.digital4.schema.LogIn;
import com.digital4.schema.Person;
import com.digital4.utils.ExceptionUtils;
import com.digital4.utils.HttpConnectionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "고객", description = "Person Related API")
@RequestMapping(value = "/rest/person")

public class PersonController {
	
	@RequestMapping(value = "/logIn", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "로그인", notes = "로그인을 위한 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Person.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> logIn(
			@Parameter(name = "로그인을 위한 정보", description = "", required = true) HttpServletRequest req, @RequestBody LogIn login) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		ErrorMsg errors = new ErrorMsg();
		try {
			String url = "8082/rest/person/logIn";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("loginId", login.getLoginId());
			map.put("password", login.getPassword());
			String response = HttpConnectionUtils.postRequest(url, map);
			System.out.println("postRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			Auth auth = objectMapper.readValue(response, Auth.class); //로그인->Auth객체 받아옴
			Long token = auth.getToken();
			//로그인 성공시 token, personId 반환
			return new ResponseEntity<SuccessMsg>(new SuccessMsg("로그인 하였습니다. token: " + token), header, HttpStatus.valueOf(200));
		
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}
	}
	
	@RequestMapping(value = "/searchByLoginId/{loginId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "회원가입 여부 검색", notes = "로그인id로 회원가입 여부 검색하는 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Person.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> findPerson2(@ApiParam(value = "로그인아이디") @PathVariable String loginId, HttpServletRequest req) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();

		ErrorMsg errors = new ErrorMsg();
		
		try {
			String token = req.getHeader("Authorization");
			String url = "8082/rest/person/searchByLoginId";
			url += "/" + URLEncoder.encode(loginId, "UTF-8");
			String response = HttpConnectionUtils.getRequest(url, token);
			System.out.println("getRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			Person person = objectMapper.readValue(response, Person.class);
			
			return new ResponseEntity<Person>(person, header, HttpStatus.valueOf(200)); // ResponseEntity를 활용한 응답 생성
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}

	}
	
	@RequestMapping(value = "/signUp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "회원가입", notes = "회원가입을 위한 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Person.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> signUp(
			@Parameter(name = "회원가입을 위한 고객정보", description = "", required = true) @RequestBody Person person) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		ErrorMsg errors = new ErrorMsg();
		Person person_res = new Person();
		try {
			String url = "8082/rest/person/signUp";
			String response = HttpConnectionUtils.postRequest(url, person);
			
			System.out.println("postRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			person_res = objectMapper.readValue(response, Person.class);
			
			return new ResponseEntity<Person>(person_res, header, HttpStatus.valueOf(200));
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}
		
		
	}
}
