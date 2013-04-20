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
import edu.syr.bytecast.jimple.impl.filter.IfFilter;
import edu.syr.bytecast.jimple.impl.filter.DivBy2NFilter;
import edu.syr.bytecast.jimple.impl.filter.CallingFilter;
import edu.syr.bytecast.jimple.impl.filter.AddFilter;
import edu.syr.bytecast.amd64.api.constants.IBytecastAMD64;
import edu.syr.bytecast.amd64.api.output.IExecutableFile;
import edu.syr.bytecast.amd64.test.DepcrecatedMock;
import edu.syr.bytecast.jimple.api.IFilter;
import edu.syr.bytecast.jimple.api.MethodInfo;
import edu.syr.bytecast.jimple.api.Methods;
import edu.syr.bytecast.jimple.beans.FilterInfo;
import edu.syr.bytecast.jimple.beans.ParsedInstructionsSet;
import edu.syr.bytecast.util.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
  
  public static void main(String[] args)
  {
     
      //FSysBasicTest test = new FSysBasicTest();
        Paths.v().setRoot("/home/peike/code/bytecast");
        try {
            Paths.v().parsePathsFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
      List<MethodInfo> m_info = new ArrayList<MethodInfo>();
      IBytecastAMD64 testdata = new DepcrecatedMock();
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
      List<MethodInfo> methods = Methods.methods;
      System.out.println(methods.size());
      System.out.println(methods.size());
      IFilter AddFilter = new AddFilter();
      finfo = new FilterInfo();
      finfo.setFilter_Name("AddFilter");
      finfo.setInst_Count(3);
      fs.scan(exefile.getSectionsWithInstructions().get(0).getAllInstructionObjects(), pis_list, AddFilter, finfo);
      
      IFilter IfFilter = new IfFilter();
      finfo = new FilterInfo();
      finfo.setFilter_Name("IfFilter");
      finfo.setInst_Count(2);
     // fs.scan(exefile.getSectionsWithInstructions().get(0).getAllInstructionObjects(), pis_list, IfFilter, finfo);
      
      IFilter CallingFilter = new CallingFilter();
      finfo = new FilterInfo();
      finfo.setFilter_Name("CallingFilter");
      finfo.setInst_Count(1);
      fs.scan(exefile.getSectionsWithInstructions().get(0).getAllInstructionObjects(), pis_list, CallingFilter, finfo);
      
      IFilter DivFilter = new DivBy2NFilter();
      finfo = new FilterInfo();
      finfo.setFilter_Name("DivFilter");
      finfo.setInst_Count(5);
      fs.scan(exefile.getSectionsWithInstructions().get(0).getAllInstructionObjects(), pis_list, DivFilter, finfo);
      
      System.out.println(pis_list.size());
      
      /*
    
    IFilter filter = new IfFilter();
    filter.setFilter_Name("IF");
    filter.setInst_Count(3);
    List<IInstruction> uiList = null;//get the list from amd64
    List<ParsedInstructionsSet> pList = null;//create new List
    filter.scan(uiList, pList, filter);
    * */
  }
  
}
