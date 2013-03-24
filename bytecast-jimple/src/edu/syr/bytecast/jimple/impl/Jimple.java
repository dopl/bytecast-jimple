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
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class Jimple implements IJimple{

    public boolean createJimple(IExecutableFile exe_file) {
        List<ISection> all_section = exe_file.getSectionsWithInstructions();
        ISection obj_sec = all_section.get(0);
        Map<ISection, List<ParsedInstructionsSet>> result = new HashMap<ISection, List<ParsedInstructionsSet>>();
        List<ParsedInstructionsSet> parsed_list_result = new ArrayList<ParsedInstructionsSet>();
        Map<String, Boolean> name_function = new HashMap<String, Boolean>();
        for(int i = 0; i < all_section.size(); i++)
        {
            if(all_section.get(i).getSectionName() == "main")
            {
                obj_sec = all_section.get(i);
                break;
            }
        }
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
                        if(all_section.get(i).getSectionName() == str)
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
     
        return false;
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
