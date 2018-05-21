package com.nagarro.pos.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nagarro.pos.constant.Constant;
import com.nagarro.pos.constant.MyDoc;
import com.nagarro.pos.constant.OrderStatus;
import com.nagarro.pos.constant.PaymentType;
import com.nagarro.pos.dao.CartDao;
import com.nagarro.pos.dao.CartProductMapperDao;
import com.nagarro.pos.dao.EmployeeDao;
import com.nagarro.pos.dao.OrderDao;
import com.nagarro.pos.exception.CustomException;
import com.nagarro.pos.model.Cart;
import com.nagarro.pos.model.CartProductMapper;
import com.nagarro.pos.model.CashDrawer;
import com.nagarro.pos.model.Customer;
import com.nagarro.pos.model.Employee;
import com.nagarro.pos.model.Orders;
import com.nagarro.pos.model.OrdersProductMapper;

@MyDoc(author = Constant.AUTHOR, date = Constant.CREATION_DATE, currentRevision = 1)
@Service
public class OrderService {

	@Autowired
	CustomerService customerService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	OrderDao iOrder;

	@Autowired
	CartDao iCart;

	@Autowired
	CartProductMapperDao iCartProductMapper;

	@Autowired
	EmployeeDao iEmployee;

	final Logger logger = Logger.getLogger(OrderService.class);

	@Transactional
	public Orders saveOrder(PaymentType paymentType, OrderStatus orderStatus, int custId, int empId)
			throws CustomException {
		float cartTotalPrice = 0;
		final Customer customer = customerService.getCustomerById(custId);
		final Employee employee = employeeService.getEmployeeById(empId);
		final Orders orders = new Orders();
		orders.setCreated(new Date());
		orders.setUpdated(new Date());
		orders.setCustomer(customer);
		orders.setEmployee(employee);
		orders.setOrderDate(new Date());
		orders.setPaymentType(paymentType);
		orders.setOrderStatus(orderStatus);

		for (final CartProductMapper cartProductMapper : customer.getCart().getCartProductMapper()) {

			// orders.getProducts().add(cartProductMapper.getProduct());
			final OrdersProductMapper ordersProductMapper = new OrdersProductMapper();
			ordersProductMapper.setOrders(orders);
			ordersProductMapper.setProduct(cartProductMapper.getProduct());
			ordersProductMapper.setQuantity(cartProductMapper.getQuantity());

			orders.getOrdersProductMappers().add(ordersProductMapper);
			cartProductMapper.getProduct().getOrdersProductMappers().add(ordersProductMapper);
			iOrder.addOrdersProductMappers(ordersProductMapper);

			if (orderStatus.COMPLETE.equals(OrderStatus.COMPLETE)) {
				cartTotalPrice += cartProductMapper.getProduct().getPrice();
			}
		}

		final Orders createdOrder = iOrder.addOrder(orders);

		employee.getOrder().add(createdOrder);

		final Cart cart = customer.getCart();
		cart.setCustomer(null);
		customer.setCart(null);
		for (final CartProductMapper cartProductMapper : cart.getCartProductMapper()) {
			iCartProductMapper.removeCartProductMapper(cartProductMapper);
		}

		iCart.removeCart(cart);
		final CashDrawer cashDrawer = employee.getCashDrawer().get(employee.getCashDrawer().size() - 1);
		cashDrawer.setEndBal(cashDrawer.getEndBal() + cartTotalPrice);

		return createdOrder;
	}

	@Transactional
	public List<Orders> getSavedOrder(int empId) throws CustomException {
		final Employee currEmp = iEmployee.getEmployeeById(empId);
		final List<Orders> savedOrder = new ArrayList<>();
		final List<Orders> allOrders = currEmp.getOrder();
		for (final Orders currOrder : allOrders) {
			if (currOrder.getOrderStatus().equals(OrderStatus.PENDING)) {
				savedOrder.add(currOrder);
			}
		}
		return savedOrder;

	}

	@Transactional
	public List<Orders> getCompleteOrder(int empId) throws CustomException {
		final Employee currEmp = iEmployee.getEmployeeById(empId);
		final List<Orders> completeOrders = new ArrayList<>();
		final List<Orders> allOrders = currEmp.getOrder();
		for (final Orders currOrder : allOrders) {
			if (currOrder.getOrderStatus().equals(OrderStatus.COMPLETE)) {
				completeOrders.add(currOrder);
			}
		}
		return completeOrders;
	}

	@Transactional
	public Orders getSavedOrderById(String orderId, int empId) throws CustomException {
		final Employee currEmp = iEmployee.getEmployeeById(empId);
		Orders order = null;
		final List<Orders> allOrders = currEmp.getOrder();
		for (final Orders currOrder : allOrders) {
			if (currOrder.getOrderStatus().equals(OrderStatus.PENDING)
					&& currOrder.getOrderId().equalsIgnoreCase(orderId)) {
				order = currOrder;
				break;
			}
		}
		return order;
	}

	@Transactional
	public List<Orders> getAllOrders(int empId) throws CustomException {

		final Employee currEmp = iEmployee.getEmployeeById(empId);
		return currEmp.getOrder();
	}

}
