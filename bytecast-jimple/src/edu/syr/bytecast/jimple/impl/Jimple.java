/*
 * This file is part of Bytecast.
 *
 * Bytecast is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Bytecast is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bytecast.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.output.IExecutableFile;
import edu.syr.bytecast.amd64.api.output.ISection;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.jimple.beans.jimpleBean.JInstructionInfo;
import edu.syr.bytecast.jimple.api.IFilter;
import edu.syr.bytecast.jimple.api.IJimple;
import edu.syr.bytecast.jimple.beans.jimpleBean.ParsedInstructionsSet;
import edu.syr.bytecast.jimple.beans.jimpleBean.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class Jimple implements IJimple{
    private JimpleDoc jDoc;
    private JimpleMethod jMainMethod;

    public boolean createJimple(IExecutableFile exe_file) {
        List<ISection> all_section = exe_file.getSectionsWithInstructions();
        ISection obj_sec = all_section.get(0);
        Map<ISection, List<ParsedInstructionsSet>> result = new HashMap<ISection, List<ParsedInstructionsSet>>();
        List<ParsedInstructionsSet> parsed_list_result = new ArrayList<ParsedInstructionsSet>();
        // used to judge whether the function is analyzed
        Map<String, Boolean> name_function = new HashMap<String, Boolean>();
        // find the main function
        for(int i = 0; i < all_section.size(); i++)
        {
            if(all_section.get(i).getSectionName().equals("main"))
            {
                obj_sec = all_section.get(i);
                break;
            }
        }
        // 
        parsed_list_result = analyze(obj_sec, name_function);
        result.put(obj_sec, parsed_list_result);
        
        // analyze the subfunction
        
        while(true)
        {
            Set<String> names = name_function.keySet();
            int judge = 0;
            for (String str : names) {
                if(name_function.get(str) == Boolean.FALSE){
                    name_function.put(str, Boolean.TRUE);
                    judge = 1;
                    for(int i = 0; i < all_section.size(); i++)
                    {
                        if(all_section.get(i).getSectionName().equals(str))
                        {
                            obj_sec = all_section.get(i);
                            break;
                        }
                    }
                    parsed_list_result = analyze(obj_sec, name_function);
                    result.put(obj_sec, parsed_list_result);
                }
            }
            if( judge == 0)
                break;
        }
        // sort the order of the set in section
        Set<ISection> sections = result.keySet();
        for(ISection section : sections)
        {
            if(section.getSectionName().equals("main"))
            {
                List<ParsedInstructionsSet> analyzing_set = result.get(section);
                List<ParsedInstructionsSet> ordered_set = new ArrayList<ParsedInstructionsSet>();
                int index = 1;
                boolean judge = false;
                while(true)
                {
                    for(ParsedInstructionsSet parsed_set : analyzing_set)
                    {
                        JInstructionInfo info = parsed_set.getInfo();
                        if(info.getStart_Index() == index)
                        {
                            ordered_set.add(parsed_set);
                            index = index + info.getInstructions_Count();
                            if(info.getInstruction_Name().equals("Leave"))
                                judge = true;
                            break;
                        }
                    }
                    if(judge == true)
                        break;
                
                }
                // used to store the parameter in the assembly language
                Map<String, String> parameter = new HashMap<String, String>();
                for(ParsedInstructionsSet parsed_set : ordered_set)
                {
                    jimpleCreator(parsed_set, parameter);
                }
            }
        }
     
        return false;
    } 
    
    private void jimpleCreator(ParsedInstructionsSet ins_set, Map<String, String> parameter){
        // initialize the jimple file
        JInstructionInfo info = ins_set.getInfo();
        List<MemoryInstructionPair> pair_list = ins_set.getInstructions_List();
        String name = info.getInstruction_Name();
        jDoc = new JimpleDoc();
        JimpleClass jClass = new JimpleClass("Test", 1);
        jDoc.addClass(jClass);
        if(name.equals("PreMemoryProcess"))
        {
            ArrayList<String> parameter_list_main = new ArrayList<String>();
            parameter_list_main.add("String[]");

            //create main method add initiate first few lines of main
            jMainMethod = new JimpleMethod(9, "void","main", parameter_list_main, jClass);
        }
        else if(name.equals("SetArgvAndArgc"))
        {
            String argc = 
                    pair_list.get(0).getInstruction().getOperands().get(1).getOperandValue().toString();
            String argv = 
                    pair_list.get(1).getInstruction().getOperands().get(1).getOperandValue().toString();
            parameter.put(argc, "argc");
            parameter.put(argv, "argv");
        }
        else if(name.equals("If"))
        {
            OperandType left_operand_type =
                    pair_list.get(0).getInstruction().getOperands().get(0).getOperandType();
            OperandType right_operand_type =
                    pair_list.get(0).getInstruction().getOperands().get(1).getOperandType();
            String left_operand = 
                    pair_list.get(0).getInstruction().getOperands().get(0).getOperandValue().toString();
            String right_operand =
                    pair_list.get(0).getInstruction().getOperands().get(1).getOperandValue().toString();
            
            transferParameter(left_operand_type, left_operand, parameter);
            transferParameter(right_operand_type, right_operand, parameter);
            InstructionType ins_type = pair_list.get(1).getInstruction().getInstructiontype();
            String symbol = judgeSymbolOfIfStatement(ins_type);
            JimpleCondition jcl;
            if(left_operand_type == OperandType.CONSTANT && right_operand_type == OperandType.MEMORY_EFFECITVE_ADDRESS)
            {
                JimpleVariable right_variable = new JimpleVariable(right_operand, "int", jMainMethod);
                jcl = new JimpleCondition(symbol, right_variable, Integer.parseInt(left_operand), jMainMethod);
            }
            else
            {
                JimpleVariable left_variable = new JimpleVariable(left_operand, "int", jMainMethod);
                JimpleVariable right_variable = new JimpleVariable(right_operand, "int", jMainMethod);
                jcl = new JimpleCondition(symbol, right_variable, left_variable, jMainMethod);
            }
            JimpleVariable right_variable = new JimpleVariable(right_operand, "int", jMainMethod);
            
                
            // write the Jimple if statement
        }
        else if(name.equals("Add"))
        {
            // write the Jimple Add statement
        }
        else if(name.equals("Calling"))
        {
            int num_para = 0;
            for(MemoryInstructionPair pair : pair_list)
            {
                if( pair.getInstruction().getInstructiontype() != InstructionType.CALLQ)
                {
                    num_para ++;
                }
            }
            List<String> parameterForFunction = new ArrayList<String>();
            for(int i = 0; i < num_para; i ++)
            {
                OperandType operand_type =
                    pair_list.get(i).getInstruction().getOperands().get(0).getOperandType();
                String operand_value = 
                    pair_list.get(i).getInstruction().getOperands().get(0).getOperandValue().toString();
                transferParameter(operand_type, operand_value, parameter);
                parameter.put(pair_list.get(i).getInstruction().getOperands().get(1).getOperandValue().toString(), operand_value);
                parameterForFunction.add(operand_value);
            }
            String function_name = pair_list.get(num_para).getInstruction().getOperands().get(1).getOperandValue().toString();
            // write the Jimple Calling statement
        }
        else if(name.equals("Leave"))
        {
            // write the Jimple Leave statement
        }
    }
    
    private void transferParameter(OperandType type, String value, Map<String, String> parameter)
    {
        if( type == OperandType.CONSTANT)
        {
            value = value.substring(3);
            int temp = Integer.parseInt(value);
            value = Integer.toOctalString(temp);
        }
        else if( type == OperandType.MEMORY_EFFECITVE_ADDRESS)
        {
            value = parameter.get(value);
        }
    }
    
    private String judgeSymbolOfIfStatement(InstructionType ins_type)
    {
        String symbol = "";
        if(ins_type == InstructionType.JNE)
                symbol = "!=";
        else if(ins_type == InstructionType.JE)
                symbol = "==";
        else if(ins_type == InstructionType.JLE)
                symbol = "<=";
        else if(ins_type == InstructionType.JGE)
                symbol = ">=";
        else if(ins_type == InstructionType.JL)
                symbol = "<";
        else if(ins_type == InstructionType.JG)
                symbol = ">";
            // there are a lot of other situation, u can add "else if" statement to handle other situation
        return symbol;
    }
    
    private List<ParsedInstructionsSet> analyze(ISection obj_section, Map<String, Boolean> name_function) {
        List<ParsedInstructionsSet> parsed_list = new ArrayList<ParsedInstructionsSet>();
        ParsedInstructionsSet parsed_set = new ParsedInstructionsSet();
        List<MemoryInstructionPair> obj_instruction = obj_section.getAllInstructionObjects();
        IFilter fil = new PreMemoryProcessFilter();
        for(int index = 0; index < obj_instruction.size(); index++)
        {
            if(fil.doTest(obj_instruction, index))
            {
                JInstructionInfo jinfo = new  JInstructionInfo();
                jinfo.setInstruction_Name("PreMemoryProcess");
                jinfo.setInstructions_Count(3);
                jinfo.setStart_Index(index);
                parsed_set.setInfo(jinfo);
                List<MemoryInstructionPair> temp_instruction = new ArrayList<MemoryInstructionPair>();
                for( int i = 0; i < 3; i++)
                    temp_instruction.add(obj_instruction.get(index + i));
                parsed_set.setInstructions_List(temp_instruction);
                parsed_list.add(parsed_set);
            }
        }
        
        fil = new SetArgvAndArgcFilter();
        for(int index = 0; index < obj_instruction.size(); index++)
        {
            if(fil.doTest(obj_instruction, index))
            {
                JInstructionInfo jinfo = new  JInstructionInfo();
                jinfo.setInstruction_Name("SetArgvAndArgc");
                jinfo.setInstructions_Count(2);
                jinfo.setStart_Index(index);
                parsed_set.setInfo(jinfo);
                List<MemoryInstructionPair> temp_instruction = new ArrayList<MemoryInstructionPair>();
                for( int i = 0; i < 2; i++)
                    temp_instruction.add(obj_instruction.get(index + i));
                parsed_set.setInstructions_List(temp_instruction);
                parsed_list.add(parsed_set);
            }
        }
        
        fil = new IfFilter();
        for(int index = 0; index < obj_instruction.size(); index++)
        {
            if(fil.doTest(obj_instruction, index))
            {
                JInstructionInfo jinfo = new  JInstructionInfo();
                jinfo.setInstruction_Name("If");
                jinfo.setInstructions_Count(2);
                jinfo.setStart_Index(index);
                parsed_set.setInfo(jinfo);
                List<MemoryInstructionPair> temp_instruction = new ArrayList<MemoryInstructionPair>();
                for( int i = 0; i < 2; i++)
                    temp_instruction.add(obj_instruction.get(index + i));
                parsed_set.setInstructions_List(temp_instruction);
                parsed_list.add(parsed_set);
            }
        }
        // for each function getting from the callingFilter function, it need to store into a list
        fil = new CallingFilter();
        for(int index = 0; index < obj_instruction.size(); index++)
        {
            if(fil.doTest(obj_instruction, index))
            {
                int count = 0;
                while(true)
                {
                    IInstruction ins = obj_instruction.get(index - count).getInstruction();
                    if( ins.getInstructiontype() == InstructionType.MOV
                            && ins.getOperands().get(0).getOperandType() == OperandType.REGISTER
                            && ins.getOperands().get(1).getOperandType() == OperandType.REGISTER
                            && ( ins.getOperands().get(1).getOperandValue().toString() == "%edi" || ins.getOperands().get(1).getOperandValue().toString() == "%esi"))
                    {
                        count ++;
                    }
                    else
                        break;
                }
                JInstructionInfo jinfo = new  JInstructionInfo();
                jinfo.setInstruction_Name("Calling");
                jinfo.setInstructions_Count(count + 1);
                jinfo.setStart_Index(index);
                parsed_set.setInfo(jinfo);
                List<MemoryInstructionPair> temp_instruction = new ArrayList<MemoryInstructionPair>();
                for( int i = count; i >= 0; i--)
                    temp_instruction.add(obj_instruction.get(index - count));
                parsed_set.setInstructions_List(temp_instruction);
                // here to get the name of the function;
                
                String fun_name = " ";
                if( name_function.get(fun_name) == null )
                    name_function.put(fun_name, Boolean.FALSE);
                parsed_list.add(parsed_set);
            }
        }
        
        fil = new LeaveFilter();
        for(int index = 0; index < obj_instruction.size(); index++)
        {
            if(fil.doTest(obj_instruction, index))
            {
                JInstructionInfo jinfo = new  JInstructionInfo();
                jinfo.setInstruction_Name("Leave");
                jinfo.setInstructions_Count(2);
                jinfo.setStart_Index(index);
                parsed_set.setInfo(jinfo);
                List<MemoryInstructionPair> temp_instruction = new ArrayList<MemoryInstructionPair>();
                for( int i = 0; i < 2; i++)
                    temp_instruction.add(obj_instruction.get(index + i));
                parsed_set.setInstructions_List(temp_instruction);
                parsed_list.add(parsed_set);
            }
        }
        
        fil = new AddFilter();
        for(int index = 0; index < obj_instruction.size(); index++)
        {
            if(fil.doTest(obj_instruction, index))
            {
                JInstructionInfo jinfo = new  JInstructionInfo();
                jinfo.setInstruction_Name("calling");
                jinfo.setInstructions_Count(3);
                jinfo.setStart_Index(index);
                parsed_set.setInfo(jinfo);
                List<MemoryInstructionPair> temp_instruction = new ArrayList<MemoryInstructionPair>();
                for( int i = 0; i < 3; i++)
                    temp_instruction.add(obj_instruction.get(index + i));
                parsed_set.setInstructions_List(temp_instruction);
                parsed_list.add(parsed_set);
            }
        }
        
        return parsed_list;
    }
}
