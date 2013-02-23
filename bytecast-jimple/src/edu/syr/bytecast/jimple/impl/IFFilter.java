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
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.jimple.api.AbstractFilter;
import edu.syr.bytecast.jimple.api.IFilter;
import java.util.List;

public class IFFilter extends AbstractFilter implements IFilter{

  @Override
  public boolean doTest(List<IInstruction> instList, int index) {
    // ray write code here
    IInstruction  instruction = instList.get(index); //get the instruction from insList depending on the index
    //<--------------Test <if> Statement(first instruction)-------------->
      if(instruction.getInstructiontype() == InstructionType.CMPQ) //check whether or not it is if's first statement
      {
         IInstruction  instructionNext = instList.get(index+1); //get next instruction
          
        if(instructionNext.getInstructiontype() == InstructionType.JE ||instructionNext.getInstructiontype()== InstructionType.JB)//JLE and JQ    
        {
            return true;
        }   
      }
      
       //<--------------Test <if> Statement(second instruction)-------------->
      else if(instruction.getInstructiontype() == InstructionType.JE) //check whether or not it is if's first statement
      {
         IInstruction  instructionNext = instList.get(index-1); //get next instruction
          
        if(instructionNext.getInstructiontype() == InstructionType.CMPQ ||instructionNext.getInstructiontype()== InstructionType.JB)//JLE and JQ    
        { 
            return true;
        }   
      }
       
      else 
      {
          return false;
      }
      
      return false;
    //
   }//throw new UnsupportedOperationException("Not supported yet.");
  }