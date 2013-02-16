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