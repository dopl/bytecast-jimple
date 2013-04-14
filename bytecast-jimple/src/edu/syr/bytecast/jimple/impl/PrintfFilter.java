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
        int count = 0;
        if( ins.getInstructiontype() == InstructionType.CALLQ 
                && ins.getOperands().get(1).getOperandType() == OperandType.SECTION_NAME
                && ins.getOperands().get(1).getOperandValue().toString() ==  "printf@plt")
        {
            count++;
            int judge = 0;
            while(judge < 2)
            {
                ins = instList.get(index - count).getInstruction();
                if( ins.getInstructiontype() == InstructionType.MOV
                    && ins.getOperands().get(1).getOperandType() == OperandType.REGISTER
                    && ins.getOperands().get(1).getOperandValue().toString() ==  "%eax") //SectionName
                {
                judge++;
                count++;
                return true;
                }
            }
        }
        return false;
    } 

    
}
