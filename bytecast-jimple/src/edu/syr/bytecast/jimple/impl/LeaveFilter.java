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
import java.util.Map;

/**
 *
 * @author nick
 */
public class LeaveFilter implements IFilter{
    public boolean doTest(Map<Long, IInstruction> instList, int index)
    {
        IInstruction ins = instList.get(index);
        if( ins.getInstructiontype() == InstructionType.LEAVEQ )         // AMD64 group will add this TYPE
        {
            ins = instList.get(index + 1);
            if(ins.getInstructiontype() == InstructionType.RETQ ) // AMD64 group will add this TYPE
            {
                return true;
            }
        }
        return false;
    }
    
}
