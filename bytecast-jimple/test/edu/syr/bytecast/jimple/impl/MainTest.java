/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.constants.IBytecastAMD64;
import edu.syr.bytecast.amd64.api.output.IExecutableFile;
import edu.syr.bytecast.amd64.test.TestBytecastAmd64;
import edu.syr.bytecast.jimple.api.IFilter;
import edu.syr.bytecast.jimple.api.MethodInfo;
import edu.syr.bytecast.jimple.beans.FilterInfo;
import edu.syr.bytecast.jimple.beans.ParsedInstructionsSet;
import edu.syr.bytecast.util.Paths;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author pahuja
 */
public class MainTest extends TestCase {
    
    public MainTest(String testName) {
        super(testName);
    }

    /**
     * Test of main method, of class Main.
     */
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Main.main(args);
        Paths.v().setRoot("/home/pahuja/code/bytecast");
        try {
            Paths.v().parsePathsFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
      List<MethodInfo> m_info = new ArrayList<MethodInfo>();
      IBytecastAMD64 testdata = new TestBytecastAmd64();
      IExecutableFile exefile = testdata.buildInstructionObjects();
      List<ParsedInstructionsSet> pis_list = new ArrayList<ParsedInstructionsSet>();
      FilterScanner fs = new FilterScanner();
      IFilter MethodStartFilter = new MethodStartFilter();
      FilterInfo finfo = new FilterInfo();
      finfo.setFilter_Name("MethodStart");
      finfo.setInst_Count(2);
      fs.scan(exefile.getSectionsWithInstructions().get(0).getAllInstructionObjects(), pis_list, MethodStartFilter, finfo);
      IFilter MethodEndFilter = new MethodEndFilter();
      finfo = new FilterInfo();
      finfo.setFilter_Name("MethodEnd");
      finfo.setInst_Count(2);
      fs.scan(exefile.getSectionsWithInstructions().get(0).getAllInstructionObjects(), pis_list, MethodEndFilter, finfo);
      ParameterScanner ps = new ParameterScanner();
      ps.getParameters(exefile.getSectionsWithInstructions().get(0).getAllInstructionObjects());//, pis_list, null);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
