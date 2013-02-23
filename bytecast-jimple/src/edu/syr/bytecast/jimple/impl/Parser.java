/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.jimple.api.AbstractFilter;
import edu.syr.bytecast.jimple.api.IParser;
import edu.syr.bytecast.jimple.beans.ParsedInstructionsSet;
import java.util.List;

/**
 *
 * @author Fei Qi
 */
public class Parser extends AbstractFilter implements IParser{
    private int tem_index_begin;
    // used to record a begin index temporarily
    private int tem_index_end;
    // used to record a end index temporarily
    private boolean hasElse;
    // @override
    private String java_statement;
    
    boolean doParser(List<ParsedInstructionsSet> pList, List<IInstruction> instList)
    {
        for( int i = 0; i < instList.size(); i++)
        {
            ParsedInstructionsSet tem_set = pList.get(i);
            for( int j = 0; j < tem_set.getInfo().)
            
        }
        
        
        
        
        return ;
    }
    
    
    
    
}
