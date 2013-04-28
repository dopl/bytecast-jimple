/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.constants.OperandTypeMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.jimple.api.Method;
import edu.syr.bytecast.jimple.api.MethodInfo;
import edu.syr.bytecast.jimple.beans.ParsedInstructionsSet;
import edu.syr.bytecast.jimple.beans.jimpleBean.JimpleAssign;
import edu.syr.bytecast.jimple.beans.jimpleBean.JimpleClass;
import edu.syr.bytecast.jimple.beans.jimpleBean.JimpleCondition;
import edu.syr.bytecast.jimple.beans.jimpleBean.JimpleDoc;
import edu.syr.bytecast.jimple.beans.jimpleBean.JimpleElement;
import edu.syr.bytecast.jimple.beans.jimpleBean.JimpleInvoke;
import edu.syr.bytecast.jimple.beans.jimpleBean.JimpleMethod;
import edu.syr.bytecast.jimple.beans.jimpleBean.JimpleVariable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author nick
 */
public class PatternTranslator {
    boolean hasUnsetTarget;
    Long jumpAddress;
    private Map<Method, List<ParsedInstructionsSet>> method_map;
    private Map<String, JimpleMethod> Map_jMethod;
    private JimpleDoc jimple_doc;
    private JimpleClass jimple_class;
    private Set<Method> methods;
    private int _vcount;
    private JimpleAssign jimAss;
    private JimpleInvoke jimInvk;
    // maintain a relation between variable and register
    // example:
    // edx : v1
    // -0x4(%rbp) : p1
//    private Map<String, String> regToVar;
    private Map<String, JimpleVariable> regToJVar;
    /**
     * the instance of the only class used in main
     */
    private JimpleVariable objectOfThisClass;
    /**
     *
     *
     */
    private Map<String, JimpleCondition> memAddrToJCond;
    
    public PatternTranslator(Map<Method, List<ParsedInstructionsSet>> temp_map,
            String classname) {
        hasUnsetTarget = false;
        jumpAddress = null;
        this.method_map = temp_map;
        this.methods = temp_map.keySet();
        Map_jMethod = new HashMap<String, JimpleMethod>();
        jimple_doc = new JimpleDoc();
        jimple_class = new JimpleClass(classname, 1);
        jimple_doc.addClass(jimple_class);

        jimAss = new JimpleAssign();
        jimInvk = new JimpleInvoke();
        this._vcount = 0;
        regToJVar = new HashMap<String, JimpleVariable>();
//        varToJVar = new HashMap<String , JimpleVariable>();
        memAddrToJCond = new HashMap<String, JimpleCondition>();
    }
    /**
     * 
     * @param outputFormat must be jimple or class 
     */
    public void createJimpleObject(String outputFormat) {



        initializeAllJimpleMethod();

        implementJimpleMethod();


        try {
            jimple_doc.printJimple(jimple_class.getJClassName(), outputFormat);

        } catch (FileNotFoundException e) {
            System.out.println("file exception");
        } catch (IOException e) {
            System.out.println("IO exception");
        }
    }

    private void initializeAllJimpleMethod() {


        // List<ParsedInstructionsSet> main_ins = new ArrayList<ParsedInstructionsSet>();

        //create all jimple method
        for (Method m : methods) {

            //main method
            if (m.getMethodInfo().getMethodName().equals("main")) {
                ArrayList<String> parameter_type = new ArrayList<String>();
                parameter_type.add("String[]");
                JimpleMethod jMethod = new JimpleMethod(9, "void", "main", parameter_type, jimple_class);
                Map_jMethod.put("main", jMethod);
                // thisclass obj = new thisclass();
            } else {
                MethodInfo m_info = m.getMethodInfo();
                ArrayList<String> parameter_type = new ArrayList<String>();
                int para_num = m_info.getParameterCount();
                if (para_num != 0) {
                    for (int i = 0; i < para_num; i++) {
                        parameter_type.add("int");
                    }
                }
                JimpleMethod jMethod = new JimpleMethod(1, "int", m_info.getMethodName(), parameter_type, this.jimple_class);
                //test for method output
                Map_jMethod.put(m_info.getMethodName(), jMethod);
            }
        }
    }
//

    private void implementJimpleMethod() {
        for (Method m : methods) {
            List<ParsedInstructionsSet> method_ParseIns = method_map.get(m);
            implementSingleJimpleMethod(m, method_ParseIns);
            cleanVarMap();
        }
    }

