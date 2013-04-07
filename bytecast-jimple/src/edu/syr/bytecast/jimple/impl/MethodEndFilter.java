/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.jimple.api.IFilter;
import edu.syr.bytecast.jimple.api.MethodInfo;
import edu.syr.bytecast.jimple.api.Methods;
import java.util.List;

/**
 *
 * @author QSA
 */
public class MethodEndFilter implements IFilter{
    private static int count = 0;
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
                //int ind = Methods.methods.size() - 1;
                MethodInfo m_info = Methods.methods.get(count);
                m_info.setEndMemAddress(mip.getmInstructionAddress());
                count++;
                return true;
            }
            
        }
        return false;
    }
    
}
