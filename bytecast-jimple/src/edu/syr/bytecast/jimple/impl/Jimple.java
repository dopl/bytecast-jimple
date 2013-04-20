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
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.jimple.api.IFilter;
import edu.syr.bytecast.jimple.api.IJimple;
import edu.syr.bytecast.jimple.beans.FilterInfo;
import edu.syr.bytecast.jimple.beans.ParsedInstructionsSet;
import java.util.ArrayList;
import java.util.List;


public class Jimple implements IJimple{

    @Override
    public boolean createJimple(IBytecastAMD64 amd64Obj, String fileName) {
        
        IExecutableFile exe_file = amd64Obj.buildInstructionObjects();
        List<MemoryInstructionPair> mip_list = exe_file.getSectionsWithInstructions().get(0).getAllInstructionObjects(); 
        
        //Run Filters
        //Run create Jimple
        ArrayList<ParsedInstructionsSet> pis_list = new ArrayList<ParsedInstructionsSet>();
        FilterScanner fs = new FilterScanner();
        ArrayList<IFilter> filtersList = new ArrayList<IFilter>(); 
        ArrayList<FilterInfo> filterInfoList = new ArrayList<FilterInfo>();
        createFilterList(filtersList, filterInfoList);
        ParameterScanner ps = new ParameterScanner();
        runFilters(filtersList, filterInfoList, mip_list, pis_list, fs, ps);
        
        CreateJimple cj = new CreateJimple();
        return cj.jimple(pis_list, fileName);
        /*
        // the result that store the filtered section
        Map<Method, List<ParsedInstructionsSet>> filter_result = new HashMap<Method, List<ParsedInstructionsSet>>();
        // get all the sections from the IExecutableFile
        
      List<ISection> all_section = exe_file.getSectionsWithInstructions();
        ISection wholeSection = exe_file.getSectionsWithInstructions().get(0);
        
        List<MethodInfo> m_info = new ArrayList<MethodInfo>();
        //call the PatternSeperator to filter all the section
        PatternSeperator patt_Seperator = new PatternSeperator();
        filter_result = patt_Seperator.doFilter(wholeSection, m_info);
        //call the JimpleFileGenerator to creathe the jimple file 
        JimpleFileGenerator jim_Generator = new JimpleFileGenerator();
        jim_Generator.doJimpleCreate(filter_result);
        return false;
        
        * 
       */
        //return true;
    } 
    
    public void createFilterList(ArrayList<IFilter> filtersList, ArrayList<FilterInfo> filterInfoList)
    {
        IFilter MethodStartFilter = new MethodStartFilter();
        FilterInfo finfoMS = new FilterInfo();
        finfoMS.setFilter_Name("MethodStart");
        finfoMS.setInst_Count(2);
        filtersList.add(MethodStartFilter);filterInfoList.add(finfoMS);
        //fs.scan(mip_list, pis_list, MethodStartFilter, finfo);
        IFilter MethodEndFilter = new MethodEndFilter();
        FilterInfo finfoME = new FilterInfo();
        finfoME.setFilter_Name("MethodEnd");
        finfoME.setInst_Count(2);
        filtersList.add(MethodEndFilter);filterInfoList.add(finfoME);
        //fs.scan(mip_list, pis_list, MethodEndFilter, finfo);
        //If Filter
        IFilter IfFilter = new IfFilter();
        FilterInfo finfoIf = new FilterInfo();
        finfoIf.setFilter_Name("If");
        finfoIf.setInst_Count(2);
        filtersList.add(IfFilter);filterInfoList.add(finfoIf);
        //Add Filter
        IFilter AddFilter = new AddFilter();
        FilterInfo finfoAdd = new FilterInfo();
        finfoAdd.setFilter_Name("Add");
        finfoAdd.setInst_Count(3);
        filtersList.add(AddFilter);filterInfoList.add(finfoAdd);
        //Calling Filter
        IFilter CallingFilter = new CallingFilter();
        FilterInfo finfoCF = new FilterInfo();
        finfoCF.setFilter_Name("Calling");
        finfoCF.setInst_Count(1);
        filtersList.add(CallingFilter);filterInfoList.add(finfoCF);
        //Divide by 2 Filter
        IFilter DivFilter = new DivBy2NFilter();
        FilterInfo finfoDIV = new FilterInfo();
        finfoDIV.setFilter_Name("Divide");
        finfoDIV.setInst_Count(5);
        filtersList.add(DivFilter);filterInfoList.add(finfoDIV);
    }
    
    public void runFilters(ArrayList<IFilter> filtersList, ArrayList<FilterInfo> filterInfoList, 
            List<MemoryInstructionPair> mip_list, ArrayList<ParsedInstructionsSet> pis_list, 
            FilterScanner fs, ParameterScanner ps)
    {
        
        for(int i=0;i<2;i++)
        {
            IFilter filter = filtersList.get(i);
            FilterInfo finfo = filterInfoList.get(i);
            fs.scan(mip_list, pis_list, filter, finfo);
        }
        
        ps.getParameters(mip_list);
        
        for(int i=2;i<filtersList.size();i++)
        {
            IFilter filter = filtersList.get(i);
            FilterInfo finfo = filterInfoList.get(i);
            fs.scan(mip_list, pis_list, filter, finfo);
        }
    }
    
}
