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
 * @author Fei Qi
 */

//this file is used to filter the pre-memory process part

public class PreMemoryProcessFilter implements IFilter{
    public boolean doTest(List<MemoryInstructionPair> instList, int index)
    {
        IInstruction ins = instList.get(index).getInstruction();
        int count = 0;
        if( ins.getInstructiontype() == InstructionType.PUSH
            && ins.getOperands().get(0).getOperandType() == OperandType.REGISTER
            && ins.getOperands().get(0).getOperandValue().toString().equals("%rbp") )
        {
            count++;
            ins = instList.get(index + count).getInstruction();
            if( ins.getInstructiontype() == InstructionType.MOV
                && ins.getOperands().get(0).getOperandType() == OperandType.REGISTER
                && ins.getOperands().get(0).getOperandValue().equals("%rsp")
                && ins.getOperands().get(1).getOperandType() == OperandType.REGISTER
                && ins.getOperands().get(1).getOperandValue().equals("%rbp")) //SectionName
            {
                count++;
                ins = instList.get(index + count).getInstruction();
                if( ins.getInstructiontype() == InstructionType.SUB
                    && ins.getOperands().get(0).getOperandType() == OperandType.CONSTANT
                    && ins.getOperands().get(1).getOperandType() == OperandType.REGISTER
                    && ins.getOperands().get(1).getOperandValue().equals("%rsp"))
                {
                        count++;
                        return true;
                    }
                }
            }
        return false;
    } 
}
