package com.seuic.hisense.utils;

import android.util.Base64;

import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class AndroidEncryptUtils {
	private static final String PUBLIC_KEY_STRING = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJck7VToPJAx7toRy6QID0WpKyUW3O7WxzVVqHq9mrTxJYVrof0xUSvk/zGS/x1BJbbKnSD43XSqa5+7o3/hYNsCAwEAAQ==";
	private static PublicKey publicKey;

	public static String Md5(String password) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes("UTF-8"));
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
			return buf.toString();// 32位加密
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String encryRSA(String data) {
		try {
			if (publicKey == null) {
				publicKey = getPublicKey(PUBLIC_KEY_STRING);
			}
            String dataUTF8 = new String(data.getBytes("UTF-8"),"UTF-8");
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] encode = Base64.encode(cipher.doFinal(dataUTF8.getBytes("utf-8")), Base64.DEFAULT);
			return new String(encode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到公钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	private static PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = Base64.decode(key.getBytes(), Base64.DEFAULT);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

}
