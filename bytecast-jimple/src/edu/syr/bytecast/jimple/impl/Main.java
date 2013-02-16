/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import com.sun.xml.internal.fastinfoset.UnparsedEntity;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.jimple.beans.ParsedInstructionsSet;
import java.util.List;

/**
 *
 * @author pahuja
 */
public class Main {
  
  public static void main(String[] args)
  {
    IFFilter filter = new IFFilter();
    filter.setFilter_Name("IF");
    filter.setInst_Count(3);
    List<IInstruction> uiList = null;//get the list from amd64
    List<ParsedInstructionsSet> pList = null;//create new List
    filter.scan(uiList, pList, filter);
  }
  
}
