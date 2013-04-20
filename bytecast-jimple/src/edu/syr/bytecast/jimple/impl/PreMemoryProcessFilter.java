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
import java.util.List;

/**
 *
 * @author Fei Qi
 */
//this file is used to filter the pre-memory process part
public class PreMemoryProcessFilter implements IFilter {

  public boolean doTest(List<MemoryInstructionPair> instList, int index) {
    IInstruction ins = instList.get(index).getInstruction();
    int count = 0;
    if (ins.getInstructiontype().equals(InstructionType.PUSH)
            && ins.getOperands().get(0).getOperandType().equals(OperandType.REGISTER)
            && ins.getOperands().get(0).getOperandValue().equals(RegisterType.RBP)) {
      count++;
      ins = instList.get(index + count).getInstruction();
      IOperand op0 = ins.getOperands().get(0);
      IOperand op1 = ins.getOperands().get(1);
      if (ins.getInstructiontype().equals(InstructionType.MOV)
              && op0.getOperandType().equals(OperandType.REGISTER)
              && op0.getOperandValue().equals(RegisterType.RSP)
              && op1.getOperandType().equals(OperandType.REGISTER)
              && op1.getOperandValue().equals(RegisterType.RBP)) //SectionName
      {
        count++;
        ins = instList.get(index + count).getInstruction();
        if (ins.getInstructiontype().equals(InstructionType.SUB)
                && ins.getOperands().get(0).getOperandType().equals(OperandType.CONSTANT)
                && ins.getOperands().get(1).getOperandType().equals(OperandType.REGISTER)
                && ins.getOperands().get(1).getOperandValue().equals(RegisterType.RSP)) {
          count++;
          return true;
        }
      }
    }
    return false;
  }
}
