/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl.filter;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.jimple.api.IFilter;
import java.util.List;

/**
 *
 * @author Fei Qi
 */
public class UseArgvAsParaWith2DFilter implements IFilter {

    public boolean doTest(List<MemoryInstructionPair> instList, int index) {
        IInstruction ins = instList.get(index).getInstruction();
        int count = 0;
        if (ins.getInstructiontype().equals(InstructionType.MOV)
                && ins.getOperands().get(0).getOperandType().equals(OperandType.MEMORY_EFFECITVE_ADDRESS)
                && ins.getOperands().get(1).getOperandValue().equals(RegisterType.RAX)) {
            count++;
            ins = instList.get(index + count).getInstruction();
            if (ins.getInstructiontype().equals(InstructionType.ADD)
                    && ins.getOperands().get(0).getOperandType().equals(OperandType.CONSTANT)
                    && ins.getOperands().get(1).getOperandValue().equals(RegisterType.RAX)) {
                count++;
                ins = instList.get(index + count).getInstruction();
                if (ins.getInstructiontype().equals(InstructionType.MOV)
                        && ins.getOperands().get(0).getOperandType().equals(OperandType.MEMORY_EFFECITVE_ADDRESS)
                        && ins.getOperands().get(1).getOperandValue().equals(RegisterType.RAX)) {
                    count++;
                    ins = instList.get(index + count).getInstruction();
                    if (ins.getInstructiontype().equals(InstructionType.ADD)
                            && ins.getOperands().get(0).getOperandType().equals(OperandType.CONSTANT)
                            && ins.getOperands().get(1).getOperandValue().equals(RegisterType.RAX)) {
                        count++;
                        ins = instList.get(index + count).getInstruction();
                        if (ins.getInstructiontype().equals(InstructionType.MOVZBL)
                                && ins.getOperands().get(0).getOperandType().equals(OperandType.MEMORY_EFFECITVE_ADDRESS)
                                && ins.getOperands().get(1).getOperandValue().equals(RegisterType.EAX)) {
                            count++;
                            ins = instList.get(index + count).getInstruction();
                            if (ins.getInstructiontype().equals(InstructionType.MOVSBL)
                                    && ins.getOperands().get(0).getOperandValue().equals(RegisterType.AL)
                                    && ins.getOperands().get(1).getOperandType().equals(OperandType.REGISTER)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
