package com.digital4.router;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.digital4.schema.Prepurchase;
import com.digital4.schema.Purchase;
import com.digital4.schema.PurchaseResult;
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
@Tag(name = "구매", description = "Purchase Related API")
@RequestMapping(value = "/rest/purchase")
public class PurchaseController {

	//주문
	@RequestMapping(value = "/purchaseResult", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "주문", notes = "주문을 위한 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = PurchaseResult.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> purchaseResult(
			@Parameter(name = "주문 상세 입력", description = "", required = true)  HttpServletRequest req, @RequestBody Purchase purchase) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		ErrorMsg errors = new ErrorMsg();
		
		PurchaseResult purchaseResult_res;
		try {
			String token = req.getHeader("Authorization");
			String targetUrl = "Order";
			String requestUrl = "/rest/purchase/purchaseResult";
			String response = HttpConnectionUtils.postRequest(targetUrl, requestUrl, purchase, token);
			
			System.out.println("postRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			purchaseResult_res = objectMapper.readValue(response, PurchaseResult.class);
			
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}
		
		return new ResponseEntity<PurchaseResult>(purchaseResult_res, header, HttpStatus.valueOf(200)); // ResponseEntity를 활용한 응답 생성
	}
	
	
	//주문 내역 확인
	@RequestMapping(value = "/purchaseHistory", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "주문내역 확인", notes = "고객 주문내역을 확인하는 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Purchase.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> getPurchaseHistory(HttpServletRequest req) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		
		ErrorMsg errors = new ErrorMsg();
		
		try {
			String token = req.getHeader("Authorization");
			String targetUrl = "Order";
			String requestUrl = "/rest/purchase/purchaseHistory"; //매개변수 없음
			String response = HttpConnectionUtils.getRequest(targetUrl, requestUrl, token);
			System.out.println("getRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			List<Purchase> purchaseList = Arrays.asList(objectMapper.readValue(response, Purchase [].class));
			
			return new ResponseEntity<List<Purchase>>(purchaseList, header, HttpStatus.valueOf(200));  // ResponseEntity를 활용한 응답 생성
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}

	}
	
}
