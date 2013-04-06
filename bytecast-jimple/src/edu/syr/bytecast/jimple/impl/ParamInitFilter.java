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
import edu.syr.bytecast.jimple.api.ParameterInfo;
import java.util.List;
import java.util.Map;

/**
 *
 * @author QSA
 */
public class ParamInitFilter implements IFilter{
    //Function Parameter initialization filter
    public boolean doTest(List<MemoryInstructionPair> instList, int index) {
        MemoryInstructionPair mip = instList.get(index);
        IInstruction inst = mip.getInstruction(); 
        //IInstruction  inst = instList.get(index);        
         int op_index = 0;
         if(inst.getInstructiontype() == InstructionType.MOV) {
             if(inst.getOperands().get(op_index).getOperandType() == OperandType.REGISTER &&
                     inst.getOperands().get(++op_index).getOperandType() == OperandType.MEMORY_EFFECITVE_ADDRESS){
                 
                 //ParameterInfo info = new ParameterInfo((String)inst.getOperands().get(op_index).getOperandValue());
                 
                 return true;
             }
             return false;
         }
         return false;
         
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
