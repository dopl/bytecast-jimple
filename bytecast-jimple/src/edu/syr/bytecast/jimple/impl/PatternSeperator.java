/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.ISection;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.jimple.api.IFilter;
import edu.syr.bytecast.jimple.api.MethodInfo;
import edu.syr.bytecast.jimple.beans.*;
import edu.syr.bytecast.jimple.api.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nick
 */
public class PatternSeperator {
  // the result that store the filtered section

  private Map<Method, List<ParsedInstructionsSet>> result;
//    private List<MethodInfo> l_method_info;
//    // store the temperate result of each ParsedInstructionsSet
//    private List<ParsedInstructionsSet> temp_set_result;
  // used to store the all section name in the file and in order to judge whether the section is analyzed
  //private Map<String, Boolean> name_of_section;

  // seperate the whole sectin into different method
  public List<Method> divideMethod(List<MemoryInstructionPair> mip, List<MethodInfo> meInfo) {
//      List<MemoryInstructionPair> mip = wholeSection.getAllInstructionObjects();
    List<Method> l_method = new ArrayList<Method>();
    for (MethodInfo m_info : meInfo) {
      Method m = new Method();
      List<MemoryInstructionPair> l_ins = new ArrayList<MemoryInstructionPair>();
      m.setMethodInfo(m_info);
      for (int i = 0; i < mip.size(); i++) {
        if (mip.get(i).getmInstructionAddress() >= m_info.getStartMemeAddress()
                && mip.get(i).getmInstructionAddress() <= m_info.getEndMemeAddress()) {
          l_ins.add(mip.get(i));
        }
      }
      m.setL_instruction(l_ins);
      l_method.add(m);
    }
    return l_method;
  }

  public Map<Method, List<ParsedInstructionsSet>> doFilter(List<Method> methods, List<MethodInfo> method_info) {
    result = new HashMap<Method, List<ParsedInstructionsSet>>();

    for (Method method : methods) {
      result.put(method, analyze(method.getL_instruction()));
    }
    return result;
  }

  // filter the section
  private List<ParsedInstructionsSet> analyze(List<MemoryInstructionPair> obj_instruction) {
    List<ParsedInstructionsSet> parsed_list = new ArrayList<ParsedInstructionsSet>();

    FilterRunner fr = new FilterRunner();
    //filter begins
    //PreMemoryProcess
    IFilter fil = new PreMemoryProcessFilter();
    FilterInfo finfo = new FilterInfo();
    finfo.setFilter_Name("PreMemoryProcess");
    finfo.setInst_Count(3);
    fr.run(obj_instruction, parsed_list, fil, finfo);

    //SetArgvAndArgcFilter
    fil = new SetArgvAndArgcFilter();
    finfo = new FilterInfo();
    finfo.setFilter_Name("SetArgvAndArgc");
    finfo.setInst_Count(2);
    fr.run(obj_instruction, parsed_list, fil, finfo);

    //IfFilter
    fil = new IfFilter();
    finfo = new FilterInfo();
    finfo.setFilter_Name("If");
    finfo.setInst_Count(2);
    fr.run(obj_instruction, parsed_list, fil, finfo);

    //CallingFilter
    fil = new CallingFilter();
    finfo = new FilterInfo();
    finfo.setFilter_Name("Calling");
    finfo.setInst_Count(1);
    fr.run(obj_instruction, parsed_list, fil, finfo);

    //PrintfFilter
    fil = new CallingFilter();
    finfo = new FilterInfo();
    finfo.setFilter_Name("Printf");
    finfo.setInst_Count(2);
    fr.run(obj_instruction, parsed_list, fil, finfo);

    //LeaveFilter
    fil = new LeaveFilter();
    finfo = new FilterInfo();
    finfo.setFilter_Name("Leave");
    finfo.setInst_Count(2);
    fr.run(obj_instruction, parsed_list, fil, finfo);


//       
    return parsed_list;
  }
}
