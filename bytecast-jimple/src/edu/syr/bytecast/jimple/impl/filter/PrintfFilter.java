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
 * @author tongxu
 */
public class PrintfFilter implements IFilter {

    public boolean doTest(List<MemoryInstructionPair> instList, int index) {
        IInstruction ins = instList.get(index).getInstruction();
        if (ins.getInstructiontype().equals(InstructionType.CALLQ)
                && ins.getOperands().get(1).getOperandType().equals(OperandType.SECTION_NAME)
                && (ins.getOperands().get(1).getOperandValue().equals("printf@plt")
                || ins.getOperands().get(1).getOperandValue().equals("Printf@plt"))) {
            return true;
        }
        return false;
    }
}
