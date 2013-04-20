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
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.jimple.api.IFilter;
import java.util.List;

public class IfFilter implements IFilter{
    public boolean doTest(List<MemoryInstructionPair> instList, int index)
    {
        IInstruction ins = instList.get(index).getInstruction();
        int count = 0;
        if( ins.getInstructiontype().equals(InstructionType.CMPL)
                && ins.getOperands().get(0).getOperandType().equals(OperandType.CONSTANT)
                && ins.getOperands().get(1).getOperandType().equals(OperandType.MEMORY_EFFECITVE_ADDRESS ))
        {
            count++;
            ins = instList.get(index + count).getInstruction();
            if(ins.getInstructiontype().equals(InstructionType.JNE)
                    || ins.getInstructiontype().equals(InstructionType.JE)
                    || ins.getInstructiontype().equals(InstructionType.JLE)
                    || ins.getInstructiontype().equals(InstructionType.JGE)
                    || ins.getInstructiontype().equals(InstructionType.JL)
                    || ins.getInstructiontype().equals(InstructionType.JG)
                    && ins.getOperands().get(0).getOperandType().equals(OperandType.MEMORY_EFFECITVE_ADDRESS)) //SectionName
            {
                count++;
                return true;
                }
            }
        return false;
    } 
}

//public class IfFilter extends Filter{
//    private int begin_if;
//    private int end_if;
//    private boolean hasElse;
//    private int begin_else;
//    private int end_else;
//    
//    // constructor
//    IfFilter()
//    {
//        begin_if = 0;
//        end_if = 0;
//        begin_else = 0;
//        end_else = 0;
//        hasElse = false;
//    }
//    
//    // get the begin index of if part
//    public int getBegin_If()
//    {
//        return begin_if;
//    }
//    
//    // get the end index of if part
//    public int getEnd_If()
//    {
//        return end_if;
//    }
//    
//    // get the begin index of else part
//    public int getBegin_Else()
//    {
//        return begin_else;
//    }
//    // get the end index of else part
//    public int getEnd_Else()
//    {
//        return end_else;
//    }
//    
//    // get whether the if statement has else part
//    public boolean hasElse()
//    {
//        return hasElse;
//    }
//    
//
////  @Override
//  public boolean doTest(List<IInstruction> instList, ParsedInstructionsSet parsed_set) {
//    // ray write code here
//    IInstruction  instruction = instList.get(index); //get the instruction from insList depending on the index
//    //<--------------Test <if> Statement(first instruction)-------------->
//      if(instruction.getInstructiontype().equals(InstructionType.CMPQ) //check whether or not it is if's first statement
//      {
//         IInstruction  instructionNext = instList.get(index+1); //get next instruction
//          
//        if(instructionNext.getInstructiontype().equals(InstructionType.JE ||instructionNext.getInstructiontype()== InstructionType.JB 
//                || instructionNext.getInstructiontype()== InstructionType.JAE || instructionNext.getInstructiontype()== InstructionType.JMP 
//                || instructionNext.getInstructiontype()== InstructionType.JNE || instructionNext.getInstructiontype()== InstructionType.JLE)//JLE and JQ    
//        {
//            begin_if = index + 1;
//            String temp = instructionNext.getOperands().get(0).getOperandValue().toString();
//            for( int i = index +1; i < instList.size(); i++)
//            {
//                if(instList.get(i).getMemoryAddress().toString().equals(temp)
//                {
//                    end_if = i - 1;
//                }
//                if(instList.get(i - 1).getInstructiontype().equals(InstructionType.JE || instList.get(i - 1).getInstructiontype().equals(InstructionType.JB 
//                        || instList.get(i - 1).getInstructiontype().equals(InstructionType.JAE || instList.get(i - 1).getInstructiontype().equals(InstructionType.JMP 
//                        || instList.get(i - 1).getInstructiontype().equals(InstructionType.JNE || instList.get(i - 1).getInstructiontype().equals(InstructionType.JLE )
//                {
//                    hasElse = true;
//                    begin_else = i;
//                    temp = instList.get(i - 1).getOperands().get(0).getOperandValue().toString();
//                    for( int j = i; j < instList.size(); j++)
//                    {
//                        if(instList.get(j).getMemoryAddress().toString().equals(temp)
//                        {
//                            end_else = j - 1;
//                            break;
//                        }
//                    }
//                }
//                break;
//            }
//            return true;
//        }   
//      }
//      return false;
//    //
//   }//throw new UnsupportedOperationException("Not supported yet.");
//  }
