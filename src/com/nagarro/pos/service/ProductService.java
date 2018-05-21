package com.nagarro.pos.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nagarro.pos.constant.Constant;
import com.nagarro.pos.constant.MyDoc;
import com.nagarro.pos.dao.ProductDao;
import com.nagarro.pos.daoImp.ProductDaoImpl;
import com.nagarro.pos.model.Product;

@MyDoc(author = Constant.AUTHOR, date = Constant.CREATION_DATE, currentRevision = 1)
@Service
public class ProductService {

	final Logger logger = Logger.getLogger(ProductService.class);

	@Autowired
	ProductDao iProduct;

	@Autowired
	ProductDaoImpl dao;

	@Transactional
	public List<Product> getProducts() {
		return iProduct.getProductsDB();
	}

	public Product getProductById(int pid) {
		return dao.getProductById(pid);
	}

}
