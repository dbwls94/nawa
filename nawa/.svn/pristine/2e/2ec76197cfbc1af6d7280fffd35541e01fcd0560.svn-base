package org.nawa.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha512 {
	public static String encrypt(String plainText) {
		String tempPassword = "";
		MessageDigest md = null;

		try {
			if (md == null)
				md = MessageDigest.getInstance("SHA-512");
			md.update(plainText.getBytes());
			byte[] mb = md.digest();
			for (int i = 0; i < mb.length; i++) {
				byte temp = mb[i];
				String s = Integer.toHexString(new Byte(temp));
				while (s.length() < 2) {
					s = "0" + s;
				}
				s = s.substring(s.length() - 2);
				tempPassword += s;
			}
			return tempPassword;
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	} // sha512
} // class