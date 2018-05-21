package com.nagarro.pos.dao;

import com.nagarro.pos.exception.CustomException;
import com.nagarro.pos.model.Employee;

public interface CashDrawerDao {

	Employee getCashDrawer(int empId) throws CustomException;
}
