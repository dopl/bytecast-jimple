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
    private List<MethodInfo> l_method_info;
//    // store the temperate result of each ParsedInstructionsSet
//    private List<ParsedInstructionsSet> temp_set_result;
    // used to store the all section name in the file and in order to judge whether the section is analyzed
    //private Map<String, Boolean> name_of_section;
    
    // seperate the whole sectin into different method
    public List<Method> divideMethod(ISection wholeSection) {
      List<MemoryInstructionPair> mip = wholeSection.getAllInstructionObjects();
      List<Method> l_method = new ArrayList<Method>();
      for(MethodInfo m_info : l_method_info)
      {
        Method m = new Method();
        List<MemoryInstructionPair> l_ins = new ArrayList<MemoryInstructionPair>();
        m.setM_info(m_info);
        for(int i = 0; i < mip.size(); i++)
        {
            if( mip.get(i).getmInstructionAddress()>= m_info.getStartMemeAddress() 
                    && mip.get(i).getmInstructionAddress()<= m_info.getEndMemeAddress() )
            {
                l_ins.add(mip.get(i));
            }
        }
        m.setL_instruction(l_ins);
        l_method.add(m);
      }
      return l_method;
    }
    
    public Map<Method, List<ParsedInstructionsSet>> doFilter(ISection wholeSection, List<MethodInfo> method_info)
    {
        // the objective section to be filtered
//        ISection obj_sec = all_section.get(0);
        result = new HashMap<Method, List<ParsedInstructionsSet>>();
        l_method_info = method_info;
        List<Method> methods = divideMethod(wholeSection);
        
        for (Method method : methods) {
              result.put(method, analyze(method.getL_instruction()));
          }
////        temp_set_result = new ArrayList<ParsedInstructionsSet>();
//        name_of_section = new HashMap<String, Boolean>();
//        
//        // find the main section
//        for(int i = 0; i < all_section.size(); i++)
//        {
//            if(all_section.get(i).getSectionName().equals("main"))
//            {
//                obj_sec = all_section.get(i);
//                break;
//            }
//        }
        // filter the main function 
        // the name_of_section record the section name needed to be filtered
        
        
        // filter the subfunction
        
//        while(true)
//        {
//            // get all names of analyzed section
//            Set<String> names = name_of_section.keySet();
//            int judge = 0;
//            for (String str : names) {
//                if(name_of_section.get(str) == Boolean.FALSE){
//                    name_of_section.put(str, Boolean.TRUE);
//                    judge = 1;
//                    for(int i = 0; i < all_section.size(); i++)
//                    {
//                        if(all_section.get(i).getSectionName().equals(str))
//                        {
//                            // find the objective section
//                            obj_sec = all_section.get(i);
//                            break;
//                        }
//                    }
//                    // filter the section
//                    result.put(obj_sec, analyze(obj_sec));
//                }
//            }
//            if( judge == 0)
//                break;
//        }
        // return the filtered result
        return result;
    }
    
    // filter the section
    private List<ParsedInstructionsSet> analyze(List<MemoryInstructionPair> obj_instruction) {
        List<ParsedInstructionsSet> parsed_list = new ArrayList<ParsedInstructionsSet>();
        
        FilterScanner fs = new FilterScanner();
        //filter begins
        //PreMemoryProcess
        IFilter fil = new PreMemoryProcessFilter();
        FilterInfo finfo = new FilterInfo();
        finfo.setFilter_Name("PreMemoryProcess");
        finfo.setInst_Count(3);
        fs.scan(obj_instruction, parsed_list, fil, finfo);
        
        //SetArgvAndArgcFilter
        fil = new SetArgvAndArgcFilter();
        finfo = new FilterInfo();
        finfo.setFilter_Name("SetArgvAndArgc");
        finfo.setInst_Count(2);
        fs.scan(obj_instruction, parsed_list, fil, finfo);
        
//        for(int index = 0; index < obj_instruction.size(); index++)
//        {
//            if(fil.doTest(obj_instruction, index))
//            {
//                JInstructionInfo jinfo = new  JInstructionInfo();
//                jinfo.setInstruction_Name("SetArgvAndArgc");
//                jinfo.setInstructions_Count(2);
//                jinfo.setStart_Index(index);
//                parsed_set.setInfo(jinfo);
//                List<MemoryInstructionPair> temp_instruction = new ArrayList<MemoryInstructionPair>();
//                for( int i = 0; i < 2; i++)
//                    temp_instruction.add(obj_instruction.get(index + i));
//                parsed_set.setInstructions_List(temp_instruction);
//                parsed_list.add(parsed_set);
//            }
//        }
        
        fil = new IfFilter();
        finfo = new FilterInfo();
        finfo.setFilter_Name("If");
        finfo.setInst_Count(2);
        fs.scan(obj_instruction, parsed_list, fil, finfo);
        
        
        
//        for(int index = 0; index < obj_instruction.size(); index++)
//        {
//            if(fil.doTest(obj_instruction, index))
//            {
//                JInstructionInfo jinfo = new  JInstructionInfo();
//                jinfo.setInstruction_Name("If");
//                jinfo.setInstructions_Count(2);
//                jinfo.setStart_Index(index);
//                parsed_set.setInfo(jinfo);
//                List<MemoryInstructionPair> temp_instruction = new ArrayList<MemoryInstructionPair>();
//                for( int i = 0; i < 2; i++)
//                    temp_instruction.add(obj_instruction.get(index + i));
//                parsed_set.setInstructions_List(temp_instruction);
//                parsed_list.add(parsed_set);
//            }
//        }
        // for each function getting from the callingFilter function, it need to store into a list
        fil = new CallingFilter();
        finfo = new FilterInfo();
        finfo.setFilter_Name("Calling");
        finfo.setInst_Count(1);
        fs.scan(obj_instruction, parsed_list, fil, finfo);
//        for(int index = 0; index < obj_instruction.size(); index++)
//        {
//            if(fil.doTest(obj_instruction, index))
//            {
//                int count = 0;
//                while(true)
//                {
//                    IInstruction ins = obj_instruction.get(index - count).getInstruction();
//                    if( ins.getInstructiontype() == InstructionType.MOV
//                            && ins.getOperands().get(0).getOperandType() == OperandType.REGISTER
//                            && ins.getOperands().get(1).getOperandType() == OperandType.REGISTER
//                            && ( ins.getOperands().get(1).getOperandValue().toString() == "%edi" || ins.getOperands().get(1).getOperandValue().toString() == "%esi"))
//                    {
//                        count ++;
//                    }
//                    else
//                        break;
//                }
//                JInstructionInfo jinfo = new  JInstructionInfo();
//                jinfo.setInstruction_Name("Calling");
//                jinfo.setInstructions_Count(count + 1);
//                jinfo.setStart_Index(index);
//                parsed_set.setInfo(jinfo);
//                List<MemoryInstructionPair> temp_instruction = new ArrayList<MemoryInstructionPair>();
//                for( int i = count; i >= 0; i--)
//                    temp_instruction.add(obj_instruction.get(index - count));
//                parsed_set.setInstructions_List(temp_instruction);
//                // here to get the name of the function;
//                
//                String fun_name = " ";
//                if( name_of_section.get(fun_name) == null )
//                    name_of_section.put(fun_name, Boolean.FALSE);
//                parsed_list.add(parsed_set);
//            }
//        }
        
        fil = new LeaveFilter();
        finfo = new FilterInfo();
        finfo.setFilter_Name("Leave");
        finfo.setInst_Count(2);
        fs.scan(obj_instruction, parsed_list, fil, finfo);
        
        
        
//        for(int index = 0; index < obj_instruction.size(); index++)
//        {
//            if(fil.doTest(obj_instruction, index))
//            {
//                JInstructionInfo jinfo = new  JInstructionInfo();
//                jinfo.setInstruction_Name("Leave");
//                jinfo.setInstructions_Count(2);
//                jinfo.setStart_Index(index);
//                parsed_set.setInfo(jinfo);
//                List<MemoryInstructionPair> temp_instruction = new ArrayList<MemoryInstructionPair>();
//                for( int i = 0; i < 2; i++)
//                    temp_instruction.add(obj_instruction.get(index + i));
//                parsed_set.setInstructions_List(temp_instruction);
//                parsed_list.add(parsed_set);
//            }
//        }
        
//        fil = new AddFilter();
//        for(int index = 0; index < obj_instruction.size(); index++)
//        {
//            if(fil.doTest(obj_instruction, index))
//            {
//                JInstructionInfo jinfo = new  JInstructionInfo();
//                jinfo.setInstruction_Name("calling");
//                jinfo.setInstructions_Count(3);
//                jinfo.setStart_Index(index);
//                parsed_set.setInfo(jinfo);
//                List<MemoryInstructionPair> temp_instruction = new ArrayList<MemoryInstructionPair>();
//                for( int i = 0; i < 3; i++)
//                    temp_instruction.add(obj_instruction.get(index + i));
//                parsed_set.setInstructions_List(temp_instruction);
//                parsed_list.add(parsed_set);
//            }
//        }
        
        return parsed_list;
    }
    
}
