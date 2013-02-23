/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.api;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.jimple.beans.JInstructionInfo;
import edu.syr.bytecast.jimple.beans.ParsedInstructionsSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author QSA
 */
public abstract class AbstractFilter {
  
  private int inst_count;
  private String filter_name;
  
  public int getInst_Count()
  {
    return inst_count;
  }
  
  public void setInst_Count(int count)
  {
    inst_count = count;
  }
  
  public String getFilter_Name()
  {
    return filter_name;
  }
  
  public void setFilter_Name(String name)
  {
    filter_name = name; 
  }
  
  public void scan(List<IInstruction> unparsed_inst_list, List<ParsedInstructionsSet> parsed_inst_list, IFilter filter){//, String type, int count) {
      int unparsed_list_size = unparsed_inst_list.size();
      int count = getInst_Count();
      String type = getFilter_Name();
      for(int j=0;j<unparsed_list_size;j++){
        if(filter.doTest(unparsed_inst_list, j)){
          ParsedInstructionsSet pis= new ParsedInstructionsSet();
          List<IInstruction> inst_list = new ArrayList<IInstruction>();
          for(int k = j; k < j+count; k++)
            inst_list.add(unparsed_inst_list.get(k));
          
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