    private int getVcount() {

        int temp = _vcount++;

        return temp;
    }

    private void implementSingleJimpleMethod(Method m, List<ParsedInstructionsSet> listPis) {
        int start_index_of_this, number_of_lines;
        int end_index_of_last = 0;
        for (ParsedInstructionsSet pis : listPis) {
            start_index_of_this = pis.getInfo().getStart_Index();
            number_of_lines = start_index_of_this - end_index_of_last;
            if (number_of_lines != 0) {
                for (int i = 0; i < number_of_lines; i++) {
                    handleUnparsedLines(m, m.getL_instruction().get(end_index_of_last + i), m.getL_instruction().get(end_index_of_last + i - 1));

                }
            }
            if (pis.getInfo().getInstruction_Name().equals("PreMemoryProcess")) {
                prememoryFilterProcess(m, pis);
            } else if (pis.getInfo().getInstruction_Name().equals("SetArgvAndArgc")) {
                setArgFilterProcess(m, pis);
            } else if (pis.getInfo().getInstruction_Name().equals("UseArgvAsPara")) {
                useArgFilterProcess(m, pis);
            } else if (pis.getInfo().getInstruction_Name().equals("Calling")) {
                callingFilterProcess(m, pis);
            } else if (pis.getInfo().getInstruction_Name().equals("Leave")) {
                leaveFilterProcess(m, pis);
            } else if (pis.getInfo().getInstruction_Name().equals("GetOneParameter")) {
                getOneParaFilterProcess(m, pis);
            } else if (pis.getInfo().getInstruction_Name().equals("GetTwoParameter")) {
                getTwoParaFilterProcess(m, pis);
            } else if (pis.getInfo().getInstruction_Name().equals("IfWithBothVariable")) {
                ifWith2VaribFilterProcess(m, pis);
            } else if (pis.getInfo().getInstruction_Name().equals("DivBy2N")) {
                divideBy2NFilterProcess(m, pis);
            } else if (pis.getInfo().getInstruction_Name().equals("If")) {
                ifFilterProcess(m, pis);
            } else if (pis.getInfo().getInstruction_Name().equals("Add")) {
                addFilterProcess(m, pis);
            }
            end_index_of_last = pis.getInfo().getStart_Index() + pis.getInfo().getInstructions_Count();
        }
    }

    private void handleUnparsedLines(Method m, MemoryInstructionPair singleLine, MemoryInstructionPair lastSingleLine) {
        IInstruction ins = singleLine.getInstruction();
        Long InstrAddr = singleLine.getmInstructionAddress();
        if (ins.getInstructiontype().equals(InstructionType.MOV)
                && ins.getOperands().get(0).getOperandType().equals(OperandType.CONSTANT)
                && ins.getOperands().get(1).getOperandValue().equals(RegisterType.EAX)) {
            // add return statement return 0 to the coresponding fucntion;
            if (memAddrToJCond.containsKey(InstrAddr.toString())
                    || hasUnsetTarget) {
                if (hasUnsetTarget) {
                    InstrAddr = jumpAddress;
                }

                checkPreLine(lastSingleLine, Map_jMethod.get(m.getMethodInfo().getMethodName()));

                JimpleInvoke exeinv = new JimpleInvoke();
                JimpleMethod currentJM = Map_jMethod.get(m.getMethodInfo().getMethodName());
                exeinv.setAsTarget();
                exeinv.invokeSystemExit(1, currentJM);
                JimpleCondition jc = memAddrToJCond.get(InstrAddr.toString());
                jc.setTargets(new JimpleElement[]{exeinv});
                currentJM.setReturn(null);
//        currentJVM.setReturn(null, memAddrToJCond.get(InstrAddr.toString()));
                hasUnsetTarget = false;
//      } else {
//        JimpleMethod currentJVM = Map_jMethod.get(m.getMethodInfo().getMethodName());
//        currentJVM.setReturn(null);   // return void
            }

        } else if (ins.getInstructiontype().equals(InstructionType.MOV)
                && ins.getOperands().get(0).getOperandValue().equals(RegisterType.EAX)) {
            IInstruction ins_last = lastSingleLine.getInstruction();
            if (ins_last.getInstructiontype().equals(InstructionType.CALLQ)) {
                String funcName = (String) ins_last.getOperands().get(1).getOperandValue();
                if (!funcName.contains("printf")) {
                    String leftop = getRegister(ins.getOperands().get(0).getOperandValue());
                    String rightop = getMemoryEffectiveAddress(ins.getOperands().get(1).getOperandValue());
                    updateRegToVarMap(rightop, getExistJVar(leftop));
                    if (memAddrToJCond.containsKey(InstrAddr.toString())) {

                        checkPreLine(lastSingleLine, Map_jMethod.get(m.getMethodInfo().getMethodName()));
                        jumpAddress = InstrAddr;
                        hasUnsetTarget = true;
                    }
                }
            }

        } else if (ins.getInstructiontype().equals(InstructionType.MOV)) {
            String leftop, rightop;
            if (ins.getOperands().get(0).getOperandValue() instanceof OperandTypeMemoryEffectiveAddress) {
                leftop = getMemoryEffectiveAddress(ins.getOperands().get(0).getOperandValue());
            } else {
                leftop = getRegister(ins.getOperands().get(0).getOperandValue());
            }
            if (ins.getOperands().get(1).getOperandValue() instanceof OperandTypeMemoryEffectiveAddress) {
                rightop = getMemoryEffectiveAddress(ins.getOperands().get(1).getOperandValue());
            } else {
                rightop = getRegister(ins.getOperands().get(1).getOperandValue());
            }

            updateRegToVarMap(rightop, getExistJVar(leftop));
            if (memAddrToJCond.containsKey(InstrAddr.toString())) {

                checkPreLine(lastSingleLine, Map_jMethod.get(m.getMethodInfo().getMethodName()));
                jumpAddress = InstrAddr;
                hasUnsetTarget = true;
            }
        }
    }

