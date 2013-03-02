/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.jimple.beans.jimpleBean.JInstructionInfo;
import java.util.List;

/**
 *
 * @author QSA
 */
public class ParsedInstructionsSet {
    private List<IInstruction> instructions_List;
    private JInstructionInfo info;
    
    public List getInstructions_List()
    {
        return this.instructions_List;
    }
    
    public JInstructionInfo getInfo()
    {
        return this.info;
    }
    
    public void setInstructions_List(List<IInstruction> list)
    {
        this.instructions_List= list;
    }
    
    public void setInfo(JInstructionInfo type)
    {
        this.info = type;
    }
    
}
