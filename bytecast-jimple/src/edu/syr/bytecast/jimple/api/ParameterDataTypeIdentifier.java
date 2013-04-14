/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.api;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import java.util.List;


public class ParameterDataTypeIdentifier  {
    
    
    static JavaDataType getDataType(ParameterInfo info, List<MemoryInstructionPair> ins){
        RegisterType currentRegister=null;
        for(MemoryInstructionPair in : ins){
            if(!(in.getInstruction().getInstructiontype()==InstructionType.MOV ||
                in.getInstruction().getInstructiontype()==InstructionType.MOVSX ||
                in.getInstruction().getInstructiontype()==InstructionType.MOVZX ))
                continue;
             
            IOperand op1 = in.getInstruction().getOperands().get(0);
            IOperand op2 = in.getInstruction().getOperands().get(1);
            if(currentRegister==null){
                if(op1.equals(info.getParamStackAddress()) && op2.getOperandType()==OperandType.REGISTER) {
                    currentRegister=(RegisterType)op2.getOperandValue();
                }
            }else{
                
            }
        }
        
        return JavaDataType.INT;
    }
    
}
