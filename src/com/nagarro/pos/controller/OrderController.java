package com.nagarro.pos.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nagarro.pos.constant.Constant;
import com.nagarro.pos.constant.MyDoc;
import com.nagarro.pos.constant.OrderStatus;
import com.nagarro.pos.constant.PaymentType;
import com.nagarro.pos.dto.ErrorMessage;
import com.nagarro.pos.exception.CustomException;
import com.nagarro.pos.model.Employee;
import com.nagarro.pos.model.Orders;
import com.nagarro.pos.service.OrderService;

@MyDoc(author = Constant.AUTHOR, date = Constant.CREATION_DATE, currentRevision = 1)
@Controller
@RequestMapping(value = "/orders")
public class OrderController {

	final Logger logger = Logger.getLogger(OrderController.class);

	@Autowired
	OrderService ordersService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> postOrder(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @RequestParam("paymode") String paymode, @RequestParam("status") String status,
			@RequestParam("custId") String custId, @RequestParam("empId") String empId) {

		ResponseEntity<Object> responseEntity = null;
		try {
			final Orders orders = ordersService.saveOrder(PaymentType.valueOf(paymode.toUpperCase()),
					OrderStatus.valueOf(status.toUpperCase()), Integer.parseInt(custId), Integer.parseInt(empId));
			if (orders == null) {
				final ErrorMessage em = new ErrorMessage();
				em.setErrorMessage("Please login first");
				em.setFlag(false);
				responseEntity = ResponseEntity.ok().body(em);
			} else {
				responseEntity = ResponseEntity.ok().body(orders);

			}
		} catch (final CustomException e) {
			e.printStackTrace();
		}
		return responseEntity;

	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getAllOrders(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		final Employee currEmp = (Employee) session.getAttribute("emp");
		List<Orders> allOrdersList = null;
		try {
			allOrdersList = ordersService.getAllOrders(currEmp.getId());
		} catch (final CustomException e) {
			e.printStackTrace();
			final ErrorMessage em = new ErrorMessage();
			em.setErrorMessage("Please login first");
			em.setFlag(false);
			return ResponseEntity.ok().body(em);
		}
		return ResponseEntity.ok().body(allOrdersList);

	}

	@RequestMapping(value = "/savedorder", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getSavedOrder(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		List<Orders> savedOrderList = null;
		final Employee employee = (Employee) session.getAttribute("emp");
		System.out.println(employee.getId());
		System.out.println(OrderStatus.PENDING);
		try {
			savedOrderList = ordersService.getSavedOrder(employee.getId());
		} catch (final CustomException e) {
			e.printStackTrace();
			final ErrorMessage em = new ErrorMessage();
			em.setErrorMessage("Please login first");
			em.setFlag(false);
			return ResponseEntity.ok().body(em);
		}
		return ResponseEntity.ok().body(savedOrderList);
	}

	@RequestMapping(value = "/completeorder", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getCompleteOrder(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		List<Orders> completeOrderList = null;
		final Employee employee = (Employee) session.getAttribute("emp");

		try {
			completeOrderList = ordersService.getCompleteOrder(employee.getId());
		} catch (final CustomException e) {
			e.printStackTrace();
			final ErrorMessage em = new ErrorMessage();
			em.setErrorMessage("Please login first");
			em.setFlag(false);
			return ResponseEntity.ok().body(em);
		}
		return ResponseEntity.ok().body(completeOrderList);
	}

	@RequestMapping(value = "/savedorder/{oid}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getSavedOrderById(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @PathVariable("oid") String orderId) {

		Orders savedOrder = null;
		System.out.println(orderId);
		final Employee employee = (Employee) session.getAttribute("emp");

		try {
			savedOrder = ordersService.getSavedOrderById(orderId, employee.getId());
		} catch (final CustomException e) {
			e.printStackTrace();
			final ErrorMessage em = new ErrorMessage();
			em.setErrorMessage("Please login first");
			em.setFlag(false);
			return ResponseEntity.ok().body(em);
		}
		return ResponseEntity.ok().body(savedOrder);
	}

	@RequestMapping(value = "/savedorder/:oid/reload", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> reloadSavedOrder(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @RequestParam("oredrId") String orderId) {

		Orders savedOrder = null;
		final Employee employee = (Employee) session.getAttribute("emp");

		try {
			savedOrder = ordersService.getSavedOrderById(orderId, employee.getId());
		} catch (final CustomException e) {
			e.printStackTrace();
			final ErrorMessage em = new ErrorMessage();
			em.setErrorMessage("Please login first");
			em.setFlag(false);
			return ResponseEntity.ok().body(em);
		}
		return ResponseEntity.ok().body(savedOrder);
	}

}
