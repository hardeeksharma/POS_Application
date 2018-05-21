package com.nagarro.pos.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nagarro.pos.constant.Constant;
import com.nagarro.pos.constant.MyDoc;

@MyDoc(author = Constant.AUTHOR, date = Constant.CREATION_DATE, currentRevision = 1)
@Entity
public class Product extends AbstractTimestampEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5094159898920442975L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String pcode;
	private String name;
	private int stock;
	private String description;
	private float price;

	/*
	 * @JsonIgnore
	 * 
	 * @ManyToMany(mappedBy = "products") private Set<Cart> cart = new HashSet<>();
	 */

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private Set<CartProductMapper> cartProductMapper = new HashSet<>();

	/*
	 * @JsonIgnore
	 * 
	 * @LazyCollection(LazyCollectionOption.FALSE)
	 * 
	 * @ManyToMany(cascade = CascadeType.ALL, mappedBy = "products") private
	 * Set<Orders> order = new HashSet<>();
	 */
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "product")
	private final Set<OrdersProductMapper> ordersProductMappers = new HashSet<>();

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<CartProductMapper> getCartProductMapper() {
		return cartProductMapper;
	}

	public void setCartProductMapper(Set<CartProductMapper> cartProductMapper) {
		this.cartProductMapper = cartProductMapper;
	}

	public Set<OrdersProductMapper> getOrdersProductMappers() {
		return ordersProductMappers;
	}

}
