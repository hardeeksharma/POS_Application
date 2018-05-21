package com.nagarro.pos.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nagarro.pos.dto.CartDeleteResponseDto;
import com.nagarro.pos.dto.ErrorMessage;
import com.nagarro.pos.exception.CustomException;
import com.nagarro.pos.model.Cart;
import com.nagarro.pos.model.CartProductMapper;
import com.nagarro.pos.model.Product;
import com.nagarro.pos.service.CartService;

@Controller
@RequestMapping("/carts")
public class CartController {

	@Autowired
	CartService cartSevice;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Object> addProductToCart(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @RequestParam("pid") int pid, @RequestParam("custId") int custId) {
		Product product = null;
		try {
			product = cartSevice.addProductToCart(pid, custId);
		} catch (final Exception e) {
			e.printStackTrace();
			final ErrorMessage em = new ErrorMessage();
			em.setErrorMessage(e.getMessage());
			em.setFlag(false);
			return ResponseEntity.badRequest().body(em);
		}
		return ResponseEntity.ok().body(product);
	}

	@RequestMapping(value = "/customer/{custId}/product/{pid}/inc", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Object> increaseQuantity(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @PathVariable("pid") int pid, @PathVariable("custId") int custId) {
		CartProductMapper cartProductMapper = null;
		try {
			cartProductMapper = cartSevice.increaseQuantity(custId, pid);
		} catch (CustomException e) {
			final ErrorMessage em = new ErrorMessage();
			em.setErrorMessage(e.getMessage());
			em.setFlag(false);
			return ResponseEntity.badRequest().body(em);
		}

		return ResponseEntity.ok().body(cartProductMapper);
	}

	@RequestMapping(value = "/customer/{custId}/product/{pid}/dec", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Object> decreaseQuantity(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @PathVariable("pid") int pid, @PathVariable("custId") int custId) {
		CartProductMapper cartProductMapper = null;
		try {
			cartProductMapper = cartSevice.decreaseQuantity(custId, pid);
		} catch (CustomException e) {
			final ErrorMessage em = new ErrorMessage();
			em.setErrorMessage(e.getMessage());
			em.setFlag(false);
			return ResponseEntity.badRequest().body(em);
		}

		return ResponseEntity.ok().body(cartProductMapper);
	}

	@RequestMapping(value = "/empty/{custId}", method = RequestMethod.DELETE)
	ResponseEntity<Object> emptyCart(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@PathVariable("custId") int custId) {

		boolean flag = false;
		CartDeleteResponseDto dto = null;
		try {

			flag = cartSevice.removeCustomerCart(custId);
			if (flag)
				dto = new CartDeleteResponseDto("Cart Deleted Successfully", true);
		} catch (CustomException e) {
			e.printStackTrace();
			final ErrorMessage em = new ErrorMessage();
			em.setErrorMessage(e.getMessage());
			em.setFlag(false);
			return ResponseEntity.badRequest().body(em);
		}
		return ResponseEntity.ok().body(dto);
	}

	@RequestMapping(value = "/customer/{custId}", method = RequestMethod.GET)
	public ResponseEntity<Object> getCustometCart(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @PathVariable("custId") int custId) {

		Cart cart = null;
		try {
			cart = cartSevice.getCustomerCart(custId);
		} catch (CustomException e) {
			e.printStackTrace();
			final ErrorMessage em = new ErrorMessage();
			em.setErrorMessage(e.getMessage());
			em.setFlag(false);
			return ResponseEntity.badRequest().body(em);
		}

		return ResponseEntity.ok().body(cart);
	}

	@RequestMapping(value = "/customer/{custId}/product/{pid}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteProduct(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @PathVariable("pid") int pid, @PathVariable("custId") int custId) {

		boolean flag = false;
		CartDeleteResponseDto dto = null;
		try {
			flag = cartSevice.deleteProductFromCart(custId, pid);
			if(flag)
			dto = new CartDeleteResponseDto("Product Deleted from Cart", true);
		} catch (Exception e) {
			e.printStackTrace();
			final ErrorMessage em = new ErrorMessage();
			em.setErrorMessage(e.getMessage());
			em.setFlag(false);
			return ResponseEntity.badRequest().body(em);
		}
		return ResponseEntity.ok().body(dto);

	}

}
