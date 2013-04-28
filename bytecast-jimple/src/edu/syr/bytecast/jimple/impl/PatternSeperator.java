/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.jimple.impl.filter.IfWithBothVariableFilter;
import edu.syr.bytecast.jimple.impl.filter.IfFilter;
import edu.syr.bytecast.jimple.impl.filter.GetTwoParameterFilter;
import edu.syr.bytecast.jimple.impl.filter.GetOneParameterFilter;
import edu.syr.bytecast.jimple.impl.filter.DivBy2NFilter;
import edu.syr.bytecast.jimple.impl.filter.CallingFilter;
import edu.syr.bytecast.jimple.impl.filter.AddFilter;
import edu.syr.bytecast.jimple.impl.filter.LeaveFilter;
import edu.syr.bytecast.jimple.impl.filter.PreMemoryProcessFilter;
import edu.syr.bytecast.jimple.impl.filter.PrintfFilter;
import edu.syr.bytecast.jimple.impl.filter.SetArgvAndArgcFilter;
import edu.syr.bytecast.jimple.impl.filter.UseArgvAsParaFilter;
import edu.syr.bytecast.amd64.api.output.ISection;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.jimple.api.IFilter;
import edu.syr.bytecast.jimple.api.MethodInfo;
import edu.syr.bytecast.jimple.beans.*;
import edu.syr.bytecast.jimple.api.Method;
import edu.syr.bytecast.jimple.api.Methods;
import edu.syr.bytecast.jimple.impl.filter.UseArgvAsParaWith2DFilter;
import java.util.*;

/**
 *
 * @author nick
 */
public class PatternSeperator {
  // the result that store the filtered section

  private Map<Method, List<ParsedInstructionsSet>> result;

  public Map<Method, List<ParsedInstructionsSet>> doSeperate(ISection section, List<MethodInfo> l_info) {
    
    List<MethodInfo> m_info = l_info;
    List<Method> result_methods = divideMethod(section.getAllInstructionObjects(), m_info);



    // sort List<ParseInstructionSet>

    Map<Method, List<ParsedInstructionsSet>> result_map =
            doFilter(result_methods, m_info);
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
    for (Method m : result_map.keySet()) {
      List<ParsedInstructionsSet> resultSet = result_map.get(m);
      Collections.sort(resultSet, mysorter);
      result_map.put(m, resultSet);
    }
    // seperate the whole sectin into different method
    return result_map;
  }
  
  private List<Method> divideMethod(List<MemoryInstructionPair> mip, List<MethodInfo> meInfo) {
//      List<MemoryInstructionPair> mip = wholeSection.getAllInstructionObjects();
    List<Method> l_method = new ArrayList<Method>();
    for (MethodInfo m_info : meInfo) {
      Method m = new Method();
      List<MemoryInstructionPair> l_ins = new ArrayList<MemoryInstructionPair>();
      if (m_info.getMethodName().equals("DEFAULT")) {
        m_info.setMethodName("main");
      }
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

  private Map<Method, List<ParsedInstructionsSet>> doFilter(List<Method> methods, List<MethodInfo> method_info) {
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
    finfo.setInst_Count(2);
    fr.run(obj_instruction, parsed_list, fil, finfo);
    
    //GetOneParameterFilter
    fil = new GetOneParameterFilter();
    finfo = new FilterInfo();
    finfo.setFilter_Name("GetOneParameter");
    finfo.setInst_Count(1);
    fr.run(obj_instruction, parsed_list, fil, finfo);

    //GetTwoParameterFilter
    fil = new GetTwoParameterFilter();
    finfo = new FilterInfo();
    finfo.setFilter_Name("GetTwoParameter");
    finfo.setInst_Count(2);
    fr.run(obj_instruction, parsed_list, fil, finfo);
    
    //SetArgvAndArgcFilter
    fil = new SetArgvAndArgcFilter();
    finfo = new FilterInfo();
    finfo.setFilter_Name("SetArgvAndArgc");
    finfo.setInst_Count(2);
    fr.run(obj_instruction, parsed_list, fil, finfo);
    
    //IfWithBothVariableFilter
    fil = new UseArgvAsParaFilter();
    finfo = new FilterInfo();
    finfo.setFilter_Name("UseArgvAsPara");
    finfo.setInst_Count(5);
    fr.run(obj_instruction, parsed_list, fil, finfo);
    
    //UseArgvAsParaWith2DFilter
    fil = new UseArgvAsParaWith2DFilter();
    finfo = new FilterInfo();
    finfo.setFilter_Name("UseArgvAsParaWith2D");
    finfo.setInst_Count(6);
    fr.run(obj_instruction, parsed_list, fil, finfo);
    
    //AddFilter
    fil = new AddFilter();
    finfo = new FilterInfo();
    finfo.setFilter_Name("Add");
    finfo.setInst_Count(3);
    fr.run(obj_instruction, parsed_list, fil, finfo);
    
    //DivBy2NFilter
    fil = new DivBy2NFilter();
    finfo = new FilterInfo();
    finfo.setFilter_Name("DivBy2N");
    finfo.setInst_Count(5);
    fr.run(obj_instruction, parsed_list, fil, finfo);
    
    //IfFilter
    fil = new IfFilter();
    finfo = new FilterInfo();
    finfo.setFilter_Name("If");
    finfo.setInst_Count(2);
    fr.run(obj_instruction, parsed_list, fil, finfo);

    //IfWithBothVariableFilter
    fil = new IfWithBothVariableFilter();
    finfo = new FilterInfo();
    finfo.setFilter_Name("IfWithBothVariable");
    finfo.setInst_Count(3);
    fr.run(obj_instruction, parsed_list, fil, finfo);
    
    //CallingFilter
    fil = new CallingFilter();
    finfo = new FilterInfo();
    finfo.setFilter_Name("Calling");
    finfo.setInst_Count(1);
    fr.run(obj_instruction, parsed_list, fil, finfo);
    
    //PrintfFilter
    fil = new PrintfFilter();
    finfo = new FilterInfo();
    finfo.setFilter_Name("Printf");
    finfo.setInst_Count(1);
    fr.run(obj_instruction, parsed_list, fil, finfo);

    //LeaveFilter
    fil = new LeaveFilter();
    finfo = new FilterInfo();
    finfo.setFilter_Name("Leave");
    finfo.setInst_Count(2);
    fr.run(obj_instruction, parsed_list, fil, finfo);

    return parsed_list;
  }
}