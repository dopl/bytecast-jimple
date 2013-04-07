/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.jimple.api.IFilter;
import edu.syr.bytecast.jimple.api.MethodInfo;
import edu.syr.bytecast.jimple.api.Methods;
import edu.syr.bytecast.jimple.beans.Utility.Registers;
import java.util.List;

/**
 *
 * @author QSA
 */
public class MethodStartFilter implements IFilter{

    @Override
    public boolean doTest(List<MemoryInstructionPair> instList, int index) {
        //throw new UnsupportedOperationException("Not supported yet.");
        
        MemoryInstructionPair mip = instList.get(index);
        IInstruction inst = mip.getInstruction();
        if(inst.getInstructiontype() == InstructionType.PUSH)
        {
            mip = instList.get(++index);
            inst = mip.getInstruction();
            if(inst.getInstructiontype() == InstructionType.MOV)
            {
                if(inst.getOperands().get(0).getOperandType() == OperandType.REGISTER && 
                        inst.getOperands().get(0).getOperandValue() == RegisterType.RSP &&
                        inst.getOperands().get(1).getOperandType() == OperandType.REGISTER && 
                        inst.getOperands().get(1).getOperandValue() == RegisterType.RBP
                        )
                {
                    MethodInfo m_info = new MethodInfo();
                    mip = instList.get(--index);
                    m_info.setStartMemAddress(mip.getmInstructionAddress());
                    System.out.println(Methods.methods.size());
                    Methods.methods.add(m_info);
                    return true;
                }
            }
            
        }
        return false;
    }
    
    
}
