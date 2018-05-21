package com.nagarro.pos.validator;

import java.util.Properties;

import com.nagarro.pos.constant.Constant;
import com.nagarro.pos.constant.MyDoc;
import com.nagarro.pos.exception.CustomException;
import com.nagarro.pos.utilities.UserProperties;

/*
 * Validator class used for validating the input.
 */
@MyDoc(author = Constant.AUTHOR, date = Constant.CREATION_DATE, currentRevision = 1)
public class Validator {
	static Properties prop = UserProperties.getProperties();

	private Validator() {
	}

	/* validate field entered by user */
	public static void validateField(String field) throws CustomException {
		if (field == null || field.isEmpty()) {
			throw new CustomException(prop.getProperty("EXCEP_FIELD_EMPTY"));
		}
	}
}
