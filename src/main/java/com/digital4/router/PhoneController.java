package com.digital4.router;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RestController;

import com.digital4.schema.Address;
import com.digital4.schema.ErrorMsg;
import com.digital4.schema.Phone;
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
@Tag(name = "전화번호", description = "Phone Related API")
@RequestMapping(value = "/rest/phone")
public class PhoneController {

	@RequestMapping(value = "/search/{phoneNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "전화번호 찾기 서비스", notes = "전화번호 검색하는 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Phone.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> findPhone(@ApiParam(value = "전화번호") @PathVariable String phoneNumber, HttpServletRequest req) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();

		ErrorMsg errors = new ErrorMsg();
		
		try {
			String token = req.getHeader("Authorization");
			
			String url = "8082/rest/phone/search";
			url += "/" + URLEncoder.encode(phoneNumber, "UTF-8");
			String response = HttpConnectionUtils.getRequest(url, token);
			System.out.println("getRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			Phone phone = objectMapper.readValue(response, Phone.class);
			
			return new ResponseEntity<Phone>(phone, header, HttpStatus.valueOf(200));  // ResponseEntity를 활용한 응답 생성
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}

	}
	
	@RequestMapping(value = "/searchById/{phoneId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "전화번호Id로 전화번호 찾기 서비스", notes = "전화번호 검색하는 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Phone.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> findPhoneById(@ApiParam(value = "전화번호", example="0") @PathVariable long phoneId, HttpServletRequest req) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();

		ErrorMsg errors = new ErrorMsg();
		
		try {
			String token = req.getHeader("Authorization");
			
			String url = "8082/rest/phone/searchById";
			url += "/" + phoneId;
			String response = HttpConnectionUtils.getRequest(url, token);
			System.out.println("getRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			Phone phone = objectMapper.readValue(response, Phone.class);
			
			return new ResponseEntity<Phone>(phone, header, HttpStatus.valueOf(200));  // ResponseEntity를 활용한 응답 생성
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}

	}
	
	@RequestMapping(value = "/insertPhone", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "전화번호 등록", notes = "전화번호등록을 위한 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Phone.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> signUp(
			@Parameter(name = "전화번호 등록을 위한 정보", description = "", required = true) @RequestBody Phone phone, HttpServletRequest req) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		ErrorMsg errors = new ErrorMsg();
		Phone phone_res = new Phone();
		try {
			String token = req.getHeader("Authorization");
			String url = "8082/rest/phone/insertPhone";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("phoneId",phone.getPhoneId());
			map.put("phoneNumber", phone.getPhoneNumber());
			String response = HttpConnectionUtils.postRequest(url, map, token);
			System.out.println("postRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			phone_res = objectMapper.readValue(response, Phone.class);
			
			return new ResponseEntity<Phone>(phone_res, header, HttpStatus.valueOf(200)); // ResponseEntity를 활용한 응답 생성
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}
		
		
	}
}
