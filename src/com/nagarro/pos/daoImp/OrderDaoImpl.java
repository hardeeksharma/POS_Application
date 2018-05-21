package com.nagarro.pos.daoImp;

import java.util.List;
import java.util.Properties;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nagarro.pos.constant.Constant;
import com.nagarro.pos.constant.MyDoc;
import com.nagarro.pos.constant.OrderStatus;
import com.nagarro.pos.dao.OrderDao;
import com.nagarro.pos.exception.CustomException;
import com.nagarro.pos.model.Orders;
import com.nagarro.pos.model.OrdersProductMapper;
import com.nagarro.pos.utilities.UserProperties;

@MyDoc(author = Constant.AUTHOR, date = Constant.CREATION_DATE, currentRevision = 1)
@Repository
public class OrderDaoImpl implements OrderDao {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	static Properties prop = UserProperties.getProperties();

	@Override
	public Orders addOrder(Orders orders) {
		final String orderId = (String) getCurrentSession().save(orders);
		if (orderId != null) {
			final Query query = getCurrentSession().createQuery("from Orders where orderId=:oid");

			query.setParameter("oid", orderId);
			return (Orders) query.getSingleResult();

		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Orders> getSavedOrders(int empId) throws CustomException {

		final Session session = getCurrentSession();
		final List<Orders> savedOrderList = null;

		try {

			// Employee currEmp =

			// final Query query = session.createQuery("from Orders where status =:status
			// and employee_id =:empId");
			// query.setParameter("status", OrderStatus.PENDING);
			// query.setParameter("employee_id", empId);
			// query.setParameter("customer_id", custId);
			// savedOrderList = query.getResultList();
		} catch (final Exception e) {
			throw new CustomException(prop.getProperty("EXCEP_GETUSER_DAO"));
		}

		return savedOrderList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Orders> getCompleteOrder(int empId) throws CustomException {

		final Session session = getCurrentSession();
		List<Orders> completeOrderList = null;

		try {
			final Query query = session.createQuery("from Orders where status =:status and employee_id ");
			query.setParameter("status", OrderStatus.COMPLETE);
			query.setParameter("employee_id", empId);
			// query.setParameter("customer_id", custId);
			completeOrderList = query.getResultList();
		} catch (final Exception e) {
			throw new CustomException(prop.getProperty("EXCEP_GETUSER_DAO"));
		}

		return completeOrderList;

	}

	@Override
	public Orders getOrderById(String orderId, int empId) throws CustomException {
		final Session session = getCurrentSession();
		Orders savedOrder = null;

		try {
			final Query query = session.createQuery("from Orders where orderId =:orderId");
			query.setParameter("orderId", orderId);
			savedOrder = (Orders) query.getSingleResult();
		} catch (final Exception e) {
			throw new CustomException(prop.getProperty("EXCEP_GETUSER_DAO"));
		}

		return savedOrder;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Orders> getAllOrders(int empId) throws CustomException {
		final Session session = getCurrentSession();
		List<Orders> allOrdersList = null;
		try {
			final Query query = session.createQuery("from Orders where employee_id =:empId");
			query.setParameter("empId", empId);
			allOrdersList = query.getResultList();
		} catch (final Exception e) {
			throw new CustomException(prop.getProperty("EXCEP_GETUSER_DAO"));
		}
		return allOrdersList;
	}

	@Override
	public boolean addOrdersProductMappers(OrdersProductMapper ordersProductMapper) {
		boolean flag = false;
		try {
			getCurrentSession().save(ordersProductMapper);
			flag = true;
		} catch (final Exception e) {
			flag = false;
		}

		return flag;
	}

}