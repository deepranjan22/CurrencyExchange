package com.jpmorgan.tech.exchange;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jpmorgan.tech.exchange.bean.Instruction;
import com.jpmorgan.tech.exchange.bean.Instructions;

/**
 * 
 * @author Deep Ranjan
 *
 */

public class CurrencyExchangeEngineTest {

	private CurrencyExchangeEngine exchnageEngine;
	Instruction instruction1 = null;
	Instruction instruction2 = null;
	Instruction instruction3 = null;
	Instruction instruction4 = null;
	Instruction instruction5 = null;
	Instruction instruction6 = null;
	Instruction instruction7 = null;
	Instruction instruction8 = null;
	Instruction instruction9 = null;
	Instruction instruction10 = null;
	Instruction instruction11 = null;
	Instruction instruction12 = null;

	@Before
	public void init() {
		exchnageEngine = new CurrencyExchangeEngine();
		initData();
	}

	@Test(expected = CurrencyExchangeException.class)
	public void isWeekDayTest_invalidInput() throws CurrencyExchangeException {
		exchnageEngine.isWeekDay(null, "AED");
	}

	/**
	 * weekday test for AED/SAR
	 * 
	 * @throws CurrencyExchangeException
	 */
	@Test
	public void isWeekDayTest() throws CurrencyExchangeException {
		Calendar calander = Calendar.getInstance();

		// Week-day for AED
		String currency = "AED";
		calander.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		boolean weekDay_Sunday_AED = exchnageEngine.isWeekDay(calander.getTime(), currency);
		Assert.assertTrue(weekDay_Sunday_AED);

		// Week-end for AED
		calander.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		boolean weekDay_Friday_AED = exchnageEngine.isWeekDay(calander.getTime(), currency);
		Assert.assertFalse(weekDay_Friday_AED);

		// weekend for SGP (other than AED or SAR)
		currency = "SGP";
		calander.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		boolean weekDay_Saturday_SGP = exchnageEngine.isWeekDay(calander.getTime(), currency);
		Assert.assertFalse(weekDay_Saturday_SGP);

		// week-day for SGP
		calander.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		boolean weekDay_Friday_SGP = exchnageEngine.isWeekDay(calander.getTime(), currency);
		Assert.assertTrue(weekDay_Friday_SGP);
	}

	@Test
	public void getSettlementDateTest() throws CurrencyExchangeException {
		Calendar calander = Calendar.getInstance();

		String currency = "AED";
		// Weekend test
		calander.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		Date settlementDate = exchnageEngine.getSettlementDate(calander.getTime(), currency);
		Assert.assertNotNull(settlementDate);

		Calendar settCal = Calendar.getInstance();
		settCal.setTime(settlementDate);
		Assert.assertTrue(settCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);

		// WeekDay test
		calander.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		settlementDate = exchnageEngine.getSettlementDate(calander.getTime(), currency);
		Assert.assertNotNull(settlementDate);

		settCal = Calendar.getInstance();
		settCal.setTime(settlementDate);
		Assert.assertTrue(settCal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY);

		// currency = "USD";

	}

	/**
	 * This will test the Incoming/Selling Report sorted in DESC order
	 * 
	 * @throws CurrencyExchangeException
	 */
	@Test
	public void testcalculateForIncomingSellingReportRanking() throws CurrencyExchangeException {

		List<Instruction> instructionList = new ArrayList<Instruction>();

		instructionList.add(exchnageEngine.calculate(instruction1));
		instructionList.add(exchnageEngine.calculate(instruction2));
		instructionList.add(exchnageEngine.calculate(instruction3));
		instructionList.add(exchnageEngine.calculate(instruction4));
		instructionList.add(exchnageEngine.calculate(instruction5));
		instructionList.add(exchnageEngine.calculate(instruction6));
		instructionList.add(exchnageEngine.calculate(instruction7));
		instructionList.add(exchnageEngine.calculate(instruction8));
		instructionList.add(exchnageEngine.calculate(instruction9));
		instructionList.add(exchnageEngine.calculate(instruction10));
		instructionList.add(exchnageEngine.calculate(instruction11));
		instructionList.add(exchnageEngine.calculate(instruction12));

		Instructions allInstructions = exchnageEngine.organisedTradeReport(instructionList);

		List<Instruction> IncomingSellingList = allInstructions.getIncomingSellingInstructions();

		Instruction[] arr = IncomingSellingList.toArray(new Instruction[IncomingSellingList.size()]);

		for (int i = 0; i < IncomingSellingList.size() - 1; i++) {
			int j = i + 1;
			Assert.assertTrue(arr[i].getTradeAmount().compareTo(arr[j].getTradeAmount()) >= 0);
		}
	}

