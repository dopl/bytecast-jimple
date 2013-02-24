/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.jimple.api.AbstractFilter;
import edu.syr.bytecast.jimple.api.IFilter;
import java.util.List;

/**
 *
 * @author Fei Qi
 */

//this file is used to filter the pre-memory process part

public class PreMemoryProcessFilter extends AbstractFilter implements IFilter{
    public boolean doTest(List<IInstruction> instList, int index)
    {
        IInstruction ins = instList.get(index);
        if( ins.getInstructiontype() == InstructionType.PUSH
                && ins.getOperands().get(0).getOperandType() == OperandType.REGISTER
                && ins.getOperands().get(0).getOperandValue() == "%rbp" )
        {
            ins = instList.get(index + 1);
            if( ins.getInstructiontype() == InstructionType.MOV
                    && ins.getOperands().get(0).getOperandType() == OperandType.REGISTER
                    && ins.getOperands().get(0).getOperandValue() == "%rsp"
                    && ins.getOperands().get(1).getOperandType() == OperandType.REGISTER
                    && ins.getOperands().get(1).getOperandValue() == "%rbp") //SectionName
            {
                ins = instList.get(index + 1);
                if( ins.getInstructiontype() == InstructionType.SUB
                    && ins.getOperands().get(0).getOperandType() == OperandType.CONSTANT
                    && ins.getOperands().get(1).getOperandType() == OperandType.REGISTER
                    && ins.getOperands().get(1).getOperandValue() == "%rsp")
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    
}
