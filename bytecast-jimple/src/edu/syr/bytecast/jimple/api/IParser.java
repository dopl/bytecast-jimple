/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.api;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.jimple.beans.ParsedInstructionsSet;
import java.util.List;

/**
 *
 * @author Fei Qi
 */
public interface IParser {
    boolean doParser(List<ParsedInstructionsSet> pList, List<IInstruction> instList);
    // provide a interface to analyze all the INstructionSet and output the whole object which
    // can be used to convert to .jimple 
    
}
