package com.bottle.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtils {
	
	private static final String ENCRYPT_KEY = "2xIV4bSDaVy5uvaahKUeTsoWKq1%PA96mzPc$X";

	/**
	 * 
	 * @Title: getMD5  
	 * @Description: 积木二代加密  
	 * @param @param val
	 * @param @param bit
	 * @param @return      
	 * @return String  
	 * @throws
	 */
	public static String getMD5(String val, int bit) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(val.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			if (bit == 32) {
				return buf.toString().toUpperCase();
			} else {
				return buf.toString().substring(8, 24).toUpperCase();
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

}
