package com.nagarro.pos.daoImp;

import java.util.Properties;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nagarro.pos.constant.Constant;
import com.nagarro.pos.constant.MyDoc;
import com.nagarro.pos.dao.CashDrawerDao;
import com.nagarro.pos.exception.CustomException;
import com.nagarro.pos.model.Employee;
import com.nagarro.pos.utilities.UserProperties;

@MyDoc(author = Constant.AUTHOR, date = Constant.CREATION_DATE, currentRevision = 1)
@Repository
public class CashDrawerDaoImpl implements CashDrawerDao {

	@Autowired
	private SessionFactory sessionFactory;
	static Properties prop = UserProperties.getProperties();

	@Override
	public Employee getCashDrawer(int empId) throws CustomException {
		Employee emp = null;
		final Session session = sessionFactory.getCurrentSession();
		try {
			final Query query = session.createQuery("from Employee where id=:empId");

			query.setParameter("empId", empId);
			emp = (Employee) query.getSingleResult();
		} catch (final Exception e) {
			throw new CustomException(prop.getProperty("EXCEP_GETUSER_DAO"));
		}
		return emp;
	}

}
