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
public class CallingFilter extends AbstractFilter implements IFilter{
    public boolean doTest(List<IInstruction> instList, int index)
    {
        //this is frame to define the calling section in future
        IInstruction ins = instList.get(index);
        //calling without parameter
        if( ins.getInstructiontype() == InstructionType.CALLQ 
                && ins.getOperands().get(0).getOperandType() == OperandType.MEMORY_ADDRESS 
                && ins.getOperands().get(1).getOperandValue() == OperandType.MEMORY_ADDRESS )
                //the value of the second operand should be the SectionName, it hasn't been set up in AMD64 api
        {
            return true;
        }
        //calling with parameter
        if( ins.getInstructiontype() == InstructionType.MOV
                && ins.getOperands().get(0).getOperandType() == OperandType.CONSTANT
                && ins.getOperands().get(1).getOperandValue() == OperandType.REGISTER )
        {
            ins = instList.get(index + 1);
            if( ins.getInstructiontype() == InstructionType.CALLQ
                    && ins.getOperands().get(0).getOperandType() == OperandType.MEMORY_ADDRESS
                    && ins.getOperands().get(1).getOperandValue() == OperandType.MEMORY_ADDRESS ) //SectionName
            {
                return true;
            }
        }
        return false;
    } 
    
}
