/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.constants.IBytecastAMD64;
import edu.syr.bytecast.amd64.api.output.IExecutableFile;
import edu.syr.bytecast.amd64.api.output.ISection;
import edu.syr.bytecast.amd64.test.DepcrecatedMock;
import edu.syr.bytecast.amd64.util.AMD64MockGenerator;
import edu.syr.bytecast.jimple.api.IJimple;
import edu.syr.bytecast.jimple.api.Method;
import edu.syr.bytecast.jimple.beans.ParsedInstructionsSet;
import edu.syr.bytecast.test.mockups.MockBytecastFsys;
import edu.syr.bytecast.util.Paths;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author peike
 */
public class TestStep1 {

  public static void main(String[] args) throws Exception {
    Map<Method, List<ParsedInstructionsSet>> filter_result = new HashMap<Method, List<ParsedInstructionsSet>>();


    Set<String> exclusion = new HashSet<String>();
    Paths.v().setRoot("/home/mandy/code/bytecast");
    try {
      Paths.v().parsePathsFile();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    exclusion.add("<_IO_printf>");
    AMD64MockGenerator gen =
            new AMD64MockGenerator(new MockBytecastFsys(),
            "/home/mandy/code/bytecast/bytecast-documents/AsciiManip01Prototype/a.out.static.objdump",
            "<main>", exclusion);
    try {
      IBytecastAMD64 amd64Object = gen.generate();//buildInstructionObjects();
      // only for getting MethodInfo
      IJimple jimple = new Jimple();
            jimple.createJimple(amd64Object, "Test");
      IExecutableFile exe_file = amd64Object.buildInstructionObjects();

      ISection wholeSection = exe_file.getSectionsWithInstructions().get(0);

      PatternSeperator patt_Seperator = new PatternSeperator();
      filter_result = patt_Seperator.doSeperate(wholeSection);
      
    
    TestStep2 ts2 = new TestStep2(filter_result);
    ts2.createJimple();
    
    } catch (FileNotFoundException ex1) {
      Logger.getLogger(AMD64MockGenerator.class.getName()).log(Level.SEVERE, null, ex1);
    } catch (IOException ex1) {
      Logger.getLogger(AMD64MockGenerator.class.getName()).log(Level.SEVERE, null, ex1);
    }
  }
}