    private void prememoryFilterProcess(Method m, ParsedInstructionsSet ins_set) {
    }

    private void setArgFilterProcess(Method m, ParsedInstructionsSet ins_set) {
        JimpleMethod jmethod = Map_jMethod.get(m.getMethodInfo().getMethodName());

        List<MemoryInstructionPair> pair_list = ins_set.getInstructions_List();
        // JimpleAssign jim_ass = new JimpleAssign();
        JimpleVariable j_argc = new JimpleVariable("argc", "int", jmethod);
        JimpleVariable j_argv = new JimpleVariable("argv", "String[]", jmethod);

        //jimAss.JimpleParameterAssign(j_argc, "int", 0, jmethod);
        jimAss.JimpleParameterAssign(j_argv, "String[]", 0, jmethod);

        objectOfThisClass = new JimpleVariable("obj", jimple_class.getJClassName(), jmethod);
        jimAss.JimpleNewClass(objectOfThisClass, jimple_class, jmethod);
        jimAss.JimpleLengthOf(j_argc, j_argv, jmethod);
        for (MemoryInstructionPair mip : pair_list) {
            InstructionType thisIType = mip.getInstruction().getInstructiontype();
            List<IOperand> curOps = mip.getInstruction().getOperands();
            String leftReg = getRegister(curOps.get(0).getOperandValue());
            String rightReg = getMemoryEffectiveAddress(curOps.get(1).getOperandValue());

            if (thisIType.equals(InstructionType.MOV) && leftReg.equals("RSI")) {
                updateRegToVarMap(rightReg, j_argv);
            } else {
                updateRegToVarMap(rightReg, j_argc);
            }

        }
    }

    private void useArgFilterProcess(Method m, ParsedInstructionsSet ins_set) {
        JimpleMethod jmethod = Map_jMethod.get(m.getMethodInfo().getMethodName());
        List<MemoryInstructionPair> pair_list = ins_set.getInstructions_List();
        String leftReg = null;
        String rightReg = null;
        JimpleVariable j_array = null;
        int index = 0;
        for (int i = 0; i < pair_list.size(); i++) {
            InstructionType thisIType = pair_list.get(i).getInstruction().getInstructiontype();
            //find move and add 
            if (thisIType.equals(InstructionType.MOV) && (i != pair_list.size())) {
                InstructionType nextIType = pair_list.get(i + 1).getInstruction().getInstructiontype();
                if (nextIType.equals(InstructionType.ADD)) {
                    //get the whole value not only the register
                    leftReg = getMemoryEffectiveAddress(pair_list.get(i).getInstruction().getOperands().get(0).getOperandValue());
                    j_array = regToJVar.get(leftReg);

                    long temp = getLong(pair_list.get(i + 1).getInstruction().getOperands().get(0).getOperandValue());
                    index = (int) temp / 8 - 1;
                }
            }

            if (thisIType.equals(InstructionType.MOVSBL)) {
                //change getReg later to obtain all the value from right operand
                rightReg = getRegister(pair_list.get(i).getInstruction().getOperands().get(1).getOperandValue());;

                JimpleVariable j_var = new JimpleVariable("argv" + Integer.toString(index), "String", jmethod);
                jimAss.JimpleAssignFromArray(j_var, j_array, index, jmethod);
                JimpleVariable j_char = new JimpleVariable("char" + Integer.toString(index) + "0", "char", jmethod);

                //judge whether this value is between 0-9  
                jimInvk.invokeCharAt(j_var, 0, j_char, jmethod);

                //j_char = checkValue(j_char, jmethod);

                updateRegToVarMap(rightReg, j_char);
            }

        }
    }

