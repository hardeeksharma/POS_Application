package com.nagarro.pos.dao;

import com.nagarro.pos.exception.CustomException;
import com.nagarro.pos.model.Cart;
import com.nagarro.pos.model.CartProductMapper;

public interface CartDao {

	public boolean addProductToCart(Cart cart);

	public boolean removeCart(Cart cart);

	public Cart getCartById(int custId);

	boolean addCartProducttoMapper(CartProductMapper cartProductMapper);

	public CartProductMapper increaseQuantity(int cartId,int pid) throws CustomException;
	
	public CartProductMapper decreaseQuantity(int cartId,int pid) throws CustomException;
	
}
