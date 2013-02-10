/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.api;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;

/**
 *
 * @author QSA
 */
public interface IFilterRule {
    boolean doTest(IInstruction inst);
}
