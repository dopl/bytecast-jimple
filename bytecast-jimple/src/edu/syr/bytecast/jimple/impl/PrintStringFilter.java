/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.output.*;
import edu.syr.bytecast.amd64.api.instruction.*;
import edu.syr.bytecast.amd64.api.constants.*;
import edu.syr.bytecast.jimple.api.AbstractFilter;
import edu.syr.bytecast.jimple.api.IFilter;
import java.util.List;

/**
 *
 * @author Fei Qi
 */
public class PrintStringFilter extends AbstractFilter implements IFilter{
    public boolean doTest(List<IInstruction> instList, int index)
    {
        IInstruction ins = instList.get(index);
        if( ins.getInstructiontype() == InstructionType.MOV
                && ins.getOperands().get(0).getOperandType() == OperandType.TYPE // AMD64 group will add this name
                && ins.getOperands().get(0).getString().toString() != "%s"
                && ins.getOperands().get(1).getOperandType() == OperandType.REGISTER
                && ins.getOperands().get(1).getOperandValue() == "%edi" )
        {
            ins = instList.get(index + 1);
            if(ins.getInstructiontype() == InstructionType.MOV
                && ins.getOperands().get(0).getOperandType() == OperandType.CONSTANT // AMD64 group will add this name
                && ins.getOperands().get(1).getOperandType() == OperandType.REGISTER
                && ins.getOperands().get(1).getOperandValue() == "%eax" )
            {
                ins = instList.get(index + 1);
                if(ins.getInstructiontype() == InstructionType.CALLQ
                    && ins.getOperands().get(0).getOperandType() == OperandType.MEMORY_ADDRESS 
                    && ins.getOperands().get(0).getOperandValue().toString() == "<Printf@plt>" )
                {
                    return true;
                }
            }
        }
        return false;
    } 
    
}

