package com.nagarro.pos.service;

import java.util.Date;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nagarro.pos.constant.Constant;
import com.nagarro.pos.constant.MyDoc;
import com.nagarro.pos.dao.CartDao;
import com.nagarro.pos.dao.CartProductMapperDao;
import com.nagarro.pos.dao.CustomerDao;
import com.nagarro.pos.exception.CustomException;
import com.nagarro.pos.model.Cart;
import com.nagarro.pos.model.CartProductMapper;
import com.nagarro.pos.model.Customer;
import com.nagarro.pos.model.Product;

@MyDoc(author = Constant.AUTHOR, date = Constant.CREATION_DATE, currentRevision = 1)
@Service
public class CartService {

	final Logger logger = Logger.getLogger(CartService.class);

	@Autowired
	CartDao iCart;

	@Autowired
	ProductService productService;

	@Autowired
	CustomerService iCustomer;

	@Autowired
	CartProductMapperDao iCartProductMapper;

	@Transactional(rollbackFor = Exception.class)
	public Product addProductToCart(int pid, int custId) throws Exception {

		final long ret = -1;
		Product product = null;
		try {
			product = productService.getProductById(pid);
			final Customer customer = iCustomer.getCustomerById(custId);
			Cart newCartData = customer.getCart();
			if (newCartData == null) {
				newCartData = new Cart();
			}

			CartProductMapper existingProduct = newCartData.getCartProductMapper().stream()
					.filter(p -> p.getProduct().getId() == pid).findAny().orElse(null);
			if (existingProduct != null) {
				existingProduct.setQuantity(existingProduct.getQuantity() + 1);
				newCartData.setUpdated(new Date());
			} else {

				final CartProductMapper cartProductMapper = new CartProductMapper();

				newCartData.setCustomer(customer);

				newCartData.getCartProductMapper().add(cartProductMapper);
				product.getCartProductMapper().add(cartProductMapper);

				cartProductMapper.setCart(newCartData);
				cartProductMapper.setProduct(product);
				cartProductMapper.setQuantity(1);

				customer.setCart(newCartData);

				customer.setCreated(new Date());
				customer.setUpdated(new Date());
				newCartData.setCreated(new Date());
				newCartData.setUpdated(new Date());

				iCart.addProductToCart(newCartData);
				iCart.addCartProducttoMapper(cartProductMapper);
			}
		} catch (final Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return product;
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean removeCustomerCart(int custId) throws CustomException {
		Customer cust = iCustomer.getCustomerById(custId);

		if (cust.getCart() == null)
			throw new CustomException("No cart Exist for this customer");

		final Cart cart = cust.getCart();
		cart.setCustomer(null);
		cust.setCart(null);
		for (final CartProductMapper cartProductMapper : cart.getCartProductMapper()) {
			iCartProductMapper.removeCartProductMapper(cartProductMapper);
		}

		return iCart.removeCart(cart);
	}

	@Transactional
	public CartProductMapper increaseQuantity(int custId, int pid) throws CustomException {

		CartProductMapper cartProductMapper = null;
		try {

			Customer cust = iCustomer.getCustomerById(custId);
			int customerCartId = cust.getCart().getId();
			cartProductMapper = iCart.increaseQuantity(customerCartId, pid);
		} catch (final CustomException e) {
			logger.error(e);
			e.printStackTrace();
			throw new CustomException(e.getMessage());
		}

		return cartProductMapper;
	}

	@Transactional
	public CartProductMapper decreaseQuantity(int custId, int pid) throws CustomException {

		CartProductMapper cartProductMapper = null;
		try {
			Customer cust = iCustomer.getCustomerById(custId);
			int customerCartId = cust.getCart().getId();
			cartProductMapper = iCart.decreaseQuantity(customerCartId, pid);
		} catch (final CustomException e) {
			logger.error(e);
			e.printStackTrace();
			throw new CustomException(e.getMessage());
		}
		return cartProductMapper;
	}

	@Transactional
	public Cart getCustomerCart(int custId) throws CustomException {
		Cart cart = null;
		try {
			Customer cust = iCustomer.getCustomerById(custId);
			cart = cust.getCart();
			if (cart == null) {
				throw new CustomException("No Cart Exist for this Customer");
			}
		} catch (CustomException e) {
			logger.error(e);
			throw new CustomException(e.getMessage());
		}

		return cart;

	}

	@Transactional
	public boolean deleteProductFromCart(int custId, int pid) throws CustomException {
		Cart cart = null;
		try {
			Customer cust = iCustomer.getCustomerById(custId);
			cart = cust.getCart();
			if (cart == null) {
				throw new CustomException("No Cart Exist for this Customer");
			}

			CartProductMapper prod = cart.getCartProductMapper().stream().filter(p -> p.getProduct().getId() == pid)
					.findAny().orElse(null);

			if (prod == null)
				throw new CustomException("Product does not exist in the cart");

			iCartProductMapper.removeCartProductMapper(prod);

		} catch (CustomException e) {
			logger.error(e);
			throw new CustomException(e.getMessage());
		}

		return true;
	}
}
