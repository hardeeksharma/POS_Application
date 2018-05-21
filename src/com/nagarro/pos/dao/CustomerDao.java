package com.nagarro.pos.dao;

import java.util.List;

import com.nagarro.pos.model.Customer;

public interface CustomerDao {

	public Customer getCustomerById(int custId);

	List<Customer> getCustomer(String toSearch);

}
