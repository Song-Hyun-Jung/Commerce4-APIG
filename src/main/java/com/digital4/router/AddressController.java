package com.digital4.router;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.digital4.schema.Address;
import com.digital4.schema.ErrorMsg;
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
@Tag(name = "주소", description = "Address Related API")
@RequestMapping(value = "/rest/address")
public class AddressController {

	@RequestMapping(value = "/search/{addressDetail}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "주소 찾기 서비스", notes = "주소 검색하는 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Address.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> findAddress(@ApiParam(value = "주소") @PathVariable String addressDetail, HttpServletRequest req) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		ErrorMsg errors = new ErrorMsg();	
		try {
			String token = req.getHeader("Authorization");
		
			String url = "8082/rest/address/search";
			url += "/" + URLEncoder.encode(addressDetail, "UTF-8");
			String response = HttpConnectionUtils.getRequest(url, token);
			System.out.println("getRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			Address address = objectMapper.readValue(response, Address.class);
			
			return new ResponseEntity<Address>(address, header, HttpStatus.valueOf(200));  // ResponseEntity를 활용한 응답 생성
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}

	}
	@RequestMapping(value = "/searchById/{addressId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "주소Id로 주소 찾기 서비스", notes = "주소 검색하는 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Address.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> findAddressById(@ApiParam(value = "주소", example="0") @PathVariable long addressId, HttpServletRequest req) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
	
		ErrorMsg errors = new ErrorMsg();
		try {
			String token = req.getHeader("Authorization");
			
			String url = "8082/rest/address/searchById";
			url += "/" + addressId;
			String response = HttpConnectionUtils.getRequest(url, token);
			System.out.println("getRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			Address address = objectMapper.readValue(response, Address.class);
			
			return new ResponseEntity<Address>(address, header, HttpStatus.valueOf(200));  // ResponseEntity를 활용한 응답 생성
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}

	}
	
	
	@RequestMapping(value = "/insertAddress", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "주소 등록", notes = "주소등록을 위한 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Address.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> signUp(
			@Parameter(name = "주소 등록을 위한 정보", description = "", required = true) @RequestBody Address address, HttpServletRequest req) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		ErrorMsg errors = new ErrorMsg();
		Address addr_res = new Address();
		try {
			String token = req.getHeader("Authorization");
			String url = "8082/rest/address/insertAddress";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("addressId", address.getAddressId());
			map.put("addressDetail", address.getAddressDetail());
			String response = HttpConnectionUtils.postRequest(url, map, token);
			System.out.println("postRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			addr_res = objectMapper.readValue(response, Address.class);
			
			return new ResponseEntity<Address>(addr_res, header, HttpStatus.valueOf(200)); 
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}
		
	
	}
}