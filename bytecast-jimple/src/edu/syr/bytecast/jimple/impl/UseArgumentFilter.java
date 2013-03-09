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
import java.util.Map;

/**
 *
 * @author nick
 */
public class UseArgumentFilter extends AbstractFilter implements IFilter{
    public boolean doTest(Map<Long, IInstruction> instList, int index)
    {
        IInstruction ins = instList.get(index);
        if( ins.getInstructiontype() == InstructionType.MOV
                //&& ins.getOperands().get(0).getOperandType() == OperandType.MEMORY_ADDRESS // I'm not sure about this TYPE
                && ins.getOperands().get(0).getOperandValue().toString().contains("%rbp")
                && ins.getOperands().get(1).getOperandType() == OperandType.REGISTER
                && ins.getOperands().get(1).getOperandValue() == "%rax" )
        {
            ins = instList.get(index + 1);
            if(ins.getInstructiontype() == InstructionType.ADD
                && ins.getOperands().get(0).getOperandType() == OperandType.CONSTANT
                && ins.getOperands().get(1).getOperandType() == OperandType.REGISTER
                && ins.getOperands().get(1).getOperandValue() == "%rax" )
            {
                ins = instList.get(index + 1);
                if(ins.getInstructiontype() == InstructionType.MOV
                    //&& ins.getOperands().get(0).getOperandType() == OperandType.MEMORY_ADDRESS // I'm not sure about this TYPE
                    && ins.getOperands().get(0).getOperandValue().toString().contains("(%rax)")
                    && ins.getOperands().get(1).getOperandType() == OperandType.REGISTER
                    && ins.getOperands().get(1).getOperandValue() == "%rax" )
                {
                    return true;
                }
            }
        }
        return false;
    } 
    
}
