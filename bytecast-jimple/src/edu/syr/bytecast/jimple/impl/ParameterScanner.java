/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.jimple.api.MethodInfo;
import edu.syr.bytecast.jimple.api.ParameterInfo;
import edu.syr.bytecast.jimple.beans.ParsedInstructionsSet;
import java.util.List;

/**
 *
 * @author QSA
 */
public class ParameterScanner {
    
    public void getParameters(List<ParsedInstructionsSet> pis_list, MethodInfo m_info)
    {
        int list_size= pis_list.size();
        
        for(int i=0;i<list_size;i++)
        {
            ParsedInstructionsSet pis = pis_list.get(i);
            if(pis.getInfo().getInstruction_Name().equalsIgnoreCase("ParameterInit"))
            {
                //int count = pis.getInfo().getInstructions_Count();
                //
                IInstruction inst = pis.getInstructions_List().get(0);
                ParameterInfo info = new ParameterInfo((String)inst.getOperands().get(0).getOperandValue());
                m_info.parameters.add(info);
            }
        }
    }
    
}
