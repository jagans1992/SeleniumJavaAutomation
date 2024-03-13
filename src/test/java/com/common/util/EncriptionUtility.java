package com.common.util;

import java.net.URLDecoder;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.testng.Reporter;

public class EncriptionUtility {
	private static String SECRET_KEY = "YourSecretKey123MSSSS"; // Replace with your secret key
	private static String KEY_DASH = "NDDUDMKMSMDKDkmdlkfmekfef";
	private static String ALGORITHM = "AES";
	private static String ENCODING = "UTF-8";
	private static String CIPHER_VALUE = "AES/ECB/PKCS5PADDING";
	
	private static String getKey() {
		if(SECRET_KEY.length() >16) {
			SECRET_KEY = SECRET_KEY.substring(0,16);
		} else {
			SECRET_KEY = SECRET_KEY + KEY_DASH.substring(0, 16 - SECRET_KEY.length());
		}
		return SECRET_KEY;
	}

	public static String encrypt(String valueToEncrypt) {
		String encryptedString = "";
		try {
			SecretKeySpec secretKey = new SecretKeySpec(EncriptionUtility.getKey().getBytes(ENCODING), ALGORITHM);
			Cipher cipher = Cipher.getInstance(CIPHER_VALUE);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] encryptedBytes = cipher.doFinal(valueToEncrypt.getBytes());
			encryptedString = new String(Base64.getEncoder().encodeToString(encryptedBytes));
		} catch (Exception e) {
			Reporter.log("Error Occured " + e.getMessage());
		}
		return encryptedString;
	}
	
	public static String decrypt(String valueToDecrypt) {
		String decryptedString = "";
		try {
			SecretKeySpec secretKey = new SecretKeySpec(EncriptionUtility.getKey().getBytes(ENCODING), ALGORITHM);
			Cipher cipher = Cipher.getInstance(CIPHER_VALUE);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(valueToDecrypt));
			decryptedString = new String(decrypted);
		} catch (Exception e) {
			Reporter.log("Error Occured While Decripting" + e.getMessage());
		}
		return decryptedString;
	}

	public static String encryptString(final String string) {
		String encryptedString = "";
		try {
			byte[] encodeBytes = Base64.getEncoder().encode(string.getBytes());
			encryptedString = new String(encodeBytes);
		} catch (Exception e) {
			Reporter.log("Error Occured " + e.getMessage());
		}
		return encryptedString;

	}
	
	public static String decryptString(final String string) {
		String decodedString = "";
		try {
			byte[] decodeBytes = Base64.getDecoder().decode(string);
			decodedString = new String(decodeBytes);
		} catch (Exception e) {
			Reporter.log("Error Occured " + e.getMessage());
		}
		return decodedString;

	}
	
	public static String decryptURLString(String stringURL) {
		String decodedStringURL = "";
		try {
			decodedStringURL = URLDecoder.decode(stringURL, "UTF-8");
			return decodedStringURL;
		} catch (Exception e) {
			Reporter.log("Error Occured " + e.getMessage());
			return decodedStringURL;
		}
		

	}

	public static void main(String[] args) {
		String value = "MqVYZ8nFwJhQ8zmZnawIDA==";
		System.out.println("Actual Value is: " + value);
		String encryptedValue = encrypt(value);
		String decryptedValue = decrypt(value);
		System.out.println("Encrypted Value is: " + encryptedValue);
		System.out.println("Decrypted Value is: " + decryptedValue);
		encryptedValue = encryptString(value);
		decryptedValue = decryptString(encryptedValue);
		
		String encodedURL = "%2Flogjgrijr%2";
		decryptedValue = decryptURLString(encodedURL);
	}
}