    private void callingFilterProcess(Method m, ParsedInstructionsSet ins_set) {
        JimpleMethod baseMethod = Map_jMethod.get(m.getMethodInfo().getMethodName());
        String startAddr = ins_set.getInstructions_List().get(0).getmInstructionAddress().toString();

        // check if current ins_set is target of some condition
        JimpleCondition jmpFrom = null;
        boolean isTarget = false;
        if (memAddrToJCond.containsKey(startAddr) || hasUnsetTarget) {
            if (hasUnsetTarget) {
                startAddr = jumpAddress.toString();
            }
            jmpFrom = memAddrToJCond.get(startAddr);
            isTarget = true;
            hasUnsetTarget = false;
        }
        // return value is default int
        JimpleVariable retResult;
        if (regToJVar.containsKey("returnVal")) {
            retResult = regToJVar.get("returnVal");
        } else {
            retResult = new JimpleVariable("returnVal", "int", baseMethod);
            regToJVar.put("returnVal", retResult);
        }
        ArrayList<JimpleVariable> parameters = new ArrayList<JimpleVariable>();

        for (MemoryInstructionPair mip : ins_set.getInstructions_List()) {
            InstructionType thisIType = mip.getInstruction().getInstructiontype();
            List<IOperand> curOps = mip.getInstruction().getOperands();

            if (thisIType.equals(InstructionType.MOV)) {
                String leftReg, rightReg;
                if (curOps.get(0).getOperandValue() instanceof OperandTypeMemoryEffectiveAddress) {
                    leftReg = getMemoryEffectiveAddress(curOps.get(0).getOperandValue());
                    rightReg = getRegister(curOps.get(1).getOperandValue());
                    updateRegToVarMap(rightReg, getExistJVar(leftReg));
                } else {
                    leftReg = getRegister(curOps.get(0).getOperandValue());
                    rightReg = getRegister(curOps.get(1).getOperandValue());
                    updateRegToVarMap(rightReg, getExistJVar(leftReg));
                    parameters.add(getExistJVar(leftReg));
                }
            } else if (thisIType.equals(InstructionType.CALLQ)) {
                Collections.reverse(parameters);
                if (isTarget) {
                    JimpleInvoke targetinvoke = new JimpleInvoke();
                    targetinvoke.setAsTarget();
                    String methodName = (String) curOps.get(1).getOperandValue();
                    if (baseMethod.getMethodName().equals("main")) {

                        targetinvoke.invokeUserDefined(objectOfThisClass,
                                Map_jMethod.get(methodName), parameters, retResult, baseMethod);
                    } else {
                        targetinvoke.invokeUserDefined(Map_jMethod.get(methodName), parameters, retResult, baseMethod);
                    }
                    jmpFrom.setTargets(new JimpleElement[]{targetinvoke});
                } else {
                    String methodName = (String) curOps.get(1).getOperandValue();
                    if (baseMethod.getMethodName().equals("main")) {
                        jimInvk.invokeUserDefined(objectOfThisClass,
                                Map_jMethod.get(methodName), parameters, retResult, baseMethod);
                    } else {
                        jimInvk.invokeUserDefined(Map_jMethod.get(methodName), parameters, retResult, baseMethod);
                    }
                }
            }
        }
        updateRegToVarMap("EAX", retResult);
    }

