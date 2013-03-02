/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import java.util.List;

/**
 *
 * @author Fei Qi
 */

// used to store different functions' all instructions
public class FunctionSet {
    private String function_name;
    
    private List<IInstruction> instruction_list;
    
    public void set_FunctionName(String _function_name)
    {
        function_name = _function_name;
    }
            
    public String get_FunctionName()
    {
        return function_name;
    }
    
    public void set_ListOfInstruction(List<IInstruction> _instruction_list)
    {
        instruction_list = _instruction_list;
    }
    
    public List<IInstruction> get_ListOfInstruction()
    {
        return instruction_list;
    }
    
}
