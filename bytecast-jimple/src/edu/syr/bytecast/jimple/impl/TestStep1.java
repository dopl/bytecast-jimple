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
import edu.syr.bytecast.jimple.api.MethodInfo;
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
    Paths.v().setRoot("/home/peike/code/bytecast");
    try {
      Paths.v().parsePathsFile();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    IBytecastAMD64 testdata = new DepcrecatedMock();
    IExecutableFile exe_file = testdata.buildInstructionObjects();

    ISection wholeSection = exe_file.getSectionsWithInstructions().get(0);

    List<MethodInfo> m_info = new ArrayList<MethodInfo>();


    MethodInfo mi1 = new MethodInfo();
    mi1.setMethodName("main");
    mi1.setStartIndex(0);
    mi1.setEndIndex(31);
    mi1.setStartMemAddress(0x400542L);
    mi1.setEndMemAddress(0x4005a9L);
    m_info.add(mi1);
    MethodInfo mi2 = new MethodInfo();
    mi2.setMethodName("dustuff");
    mi2.setStartIndex(32);
    mi2.setEndIndex(50);
    mi2.setStartMemAddress(0x40050fL);
    mi2.setEndMemAddress(0x400541L);
    m_info.add(mi2);
    MethodInfo mi3 = new MethodInfo();
    mi3.setMethodName("sum");
    mi3.setStartIndex(51);
    mi3.setEndIndex(60);
    mi3.setStartMemAddress(0x400414L);
    mi3.setEndMemAddress(0x4004f8L);
    m_info.add(mi3);
    MethodInfo mi4 = new MethodInfo();
    mi4.setMethodName("halve");
    mi4.setStartIndex(61);
    mi4.setEndIndex(71);
    mi4.setStartMemAddress(0x4004f9L);
    mi4.setEndMemAddress(0x40050eL);
    m_info.add(mi4);
    

    //call the PatternSeperator to filter all the section
    PatternSeperator patt_Seperator = new PatternSeperator();
    List<Method> result_methods = patt_Seperator.divideMethod(
            wholeSection.getAllInstructionObjects(), m_info);
    filter_result = patt_Seperator.doFilter(
            result_methods, m_info);
    
    
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
    
    
    for (Method m : result_methods) {
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
