/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.jimple.api.IFilter;
import java.util.List;

/**
 *
 * @author Fei Qi
 */
public class LeaveFilter implements IFilter{
    public boolean doTest(List<MemoryInstructionPair> instList, int index)
    {
        IInstruction ins = instList.get(index).getInstruction();
        if( ins.getInstructiontype().equals(InstructionType.LEAVEQ))         // AMD64 group will add this TYPE
        {
            ins = instList.get(index + 1).getInstruction();
            if(ins.getInstructiontype().equals(InstructionType.RETQ)) // AMD64 group will add this TYPE
            {
                return true;
            }
        }
        return false;
    }
    
}
