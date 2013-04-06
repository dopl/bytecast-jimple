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
    
    public void scan(List<MemoryInstructionPair> unparsed_inst_list, List<ParsedInstructionsSet> parsed_inst_list, IFilter filter, FilterInfo filter_info){//, String type, int count) {
      int unparsed_list_size = unparsed_inst_list.size();
      int count = filter_info.getInst_Count();
      String type = filter_info.getFilter_Name();
      for(int j=0;j<unparsed_list_size;j++){
        if(filter.doTest(unparsed_inst_list, j)){
          ParsedInstructionsSet pis= new ParsedInstructionsSet();
          List<MemoryInstructionPair> inst_list = new ArrayList<MemoryInstructionPair>();
          for(int k = j; k < j+count; k++){
            inst_list.add(unparsed_inst_list.get(k));
          }
          
          JInstructionInfo jinfo = new  JInstructionInfo();
          jinfo.setInstruction_Name(type);
          jinfo.setInstructions_Count(count);
          jinfo.setStart_Index(j);
          pis.setInfo(jinfo);
          pis.setInstructions_List(inst_list);
          parsed_inst_list.add(pis);
          j = j + count;
          j--;
        }
      }
    }
}
