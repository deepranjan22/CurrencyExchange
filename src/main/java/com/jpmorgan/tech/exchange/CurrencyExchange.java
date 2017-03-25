package com.jpmorgan.tech.exchange;

import java.util.Calendar;
import java.util.Date;

public class CurrencyExchange {

	/**
	 * For currency AED or SAR, week starts Sunday and ends Thursday. For other
	 * currency week starts from Monday and ends Friday.
	 * 
	 * @param instructionDate
	 * @param currency
	 * @return
	 * @throws CurrencyExchangeException
	 */
	public boolean isWeekDay(Date instructionDate, String currency) throws CurrencyExchangeException {
		if (instructionDate == null || currency == null) {
			throw new CurrencyExchangeException("Invalid Instruction Date or Currency!");
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(instructionDate);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

		if (currency.equalsIgnoreCase("AED") || currency.equalsIgnoreCase("SAR")) {
			if (dayOfWeek != Calendar.FRIDAY && dayOfWeek != Calendar.SATURDAY) {
				return true;
			}
		} else {
			if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
				return true;
			}
		}

		return false;
	}
}