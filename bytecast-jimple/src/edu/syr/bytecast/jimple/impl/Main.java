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
import edu.syr.bytecast.amd64.util.AMD64MockGenerator;
import edu.syr.bytecast.jimple.api.IJimple;
import edu.syr.bytecast.jimple.api.Method;
import edu.syr.bytecast.jimple.beans.ParsedInstructionsSet;
import edu.syr.bytecast.test.mockups.MockBytecastFsys;
import edu.syr.bytecast.util.Paths;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws Exception {
        Map<Method, List<ParsedInstructionsSet>> filter_result = new HashMap<Method, List<ParsedInstructionsSet>>();
        
        Set<String> exclusion = new HashSet<String>();
    Paths.v().setRoot("/home/peike/code/bytecast");
    try {
      Paths.v().parsePathsFile();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    exclusion.add("<_IO_printf>");
    AMD64MockGenerator gen =
            new AMD64MockGenerator(new MockBytecastFsys(),
            "/home/peike/code/bytecast/bytecast-documents/AsciiManip01Prototype/a.out.static.objdump",
            "<main>", exclusion);
        try {
            IBytecastAMD64 amd64Object = gen.generate();//buildInstructionObjects();
            // only for getting MethodInfo
            IJimple jimple = new Jimple();
            jimple.createJimple(amd64Object, "test", "jimple");

        } catch (FileNotFoundException ex1) {
            Logger.getLogger(AMD64MockGenerator.class.getName()).log(Level.SEVERE, null, ex1);
        } catch (IOException ex1) {
            Logger.getLogger(AMD64MockGenerator.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }
}
