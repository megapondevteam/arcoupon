package com.megapon.arcoupon.common.util.security;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class converts a UTF-8 string into a cipher string, and vice versa. It uses 128-bit AES Algorithm in Cipher Block Chaining (CBC) mode with a UTF-8 key string and a UTF-8 initial vector string which are hashed by MD5. PKCS5Padding is used as a padding mode and binary output is encoded by Base64.
 * 
 * @author JO Hyeong-ryeol
 */
public class StringEncrypter {
	private static Cipher rijndael;
	private static SecretKeySpec key;
	private static IvParameterSpec initalVector;
	private static final char BASE64_PADDING = '=';
	private static final String BASE64_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

	public static String encrypt(String aes_key, String aes_ivt, String value) throws Exception {
		if (aes_key == null || aes_key.equals("")) {
			throw new Exception("The key can not be null or an empty string.");
		}

		if (aes_ivt == null || aes_ivt.equals("")) {
			throw new Exception("The key can not be null or an empty string.");
		}

		String initialkey = aes_key;
		String initialVector = aes_ivt;

		if (initialkey == null || initialkey.equals(""))
			throw new Exception("The key can not be null or an empty string.");

		if (initialVector == null || initialVector.equals(""))
			throw new Exception("The initial vector can not be null or an empty string.");

		// Create a AES algorithm.
		rijndael = Cipher.getInstance("AES/CBC/PKCS5Padding");

		// Initialize an encryption key and an initial vector.
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		key = new SecretKeySpec(md5.digest(initialkey.getBytes("UTF8")), "AES");
		initalVector = new IvParameterSpec(md5.digest(initialVector.getBytes("UTF8")));

		if (value == null)
			value = "";

		// Initialize the cryptography algorithm.
		rijndael.init(Cipher.ENCRYPT_MODE, key, initalVector);

		// Get a UTF-8 byte array from a unicode string.
		byte[] utf8Value = value.getBytes("UTF8");

		// Encrypt the UTF-8 byte array.
		byte[] encryptedValue = rijndael.doFinal(utf8Value);

		// Return a base64 encoded string of the encrypted byte array.
		return encode(encryptedValue);
	}

	public static String decrypt(String aes_key, String aes_ivt, String value) throws Exception {

		if (aes_key == null || aes_key.equals("")) {
			throw new Exception("The key can not be null or an empty string.");
		}

		if (aes_ivt == null || aes_ivt.equals("")) {
			throw new Exception("The key can not be null or an empty string.");
		}
		String initialkey = aes_key;
		String initialVector = aes_ivt;

		if (initialkey == null || initialkey.equals(""))
			throw new Exception("The key can not be null or an empty string.");

		if (initialVector == null || initialVector.equals(""))
			throw new Exception("The initial vector can not be null or an empty string.");

		// Create a AES algorithm.
		rijndael = Cipher.getInstance("AES/CBC/PKCS5Padding");

		// Initialize an encryption key and an initial vector.
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		key = new SecretKeySpec(md5.digest(initialkey.getBytes("UTF8")), "AES");
		initalVector = new IvParameterSpec(md5.digest(initialVector.getBytes("UTF8")));

		if (value == null)
			value = "";

		// Initialize the cryptography algorithm.
		rijndael.init(Cipher.DECRYPT_MODE, key, initalVector);

		// Get an encrypted byte array from a base64 encoded string.
		byte[] encryptedValue = decode(value);

		// Decrypt the byte array.
		byte[] decryptedValue = rijndael.doFinal(encryptedValue);

		// Return a string converted from the UTF-8 byte array.
		return new String(decryptedValue);
	}

	public String md5(String value) throws Exception {
		byte[] bpara = new byte[value.length()];
		byte[] rethash;
		int i;

		for (i = 0; i < value.length(); i++)
			bpara[i] = (byte) (value.charAt(i) & 0xff);

		try {
			MessageDigest md5er = MessageDigest.getInstance("MD5");
			rethash = md5er.digest(bpara);
		} catch (GeneralSecurityException ex) {
			throw new RuntimeException(ex);
		}

		StringBuffer r = new StringBuffer(32);
		for (i = 0; i < rethash.length; i++) {
			String x = Integer.toHexString(rethash[i] & 0xff).toUpperCase();
			if (x.length() < 2)
				r.append("0");
			r.append(x);
		}

		return r.toString();
	}

