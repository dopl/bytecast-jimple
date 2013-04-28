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

import edu.syr.bytecast.amd64.api.constants.IBytecastAMD64;
import edu.syr.bytecast.amd64.api.output.IExecutableFile;
import edu.syr.bytecast.amd64.api.output.ISection;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.jimple.api.IJimple;
import edu.syr.bytecast.jimple.api.Method;
import edu.syr.bytecast.jimple.api.MethodInfo;
import edu.syr.bytecast.jimple.beans.ParsedInstructionsSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Jimple implements IJimple {

    @Override
    public boolean createJimple(IBytecastAMD64 amd64Obj, String fileName, String outputFormat) {

        //get the all instruction
        IExecutableFile e_file = amd64Obj.buildInstructionObjects();
        ISection wholeSection = e_file.getSectionsWithInstructions().get(0);

        //get the method information
        MethodInformation mi = new MethodInformation();
        List<MethodInfo> l_info = mi.getMethodsInfo(wholeSection.getAllInstructionObjects());

        //seperate into different pattern
        PatternSeperator patt_Seperator = new PatternSeperator();
        Map<Method, List<ParsedInstructionsSet>> filter_result = 
                patt_Seperator.doSeperate(wholeSection, l_info);

        //translate the pattern into jimple file
        PatternTranslator patt_Translator = new PatternTranslator(filter_result, fileName);
        patt_Translator.createJimpleObject(outputFormat);
        return true;
    }
}
