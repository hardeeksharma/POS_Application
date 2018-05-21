package com.nagarro.pos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nagarro.pos.constant.Constant;
import com.nagarro.pos.constant.MyDoc;
import com.nagarro.pos.daoImp.CustomerDaoImpl;
import com.nagarro.pos.dto.CustomerSearchDto;
import com.nagarro.pos.model.Customer;

@MyDoc(author = Constant.AUTHOR, date = Constant.CREATION_DATE, currentRevision = 1)
@Service
public class CustomerService {
	@Autowired
	CustomerDaoImpl customerDaoImpl;

	@Transactional(readOnly = true)
	public Customer getCustomerById(int custId) {
		return customerDaoImpl.getCustomerById(custId);
	}

	@Transactional
	public List<CustomerSearchDto> getCustomers(String toSearch) {

		final List<CustomerSearchDto> customerDtoList = new ArrayList<>();
		for (final Customer c : customerDaoImpl.getCustomer(toSearch)) {
			final CustomerSearchDto customerSearchDto = new CustomerSearchDto(c.getId(), c.getFirstName(),
					c.getLastName(), c.getEmail(), c.getMobile());
			customerDtoList.add(customerSearchDto);
		}

		return customerDtoList;
	}
}
