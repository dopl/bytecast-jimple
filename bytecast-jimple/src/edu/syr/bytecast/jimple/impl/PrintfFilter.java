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
import java.util.List;

/**
 *
 * @author tongxu
 */

public class PrintfFilter {
    public boolean doTest(List<MemoryInstructionPair> instList, int index){
        IInstruction ins = instList.get(index).getInstruction();
        if( ins.getInstructiontype().equals(InstructionType.MOV)
                && ins.getOperands().get(0).getOperandType().equals(OperandType.CONSTANT)
                && ins.getOperands().get(0).getOperandValue().equals(0L)
                && ins.getOperands().get(1).getOperandValue().equals(RegisterType.EAX)
                && ins.getOperands().get(1).getOperandType().equals(OperandType.REGISTER))
        {
            ins = instList.get(index + 1).getInstruction();
            if( ins.getInstructiontype().equals(InstructionType.CALLQ )
                    && ins.getOperands().get(1).getOperandType().equals(OperandType.SECTION_NAME)
                    && ins.getOperands().get(1).getOperandValue().equals( "printf@plt"))
            {
                return true;
            }
        }
        return false;
    }
}
            