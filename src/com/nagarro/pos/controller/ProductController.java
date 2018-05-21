package com.nagarro.pos.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nagarro.pos.constant.Constant;
import com.nagarro.pos.constant.MyDoc;
import com.nagarro.pos.model.Product;
import com.nagarro.pos.service.ProductService;

@MyDoc(author = Constant.AUTHOR, date = Constant.CREATION_DATE, currentRevision = 1)
@Controller
@RequestMapping(value = "/products")
public class ProductController {

	final Logger logger = Logger.getLogger(OrderController.class);

	@Autowired
	ProductService productService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Product>> getProducts(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		return ResponseEntity.ok().body(productService.getProducts());
	}
}
