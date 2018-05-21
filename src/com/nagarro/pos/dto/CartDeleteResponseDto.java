package com.nagarro.pos.dto;

public class CartDeleteResponseDto {
	private String message;
	private boolean flag;

	
	public CartDeleteResponseDto(String message, boolean flag) {
		super();
		this.message = message;
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "CartDeleteResponseDto [message=" + message + ", flag=" + flag + "]";
	}

}
