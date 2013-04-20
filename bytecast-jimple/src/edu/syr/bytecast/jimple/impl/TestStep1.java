/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;   

import edu.syr.bytecast.amd64.api.constants.IBytecastAMD64;
import edu.syr.bytecast.amd64.api.output.IExecutableFile;
import edu.syr.bytecast.amd64.api.output.ISection;
import edu.syr.bytecast.amd64.test.DepcrecatedMock;
import edu.syr.bytecast.jimple.api.Method;
import edu.syr.bytecast.jimple.beans.ParsedInstructionsSet;
import edu.syr.bytecast.util.Paths;
import java.util.*;

/**
 *
 * @author peike
 */
public class TestStep1 {
  
  public static void main(String[] args) {
    Map<Method, List<ParsedInstructionsSet>> filter_result = new HashMap<Method, List<ParsedInstructionsSet>>();
    // get all the sections from the IExecutableFile
    Paths.v().setRoot("/home/nick/code/bytecast");
    try {
      Paths.v().parsePathsFile();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    IBytecastAMD64 testdata = new DepcrecatedMock();
    IExecutableFile exe_file = testdata.buildInstructionObjects();

    ISection wholeSection = exe_file.getSectionsWithInstructions().get(0);

    
    

    //call the PatternSeperator to filter all the section
    PatternSeperator patt_Seperator = new PatternSeperator();
    filter_result = patt_Seperator.doSeperate(wholeSection);
    
    
    Comparator<ParsedInstructionsSet> mysorter = new Comparator<ParsedInstructionsSet>() {

      @Override
      public int compare(ParsedInstructionsSet pis1, ParsedInstructionsSet pis2) {
            if (pis1.getInfo().getStart_Index() > pis2.getInfo().getStart_Index()) {
                return 1;
            } else if (pis1.getInfo().getStart_Index() == pis2.getInfo().getStart_Index()) {
                return 0;
            } else {
                return -1;
            }
        }
    };
    
    Set<Method> methods = filter_result.keySet();
    
    for (Method m : methods) {
      List<ParsedInstructionsSet> resultSet = filter_result.get(m);
      Collections.sort(resultSet, mysorter);
      
      
      System.out.println("method: " + m.getMethodInfo().getMethodName());
      for (ParsedInstructionsSet pis : resultSet) {
        System.out.println("pattern name: " + pis.getInfo().getInstruction_Name());
        System.out.println("pattern start index: " + pis.getInfo().getStart_Index());
      }
    }
  }
}
