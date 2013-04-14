/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.jimple.api.IFilter;
import edu.syr.bytecast.jimple.beans.FilterInfo;
import edu.syr.bytecast.jimple.beans.JInstructionInfo;
import edu.syr.bytecast.jimple.beans.ParsedInstructionsSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author QSA
 */
public class FilterScanner {
    
    public void scan(List<MemoryInstructionPair> unparsed_inst_list, List<ParsedInstructionsSet> parsed_inst_list, IFilter filter, FilterInfo filter_info){
      int count = filter_info.getInst_Count();
      for(int j=0;j<unparsed_inst_list.size();j++){
        if(filter.doTest(unparsed_inst_list, j)){
          ParsedInstructionsSet pis= new ParsedInstructionsSet();
          // get the instruction
          List<MemoryInstructionPair> inst_list = new ArrayList<MemoryInstructionPair>();
          for(int k = j; k < j+count; k++){
            inst_list.add(unparsed_inst_list.get(k));
          }
          // define the Jinfo
          JInstructionInfo jinfo = new  JInstructionInfo();
          jinfo.setInstruction_Name(filter_info.getFilter_Name());
          jinfo.setInstructions_Count(count);
          jinfo.setStart_Index(j);
          // set the detail into ParsedInstructionSet
          pis.setInfo(jinfo);
          pis.setInstructions_List(inst_list);
          parsed_inst_list.add(pis);
          j = j + count;
          j--;
        }
      }
    }
}
