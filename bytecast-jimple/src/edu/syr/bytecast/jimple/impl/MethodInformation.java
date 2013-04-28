/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.jimple.api.IFilter;
import edu.syr.bytecast.jimple.api.MethodInfo;
import edu.syr.bytecast.jimple.api.Methods;
import edu.syr.bytecast.jimple.beans.FilterInfo;
import edu.syr.bytecast.jimple.beans.ParsedInstructionsSet;
import edu.syr.bytecast.jimple.impl.filter.CallingFilter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author QSA
 */
public class MethodInformation {

    public ArrayList<MethodInfo> getMethodsInfo(List<MemoryInstructionPair> mip_list) {
        ArrayList<ParsedInstructionsSet> pis_list = new ArrayList<ParsedInstructionsSet>();
        FilterScanner fs = new FilterScanner();
        ArrayList<IFilter> filtersList = new ArrayList<IFilter>();
        ArrayList<FilterInfo> filterInfoList = new ArrayList<FilterInfo>();
        createFilterList(filtersList, filterInfoList);
        ParameterScanner ps = new ParameterScanner();
        runFilters(filtersList, filterInfoList, mip_list, pis_list, fs, ps);
        return Methods.methods;
    }

    private void createFilterList(ArrayList<IFilter> filtersList, ArrayList<FilterInfo> filterInfoList) {
        IFilter MethodStartFilter = new MethodStartFilter();
        FilterInfo finfoMS = new FilterInfo();
        finfoMS.setFilter_Name("MethodStart");
        finfoMS.setInst_Count(2);
        filtersList.add(MethodStartFilter);
        filterInfoList.add(finfoMS);
        //fs.scan(mip_list, pis_list, MethodStartFilter, finfo);
        
        IFilter CallingFilter = new CallingFilter();
        FilterInfo finfoC = new FilterInfo();
        finfoC.setFilter_Name("Calling");
        finfoC.setInst_Count(1);
        filtersList.add(CallingFilter);
        filterInfoList.add(finfoC);
        
        IFilter MethodEndFilter = new MethodEndFilter();
        FilterInfo finfoME = new FilterInfo();
        finfoME.setFilter_Name("MethodEnd");
        finfoME.setInst_Count(2);
        filtersList.add(MethodEndFilter);
        filterInfoList.add(finfoME);
    }

    private void runFilters(ArrayList<IFilter> filtersList, ArrayList<FilterInfo> filterInfoList,
            List<MemoryInstructionPair> mip_list, ArrayList<ParsedInstructionsSet> pis_list,
            FilterScanner fs, ParameterScanner ps) {

        for (int i = 0; i < filtersList.size(); i++) {
            IFilter filter = filtersList.get(i);
            FilterInfo finfo = filterInfoList.get(i);
            fs.scan(mip_list, pis_list, filter, finfo);
        }
        ps.getParameters(mip_list);
    }
}
