/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.jimple.api.AbstractFilter;
import edu.syr.bytecast.jimple.api.IFilter;
import edu.syr.bytecast.jimple.api.IFilterRule;
import edu.syr.bytecast.jimple.beans.JInstructionInfo;
import edu.syr.bytecast.jimple.beans.ParsedInstructionsSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author QSA
 */
public class FilterIfStatement extends AbstractFilter implements IFilter{

  @Override
  public void scan(List<IInstruction> unparsed_inst_list, List<ParsedInstructionsSet> parsed_inst_list, List<IFilterRule> rulesList) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

    /*
    @Override
    public void scan(List<IInstruction> unparsed_inst_list, List<ParsedInstructionsSet> parsed_inst_list, List<IFilterRule> rulesList) {
      int index = 0;  
      int rules_size = rulesList.size(); 
      int unparsed_list_size = unparsed_inst_list.size();
      
      int i = 0;
      //for(int i=0;i<rules_size;i++){
        for(int j=index;j<unparsed_list_size;j++){
          if(rulesList.get(i).doTest(unparsed_inst_list.get(j))){
            i++;
            //index = j+1;
            //break;
          }
          index = j+1;
          if(index >= unparsed_list_size)
            break;
          if(i >= rules_size)
          {
            i = 0;
            
            ParsedInstructionsSet pis= new ParsedInstructionsSet();
            int u = j - rules_size;
            List<IInstruction> inst_list = new ArrayList<IInstruction>();
            for(int k=u;k<j;k++)
              inst_list.add(unparsed_inst_list.get(k));
            
            JInstructionInfo jinfo = new  JInstructionInfo();
            jinfo.setInstruction_Name("if");
            jinfo.setInstructions_Count(rules_size);
            pis.setInfo(jinfo);
            pis.setInstructions_List(inst_list);
            parsed_inst_list.add(pis);
            //Add instructions to parsed instructions
            
          }
        }
      //}
    }*/
}
