package com.nagarro.pos.constant;

public enum PaymentType {
	CASH("CASH"), CARD("CARD");

	private final String type;

	private PaymentType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}
}
