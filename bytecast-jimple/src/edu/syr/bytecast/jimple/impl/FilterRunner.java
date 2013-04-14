/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.jimple.api.IFilter;
import edu.syr.bytecast.jimple.beans.FilterInfo;
import edu.syr.bytecast.jimple.beans.JInstructionInfo;
import edu.syr.bytecast.jimple.beans.ParsedInstructionsSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fei Qi
 */
public class FilterRunner {
    
    public void run(List<MemoryInstructionPair> unparsed_inst_list, List<ParsedInstructionsSet> parsed_inst_list, IFilter filter, FilterInfo filter_info){
      int count = filter_info.getInst_Count();
      int back = 0;
      for(int j = 0; j < unparsed_inst_list.size(); j++){
        if(filter.doTest(unparsed_inst_list, j)){
          ParsedInstructionsSet pis= new ParsedInstructionsSet();
          // get the instruction
          if(filter_info.getFilter_Name().equals("Calling"))
          {
              while(true)
              {
                    IInstruction ins = unparsed_inst_list.get(j - back - 1).getInstruction();
                    if( ins.getInstructiontype() == InstructionType.MOV
                            && ins.getOperands().get(0).getOperandType() == OperandType.REGISTER
                            && ins.getOperands().get(1).getOperandType() == OperandType.REGISTER
                            && ( ins.getOperands().get(1).getOperandValue().toString() == "%edi" || ins.getOperands().get(1).getOperandValue().toString() == "%esi"))
                    {
                        back ++;
                    }
                    else
                        break;
              }
              filter_info.setInst_Count(count + back);
          }
          else if(filter_info.getFilter_Name().equals("Printf"))
          {
              while(true)
              {
                    IInstruction ins = unparsed_inst_list.get(j - back - 1).getInstruction();
                    if( ins.getInstructiontype() == InstructionType.MOV
                            && ins.getOperands().get(0).getOperandType() == OperandType.CONSTANT
                            && ins.getOperands().get(1).getOperandType() == OperandType.REGISTER
                            && ins.getOperands().get(1).getOperandValue().toString() ==  "%eax") //SectionName
                    {
                        back ++;
                        break;
                    }
                    back ++;
              }
              filter_info.setInst_Count(back + count);
          }
          List<MemoryInstructionPair> inst_list = new ArrayList<MemoryInstructionPair>();
          int index = j - back;
          for(int k = index; k < index + filter_info.getInst_Count() ; k++){
            inst_list.add(unparsed_inst_list.get(k));
          }
          // define the Jinfo
          JInstructionInfo jinfo = new  JInstructionInfo();
          jinfo.setInstruction_Name(filter_info.getFilter_Name());
          jinfo.setInstructions_Count(filter_info.getInst_Count());
          jinfo.setStart_Index(index);
          // set the detail into ParsedInstructionSet
          pis.setInfo(jinfo);
          pis.setInstructions_List(inst_list);
          parsed_inst_list.add(pis);
          j = index + filter_info.getInst_Count();
        }
      }
    }
}
