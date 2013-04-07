/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.jimple.api.IFilter;
import edu.syr.bytecast.jimple.beans.Utility;
import java.util.List;

/**
 *
 * @author QSA
 */
public class TypeCastFilter implements IFilter{

    @Override
    public boolean doTest(List<MemoryInstructionPair> instList, int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /*
    @Override
    public boolean doTest(List<IInstruction> instList, int index) {
        IInstruction inst = instList.get(index);
        if(inst.getInstructiontype() == InstructionType.MOV){
            IOperand op1 = inst.getOperands().get(0);
            if(op1.getOperandType() == OperandType.REGISTER && op1.getOperandValue() == RegisterType.EDI){
                IOperand op2 = inst.getOperands().get(1);    
                if(op2.getOperandType() == OperandType.MEMORY_EFFECITVE_ADDRESS){
                    Utility.registerMemoryMap.put(RegisterType.EDI, op2.getOperandValue());
                    return true;
                }
            }
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }*/
    
}
