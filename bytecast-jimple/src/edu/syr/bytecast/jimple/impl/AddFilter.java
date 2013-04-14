/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.jimple.api.IFilter;
import java.util.List;

/**
 *
 * @author QSA
 */
public class AddFilter implements IFilter{
    @Override
    
    public boolean doTest(List<MemoryInstructionPair> instList, int index) {
        //throw new UnsupportedOperationException("Not supported yet.");
        MemoryInstructionPair mip = instList.get(index);
        IInstruction inst = mip.getInstruction();
         int op_index = 0;
         if(inst.getInstructiontype() == InstructionType.MOV) {
             if(inst.getOperands().get(op_index).getOperandType() == OperandType.MEMORY_EFFECITVE_ADDRESS && 
                     inst.getOperands().get(++op_index).getOperandType() == OperandType.REGISTER){
                 mip = instList.get(index+1);
                 inst = mip.getInstruction();
                 op_index = 0;
                 if(inst.getInstructiontype() == InstructionType.MOV){
                     if(inst.getOperands().get(op_index).getOperandType() == OperandType.MEMORY_EFFECITVE_ADDRESS && 
                     inst.getOperands().get(++op_index).getOperandType() == OperandType.REGISTER){
                         mip = instList.get(index+2);
                         inst = mip.getInstruction();
                         if(inst.getInstructiontype() == InstructionType.LEA){
                            return true;
                            }
                        }                 
                    }
                }
            }
         return false;
         
    }
    
}
