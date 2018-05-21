package com.nagarro.pos.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nagarro.pos.constant.Constant;
import com.nagarro.pos.constant.MyDoc;
import com.nagarro.pos.dto.ErrorMessage;
import com.nagarro.pos.model.Employee;
import com.nagarro.pos.service.CashDrawerService;

@MyDoc(author = Constant.AUTHOR, date = Constant.CREATION_DATE, currentRevision = 1)
@Controller
@RequestMapping(value = "/cashdrawer")
public class CashDrawerController {

	final Logger logger = Logger.getLogger(CashDrawerController.class);

	@Autowired
	CashDrawerService cashDrawerService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getCashDrawer(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		if ((Employee) session.getAttribute("emp") == null) {
			final ErrorMessage em = new ErrorMessage();
			em.setErrorMessage("Please login first");
			em.setFlag(false);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(em);
		}
		final Employee emp = (Employee) session.getAttribute("emp");
		return ResponseEntity.ok().body(cashDrawerService.getProducts(emp.getId()));
	}

}
