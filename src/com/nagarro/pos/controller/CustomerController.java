package com.nagarro.pos.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nagarro.pos.dto.ErrorMessage;
import com.nagarro.pos.exception.CustomException;
import com.nagarro.pos.service.CustomerService;
import com.nagarro.pos.validator.Validator;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

	final Logger logger = Logger.getLogger(CustomerController.class);

	@Autowired
	CustomerService customerService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> searchCustomerList(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @RequestParam(value = "search", required = true) String toSearch) {

		try {
			Validator.validateField(toSearch);

		} catch (final CustomException e2) {
			logger.error(e2);
			final ErrorMessage em = new ErrorMessage();
			em.setErrorMessage("Some Error Occured");
			em.setFlag(false);
			return ResponseEntity.ok().body(em);
		}

		return ResponseEntity.ok().body(customerService.getCustomers(toSearch));

	}

}