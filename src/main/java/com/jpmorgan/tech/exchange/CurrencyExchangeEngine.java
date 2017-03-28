package com.jpmorgan.tech.exchange;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.jpmorgan.tech.exchange.bean.Instruction;
import com.jpmorgan.tech.exchange.bean.Instructions;
import com.jpmorgan.tech.exchange.util.ExchangeReportConstant;

/**
 * 
 * @author Deep Ranjan
 *
 */
public class CurrencyExchangeEngine {

	public static final Logger LOGGER = Logger.getLogger(CurrencyExchangeEngine.class);

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

	public Date getSettlementDate(Date instructionDate, String currency) throws CurrencyExchangeException {
		if (instructionDate == null) {
			throw new CurrencyExchangeException("Invalid Instruction Date!");
		}

		if (isWeekDay(instructionDate, currency)) {
			return instructionDate;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(instructionDate);
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			if (currency.equalsIgnoreCase("AED") || currency.equalsIgnoreCase("SAR")) {
				if (dayOfWeek == Calendar.FRIDAY) {
					calendar.add(Calendar.DATE, ExchangeReportConstant.DAY_2);
				} else if (dayOfWeek == Calendar.SATURDAY) {
					calendar.add(Calendar.DATE, ExchangeReportConstant.DAY_1);
				}
			} else {
				if (dayOfWeek == Calendar.SATURDAY) {
					calendar.add(Calendar.DATE, ExchangeReportConstant.DAY_2);
				} else if (dayOfWeek == Calendar.SUNDAY) {
					calendar.add(Calendar.DATE, ExchangeReportConstant.DAY_1);
				}
			}
			return calendar.getTime();
		}
	}

	public Instruction calculate(Instruction instruction) throws CurrencyExchangeException {
		if (instruction.getPricePerUnit() == null || instruction.getUnits() == null
				|| instruction.getAgreedFX() == null) {
			LOGGER.error("Instruction Price Per Unit or unit or agreedFx is invalid");
			throw new CurrencyExchangeException("Instruction Price Per Unit or unit or agreedFx is invalid");
		}

		Date settlementDate = getSettlementDate(instruction.getInstructionDate(), instruction.getCurrency());
		instruction.setSettlementDate(settlementDate);

		BigDecimal tradeAmount = instruction.getPricePerUnit().multiply(new BigDecimal(instruction.getUnits()))
				.multiply(instruction.getAgreedFX()).setScale(2, BigDecimal.ROUND_HALF_DOWN);

		instruction.setTradeAmount(tradeAmount);

		LOGGER.debug("Calculate Trade Amount : END");

		return instruction;
	}

	public Instructions organisedTradeReport(List<Instruction> instructionList) {
		LOGGER.debug("Organised Trade Report : START");
		Instructions allExecutedInstructions = new Instructions();

		for (Instruction instruction : instructionList) {
			if (ExchangeReportConstant.BUY.equalsIgnoreCase(instruction.getInstructionType())) {
				allExecutedInstructions.getOutgoingBuyingInstructions().add(instruction);
			} else {
				allExecutedInstructions.getIncomingSellingInstructions().add(instruction);
			}
		}
		Collections.sort(allExecutedInstructions.getOutgoingBuyingInstructions());
		Collections.sort(allExecutedInstructions.getIncomingSellingInstructions());
		BigDecimal tradeAmountIncoming = BigDecimal.ZERO;
		BigDecimal tradeAmountOutgoing = BigDecimal.ZERO;

		LOGGER.info("************* Incoming Instruction ****************");
		for (Instruction instruction : allExecutedInstructions.getIncomingSellingInstructions()) {
			tradeAmountIncoming = tradeAmountIncoming.add(instruction.getTradeAmount());
			LOGGER.info(instruction.toString());
		}

		LOGGER.info("***************************************************" + System.lineSeparator());
		LOGGER.info("Amount in USD settled incoming everyday ****************" + tradeAmountIncoming
				+ System.lineSeparator());

		LOGGER.info("************* Outgoing Instruction ****************");
		for (Instruction instruction : allExecutedInstructions.getOutgoingBuyingInstructions()) {
			tradeAmountOutgoing = tradeAmountIncoming.add(instruction.getTradeAmount());
			LOGGER.info(instruction.toString());
		}
		LOGGER.info("***************************************************" + System.lineSeparator());
		LOGGER.info("Amount in USD settled outgoing everyday ****************" + tradeAmountOutgoing);

		LOGGER.debug("Organised Trade Report : END");
		return allExecutedInstructions;
	}
}