	public void initData() {
		instruction1 = new Instruction(1l, "foo", "B", new BigDecimal(0.40), "SAR",
				getInputDate(2016, Calendar.JANUARY, 1), getInputDate(2016, Calendar.JANUARY, 1), 200l,
				new BigDecimal(100.25d));

		instruction2 = new Instruction(2l, "bar", "S", new BigDecimal(0.22d), "AED",
				getInputDate(2016, Calendar.JANUARY, 5), getInputDate(2016, Calendar.JANUARY, 2), 450l,
				new BigDecimal(150.5d));

		// Weekends for USD-Saturday and GBP-Sunday
		instruction3 = new Instruction(3l, "tar", "S", new BigDecimal(0.55d), "USD",
				getInputDate(2016, Calendar.JANUARY, 1), getInputDate(2016, Calendar.JANUARY, 2), 100l,
				new BigDecimal(100.25d));
		instruction4 = new Instruction(4l, "gar", "B", new BigDecimal(0.85d), "GBP",
				getInputDate(2016, Calendar.JANUARY, 5), getInputDate(2016, Calendar.JANUARY, 3), 350l,
				new BigDecimal(150.5d));

		// Weekdays for GBP-Tuesday and SAR-Tuesday
		instruction5 = new Instruction(5l, "gar", "B", new BigDecimal(0.85d), "GBP",
				getInputDate(2016, Calendar.JANUARY, 1), getInputDate(2016, Calendar.JANUARY, 5), 250l,
				new BigDecimal(100.25d));
		instruction6 = new Instruction(6l, "foo", "S", new BigDecimal(0.40d), "SAR",
				getInputDate(2016, Calendar.JANUARY, 5), getInputDate(2016, Calendar.JANUARY, 5), 259l,
				new BigDecimal(150.5d));

		// Extra for generating report
		instruction7 = new Instruction(7l, "foo", "B", new BigDecimal(0.50d), "SAR",
				getInputDate(2016, Calendar.JANUARY, 1), getInputDate(2016, Calendar.JANUARY, 4), 379l,
				new BigDecimal(100.25d));
		instruction8 = new Instruction(8l, "bar", "S", new BigDecimal(0.22d), "AED",
				getInputDate(2016, Calendar.JANUARY, 5), getInputDate(2016, Calendar.JANUARY, 4), 152l,
				new BigDecimal(150.5d));

		instruction9 = new Instruction(9l, "tar", "S", new BigDecimal(0.55d), "USD",
				getInputDate(2016, Calendar.JANUARY, 1), getInputDate(2016, Calendar.JANUARY, 4), 321l,
				new BigDecimal(100.25d));
		instruction10 = new Instruction(10l, "gar", "B", new BigDecimal(0.85d), "GBP",
				getInputDate(2016, Calendar.JANUARY, 5), getInputDate(2016, Calendar.JANUARY, 4), 123l,
				new BigDecimal(150.5d));

		instruction11 = new Instruction(11l, "gar", "B", new BigDecimal(0.85d), "GBP",
				getInputDate(2016, Calendar.JANUARY, 1), getInputDate(2016, Calendar.JANUARY, 5), 456l,
				new BigDecimal(100.25d));
		instruction12 = new Instruction(12l, "foo", "S", new BigDecimal(0.50d), "SAR",
				getInputDate(2016, Calendar.JANUARY, 5), getInputDate(2016, Calendar.JANUARY, 5), 654l,
				new BigDecimal(150.5d));
	}

	private Date getInputDate(int year, int month, int day) {
		Calendar inputDate = Calendar.getInstance();
		inputDate.set(year, month, day);
		return inputDate.getTime();
	}


}