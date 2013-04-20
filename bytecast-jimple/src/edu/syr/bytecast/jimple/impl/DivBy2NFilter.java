/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.jimple.api.IFilter;
import java.util.List;
/**
 *
 * @author QSA
 */
public class DivBy2NFilter implements IFilter {
    //Divide by 2^n filter

    @Override
    public boolean doTest(List<MemoryInstructionPair> instList, int index) {
        IInstruction ins = instList.get(index).getInstruction();
        int count = 0;
        if (ins.getInstructiontype().equals(InstructionType.MOV)
                && ins.getOperands().get(0).getOperandType().equals(OperandType.MEMORY_EFFECITVE_ADDRESS)
                && ins.getOperands().get(1).getOperandValue().equals(RegisterType.EAX)) {
            count++;
            ins = instList.get(index + count).getInstruction();
            if (ins.getInstructiontype().equals(InstructionType.MOV)
                    && ins.getOperands().get(0).getOperandValue().equals(RegisterType.EAX)
                    && ins.getOperands().get(1).getOperandValue().equals(RegisterType.EDX)) {
                count++;
                ins = instList.get(index + count).getInstruction();
                if (ins.getInstructiontype().equals(InstructionType.SHR)
                        && ins.getOperands().get(0).getOperandType().equals(OperandType.CONSTANT)
                        && ins.getOperands().get(1).getOperandValue().equals(RegisterType.EDX)) {
                    count++;
                    ins = instList.get(index + count).getInstruction();
                    if (ins.getInstructiontype().equals(InstructionType.LEA)
                            && ins.getOperands().get(0).getOperandType().equals(OperandType.MEMORY_EFFECITVE_ADDRESS)
                            && ins.getOperands().get(1).getOperandValue().equals(RegisterType.EAX)) {
                        count++;
                        ins = instList.get(index + count).getInstruction();
                        if (ins.getInstructiontype().equals(InstructionType.SAR)
                                && ins.getOperands().get(0).getOperandValue().equals(RegisterType.EAX)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
