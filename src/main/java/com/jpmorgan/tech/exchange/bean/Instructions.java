package com.jpmorgan.tech.exchange.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Deep Ranjan
 *
 */
public class Instructions {
    private List<Instruction> outgoingBuyingInstructions;
    private List<Instruction> incomingSellingInstructions;

    public List<Instruction> getOutgoingBuyingInstructions() {
        if (outgoingBuyingInstructions == null) {
            outgoingBuyingInstructions = new ArrayList<Instruction>();
        }
        return outgoingBuyingInstructions;
    }

    public void setOutgoingBuyingInstructions(List<Instruction> outgoingBuyingInstructions) {
        this.outgoingBuyingInstructions = outgoingBuyingInstructions;
    }

    public List<Instruction> getIncomingSellingInstructions() {
        if (incomingSellingInstructions == null) {
            incomingSellingInstructions = new ArrayList<Instruction>();
        }
        return incomingSellingInstructions;
    }

    public void setIncomingSellingInstructions(List<Instruction> incomingSellingInstructions) {
        this.incomingSellingInstructions = incomingSellingInstructions;
    }
}