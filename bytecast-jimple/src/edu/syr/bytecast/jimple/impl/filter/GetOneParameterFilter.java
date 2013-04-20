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
public class GetOneParameterFilter implements IFilter {

    public boolean doTest(List<MemoryInstructionPair> instList, int index) {
        IInstruction ins = instList.get(index).getInstruction();
        int count = 0;
        if (ins.getInstructiontype().equals(InstructionType.MOV)
                && ins.getOperands().get(0).getOperandValue().equals(RegisterType.EDI)
                && ins.getOperands().get(1).getOperandType().equals(OperandType.MEMORY_EFFECITVE_ADDRESS)) {
            count++;
            ins = instList.get(index + count).getInstruction();
            if (!(ins.getInstructiontype().equals(InstructionType.MOV)
                    && ins.getOperands().get(0).getOperandValue().equals(RegisterType.ESI)
                    && ins.getOperands().get(1).getOperandType().equals(OperandType.MEMORY_EFFECITVE_ADDRESS))
                    && !(ins.getInstructiontype().equals(InstructionType.MOV)
                    && ins.getOperands().get(0).getOperandValue().equals(RegisterType.RSI)
                    && ins.getOperands().get(1).getOperandType().equals(OperandType.MEMORY_EFFECITVE_ADDRESS))) //SectionName
            {
                return true;
            }
        }
        return false;
    }
}