    private void leaveFilterProcess(Method m, ParsedInstructionsSet ins_set) {

        String startAddr = ins_set.getInstructions_List().get(0).getmInstructionAddress().toString();

        // check if current ins_set is target of some condition
        JimpleCondition jmpFrom = null;
        boolean isTarget = false;
        if (memAddrToJCond.containsKey(startAddr) || hasUnsetTarget) {
            if (hasUnsetTarget) {
                startAddr = jumpAddress.toString();
            }
            jmpFrom = memAddrToJCond.get(startAddr);
            isTarget = true;
            hasUnsetTarget = false;
        }

        JimpleMethod baseMethod = Map_jMethod.get(m.getMethodInfo().getMethodName());


        JimpleVariable return_j = null;
        if (!baseMethod.getMethodName().equals("main")) {
            return_j = regToJVar.get("EAX");
        }

        if (isTarget) {
            if (baseMethod.getMethodName().equals("main")) {
                JimpleInvoke exeinv = new JimpleInvoke();
                exeinv.setAsTarget();
                exeinv.invokeSystemExit(0, baseMethod);
                JimpleCondition jc = memAddrToJCond.get(startAddr.toString());
                jc.setTargets(new JimpleElement[]{exeinv});
                baseMethod.setReturn(null);
            } else if (return_j == null) {
                baseMethod.setReturn(null, jmpFrom);
            } else {
                baseMethod.setReturn(return_j, jmpFrom);
            }
        } else {
            if (baseMethod.getMethodName().equals("main")) {
                JimpleInvoke exeinv = new JimpleInvoke();
                exeinv.setAsTarget();
                exeinv.invokeSystemExit(0, baseMethod);
                JimpleCondition jc = memAddrToJCond.get(startAddr.toString());
                jc.setTargets(new JimpleElement[]{exeinv});
            } 
            if (return_j == null) {
                baseMethod.setReturn(null);
            } else {
                baseMethod.setReturn(return_j);
            }
        }


    }

    private void addFilterProcess(Method m, ParsedInstructionsSet ins_set) {
        JimpleMethod currentJM = Map_jMethod.get(m.getMethodInfo().getMethodName());
        for (MemoryInstructionPair mip : ins_set.getInstructions_List()) {
            InstructionType thisIType = mip.getInstruction().getInstructiontype();
            List<IOperand> curOps = mip.getInstruction().getOperands();

            if (thisIType.equals(InstructionType.MOV)) {
                String leftReg = getMemoryEffectiveAddress(curOps.get(0).getOperandValue());
                String rightReg = getRegister(curOps.get(1).getOperandValue());

                updateRegToVarMap(rightReg, getExistJVar(leftReg));

            } else if (thisIType.equals(InstructionType.ADD)) {

                if (curOps.get(0).getOperandType().equals(OperandType.CONSTANT)) {
                    long addend = getLong(curOps.get(0).getOperandValue());
                    JimpleVariable lhs = getExistJVar(getRegister(curOps.get(1).getOperandValue()));
                    jimAss.JimpleDirectAssign(lhs, (int) addend, currentJM);

                } else {
                    JimpleVariable lhs = getExistJVar(getRegister(curOps.get(1).getOperandValue()));
                    JimpleVariable rhs = getExistJVar(getRegister(curOps.get(0).getOperandValue()));
                    jimAss.JimpleDirectAssign(lhs, rhs, currentJM);
                }

            } else if (thisIType.equals(InstructionType.LEA)) {
                OperandTypeMemoryEffectiveAddress otmea = (OperandTypeMemoryEffectiveAddress) curOps.get(0).getOperandValue();
//          long offset = otmea.getOffset();
                String base = getRegister(otmea.getBase());
                String index = getRegister(otmea.getIndex());
                int scale = otmea.getScale();

                JimpleVariable addend = getExistJVar(base);
                JimpleVariable augend = getExistJVar(index);
                JimpleVariable sum = new JimpleVariable(getNewVarName(), "int", currentJM);
                jimAss.JimpleMul(addend, scale, currentJM);

                String rhs = getRegister(curOps.get(1).getOperandValue());
                // the offset is defaulted zero
                jimAss.JimpleAdd(sum, addend, augend, currentJM);
                updateRegToVarMap(rhs, sum);
            }

        }

    }

