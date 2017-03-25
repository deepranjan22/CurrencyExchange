package com.jpmorgan.tech.exchange;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CurrencyExchangeTest {

	private CurrencyExchange exchange;

	@Before
	public void init() {
		exchange = new CurrencyExchange();
	}

	@Test(expected = CurrencyExchangeException.class)
	public void isWeekDayTest_invalidInput() throws CurrencyExchangeException {
		exchange.isWeekDay(null, "AED");
	}

	/**
	 * week-day test for AED/SAR
	 * 
	 * @throws CurrencyExchangeException
	 */
	@Test
	public void isWeekDayTest() throws CurrencyExchangeException {
		Calendar calander = Calendar.getInstance();

		// Week-day for AED
		String currency = "AED";
		calander.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		boolean weekDay_Sunday_AED = exchange.isWeekDay(calander.getTime(), currency);
		Assert.assertTrue(weekDay_Sunday_AED);

		// Week-end for AED
		calander.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		boolean weekDay_Friday_AED = exchange.isWeekDay(calander.getTime(), currency);
		Assert.assertFalse(weekDay_Friday_AED);

		// weekend for SGP (other than AED or SAR)
		currency = "SGP";
		calander.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		boolean weekDay_Saturday_SGP = exchange.isWeekDay(calander.getTime(), currency);
		Assert.assertFalse(weekDay_Saturday_SGP);

		// week-day for SGP
		calander.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		boolean weekDay_Friday_SGP = exchange.isWeekDay(calander.getTime(), currency);
		Assert.assertTrue(weekDay_Friday_SGP);
	}

}