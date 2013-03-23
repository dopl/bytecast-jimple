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

import edu.syr.bytecast.amd64.api.output.IExecutableFile;
import edu.syr.bytecast.amd64.api.output.ISection;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.jimple.beans.jimpleBean.JInstructionInfo;
import edu.syr.bytecast.jimple.api.IFilter;
import edu.syr.bytecast.jimple.api.IJimple;
import edu.syr.bytecast.jimple.beans.jimpleBean.ParsedInstructionsSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Jimple implements IJimple{

    public boolean createJimple(IExecutableFile exe_file) {
        List<ISection> all_section = exe_file.getAllSections();
        ISection main = all_section.get(0);
        List<ParsedInstructionsSet> parsed_list = new ArrayList<ParsedInstructionsSet>();
        ParsedInstructionsSet parsed_set = new ParsedInstructionsSet();
        for(int i = 0; i < all_section.size(); i++)
        {
            if(all_section.get(i).getSectionName() == "main")
            {
                main = all_section.get(i);
                break;
            }
        }
        Map<Long, IInstruction> obj_instruction = main.getAllInstructionObjects();
        //IFilter fil = new PreMemoryProcessFilter();
        
        IFilter fil = new PreMemoryProcessFilter();
        for(int index = 0; index < obj_instruction.size(); index++)
        {
            if(fil.doTest(obj_instruction, index))
            {
                JInstructionInfo jinfo = new  JInstructionInfo();
                jinfo.setInstruction_Name("if");
                jinfo.setInstructions_Count(3);
                jinfo.setStart_Index(index);
                parsed_set.setInfo(jinfo);
                //parsed_set.setInstructions_List(obj_instruction);
                parsed_list.add(parsed_set);
            }
        }
        fil = new CallingFilter();
        for(int index = 0; index < obj_instruction.size(); index++)
        {
            if(fil.doTest(obj_instruction, index))
            {
                JInstructionInfo jinfo = new  JInstructionInfo();
                jinfo.setInstruction_Name("calling");
                jinfo.setInstructions_Count(3);
                jinfo.setStart_Index(index);
                parsed_set.setInfo(jinfo);
                //parsed_set.setInstructions_List(obj_instruction);
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
                //parsed_set.setInstructions_List(obj_instruction);
                parsed_list.add(parsed_set);
            }
        }
        
        
//        if( fil.doTest(obj_instruction, parsed_set))
//        {
//            parsed_list.add(parsed_set);
//        }
        
        
        
        
        
        return false;
    } 
}
