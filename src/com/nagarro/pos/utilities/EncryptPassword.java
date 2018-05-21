package com.nagarro.pos.utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.nagarro.pos.constant.Constant;
import com.nagarro.pos.constant.MyDoc;
import com.nagarro.pos.exception.CustomException;

/**
 * encrypt password
 */
@MyDoc(author = Constant.AUTHOR, date = Constant.CREATION_DATE, currentRevision = 1)
@Component
public class EncryptPassword {

	static Properties prop = UserProperties.getProperties();

	/**
	 * Encrypt password in MD5 format
	 * 
	 * @param stringpassword
	 * @return encryptedString
	 */
	public String encPassword(String password) throws CustomException {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(Constant.MD5);
		} catch (final NoSuchAlgorithmException e) {
			throw new CustomException(prop.getProperty("EXCEP_PASS_ENCRYPT"));
		}
		md.update(password.getBytes());

		final byte[] byteData = md.digest();

		// convert the byte to hex format
		final StringBuilder hexString = new StringBuilder();
		for (final byte element : byteData) {
			final String hex = Integer.toHexString(0xff & element);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}

		return hexString.toString();
	}

}
