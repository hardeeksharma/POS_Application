package com.nagarro.pos.exception;

import com.nagarro.pos.constant.Constant;
import com.nagarro.pos.constant.MyDoc;

@MyDoc(author = Constant.AUTHOR, date = Constant.CREATION_DATE, currentRevision = 1)
public class CustomException extends Exception {

	private static final long serialVersionUID = 192003551293800393L;

	public CustomException(String s) {
		super(s);
	}
}
