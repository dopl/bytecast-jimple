/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.jimple.api.IFilter;
import java.util.List;

/**
 *
 * @author QSA
 */
public class DivBy2NFilter implements IFilter {
    //Divide by 2^n filter

    @Override
    public boolean doTest(List<IInstruction> instList, int index) {
        IInstruction inst = instList.get(index);
        int op_index = 0;
        if (inst.getInstructiontype() == InstructionType.MOV) {
            if (inst.getOperands().get(op_index).getOperandType() == OperandType.MEMORY_EFFECITVE_ADDRESS
                    && inst.getOperands().get(++op_index).getOperandType() == OperandType.REGISTER) {
                inst = instList.get(index + 1);
                op_index = 0;
                if (inst.getOperands().get(op_index).getOperandType() == OperandType.REGISTER
                        && inst.getOperands().get(++op_index).getOperandType() == OperandType.REGISTER) {
                    inst = instList.get(index + 2);
                    if (inst.getInstructiontype() == InstructionType.SHR) {
                        inst = instList.get(index + 3);
                        if (inst.getInstructiontype() == InstructionType.LEA) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;

        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
