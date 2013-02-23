/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.jimple.api.AbstractFilter;
import edu.syr.bytecast.jimple.api.IFilter;
import java.util.List;


/**
 *
 * @author Ray
 */
public class IFFilter extends AbstractFilter implements IFilter{
    private int begin_if;
    private int end_if;
    private boolean hasElse;
    private int begin_else;
    private int end_else;
    
    // constructor
    IFFilter()
    {
        begin_if = 0;
        end_if = 0;
        begin_else = 0;
        end_else = 0;
        hasElse = false;
    }
    
    // get the begin index of if part
    public int getBegin_If()
    {
        return begin_if;
    }
    
    // get the end index of if part
    public int getEnd_If()
    {
        return end_if;
    }
    
    // get the begin index of else part
    public int getBegin_Else()
    {
        return begin_else;
    }
    // get the end index of else part
    public int getEnd_Else()
    {
        return end_else;
    }
    
    // get whether the if statement has else part
    public boolean hasElse()
    {
        return hasElse;
    }
    

  @Override
  public boolean doTest(List<IInstruction> instList, int index) {
    // ray write code here
    IInstruction  instruction = instList.get(index); //get the instruction from insList depending on the index
    //<--------------Test <if> Statement(first instruction)-------------->
      if(instruction.getInstructiontype() == InstructionType.CMPQ) //check whether or not it is if's first statement
      {
         IInstruction  instructionNext = instList.get(index+1); //get next instruction
          
        if(instructionNext.getInstructiontype() == InstructionType.JE ||instructionNext.getInstructiontype()== InstructionType.JB 
                || instructionNext.getInstructiontype()== InstructionType.JAE || instructionNext.getInstructiontype()== InstructionType.JMP 
                || instructionNext.getInstructiontype()== InstructionType.JNE || instructionNext.getInstructiontype()== InstructionType.JLE)//JLE and JQ    
        {
            begin_if = index + 1;
            String temp = instructionNext.getOperands().get(0).getOperandValue().toString();
            for( int i = index +1; i < instList.size(); i++)
            {
                if(instList.get(i).getMemoryAddress().toString() == temp)
                {
                    end_if = i - 1;
                }
                if(instList.get(i - 1).getInstructiontype() == InstructionType.JE || instList.get(i - 1).getInstructiontype() == InstructionType.JB 
                        || instList.get(i - 1).getInstructiontype() == InstructionType.JAE || instList.get(i - 1).getInstructiontype() == InstructionType.JMP 
                        || instList.get(i - 1).getInstructiontype() == InstructionType.JNE || instList.get(i - 1).getInstructiontype() == InstructionType.JLE )
                {
                    hasElse = true;
                    begin_else = i;
                    temp = instList.get(i - 1).getOperands().get(0).getOperandValue().toString();
                    for( int j = i; j < instList.size(); j++)
                    {
                        if(instList.get(j).getMemoryAddress().toString() == temp)
                        {
                            end_else = j - 1;
                            break;
                        }
                    }
                }
                break;
            }
            return true;
        }   
      }
      return false;
    //
   }//throw new UnsupportedOperationException("Not supported yet.");
  }