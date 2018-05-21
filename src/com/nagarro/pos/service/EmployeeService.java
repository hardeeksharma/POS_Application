package com.nagarro.pos.service;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nagarro.pos.constant.Constant;
import com.nagarro.pos.constant.MyDoc;
import com.nagarro.pos.dao.EmployeeDao;
import com.nagarro.pos.exception.CustomException;
import com.nagarro.pos.model.CashDrawer;
import com.nagarro.pos.model.Employee;
import com.nagarro.pos.model.UserSecret;

@MyDoc(author = Constant.AUTHOR, date = Constant.CREATION_DATE, currentRevision = 1)
@Service
public class EmployeeService {

	final Logger logger = Logger.getLogger(EmployeeService.class);

	@Autowired
	EmployeeDao iEmployee;

	@Transactional
	public Employee checkUser(String email, String password) {
		Employee emp = null;
		try {
			final Employee currEmp = iEmployee.getUser(email);
			final UserSecret userSecret = iEmployee.getPass(currEmp.getId());
			if (currEmp != null && userSecret != null) {
				if (userSecret.getPass().equals(password)) {
					emp = currEmp;
				}

			}
		} catch (final CustomException e) {
			logger.error(e);
		}

		return emp;
	}

	@Transactional
	public boolean addCashDrawer(int startBal, Employee emp) {

		boolean isAdded = false;
		final CashDrawer cashDrawer = new CashDrawer();
		cashDrawer.setEmployee(emp);
		cashDrawer.setStartBal(startBal);
		cashDrawer.setEndBal(startBal);
		cashDrawer.setDate(new Date());
		cashDrawer.setCreated(new Date());
		cashDrawer.setUpdated(new Date());
		emp.getCashDrawer().add(cashDrawer);
		emp.setCreated(new Date());
		emp.setUpdated(new Date());
		try {
			iEmployee.addCashDrawer(cashDrawer);
			iEmployee.updateEmployee(emp);
			isAdded = true;
		} catch (final CustomException e) {
			logger.error(e);
		}
		return isAdded;

	}

	@Transactional
	public Employee getEmployeeById(int empId) throws CustomException {
		return iEmployee.getEmployeeById(empId);
	}

}
