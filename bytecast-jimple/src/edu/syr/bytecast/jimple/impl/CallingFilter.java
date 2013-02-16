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
 * @author Fei Qi
 */
public class CallingFilter implements IFilter {
    public boolean doTest(List<IInstruction> instList, int index)
    {
        //this is frame to define the calling section in future
        IInstruction ins = instList.get(index);
        if( ins.getInstructiontype() == InstructionType.CALLQ 
                && ins.getOperands().get(0).getOperandType() == OperandType.MEMORY_ADDRESS )
        {
            return true;
        }
        else
            return false;
    } 
    
}
