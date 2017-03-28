package com.jpmorgan.tech.exchange;

/**
 * 
 * @author Deep Ranjan
 *
 */
public class CurrencyExchangeException extends Exception {

	private static final long serialVersionUID = 1L;

	public CurrencyExchangeException() {
		super();
	}

	public CurrencyExchangeException(String errorMsg) {
		super(errorMsg);
	}

	public CurrencyExchangeException(String errorMsg, Exception ex) {
		super(errorMsg, ex);
	}
}
