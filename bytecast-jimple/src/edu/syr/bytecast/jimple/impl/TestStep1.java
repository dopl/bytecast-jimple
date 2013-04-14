/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.constants.IBytecastAMD64;
import edu.syr.bytecast.amd64.api.output.IExecutableFile;
import edu.syr.bytecast.amd64.api.output.ISection;
import edu.syr.bytecast.amd64.test.TestBytecastAmd64;
import edu.syr.bytecast.jimple.api.Method;
import edu.syr.bytecast.jimple.api.MethodInfo;
import edu.syr.bytecast.jimple.beans.ParsedInstructionsSet;
import edu.syr.bytecast.util.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    IBytecastAMD64 testdata = new TestBytecastAmd64();
    IExecutableFile exe_file = testdata.buildInstructionObjects();

    List<ISection> all_section = exe_file.getSectionsWithInstructions();
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
    mi2.setEndIndex(18);
    mi2.setStartMemAddress(0x40050fL);
    mi2.setEndMemAddress(0x400541L);
    m_info.add(mi2);


    //call the PatternSeperator to filter all the section
    PatternSeperator patt_Seperator = new PatternSeperator();
    List<Method> result_methods = patt_Seperator.divideMethod(
            wholeSection.getAllInstructionObjects(), m_info);
    for (Method m : result_methods) {
      System.out.println(m.getMethodInfo().getMethodName());
    }
    filter_result = patt_Seperator.doFilter(
            patt_Seperator.divideMethod(wholeSection.getAllInstructionObjects(),
            m_info), m_info);
    //call the JimpleFileGenerator to creathe the jimple file 
    JimpleFileGenerator jim_Generator = new JimpleFileGenerator();
//    jim_Generator.doJimpleCreate(filter_result);
//    return false;
  }
}
