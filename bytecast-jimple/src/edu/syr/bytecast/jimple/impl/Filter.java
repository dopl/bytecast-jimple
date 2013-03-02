/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import java.util.List;

/**
 *
 * @author nick
 */
public abstract class Filter {
    abstract boolean doTest(List<IInstruction> instList, int index);
    
}
