/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import java.util.List;

/**
 *
 * @author tongxu
 */

public class PrintfFilter {
    public boolean doTest(List<MemoryInstructionPair> instList, int index){
        IInstruction ins = instList.get(index).getInstruction();
        if( ins.getInstructiontype() == InstructionType.MOV
                && ins.getOperands().get(0).getOperandType() == OperandType.CONSTANT
                && ins.getOperands().get(0).getOperandValue().toString().equals("$0x0")
                && ins.getOperands().get(1).getOperandValue().toString().equals("%eax")
                && ins.getOperands().get(1).getOperandType() == OperandType.REGISTER)
        {
            ins = instList.get(index + 1).getInstruction();
            if( ins.getInstructiontype() == InstructionType.CALLQ 
                    && ins.getOperands().get(1).getOperandType() == OperandType.SECTION_NAME
                    && ins.getOperands().get(1).getOperandValue().toString() ==  "printf@plt")
            {
                return true;
            }
        }
        return false;
    }
}
            