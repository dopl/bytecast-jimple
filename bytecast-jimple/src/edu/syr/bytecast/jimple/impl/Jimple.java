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
import edu.syr.bytecast.jimple.api.IFilter;
import edu.syr.bytecast.jimple.api.IJimple;
import edu.syr.bytecast.jimple.beans.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class Jimple implements IJimple{

    public boolean createJimple(IExecutableFile exe_file) {
        // the result that store the filtered section
        Map<ISection, List<ParsedInstructionsSet>> filter_result = new HashMap<ISection, List<ParsedInstructionsSet>>();
        // get all the sections from the IExecutableFile
        List<ISection> all_section = exe_file.getSectionsWithInstructions();
        //call the PatternSeperator to filter all the section
        PatternSeperator patt_Seperator = new PatternSeperator();
        filter_result = patt_Seperator.doFilter(all_section);
        //call the JimpleFileGenerator to creathe the jimple file 
        JimpleFileGenerator jim_Generator = new JimpleFileGenerator();
        jim_Generator.doJimpleCreate(filter_result);
        return false;
    } 
}
