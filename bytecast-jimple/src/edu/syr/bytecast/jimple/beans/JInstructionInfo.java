/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans;

/**
 *
 * @author QSA
 */
public class JInstructionInfo {
    private String instruction_name;
    private int instructions_count;
    private int start_index;
    
    public String getInstruction_Name()
    {
        return this.instruction_name;
    }
    
    public int getInstructions_Count()
    {
        return this.instructions_count;
    }
    
    public int getStart_Index()
    {
        return this.start_index;
    }
    
    public void setInstruction_Name(String name)
    {
        this.instruction_name = name;
    }
    
    public void setInstructions_Count(int count)
    {
        this.instructions_count = count;
    }
    
    protected void setStart_Index(int index)
    {
        this.start_index = index;
    }
}
