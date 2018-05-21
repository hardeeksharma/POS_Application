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
import com.nagarro.pos.dao.ProductDao;
import com.nagarro.pos.model.Product;
import com.nagarro.pos.utilities.UserProperties;

@MyDoc(author = Constant.AUTHOR, date = Constant.CREATION_DATE, currentRevision = 1)
@Repository
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private SessionFactory sessionFactory;
	static Properties prop = UserProperties.getProperties();

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<Product> getProductsDB() {
		final Session session = getCurrentSession();
		final Query query = session.createQuery("from Product");

		return query.getResultList();
	}

	public Product getProductById(int pid) {
		return getCurrentSession().get(Product.class, pid);
	}

}
