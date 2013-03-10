/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.jimple.beans.jimpleBean.ParsedInstructionsSet;
import java.util.List;

/**
 *
 * @author Fei Qi
 */
public abstract class Filter {
    public abstract boolean doTest(List<IInstruction> inst_list, ParsedInstructionsSet parsed_set);
    
}
