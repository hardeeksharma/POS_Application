package com.nagarro.pos.dao;

import com.nagarro.pos.exception.CustomException;
import com.nagarro.pos.model.CashDrawer;
import com.nagarro.pos.model.Employee;
import com.nagarro.pos.model.UserSecret;

public interface EmployeeDao {

	Employee getUser(String email) throws CustomException;

	UserSecret getPass(int id) throws CustomException;

	void addCashDrawer(CashDrawer cashDrawer) throws CustomException;

	void updateEmployee(Employee employee) throws CustomException;

	Employee getEmployeeById(int empId) throws CustomException;

}
