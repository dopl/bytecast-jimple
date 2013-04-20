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

    public Map<Method, List<ParsedInstructionsSet>> doSeperate(ISection section) {
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
        List<Method> result_methods = divideMethod(section.getAllInstructionObjects(), m_info);
        return doFilter(result_methods, m_info);
    }
    // seperate the whole sectin into different method

    private List<Method> divideMethod(List<MemoryInstructionPair> mip, List<MethodInfo> meInfo) {
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

        //SetArgvAndArgcFilter
        fil = new SetArgvAndArgcFilter();
        finfo = new FilterInfo();
        finfo.setFilter_Name("SetArgvAndArgc");
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

        //IfWithBothVariableFilter
        fil = new UseArgvAsParaFilter();
        finfo = new FilterInfo();
        finfo.setFilter_Name("UseArgvAsPara");
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

        //PrintfFilter
        fil = new PrintfFilter();
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
