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
import java.util.List;

/**
 *
 * @author nick
 */
//this file is used to filter the pre-memory process part
public class SetArgvAndArgcFilter implements IFilter {

  @Override
  public boolean doTest(List<MemoryInstructionPair> instList, int index) {
    IInstruction ins = instList.get(index).getInstruction();
    int count = 0;
    if (ins.getInstructiontype().equals(InstructionType.MOV)
            && ins.getOperands().get(0).getOperandType().equals(OperandType.REGISTER)
            && ins.getOperands().get(0).getOperandValue().equals(RegisterType.EDI)
            //&& ins.getOperands().get(1).getOperandType() == OperandType.MEMORY_ADDRESS
            && ins.getOperands().get(1).getOperandValue().equals(RegisterType.RBP)) {
      count++;
      ins = instList.get(index + count).getInstruction();
      if (ins.getInstructiontype().equals(InstructionType.MOV)
              && ins.getOperands().get(0).getOperandType().equals(OperandType.REGISTER)
              && ins.getOperands().get(0).getOperandValue().equals(RegisterType.RSI)
              //&& ins.getOperands().get(1).getOperandType() == OperandType.MEMORY_ADDRESS
              && ins.getOperands().get(1).getOperandValue().equals(RegisterType.RBP)) //SectionName
      {
        count++;
        return true;
      }
    }
    return false;
  }  
}
