package com.jpmorgan.tech.exchange.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author Deep Ranjan
 *
 */
public class Instruction implements Comparable<Instruction> {

    private Long instructionId;
    private String entity;
    private String instructionType;
	private BigDecimal agreedFX;
    private String currency;
	private Date instructionDate;
	private Date settlementDate;
    private Long units;
	private BigDecimal pricePerUnit;
	private BigDecimal tradeAmount;

	public Instruction(Long instructionId, String entity, String instructionType, BigDecimal agreedFX, String currency,
			Date instructionDate, Date settlementDate, Long units, BigDecimal pricePerUnit) {
        this.instructionId = instructionId;
        this.entity = entity;
        this.instructionType = instructionType;
        this.agreedFX = agreedFX;
        this.currency = currency;
        this.instructionDate = instructionDate;
        this.settlementDate = settlementDate;
        this.units = units;
        this.pricePerUnit = pricePerUnit;
    }

    public int compareTo(Instruction instruction) {
        return instruction.getTradeAmount().compareTo(this.getTradeAmount());
    }

    public Long getInstructionId() {
        return instructionId;
    }

    public void setInstructionIdId(Long instructionId) {
        this.instructionId = instructionId;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getInstructionType() {
        return instructionType;
    }

    public void setInstructionType(String instructionType) {
        this.instructionType = instructionType;
    }

	public BigDecimal getAgreedFX() {
		if (agreedFX != null) {
			agreedFX = agreedFX.setScale(2, BigDecimal.ROUND_HALF_DOWN);
		}
        return agreedFX;
    }

	public void setAgreedFX(BigDecimal agreedFX) {
        this.agreedFX = agreedFX;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

	public Date getInstructionDate() {
        return instructionDate;
    }

	public void setInstructionDate(Date instructionDate) {
        this.instructionDate = instructionDate;
    }

	public Date getSettlementDate() {
        return settlementDate;
    }

	public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public Long getUnits() {
        return units;
    }

    public void setUnits(Long units) {
        this.units = units;
    }

	public BigDecimal getPricePerUnit() {
		if (pricePerUnit != null) {
			pricePerUnit = pricePerUnit.setScale(2, BigDecimal.ROUND_HALF_DOWN);
		}
        return pricePerUnit;
    }

	public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

	public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

	public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    @Override
    public String toString() {
        return "Instruction [instructionId=" + instructionId + ", entity=" + entity + ", tradeAmount=" + tradeAmount
            + ", instructionType=" + instructionType + ", agreedFX=" + agreedFX + ", currency=" + currency
				+ ", instructionDate=" + instructionDate + ", settlementDate=" + settlementDate
            + ", units=" + units + ", pricePerUnit=" + pricePerUnit + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Instruction other = (Instruction) obj;
        if (agreedFX == null) {
            if (other.agreedFX != null)
                return false;
        } else if (!agreedFX.equals(other.agreedFX))
            return false;
        if (currency == null) {
            if (other.currency != null)
                return false;
        } else if (!currency.equals(other.currency))
            return false;
        if (entity == null) {
            if (other.entity != null)
                return false;
        } else if (!entity.equals(other.entity))
            return false;
        if (instructionDate == null) {
            if (other.instructionDate != null)
                return false;
        } else if (!instructionDate.equals(other.instructionDate))
            return false;
        if (instructionId == null) {
            if (other.instructionId != null)
                return false;
        } else if (!instructionId.equals(other.instructionId))
            return false;
        if (instructionType == null) {
            if (other.instructionType != null)
                return false;
        } else if (!instructionType.equals(other.instructionType))
            return false;
        if (pricePerUnit == null) {
            if (other.pricePerUnit != null)
                return false;
        } else if (!pricePerUnit.equals(other.pricePerUnit))
            return false;
        if (settlementDate == null) {
            if (other.settlementDate != null)
                return false;
        } else if (!settlementDate.equals(other.settlementDate))
            return false;
        if (tradeAmount == null) {
            if (other.tradeAmount != null)
                return false;
        } else if (!tradeAmount.equals(other.tradeAmount))
            return false;
        if (units == null) {
            if (other.units != null)
                return false;
        } else if (!units.equals(other.units))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((agreedFX == null) ? 0 : agreedFX.hashCode());
        result = prime * result + ((currency == null) ? 0 : currency.hashCode());
        result = prime * result + ((entity == null) ? 0 : entity.hashCode());
        result = prime * result + ((instructionDate == null) ? 0 : instructionDate.hashCode());
        result = prime * result + ((instructionId == null) ? 0 : instructionId.hashCode());
        result = prime * result + ((instructionType == null) ? 0 : instructionType.hashCode());
        result = prime * result + ((pricePerUnit == null) ? 0 : pricePerUnit.hashCode());
        result = prime * result + ((settlementDate == null) ? 0 : settlementDate.hashCode());
        result = prime * result + ((tradeAmount == null) ? 0 : tradeAmount.hashCode());
        result = prime * result + ((units == null) ? 0 : units.hashCode());
        return result;
    }

}