	public static String encode(byte[] value) {
		if (value == null || value.length == 0)
			return "";

		// Get a required number of characters.
		int requiredSize = (value.length / 3) * 4;
		if (value.length % 3 > 0)
			requiredSize += 4;

		// Allocate memory
		char[] output = new char[requiredSize];

		// Converts the byte array into a Base64 string. (3 bytes to 4
		// characters)
		int outputIndex = 0, bufferIndex = 0;
		int[] buffer = new int[3];
		int base64CharIndex;

		for (int i = 0; i < value.length; i++) {
			// Accumulate bytes in buffer.
			buffer[bufferIndex++] = 0xFF & value[i];

			// If 3 bytes are collected or the last byte is collected
			if (bufferIndex == 3 || i == (value.length - 1)) {
				// Make a first character
				base64CharIndex = buffer[0] >>> 2;
				output[outputIndex++] = BASE64_CHARS.charAt(base64CharIndex);

				// Make a second character
				base64CharIndex = ((buffer[0] & 0x03) << 4) | (buffer[1] >>> 4);
				output[outputIndex++] = BASE64_CHARS.charAt(base64CharIndex);

				// Make a third character
				base64CharIndex = ((buffer[1] & 0x0F) << 2) | (buffer[2] >>> 6);
				output[outputIndex++] = bufferIndex > 1 ? BASE64_CHARS.charAt(base64CharIndex) : BASE64_PADDING;

				// Make a fourth character
				base64CharIndex = buffer[2] & 0x3F;
				output[outputIndex++] = bufferIndex > 2 ? BASE64_CHARS.charAt(base64CharIndex) : BASE64_PADDING;

				// Reset variables related to the buffer
				bufferIndex = 0;
				for (int j = 0; j < buffer.length; j++)
					buffer[j] = 0;
			}
		}

		// Return as a string
		return new String(output);
	}

	/**
	 * Converts a Base64 encoded string into a binary data. If the string is null or an empty string, a zero-length array is returned.
	 * 
	 * @param value
	 *            A Base64 encoded string to decode. The length of the string must be a multiple of 4. If it is null or an empty string, a zero-length array is returned.
	 * @return A converted byte array.
	 * @throws Exception
	 */
	public static byte[] decode(String value) throws Exception {
		if (value == null || value.length() == 0)
			return new byte[0];

		// The length of the string must be a multiple of 4.
		if ((value.length() % 4) != 0)
			throw new Exception("The length of a Base64 string must be a multiple of 4.");

		// Check the Base64 string
		for (int i = 0; i < value.length(); i++) {
			char chr = value.charAt(i);

			// If the last character is a padding
			if ((i == value.length() - 1) && (chr == BASE64_PADDING))
				break;

			// If the last two characters are padding
			if ((i == value.length() - 2) && (chr == BASE64_PADDING))
				if (value.charAt(i + 1) == BASE64_PADDING)
					break;

			// If it is a Base64 character
			if (BASE64_CHARS.indexOf(chr) != -1)
				continue;

			throw new Exception("An invalid character is found in the Base64 string.");
		}

		// Get a required number of bytes.
		int requiredSize = (value.length() / 4) * 3;
		if (value.charAt(value.length() - 1) == BASE64_PADDING)
			requiredSize--;

		if (value.charAt(value.length() - 2) == BASE64_PADDING)
			requiredSize--;

		// Allocate memory
		byte[] output = new byte[requiredSize];

		// Converts the Base64 encoded string into a binary data. (4 characters
		// to 3 bytes)
		int outputIndex = 0, bufferIndex = 0;
		char[] buffer = new char[4];
		int base64CharIndex;

		for (int i = 0; i < value.length(); i++) {
			// Accumulate characters in buffer.
			buffer[bufferIndex++] = value.charAt(i);

			// If 4 characters are collected
			if (bufferIndex == 4) {
				// Make a first byte
				base64CharIndex = BASE64_CHARS.indexOf(buffer[0]);
				output[outputIndex] = (byte) (base64CharIndex << 2);

				base64CharIndex = BASE64_CHARS.indexOf(buffer[1]);
				output[outputIndex++] |= base64CharIndex >>> 4;

				// Make a second byte
				if (buffer[2] == BASE64_PADDING)
					break;

				output[outputIndex] = (byte) ((base64CharIndex & 0x0F) << 4);

				base64CharIndex = BASE64_CHARS.indexOf(buffer[2]);
				output[outputIndex++] |= base64CharIndex >>> 2;

				// Make a third byte
				if (buffer[3] == BASE64_PADDING)
					break;

				output[outputIndex] = (byte) ((base64CharIndex & 0x03) << 6);

				base64CharIndex = BASE64_CHARS.indexOf(buffer[3]);
				output[outputIndex++] |= base64CharIndex;

				// Reset variables related to the buffer
				bufferIndex = 0;
			}
		}

		// Return the converted byte array
		return output;
	}
}