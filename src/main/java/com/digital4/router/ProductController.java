package com.digital4.router;

import java.lang.reflect.Array;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.digital4.schema.ErrorMsg;
import com.digital4.schema.Person;
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
@Tag(name = "상품", description = "Product Related API")
@RequestMapping(value = "/rest/product")

public class ProductController {
	
	@RequestMapping(value = "/insertProduct", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "상품등록", notes = "상품등록을 위한 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Product.class),
					@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> insertProduct(
			@Parameter(name = "상품 등록을 위한 카테고리 정보", description = "", required = true) @RequestBody Product product, HttpServletRequest request){ 
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		ErrorMsg errors = new ErrorMsg();
		
		Product product_res = new Product();
		String token = request.getHeader("Authorization");
		try {
			String url = "8083/rest/product/insertProduct";
			String response = HttpConnectionUtils.postRequest(url, product, token);
			
			System.out.println("postRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			product_res = objectMapper.readValue(response, Product.class);
			
			return new ResponseEntity<Product>(product_res, header, HttpStatus.valueOf(200));
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}
		
		 // ResponseEntity를 활용한 응답 생성
	}
	
	//관리자 검색-상품 이름
	@RequestMapping(value = "/search/product/{productName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "관리자 상품 검색", notes = "관리자가 상품 검색하는 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Product.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> findProduct(@ApiParam(value = "상품명") @PathVariable String productName, HttpServletRequest request) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();

		ErrorMsg errors = new ErrorMsg();
		
		try {
			String token = request.getHeader("Authorization");
			String url = "8083/rest/product/search/product";
			url += "/" + URLEncoder.encode(productName, "UTF-8");
			String response = HttpConnectionUtils.getRequest(url, token);
			System.out.println("getRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			Product product = objectMapper.readValue(response, Product.class);
			return new ResponseEntity<Product>(product, header, HttpStatus.valueOf(200));  
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}

	}	
	
	//관리자 검색-상품ID
	@RequestMapping(value = "/searchById/product/{productId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "관리자 상품Id로 상품 검색", notes = "관리자가 상품Id로 상품 검색하는 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Product.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> findProductById(@ApiParam(value = "0", example="0") @PathVariable long productId, HttpServletRequest request) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();

		ErrorMsg errors = new ErrorMsg();
			
		try {
			String token = request.getHeader("Authorization");
			String url = "8083/rest/product/searchById/product";
			url += "/" + productId;
			String response = HttpConnectionUtils.getRequest(url, token);
			System.out.println("getRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			Product product = objectMapper.readValue(response, Product.class);
			return new ResponseEntity<Product>(product, header, HttpStatus.valueOf(200)); 
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}

	}

	
	//상품 구매자 검색-키워드가 포함된 모든 상품 검색
	@RequestMapping(value = "/searchByKeyword/{keyword}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "고객 상품 키워드 검색", notes = "고객이 상품을 키워드로 검색하는 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Product.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> searchByKeyword(@ApiParam(value = "검색 키워드") @PathVariable String keyword, HttpServletRequest request) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();

		ErrorMsg errors = new ErrorMsg();
		
		try {
			String token = request.getHeader("Authorization");
			String url = "8083/rest/product/searchByKeyword";
			url += "/" + URLEncoder.encode(keyword, "UTF-8");
			String response = HttpConnectionUtils.getRequest(url, token);
			System.out.println("getRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			List<Product> product = Arrays.asList(objectMapper.readValue(response, Product [].class));
			
			return new ResponseEntity<List<Product>>(product, header, HttpStatus.valueOf(200));  // ResponseEntity를 활용한 응답 생성
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}

	}
	
	@RequestMapping(value = "/searchByCategory/{categoryName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "고객 상품 카테고리별 검색", notes = "고객이 상품을 카테고리별로 검색하는 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Product.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> searchByCategory(@ApiParam(value = "카테고리명") @PathVariable String categoryName, HttpServletRequest request) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();

		ErrorMsg errors = new ErrorMsg();
		
		try {
			String token = request.getHeader("Authorization");
			String url = "8083/rest/product/searchByCategory";
			url += "/" + URLEncoder.encode(categoryName, "UTF-8");
			String response = HttpConnectionUtils.getRequest(url, token);
			System.out.println("getRequest:" + response);
			ObjectMapper objectMapper = new ObjectMapper();
			List<Product> productList = Arrays.asList(objectMapper.readValue(response, Product [].class));
			return new ResponseEntity<List<Product>>(productList, header, HttpStatus.valueOf(200));  // ResponseEntity를 활용한 응답 생성
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}

	}
	
}
