package com.digital4.router;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.digital4.schema.ErrorMsg;
import com.digital4.schema.Inventory;
import com.digital4.schema.Prepurchase;
import com.digital4.schema.Product;
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
@Tag(name = "주문상세", description = "Prepurchase Related API")
@RequestMapping(value = "/rest/prepurchase")
public class PrepurchaseController {
	
	@RequestMapping(value = "/purchaseDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "주문 상세 입력", notes = "주문 상세 입력을 위한 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Prepurchase.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> purchaseDetail(
			@Parameter(name = "주문 상세 입력", description = "", required = true) HttpServletRequest req, @RequestBody Prepurchase prepurchase) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		ErrorMsg errors = new ErrorMsg();
		
		Prepurchase prepurchase_res;
		try {
			String token = req.getHeader("Authorization");
			String targetUrl = "Order";
			String requestUrl = "/rest/prepurchase/purchaseDetail";
			String response = HttpConnectionUtils.postRequest(targetUrl, requestUrl, prepurchase, token);
			
			System.out.println("postRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			prepurchase_res = objectMapper.readValue(response, Prepurchase.class);
			
			return new ResponseEntity<Prepurchase>(prepurchase_res, header, HttpStatus.valueOf(200));
			
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}

	}
	
	//prepurchaseId로 주문 상세 조회
	@RequestMapping(value = "/getPrepurchase/{keyword}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "prepurchaseId로 주문 상세 조회", notes = "주문 상세 조회하는 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Prepurchase.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> getPrepurchase(@ApiParam(value = "prepurchaseId") @PathVariable String keyword, HttpServletRequest req) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();

		ErrorMsg errors = new ErrorMsg();
		Prepurchase prepurchase_res = new Prepurchase();
		try {
			String token = req.getHeader("Authorization");
			String targetUrl = "Order";
			String requestUrl = "/rest/prepurchase/getPrepurchase";
			requestUrl += "/" + URLEncoder.encode(keyword, "UTF-8");
			String response = HttpConnectionUtils.getRequest(targetUrl, requestUrl, token);
			System.out.println("getRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			prepurchase_res = objectMapper.readValue(response, Prepurchase.class);
			
			return new ResponseEntity<Prepurchase>(prepurchase_res, header, HttpStatus.valueOf(200));  // ResponseEntity를 활용한 응답 생성
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}

	}
	
	
	//personId로 전체 주문상세 조회
	@RequestMapping(value = "/myPrepurchase", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "전체 주문상세 확인", notes = "고객의 전체 주문상세를 확인하는 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Prepurchase.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> getPurchaseHistory(HttpServletRequest req) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		
		ErrorMsg errors = new ErrorMsg();
		
		try {
			String token = req.getHeader("Authorization");
			String targetUrl = "Order";
			String requestUrl = "/rest/prepurchase/myPrepurchase"; //매개변수 없음
			String response = HttpConnectionUtils.getRequest(targetUrl, requestUrl, token);
			System.out.println("getRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			List<Prepurchase> prepurchaseList = Arrays.asList(objectMapper.readValue(response, Prepurchase [].class));
			
			return new ResponseEntity<List<Prepurchase>>(prepurchaseList, header, HttpStatus.valueOf(200));  // ResponseEntity를 활용한 응답 생성
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}

	}
	
}