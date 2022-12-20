 package com.digital4.router;

import java.net.URLEncoder;

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
@Tag(name = "재고", description = "Inventory Related API")
@RequestMapping(value = "/rest/inventory")

public class InventoryController {
	
	@RequestMapping(value = "/search/{productName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "상품 이름으로 재고 검색", notes = "상품이름로 인벤토리를 검색하는 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Inventory.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> findInventory(@ApiParam(value = "상품이름") @PathVariable String productName, HttpServletRequest req) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();

		ErrorMsg errors = new ErrorMsg();
		
		try {
			String token = req.getHeader("Authorization");
			String url = "8084/rest/inventory/search";
			url += "/" + URLEncoder.encode(productName, "UTF-8");
			String response = HttpConnectionUtils.getRequest(url, token);
			System.out.println("getRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			Inventory inventory = objectMapper.readValue(response, Inventory.class);
			return new ResponseEntity<Inventory>(inventory, header, HttpStatus.valueOf(200));  
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}

	}

	@RequestMapping(value = "/searchById/{productId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "상품Id으로 재고 검색", notes = "상품Id로 인벤토리를 검색하는 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Inventory.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> findInventoryById(@ApiParam(value = "상품ID") @PathVariable String productId, HttpServletRequest req) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();

		ErrorMsg errors = new ErrorMsg();
		
		try {
			String token = req.getHeader("Authorization");
			String url = "8084/rest/inventory/searchById";
			url += "/" + URLEncoder.encode(productId, "UTF-8");
			String response = HttpConnectionUtils.getRequest(url, token);
			System.out.println("getRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			Inventory inventory = objectMapper.readValue(response, Inventory.class);
			return new ResponseEntity<Inventory>(inventory, header, HttpStatus.valueOf(200));  // ResponseEntity를 활용한 응답 생성
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}

	}
	
	@RequestMapping(value = "/updateQuantity", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "재고관리", notes = "재고관리를 위한 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Inventory.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> updateQuantity(
			@Parameter(name = "재고관리를 위한 정보", description = "", required = true) @RequestBody Inventory inventory, HttpServletRequest req) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		ErrorMsg errors = new ErrorMsg();
		Inventory inven_res = new Inventory();
		try {
			String token = req.getHeader("Authorization");
			String url = "8084/rest/inventory/updateQuantity";
			String response = HttpConnectionUtils.postRequest(url, inventory, token);
			
			System.out.println("postRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			inven_res = objectMapper.readValue(response, Inventory.class);
			
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}
		
		return new ResponseEntity<Inventory>(inven_res, header, HttpStatus.valueOf(200)); // ResponseEntity를 활용한 응답 생성
	}
	
	@RequestMapping(value = "/insertInventory", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "재고등록", notes = "재고등록을 위한 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Inventory.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> insertInventory(
			@Parameter(name = "재고등록을 위한 정보", description = "", required = true) @RequestBody Inventory inventory, HttpServletRequest req) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		ErrorMsg errors = new ErrorMsg();
		Inventory inven_res = new Inventory();
		try {
			String token = req.getHeader("Authorization");
			String url = "8084/rest/inventory/insertInventory";
			String response = HttpConnectionUtils.postRequest(url, inventory, token);
			
			System.out.println("postRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			inven_res = objectMapper.readValue(response, Inventory.class);
			
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}
		
		return new ResponseEntity<Inventory>(inven_res, header, HttpStatus.valueOf(200)); // ResponseEntity를 활용한 응답 생성
	}
	
	
	@RequestMapping(value = "/purchaseInven", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "구매로 인한 재고 감소", notes = "구매로 인한 재고 감소를 위한 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Inventory.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> subtractQuantity(
			@Parameter(name = "구매를 위한 구매수량 정보", description = "", required = true) @RequestBody Inventory inventory, HttpServletRequest req) {//인벤토리아이디, 구매수량이 들어간 객체(인벤토리아이디, 인벤토리 수량이 아님)
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		ErrorMsg errors = new ErrorMsg();
		Inventory inven_res = new Inventory();
		try {
			String token = req.getHeader("Authorization");
			String url = "8084/rest/inventory/purchaseInven";
			String response = HttpConnectionUtils.postRequest(url, inventory, token);
			
			System.out.println("postRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			inven_res = objectMapper.readValue(response, Inventory.class);
			
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}
		
		return new ResponseEntity<Inventory>(inven_res, header, HttpStatus.valueOf(200)); // ResponseEntity를 활용한 응답 생성
	}
}