    private void getOneParaFilterProcess(Method m, ParsedInstructionsSet ins_set) {
        JimpleMethod jmethod = Map_jMethod.get(m.getMethodInfo().getMethodName());
        List<MemoryInstructionPair> pair_list = ins_set.getInstructions_List();
        List<String> paratypes = jmethod.getParameterTypes();
        int index = paratypes.size() - 1;
        // assign parameter from the last para to the first para
        for (MemoryInstructionPair mip : pair_list) {
            InstructionType thisIType = mip.getInstruction().getInstructiontype();
            List<IOperand> curOps = mip.getInstruction().getOperands();
            if (thisIType.equals(InstructionType.MOV)) {
                String leftReg = getRegister(curOps.get(0).getOperandValue());
                String rightReg = getMemoryEffectiveAddress(curOps.get(1).getOperandValue());
// for this case just have one parameter    
                if (index >= 0) {
                    JimpleVariable jvar = new JimpleVariable("l" + Integer.toString(index), paratypes.get(index), jmethod);
                    jimAss.JimpleParameterAssign(jvar, paratypes.get(index), index, jmethod);
                    updateRegToVarMap(rightReg, jvar);
                    index--;
                }
            }
            //get the para value of function
        }
    }

    private void getTwoParaFilterProcess(Method m, ParsedInstructionsSet ins_set) {
        JimpleMethod jmethod = Map_jMethod.get(m.getMethodInfo().getMethodName());
        List<MemoryInstructionPair> pair_list = ins_set.getInstructions_List();
        List<String> paratypes = jmethod.getParameterTypes();

        for (int mipIndex = 0; mipIndex < pair_list.size(); ++mipIndex) {
            MemoryInstructionPair mip = ins_set.getInstructions_List().get(mipIndex);
            InstructionType thisIType = mip.getInstruction().getInstructiontype();
            List<IOperand> curOps = mip.getInstruction().getOperands();

            if (thisIType.equals(InstructionType.MOV)) {

                String leftReg = getRegister(curOps.get(0).getOperandValue());
                String rightReg = getMemoryEffectiveAddress(curOps.get(1).getOperandValue());

                //two paranums , and will assign to second para first then the first para
                //use mipindex to decide which para is assigned         
                JimpleVariable jvar = new JimpleVariable("l" + Integer.toString(mipIndex), paratypes.get(mipIndex), jmethod);
                jimAss.JimpleParameterAssign(jvar, paratypes.get(mipIndex), mipIndex, jmethod);
                updateRegToVarMap(rightReg, jvar);
                //get the para value of function
            }
        }

    }

    //cmp -0x8(%rbp)<right hand side>, %eax<left hand side>
    private void ifFilterProcess(Method m, ParsedInstructionsSet ins_set) {

        List<MemoryInstructionPair> mips = ins_set.getInstructions_List();
        JimpleCondition ifcondition = null;
        JimpleMethod baseMethod = Map_jMethod.get(m.getMethodInfo().getMethodName());
        for (int mipIndex = 0; mipIndex < ins_set.getInstructions_List().size(); ++mipIndex) {//MemoryInstructionPair mip : ins_set.getInstructions_List()) {
            if (mips.get(mipIndex).getInstruction().getInstructiontype().equals(InstructionType.MOV)) {

                String lhsStr = getOperandValue(mips.get(mipIndex).getInstruction().getOperands().get(0).getOperandValue());
                String rhsStr = getOperandValue(mips.get(mipIndex).getInstruction().getOperands().get(1).getOperandValue());
                updateRegToVarMap(rhsStr, getExistJVar(lhsStr));
            } else if (mips.get(mipIndex).getInstruction().getInstructiontype().equals(InstructionType.CMP)) {
                long rhs = getLong(ins_set.getInstructions_List().get(0).
                        getInstruction().getOperands().get(0).getOperandValue());
                String rhsStr = getOperandValue(mips.get(mipIndex).getInstruction().getOperands().get(1).getOperandValue());

                mipIndex += 1;
                MemoryInstructionPair jmpMip = mips.get(mipIndex);
                String cmprSymbol = judgeSymbolOfIfStatement(jmpMip.getInstruction().getInstructiontype());

                ifcondition = new JimpleCondition(cmprSymbol, getExistJVar(rhsStr), (int) rhs, baseMethod);
            }
        }
        Object jmpTo = ins_set.getInstructions_List().get(1).getInstruction().getOperands().get(0).getOperandValue();
        String targetAddr = getMemoryEffectiveAddress(jmpTo);
        memAddrToJCond.put(targetAddr, ifcondition);





//    Object rhsobj = ins_set.getInstructions_List().get(0).
//            getInstruction().getOperands().get(0).getOperandValue();
//    JimpleCondition ifcondition = null;
//    String targetAddr;
//    if (rhsobj instanceof Long) {
//      long rhs = getLong(ins_set.getInstructions_List().get(0).
//              getInstruction().getOperands().get(0).getOperandValue());
//
//      JimpleVariable lhs = getExistJVar(getMemoryEffectiveAddress(ins_set.getInstructions_List().get(0).getInstruction().
//              getOperands().get(1).getOperandValue()));
//
//      String compareSymbol = judgeSymbolOfIfStatement(ins_set.getInstructions_List().get(1).getInstruction().getInstructiontype());
//
//      ifcondition = new JimpleCondition(
//              compareSymbol, lhs, (int) rhs, Map_jMethod.get(m.getMethodInfo().getMethodName()));
//
//    } else if (rhsobj instanceof OperandTypeMemoryEffectiveAddress) {
//      JimpleVariable rhs = getExistJVar(getMemoryEffectiveAddress(rhsobj));
//      JimpleVariable lhs = getExistJVar(getRegister(ins_set.getInstructions_List().get(0).getInstruction().
//              getOperands().get(1).getOperandValue()));
//      String compareSymbol = judgeSymbolOfIfStatement(ins_set.getInstructions_List().get(1).getInstruction().getInstructiontype());
//
//      ifcondition = new JimpleCondition(compareSymbol, lhs, rhs,
//              Map_jMethod.get(m.getMethodInfo().getMethodName()));
//    }
//
//    Object jmpTo = ins_set.getInstructions_List().get(1).getInstruction().getOperands().get(0).getOperandValue();
//    targetAddr = getMemoryEffectiveAddress(jmpTo);
//    memAddrToJCond.put(targetAddr, ifcondition);
    }

