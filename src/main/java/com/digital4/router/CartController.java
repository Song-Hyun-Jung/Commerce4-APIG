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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.digital4.schema.Cart;
import com.digital4.schema.ErrorMsg;
import com.digital4.schema.Inventory;
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
@Tag(name = "장바구니", description = "Cart Related API")
@RequestMapping(value = "/rest/cart")
public class CartController {
	
	@RequestMapping(value = "/insertCart", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "장바구니 추가", notes = "장바구니에 추가를 위한 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Cart.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> insertCart(
			@Parameter(name = "장바구니에 상품을 추가", description = "", required = true) @RequestBody Cart cart, HttpServletRequest req) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		ErrorMsg errors = new ErrorMsg();
		Cart cart_res = new Cart();
		try {
			String token = req.getHeader("Authorization");
			String url = "8085/rest/cart/insertCart";
			String response = HttpConnectionUtils.postRequest(url, cart, token);
			
			System.out.println("postRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			cart_res = objectMapper.readValue(response, Cart.class);
			
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}
		
		return new ResponseEntity<Cart>(cart_res, header, HttpStatus.valueOf(200)); // ResponseEntity를 활용한 응답 생성
	}
	
	@RequestMapping(value = "/getCart", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "장바구니 조회", notes = "장바구니에 담긴 전체 상품을 조회하는 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Cart.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> getCart(HttpServletRequest req) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();

		ErrorMsg errors = new ErrorMsg();
		
		try {
			String token = req.getHeader("Authorization");
			String url = "8085/rest/cart/getCart"; //파라미터 없음
			
			String response = HttpConnectionUtils.getRequest(url, token);
			System.out.println("getRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			List<Cart> cartList = Arrays.asList(objectMapper.readValue(response, Cart [].class));
		
			return new ResponseEntity<List<Cart>>(cartList, header, HttpStatus.valueOf(200));  // ResponseEntity를 활용한 응답 생성
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}

	}
	
	
}

