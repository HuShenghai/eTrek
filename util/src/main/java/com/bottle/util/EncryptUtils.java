package com.bottle.util;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtils {

	/**
	 * 获取文件的MD5值
	 * @param file
	 * @return
	 */
	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest;
		FileInputStream fileInputStream;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			fileInputStream = new FileInputStream(file);
			len = fileInputStream.read(buffer, 0, 1024);
			while (len != -1) {
				digest.update(buffer, 0, len);
				fileInputStream.read(buffer, 0, 1024);
			}
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return bytesToHexString(digest.digest());
	}

	private static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

}
