/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.jimple.api.IFilter;
import java.util.List;

/**
 *
 * @author nick
 */
public class IfWithBothVariableFilter implements IFilter {

    public boolean doTest(List<MemoryInstructionPair> instList, int index) {
        IInstruction ins = instList.get(index).getInstruction();
        int count = 0;
        if (ins.getInstructiontype().equals(InstructionType.MOV)
                && ins.getOperands().get(0).getOperandType().equals(OperandType.MEMORY_EFFECITVE_ADDRESS)
                && ins.getOperands().get(1).getOperandType().equals(OperandType.REGISTER)) {
            count++;
            ins = instList.get(index + count).getInstruction();
            if (ins.getInstructiontype().equals(InstructionType.CMP)
                    && ins.getOperands().get(0).getOperandType().equals(OperandType.MEMORY_EFFECITVE_ADDRESS)
                    && ins.getOperands().get(1).getOperandType().equals(OperandType.REGISTER)) {
                count++;
                ins = instList.get(index + count).getInstruction();
                if ((ins.getInstructiontype().equals(InstructionType.JNE)
                        || ins.getInstructiontype().equals(InstructionType.JE)
                        || ins.getInstructiontype().equals(InstructionType.JLE)
                        || ins.getInstructiontype().equals(InstructionType.JGE)
                        || ins.getInstructiontype().equals(InstructionType.JL)
                        || ins.getInstructiontype().equals(InstructionType.JG))
                        && ins.getOperands().get(0).getOperandType().equals(OperandType.MEMORY_EFFECITVE_ADDRESS)) //SectionName
                {
                    count++;
                    return true;
                }
            }
        }
        return false;
    }
}