    private void ifWith2VaribFilterProcess(Method m, ParsedInstructionsSet ins_set) {
        List<MemoryInstructionPair> mips = ins_set.getInstructions_List();
        JimpleCondition ifcondition = null;
        JimpleMethod baseMethod = Map_jMethod.get(m.getMethodInfo().getMethodName());
        for (int mipIndex = 0; mipIndex < ins_set.getInstructions_List().size(); ++mipIndex) {//MemoryInstructionPair mip : ins_set.getInstructions_List()) {
            if (mips.get(mipIndex).getInstruction().getInstructiontype().equals(InstructionType.MOV)) {

                String lhsStr = getOperandValue(mips.get(mipIndex).getInstruction().getOperands().get(0).getOperandValue());
                String rhsStr = getOperandValue(mips.get(mipIndex).getInstruction().getOperands().get(1).getOperandValue());
                updateRegToVarMap(rhsStr, getExistJVar(lhsStr));
            } else if (mips.get(mipIndex).getInstruction().getInstructiontype().equals(InstructionType.CMP)) {
                String lhsStr = getOperandValue(mips.get(mipIndex).getInstruction().getOperands().get(1).getOperandValue());
                String rhsStr = getOperandValue(mips.get(mipIndex).getInstruction().getOperands().get(0).getOperandValue());

                mipIndex += 1;
                MemoryInstructionPair jmpMip = mips.get(mipIndex);
                String cmprSymbol = judgeSymbolOfIfStatement(jmpMip.getInstruction().getInstructiontype());

                ifcondition = new JimpleCondition(cmprSymbol, getExistJVar(lhsStr), getExistJVar(rhsStr), baseMethod);
            }
        }
        Object jmpTo = ins_set.getInstructions_List().get(2).getInstruction().getOperands().get(0).getOperandValue();
        String targetAddr = getMemoryEffectiveAddress(jmpTo);
        memAddrToJCond.put(targetAddr, ifcondition);
    }

    private void divideBy2NFilterProcess(Method m, ParsedInstructionsSet ins_set) {
        JimpleMethod currentJM = Map_jMethod.get(m.getMethodInfo().getMethodName());
        for (int mipIndex = 0; mipIndex < ins_set.getInstructions_List().size(); ++mipIndex) {
            MemoryInstructionPair mip = ins_set.getInstructions_List().get(mipIndex);
            InstructionType thisIType = mip.getInstruction().getInstructiontype();
            List<IOperand> curOps = mip.getInstruction().getOperands();

            if (thisIType.equals(InstructionType.MOV)) {
                String leftReg;
                if (curOps.get(0).getOperandValue() instanceof OperandTypeMemoryEffectiveAddress) {
                    leftReg = getMemoryEffectiveAddress(curOps.get(0).getOperandValue());
                } else {
                    leftReg = getRegister(curOps.get(0).getOperandValue());
                }
                String rightReg = getRegister(curOps.get(1).getOperandValue());
                updateRegToVarMap(rightReg, getExistJVar(leftReg));

            } else if (thisIType.equals(InstructionType.SHR)
                    && getLong(mip.getInstruction().getOperands().get(0).getOperandValue()) == 31) { // $0x1f = 31
                MemoryInstructionPair nextMip = ins_set.getInstructions_List().get(mipIndex + 1);
                if (nextMip.getInstruction().getInstructiontype().equals(InstructionType.LEA)) {
                    MemoryInstructionPair nextNextmip = ins_set.getInstructions_List().get(mipIndex + 2);
                    if (nextNextmip.getInstruction().getInstructiontype().equals(InstructionType.SAR)) {
                        String dividendRegName = getRegister(nextNextmip.getInstruction().getOperands().get(0).getOperandValue());
                        JimpleVariable dividendJV = getExistJVar(dividendRegName);
                        // a /= 2;
                        jimAss.JimpleDiv(dividendJV, 2, currentJM);
                    }
                }
            }
        }
    }

