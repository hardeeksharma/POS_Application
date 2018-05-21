package com.nagarro.pos.constant;

public enum OrderStatus {
	COMPLETE("COMPLETE"), PENDING("PENDING");
	private final String type;

	private OrderStatus(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}

}
