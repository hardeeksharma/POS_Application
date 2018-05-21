package com.nagarro.pos.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nagarro.pos.constant.Constant;
import com.nagarro.pos.constant.MyDoc;
import com.nagarro.pos.dao.CashDrawerDao;
import com.nagarro.pos.exception.CustomException;
import com.nagarro.pos.model.CashDrawer;

@MyDoc(author = Constant.AUTHOR, date = Constant.CREATION_DATE, currentRevision = 1)
@Service
public class CashDrawerService {

	final Logger logger = Logger.getLogger(CashDrawerService.class);

	@Autowired
	CashDrawerDao iCashDrawer;

	@Transactional
	public List<CashDrawer> getProducts(int empId) {
		try {
			return iCashDrawer.getCashDrawer(empId).getCashDrawer();
		} catch (final CustomException e) {
			e.printStackTrace();
		}
		return null;
	}
}
