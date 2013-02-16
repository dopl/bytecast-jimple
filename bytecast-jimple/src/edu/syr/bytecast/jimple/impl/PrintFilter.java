/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.output.*;
import edu.syr.bytecast.amd64.api.instruction.*;
import edu.syr.bytecast.amd64.api.constants.*;
import edu.syr.bytecast.jimple.api.IFilter;
import java.util.List;

/**
 *
 * @author Fei Qi
 */
public class PrintFilter implements IFilter {
    public boolean doTest(List<IInstruction> instList, int index)
    {
        IInstruction ins = instList.get(index);
        if( ins.getInstructiontype() == InstructionType.CALLQ 
                && ins.getOperands().get(0).getOperandType() == OperandType.MEMORY_ADDRESS
                && ins.getOperands().get(1).getOperandValue() == "<printf@plt>" )
        {
            return true;
        }
        else
            return false;
    } 
    
}
