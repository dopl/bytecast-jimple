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
 * @author QSA
 */
public class MethodEndFilter implements IFilter{
    
    @Override
    public boolean doTest(List<MemoryInstructionPair> instList, int index) {
        //throw new UnsupportedOperationException("Not supported yet.");
        
        MemoryInstructionPair mip = instList.get(index);
        IInstruction inst = mip.getInstruction();
        if(inst.getInstructiontype() == InstructionType.LEAVEQ)
        {
            mip = instList.get(++index);
            inst = mip.getInstruction();
            if(inst.getInstructiontype() == InstructionType.RETQ)
            {
                return true;
            }
            
        }
        return false;
    }
    
}
