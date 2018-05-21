package com.nagarro.pos.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nagarro.pos.constant.Constant;
import com.nagarro.pos.constant.MyDoc;
import com.nagarro.pos.dto.EmployeeLoginDto;
import com.nagarro.pos.dto.ErrorMessage;
import com.nagarro.pos.exception.CustomException;
import com.nagarro.pos.model.Employee;
import com.nagarro.pos.service.EmployeeService;
import com.nagarro.pos.validator.Validator;

@MyDoc(author = Constant.AUTHOR, date = Constant.CREATION_DATE, currentRevision = 1)
@Controller
@RequestMapping(value = "/employees")
public class EmployeeController {

	final Logger logger = Logger.getLogger(EmployeeController.class);

	@Autowired
	EmployeeService employeeService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> empLogin(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @ModelAttribute("employee") Employee employee,
			@RequestParam("startbal") String startBal) {

		try {
			System.out.println(startBal);
			Validator.validateField(employee.getEmail());
			Validator.validateField(employee.getUserSecret().getPass());

		} catch (final CustomException e2) {
			logger.error(e2);
			final ErrorMessage em = new ErrorMessage();
			em.setErrorMessage("Some Error Occured");
			em.setFlag(false);
			return ResponseEntity.ok().body(em);
		}

		final Employee emp = employeeService.checkUser(employee.getEmail(), employee.getUserSecret().getPass());
		if (emp != null) {
			employeeService.addCashDrawer(Integer.parseInt(startBal), emp);
			session.setAttribute(Constant.USER, emp);
			final EmployeeLoginDto employeeLoginDto = new EmployeeLoginDto(emp.getId(), emp.getFirstName(),
					emp.getLastName(), emp.getEmail(), emp.getMobile());
			return ResponseEntity.ok().body(employeeLoginDto);

		}
		final ErrorMessage em = new ErrorMessage();
		em.setErrorMessage("Some Error Occured");
		em.setFlag(false);
		return ResponseEntity.ok().body(em);

	}

}
