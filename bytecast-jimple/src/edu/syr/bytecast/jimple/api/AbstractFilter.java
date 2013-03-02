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

package edu.syr.bytecast.jimple.api;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.jimple.beans.JInstructionInfo;
import edu.syr.bytecast.jimple.beans.ParsedInstructionsSet;
import java.util.ArrayList;
import java.util.List;

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
      int index = 0;
      int result = 0;
      while( index < unparsed_list_size)
      {
          result = filter.doTest(unparsed_inst_list, index);
          if( result != 0 )
          {
              index = result;
              continue;
          }
          index++;
      }
      for(int i = 0; i< )
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
