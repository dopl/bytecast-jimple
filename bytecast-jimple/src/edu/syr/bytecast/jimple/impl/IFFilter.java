/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.jimple.api.AbstractFilter;
import edu.syr.bytecast.jimple.api.IFilter;
import java.util.List;

/**
 *
 * @author QSA
 */
public class IFFilter extends AbstractFilter implements IFilter{

  @Override
  public boolean doTest(List<IInstruction> instList, int index) {
    // write code here
    
    return true;
    //
    //throw new UnsupportedOperationException("Not supported yet.");
  }

  
  
}
