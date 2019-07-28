package com.megapon.arcoupon.common.util.security;

import java.util.Random;

public class PasswordUtil {
	public static String getTempPassword() {
		String password = "";

		while (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*()-+=])(?!.*[ㄱ-ㅎㅏ-ㅣ가-힣A-Z\\s])[a-z0-9!@#$%^&*()-+=]{8,15}$")) {
			// 비밀번호는 최소 8 ~ 15자, 영문소문자/숫자/특수문자만 가능하며 반드시 각 1개이상 포함,
			// 한글/영문대문자/whitespace 불가
			password = createTempPassword();
		}

		return password;
	}

	private static String createTempPassword() {
		StringBuffer sb = new StringBuffer();
		Random rnd = new Random();

		for (int i = 0; i < 12; i++) {
			switch (rnd.nextInt(3)) {
			case 0:
				// 소문자
				sb.append((char) (rnd.nextInt(26) + 97));
				break;
			case 1:
				// 숫자
				sb.append((char) (rnd.nextInt(10) + 48));
				break;
			case 2:
				// 특수문자
				String specialChars = "!@#$%^&*";
				int num = (int) (rnd.nextInt(specialChars.length()));
				sb.append(specialChars.substring(num, (num == specialChars.length()) ? num : num + 1));
				break;
			}
		}

		return sb.toString();
	}
}