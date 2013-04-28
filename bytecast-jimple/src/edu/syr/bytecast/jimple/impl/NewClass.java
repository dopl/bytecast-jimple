///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package edu.syr.bytecast.jimple.impl;
//
//
//import edu.syr.bytecast.amd64.api.constants.InstructionType;
//import edu.syr.bytecast.amd64.api.constants.OperandType;
//import edu.syr.bytecast.amd64.api.constants.OperandTypeMemoryEffectiveAddress;
//import edu.syr.bytecast.amd64.api.constants.RegisterType;
//import edu.syr.bytecast.amd64.api.instruction.IInstruction;
//import edu.syr.bytecast.amd64.api.instruction.IOperand;
//import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
//import edu.syr.bytecast.jimple.api.IFilter;
//import edu.syr.bytecast.jimple.api.MethodInfo;
//import edu.syr.bytecast.jimple.api.Methods;
//import edu.syr.bytecast.jimple.beans.FilterInfo;
//import edu.syr.bytecast.jimple.beans.ParsedInstructionsSet;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import soot.IntType;
//import soot.Local;
//import soot.Modifier;
//import soot.SootClass;
//import soot.SootMethod;
//import soot.Unit;
//import soot.jimple.JimpleBody;
//import soot.util.Chain;
//
///**
// *
// * @author QSA
// */
//public class NewClass {
//
//    public NewClass(List<MemoryInstructionPair> list, ArrayList<ParsedInstructionsSet> pis_list, String fileName) throws FileNotFoundException, IOException
//    {
//        CreateJimple sj = new CreateJimple();
//        sj.jimple(pis_list, fileName);
//        sClass = sj.getSootClass();
//        createBody(list);
//        sj.setSClass(sClass);
//        sj.createClassFile(fileName, 0);
//    }
//    
//    private SootClass sClass;// = new SootClass("TestClass", Modifier.PUBLIC);
//    private ArrayList<SootMethod> sootMethodList;// = new ArrayList<SootMethod>();
//
//    
//    
//    public void createBody(List<MemoryInstructionPair> list) {
//        /*
//        //ArrayList<IFilter> filtersList; 
//        //ArrayList<FilterInfo> filterInfoList;
//        IFilter MethodStartFilter = new MethodStartFilter();
//        FilterInfo finfoMS = new FilterInfo();
//        finfoMS.setFilter_Name("MethodStart");
//        finfoMS.setInst_Count(2);
//        //filtersList.add(MethodStartFilter);filterInfoList.add(finfoMS);
//        //fs.scan(mip_list, pis_list, MethodStartFilter, finfo);
//        IFilter MethodEndFilter = new MethodEndFilter();
//        FilterInfo finfoME = new FilterInfo();
//        finfoME.setFilter_Name("MethodEnd");
//        finfoME.setInst_Count(2);
//        //filtersList.add(MethodEndFilter);filterInfoList.add(finfoME);
//        ArrayList<ParsedInstructionsSet> pis_list = new ArrayList<ParsedInstructionsSet>();
//        FilterScanner fs = new FilterScanner();
//        fs.scan(list, pis_list, MethodStartFilter, finfoMS);
//        fs.scan(list, pis_list, MethodEndFilter, finfoME);
//
//        ParameterScanner ps = new ParameterScanner();
//        ps.getParameters(list);
//        */
//        Map<String, Unit> memUnitMap = new HashMap<String, Unit>();
//        ArrayList<MethodInfo> methods = Methods.methods;
//
//        for (int index = 1; index < methods.size(); index++) {
//            int stIndex = methods.get(index).getStartIndex();
//            int enIndex = methods.get(index).getEndIndex();
//            //Methods
//            SootMethod method = sClass.getMethodByName(methods.get(index).getMethodName());//sootMethodList.get(index);
//            
//            //System.out.println(method.hasActiveBody());
//            //JimpleBody body = soot.jimple.Jimple.v().newBody(method);
//            JimpleBody body = (JimpleBody)method.getActiveBody();
//            method.setActiveBody(body);
//            Chain<Local> lc = body.getLocals();
//            //Chain units = body.getUnits();
//            //int size = list.size();
//            for (int i = stIndex; i < enIndex; i++) {
//                MemoryInstructionPair mip = list.get(i);
//                if (check(mip)) {
//                    IInstruction inst = mip.getInstruction();
//                    long val = ((OperandTypeMemoryEffectiveAddress) inst.getOperands().get(0).getOperandValue()).getOffset();
//                    String localVal = String.valueOf(val);
//                    localVal = getName(localVal);
//                    String localName = String.valueOf((RegisterType)(inst.getOperands().get(1).getOperandValue()));
//                    localName = getName(localName);
//                    Local arg = checkLocal(lc, localName);
//                    if(arg == null)
//                    {
//                        arg = soot.jimple.Jimple.v().newLocal(localName, IntType.v());
//                        lc.add(arg);
//                    }
//                    //Local arg = soot.jimple.Jimple.v().newLocal(localName, IntType.v());
//                    //lc.add(arg);
//                    Local l = checkLocal(lc, localVal);
//                    if (l != null) {
//                        //Assign Statement local 1 = local 2;
//                        soot.jimple.Stmt assign_stmt = soot.jimple.Jimple.v().newAssignStmt(arg, l);
//                        body.getUnits().add(assign_stmt);
//                        memUnitMap.put(String.valueOf(mip.getmInstructionAddress()), assign_stmt);
//                    }
//                }
//            }
//        }
//
//    }
//
//    private boolean check(MemoryInstructionPair mip) {
//        IInstruction inst = mip.getInstruction();
//        //IOperand op = inst.
//        if (inst.getInstructiontype().equals(InstructionType.MOV)) {
//            IOperand op0 = inst.getOperands().get(0);
//            IOperand op1 = inst.getOperands().get(1);
//            if (op1.getOperandType().equals(OperandType.REGISTER) && op0.getOperandType().equals(OperandType.MEMORY_EFFECITVE_ADDRESS)) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//
//        return false;
//    }
//
//    private Local checkLocal(Chain<Local> lc, String name) {
//        Iterator<Local> li = lc.iterator();
//        while (li.hasNext()) {
//            Local l = li.next();
//            if (l.getName().equalsIgnoreCase(name)) {
//                return l;
//            }
//        }
//
//        return null;
//    }
//    
//    private String getName(String name)
//    {
//        String local = "loc";
//        int size = name.length();
//        if(name.charAt(0) == '-')
//        {
//            local += "M";
//            local += name.substring(1, size);
//        }
//        else
//        {
//            local += name.substring(0, size);
//        }
//        
//        
//        return local;
//    }
//}