    private String getOperandValue(Object operandValue) {
        if (operandValue instanceof OperandTypeMemoryEffectiveAddress) {
            return getMemoryEffectiveAddress(operandValue);
        } else if (operandValue instanceof RegisterType) {
            return getRegister(operandValue);
        } else if (operandValue instanceof Long) {
            return ((Long) operandValue).toString();
        } else {
            return "";
        }
    }

    private String getMemoryEffectiveAddress(Object obj) {
        OperandTypeMemoryEffectiveAddress otmea = (OperandTypeMemoryEffectiveAddress) obj;
        long offset = otmea.getOffset();
        String base = getRegister(otmea.getBase());
        String index = getRegister(otmea.getIndex());
//    int scale = otmea.getScale();
        if (base.equals("") && index.equals("")) {
            return String.valueOf(offset);
        } else if (index.equals("")) {
            String result = String.valueOf(offset) + "(" + base + ")";
            return result;
        } else {
            String result = String.valueOf(offset) + "(" + base + "," + index + ")";
            return result;
        }
    }

    private String getRegister(Object obj) {
        if (obj != null) {
            RegisterType rt = (RegisterType) obj;
            return rt.name();
        } else {
            return "";
        }
    }

    private long getLong(Object obj) {
        Long rt = (Long) obj;
        return rt.longValue();
    }

    private String getNewVarName() {
        String temp = "v" + Integer.toString(getVcount());
        return temp;
    }

    private void cleanVarMap() {

        regToJVar.clear();;

    }

    private JimpleVariable getExistJVar(String regName) {
        if (regToJVar.containsKey(regName)) {
            return regToJVar.get(regName);
        } else {
            // I know, too ugly
            if (regName.equals("RAX")) {
                return regToJVar.get("EAX");
            } else if (regName.equals("RDX")) {
                return regToJVar.get("EDX");

            } else {
                return null;
            }
        }
    }

    private boolean updateRegToVarMap(String key, JimpleVariable value) {
        regToJVar.put(key, value);
        return true;
    }

    private String transferHexToOctal(String value) {
        String temp = value.substring(3);
        int temp1 = Integer.parseInt(temp, 16);
        return Integer.toOctalString(temp1);
    }

    // judge the symbol of the judgement statement
    private String judgeSymbolOfIfStatement(InstructionType ins_type) {
        String symbol = "";
        if (ins_type == InstructionType.JNE) {
            symbol = "!=";
        } else if (ins_type == InstructionType.JE) {
            symbol = "==";
        } else if (ins_type == InstructionType.JLE) {
            symbol = "<=";
        } else if (ins_type == InstructionType.JGE) {
            symbol = ">=";
        } else if (ins_type == InstructionType.JL) {
            symbol = "<";
        } else if (ins_type == InstructionType.JG) {
            symbol = ">";
        }
        // there are a lot of other situation, u can add "else if" statement to handle other situation
        return symbol;
    }

    /**
     * check if last line is JMP
     *
     * @param mip
     * @param basemethod
     */
    private void checkPreLine(MemoryInstructionPair mip, JimpleMethod basemethod) {
        if (mip.getInstruction().getInstructiontype().equals(InstructionType.JMP)) {
            JimpleCondition gotoCondition = new JimpleCondition(basemethod);
            String addrJmpTo = getOperandValue(mip.getInstruction().getOperands().get(0).getOperandValue());
            memAddrToJCond.put(addrJmpTo, gotoCondition);
        }
    }
    
}
