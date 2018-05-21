package com.nagarro.pos.utilities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class GenerateOrderId implements IdentifierGenerator {

	public int generateTicketId() {
		final Random random = new Random();
		return random.nextInt(1000000);
	}

	@Override
	public Serializable generate(SharedSessionContractImplementor arg0, Object arg1) throws HibernateException {
		final Calendar calendar = Calendar.getInstance();
		return "ORDER_" + this.generateTicketId() + "_" + calendar.get(Calendar.YEAR);
	}

}
