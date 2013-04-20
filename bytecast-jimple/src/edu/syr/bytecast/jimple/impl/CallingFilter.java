/*
 * This file is part of Bytecast.
 *
 * Bytecast is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Bytecast is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bytecast.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.constants.OperandTypeMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandSectionName;
import edu.syr.bytecast.jimple.api.IFilter;
import edu.syr.bytecast.jimple.api.MethodInfo;
import edu.syr.bytecast.jimple.api.Methods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fei Qi
 */
public class CallingFilter implements IFilter {

  public boolean doTest(List<MemoryInstructionPair> instList, int index) {
    IInstruction ins = instList.get(index).getInstruction();
    if (ins.getInstructiontype().equals(InstructionType.CALLQ)) {
        String funcName = ins.getOperands().get(1).getOperandValue().toString();
      if (ins.getOperands().get(0).getOperandType().equals(OperandType.MEMORY_EFFECITVE_ADDRESS)//OperandType.MEMORY_PHYSICAL_ADDRESS )
              && !funcName.contains("printf")) //the value of the second operand should be the SectionName, it hasn't been set up in AMD64 api
      {
          long val = ((OperandTypeMemoryEffectiveAddress)ins.getOperands().get(0).getOperandValue()).getOffset();
          //OperandSectionName operandSectionName = (OperandSectionName)ins.getOperands().get(1);
          String name = (String)ins.getOperands().get(1).getOperandValue();
          getName(val, name);
        return true;
      }
    }
    return false;
  }
  
  private void getName(long val, String name)
  {
      ArrayList<MethodInfo> methods = Methods.methods;
      for(int i=0;i<methods.size();i++)
      {
          if(methods.get(i).getStartMemeAddress() == val)
          {
              methods.get(i).setMethodName(name);
              break;
          }
      }
  }
  
}
