package com.megapon.arcoupon.common.util;

import java.util.Optional;

public class LuhnUtil {
	public static int calculateLuhnsCheckDigit(long number) {
		int sum = 0;
		boolean alternate = false;
		String digits = Long.toString(number);

		for (int i = digits.length() - 1; i >= 0; --i) {
			int digit = Character.getNumericValue(digits.charAt(i)); // get the digit at the given index
			digit = (alternate = !alternate) ? (digit * 2) : digit; // double every other digit
			digit = (digit > 9) ? (digit - 9) : digit; // subtract 9 if the value is greater than 9
			sum += digit; // add the digit to the sum
		}

		return (sum * 9) % 10;
	}

	public static Optional<Long> extractLuhnsNumber(String luhnsNumber) {
		int sum = 0;
		boolean alternate = true;
		Long number = Long.parseLong(luhnsNumber.substring(0, luhnsNumber.length() - 1));

		for (int i = luhnsNumber.length() - 1; i >= 0; --i) {
			int digit = Character.getNumericValue(luhnsNumber.charAt(i)); // get the digit at the given index
			digit = (alternate = !alternate) ? (digit * 2) : digit; // double every other digit
			digit = (digit > 9) ? (digit - 9) : digit; // subtract 9 if the value is greater than 9
			sum += digit; // add the digit to the sum
		}

		return (sum % 10 == 0) ? Optional.of(number) : Optional.empty();
	}
}
