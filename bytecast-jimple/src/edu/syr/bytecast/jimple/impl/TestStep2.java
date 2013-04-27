/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

/**
 *
 * @author mandy
 */
import edu.syr.bytecast.amd64.api.constants.IBytecastAMD64;
import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.constants.OperandTypeMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.amd64.api.output.IExecutableFile;
import edu.syr.bytecast.amd64.api.output.ISection;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.amd64.test.DepcrecatedMock;
import edu.syr.bytecast.jimple.api.Method;
import edu.syr.bytecast.jimple.api.MethodInfo;
import edu.syr.bytecast.jimple.beans.*;
import edu.syr.bytecast.jimple.beans.jimpleBean.JimpleAssign;
import edu.syr.bytecast.jimple.beans.jimpleBean.JimpleClass;
import edu.syr.bytecast.jimple.beans.jimpleBean.JimpleDoc;
import edu.syr.bytecast.jimple.beans.jimpleBean.JimpleMethod;
import edu.syr.bytecast.jimple.beans.jimpleBean.JimpleVariable;
import edu.syr.bytecast.jimple.beans.jimpleBean.*;
import edu.syr.bytecast.util.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestStep2 {

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

    public TestStep2(Map<Method, List<ParsedInstructionsSet>> temp_map) {
        this.method_map = temp_map;
        this.methods = temp_map.keySet();
        Map_jMethod = new HashMap<String, JimpleMethod>();
        jimple_doc = new JimpleDoc();
        jimple_class = new JimpleClass("test2", 1);
        jimple_doc.addClass(jimple_class);
        this._vcount = 0;
        regToJVar = new HashMap<String, JimpleVariable>();
//        varToJVar = new HashMap<String , JimpleVariable>();
    }

    public void createJimple() {



        initializeAllJimpleMethod();

        // implementJimpleMethod();
    }

    private void initializeAllJimpleMethod() {


        // List<ParsedInstructionsSet> main_ins = new ArrayList<ParsedInstructionsSet>();

        //create all jimple method
        for (Method m : methods) {

            //main method
            if (m.getMethodInfo().getMethodName().equals("main")) {
                ArrayList<String> parameter_type = new ArrayList<String>();
                parameter_type.add("String[]");
                JimpleMethod jMethod = new JimpleMethod(1, "void", "main", parameter_type, jimple_class);
                Map_jMethod.put("main", jMethod);
                // thisclass obj = new thisclass();
                objectOfThisClass = new JimpleVariable("obj", jimple_class.getJClassName(), jMethod);
                jimAss.JimpleNewClass(objectOfThisClass, jimple_class, jMethod);
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
//        try {
//            jimple_doc.printJimple(jimple_class.getJClassName(), "jimple");
//
//        } catch (FileNotFoundException e) {
//            System.out.println("file exception");
//        } catch (IOException e) {
//            System.out.println("IO exception");
//        }



    }

    private void implementJimpleMethod() {
        for (Method m : methods) {
            List<ParsedInstructionsSet> method_ParseIns = method_map.get(m);
            implementSingleJimpleMethod(m, method_ParseIns);
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
                for (int i = end_index_of_last; i < number_of_lines; i++) {
                    handleUnparsedLines(m, m.getL_instruction().get(i), m.getL_instruction().get(i - 1));

                }
            }
            if (pis.getInfo().getInstruction_Name().equals("PreMemoryProcess")) {
                prememoryFilterProcess(m, pis);
            } else if (pis.getInfo().getInstruction_Name().equals("SetArgvAndArgc")) {
                setArgFilterProcess(m, pis);
            } else if (pis.getInfo().getInstruction_Name().equals("UseArgvAndArgc")) {
                useArgFilterProcess(m, pis);
            } else if (pis.getInfo().getInstruction_Name().equals("Calling")) {
                callingFilterProcess(m, pis);
            } else if (pis.getInfo().getInstruction_Name().equals("Leave")) {
                leaveFilterProcess(m, pis);
            } else if (pis.getInfo().getInstruction_Name().equals("GetOneParameter")) {
                addFilterProcess(m, pis);
            } else if (pis.getInfo().getInstruction_Name().equals("GetTwoParameter")) {
                addFilterProcess(m, pis);
            } else if (pis.getInfo().getInstruction_Name().equals("IfWithBothVariable")) {
                addFilterProcess(m, pis);
            } else if (pis.getInfo().getInstruction_Name().equals("DivBy2N")) {
                addFilterProcess(m, pis);
            } else if (pis.getInfo().getInstruction_Name().equals("If")) {
                addFilterProcess(m, pis);
            } else if (pis.getInfo().getInstruction_Name().equals("Add")) {
                addFilterProcess(m, pis);
            }
            end_index_of_last = pis.getInfo().getStart_Index() + pis.getInfo().getInstructions_Count();
        }
    }

    private void handleUnparsedLines(Method m ,MemoryInstructionPair singleLine, MemoryInstructionPair lastSingleLine) {
        IInstruction ins = singleLine.getInstruction();
        if (ins.getInstructiontype().equals(InstructionType.MOV)
                && ins.getOperands().get(0).getOperandType().equals(OperandType.REGISTER)
                && ins.getOperands().get(1).getOperandValue().equals(RegisterType.EAX)) {
            // add return statement return 0 to the coresponding fucntion;
            JimpleMethod currentJVM = Map_jMethod.get(m.getMethodInfo().getMethodName());
            currentJVM.setReturn(null);   // return void
                           
        } else if (ins.getInstructiontype().equals(InstructionType.MOV)
                && ins.getOperands().get(0).getOperandValue().equals(RegisterType.EAX)) {
            IInstruction ins_last = lastSingleLine.getInstruction();
            if (ins_last.getInstructiontype().equals(InstructionType.CALLQ)) {
                String funcName = ins.getOperands().get(1).getOperandValue().toString();
                if (ins.getOperands().get(0).getOperandType().equals(OperandType.MEMORY_EFFECITVE_ADDRESS)//OperandType.MEMORY_PHYSICAL_ADDRESS )
                        && !funcName.contains("printf")) {
                    // put the result of function "eax" into map;
                    // leave printf for runtime team
                  //  updateRegToVarMap(leftvalue, rightvalue);
                }
            }
        }

    }

    private void prememoryFilterProcess(Method m, ParsedInstructionsSet ins_set) {
    }

    private void setArgFilterProcess(Method m, ParsedInstructionsSet ins_set) {
        JimpleMethod jmethod = Map_jMethod.get(m.getMethodInfo().getMethodName());

        List<MemoryInstructionPair> pair_list = ins_set.getInstructions_List();
        //JimpleAssign jim_ass = new JimpleAssign();
        JimpleVariable j_argv = new JimpleVariable("argv", "String []", jmethod);
        JimpleVariable j_argc = new JimpleVariable("argc", "int", jmethod);
        jimAss.JimpleParameterAssign(j_argv, "String []", 0, jmethod);
        jimAss.JimpleLengthOf(j_argc, j_argv, jmethod);
        for (MemoryInstructionPair mip : pair_list) {
            InstructionType thisIType = mip.getInstruction().getInstructiontype();
            List<IOperand> curOps = mip.getInstruction().getOperands();
            String leftReg = getRegister(curOps.get(0).getOperandValue());
            String rightReg = getRegister(curOps.get(1).getOperandValue());

            if (thisIType.equals(InstructionType.MOV) && rightReg.equals("%rsi")) {
                updateRegToVarMap(rightReg, j_argv);
            } else {
                updateRegToVarMap(rightReg, j_argc);
            }

        }
        // jmethod.
        //updateRegToVarMap(argv, argv);



        //updateRegToVarMap(argv, argv);
    }

    private void useArgFilterProcess(Method m, ParsedInstructionsSet ins_set) {
        JimpleMethod jmethod = Map_jMethod.get(m.getMethodInfo().getMethodName());
        List<MemoryInstructionPair> pair_list = ins_set.getInstructions_List();
         String leftReg = null;
         String rightReg = null;
         JimpleVariable j_array =null;
         int index=0;
         for (int i=0; i<pair_list.size();i++){
          InstructionType thisIType = pair_list.get(i).getInstruction().getInstructiontype();
            
          //find move and add 
          if(thisIType.equals(InstructionType.MOV)&& (i!=pair_list.size())){
              InstructionType nextIType = pair_list.get(i+1).getInstruction().getInstructiontype();
                if(nextIType.equals(InstructionType.ADD)){
                    //get the whole value not only the register
                    leftReg = getRegister(pair_list.get(i).getInstruction().getOperands().get(0).getOperandValue());
                    j_array =  regToJVar.get(leftReg); 
                    
                    long temp = getLong(pair_list.get(i+1).getInstruction().getOperands().get(0).getOperandValue());
                    index = (int)temp/8 -1;      
                }                
         }
          
              if(thisIType.equals(InstructionType.MOVSBL)){
                   //change getReg later to obtain all the value from right operand
                    rightReg =getRegister(pair_list.get(i).getInstruction().getOperands().get(1).getOperandValue());;
                    JimpleVariable j_var = new JimpleVariable("argv"+ Integer.toString(index), "String", jmethod);
                    jimAss.JimpleAssignFromArray(j_var, j_array, index, jmethod);
                    updateRegToVarMap(rightReg, j_var);
                }
             
         }
//        String left_operand1 =
//                getMemoryEffectiveAddress(pair_list.get(0).getInstruction().getOperands().get(0).getOperandValue());
//        String right_operand1 =
//                getRegister(pair_list.get(0).getInstruction().getOperands().get(1).getOperandValue());
//        left_operand1 = (left_operand1);
//        updateRegToVarMap(right_operand1, left_operand1);
//        long left_operand2 = getLong(pair_list.get(1).getInstruction().getOperands().get(0).getOperandValue());
//        long argv_index = left_operand2 / 8;
//        updateRegToVarMap("rax", left_operand1 + "[" + Long.toOctalString(argv_index) + "]");
//        updateRegToVarMap("eax", left_operand1);


    }

    private void callingFilterProcess(Method m, ParsedInstructionsSet ins_set) {
        JimpleMethod baseMethod = Map_jMethod.get(m.getMethodInfo().getMethodName());

        ArrayList<JimpleVariable> parameters = new ArrayList<JimpleVariable>();

        for (MemoryInstructionPair mip : ins_set.getInstructions_List()) {
            InstructionType thisIType = mip.getInstruction().getInstructiontype();
            List<IOperand> curOps = mip.getInstruction().getOperands();

            if (thisIType.equals(InstructionType.MOV)) {
                String leftReg = getRegister(curOps.get(0).getOperandValue());
                String rightReg = getRegister(curOps.get(1).getOperandValue());

                updateRegToVarMap(rightReg, getExistJVar(leftReg));

                parameters.add(getExistJVar(rightReg));
            } else if (thisIType.equals(InstructionType.CALLQ)) {
                String methodName = curOps.get(1).getOperandValue().toString();

                jimInvk.invokeUserDefined(objectOfThisClass,
                        Map_jMethod.get(methodName), parameters, null, baseMethod);
            }
        }
    }

    private void leaveFilterProcess(Method m, ParsedInstructionsSet ins_set) {
        
        
    }

    private void addFilterProcess(Method m, ParsedInstructionsSet ins_set) {
        JimpleMethod currentJM = Map_jMethod.get(m.getMethodInfo().getMethodName());
        for (MemoryInstructionPair mip : ins_set.getInstructions_List()) {
            InstructionType thisIType = mip.getInstruction().getInstructiontype();
            List<IOperand> curOps = mip.getInstruction().getOperands();

            if (thisIType.equals(InstructionType.MOV)) {
                String leftReg = getRegister(curOps.get(0).getOperandValue());
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


                // the offset is defaulted zero
                jimAss.JimpleAdd(sum, addend, augend, currentJM);
            }

        }

    }

    private void getOneParaFilterProcess(Method m, ParsedInstructionsSet ins_set) {
        JimpleMethod jmethod = Map_jMethod.get(m.getMethodInfo().getMethodName());
        List<MemoryInstructionPair> pair_list = ins_set.getInstructions_List();
        for (MemoryInstructionPair mip : pair_list) {
            InstructionType thisIType = mip.getInstruction().getInstructiontype();
            List<IOperand> curOps = mip.getInstruction().getOperands();

            String leftReg = getRegister(curOps.get(0).getOperandValue());
            String rightReg = getRegister(curOps.get(1).getOperandValue());

            List<String> paratypes = jmethod.getParameterTypes();
            for (int i = 0; i < paratypes.size(); ++i) {
                JimpleVariable jvar = new JimpleVariable("l" + Integer.toString(i), paratypes.get(i), jmethod);
                JimpleAssign jim_ass = new JimpleAssign();
                jim_ass.JimpleParameterAssign(jvar, paratypes.get(i), i, jmethod);

                updateRegToVarMap(rightReg, jvar);
            }
            //get the para value of function
        }
    }

    private void getTwoParaFilterProcess(Method m, ParsedInstructionsSet ins_set) {
             JimpleMethod jmethod = Map_jMethod.get(m.getMethodInfo().getMethodName());
        List<MemoryInstructionPair> pair_list = ins_set.getInstructions_List();
       
        
        for (int mipIndex = 0; mipIndex < pair_list.size(); ++mipIndex) {
            MemoryInstructionPair mip = ins_set.getInstructions_List().get(mipIndex);
            InstructionType thisIType = mip.getInstruction().getInstructiontype();
            List<IOperand> curOps = mip.getInstruction().getOperands();

           if(thisIType.equals(InstructionType.MOV)){
            
            String leftReg = getRegister(curOps.get(0).getOperandValue());
            String rightReg = getRegister(curOps.get(1).getOperandValue());

            List<String> paratypes = jmethod.getParameterTypes();
            //two paranums , and will assign to second para first then the first para
            //use mipindex to decide which para is assigned         
                JimpleVariable jvar = new JimpleVariable("l" + Integer.toString(mipIndex), paratypes.get(mipIndex), jmethod);
                JimpleAssign jim_ass = new JimpleAssign();
                jim_ass.JimpleParameterAssign(jvar, paratypes.get(mipIndex), mipIndex, jmethod);
                updateRegToVarMap(rightReg, jvar);       
            //get the para value of function
           }
        }
        

        
    }

    private void ifFilterProcess(Method m, ParsedInstructionsSet ins_set) {
      
      long rhs = getLong(ins_set.getInstructions_List().get(0).
              getInstruction().getOperands().get(0).getOperandValue());
      
      JimpleVariable lhs = regToJVar.get(getMemoryEffectiveAddress(ins_set.
              getInstructions_List().get(0).getInstruction().
              getOperands().get(1).getOperandValue()));
      
      String compareSymbol = judgeSymbolOfIfStatement(ins_set.getInstructions_List().get(1)
              .getInstruction().getInstructiontype());
      
      JimpleCondition ifcondition = new JimpleCondition(
              compareSymbol, lhs, (int)rhs, Map_jMethod.get(m.getMethodInfo().getMethodName()));
      
    }

    private void ifWith2VaribFilterProcess(Method m, ParsedInstructionsSet ins_set) {
    }

    private void divideBy2NFilterProcess(Method m, ParsedInstructionsSet ins_set) {
        JimpleMethod currentJM = Map_jMethod.get(m.getMethodInfo().getMethodName());
        for (int mipIndex = 0; mipIndex < ins_set.getInstructions_List().size(); ++mipIndex) {
            MemoryInstructionPair mip = ins_set.getInstructions_List().get(mipIndex);
            InstructionType thisIType = mip.getInstruction().getInstructiontype();
            List<IOperand> curOps = mip.getInstruction().getOperands();

            if (thisIType.equals(InstructionType.MOV)) {
                String leftReg = getRegister(curOps.get(0).getOperandValue());
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

    private String getMemoryEffectiveAddress(Object obj) {
        OperandTypeMemoryEffectiveAddress otmea = (OperandTypeMemoryEffectiveAddress) obj;
        long offset = otmea.getOffset();
        String base = getRegister(otmea.getBase());
        String index = getRegister(otmea.getIndex());
        int scale = otmea.getScale();
        String result = String.valueOf(offset) + "(" + base + "," + index + ")";
        return result;
    }

    private String getRegister(Object obj) {
        if (obj == null) {
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

    private JimpleVariable getExistJVar(String regName) {
        if (regToJVar.containsKey(regName)) {
            return regToJVar.get(regName);
        } else {
            // I know, too ugly
            if (regName.equals("rax")) {
                return regToJVar.get("eax");
            } else if (regName.equals("rdx")) {
                return regToJVar.get("edx");

            } else {
                return null;
            }
        }
    }

    private boolean updateRegToVarMap(String key, JimpleVariable value) {

        regToJVar.put(key, value);
//        JimpleVariable JVar = new JimpleVariable(getNewVarName(regName), "int", baseMethod);
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

    public static void main(String[] argv) {
        Map<Method, List<ParsedInstructionsSet>> filter_result = new HashMap<Method, List<ParsedInstructionsSet>>();
        // get all the sections from the IExecutableFile
        Paths.v().setRoot("/home/mandy/code/bytecast");
        try {
            Paths.v().parsePathsFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        IBytecastAMD64 testdata = new DepcrecatedMock();
        IExecutableFile exe_file = testdata.buildInstructionObjects();

        ISection wholeSection = exe_file.getSectionsWithInstructions().get(0);

        PatternSeperator patt_Seperator = new PatternSeperator();
        filter_result = patt_Seperator.doSeperate(wholeSection);

        TestStep2 test2 = new TestStep2(filter_result);
        test2.createJimple();
    }
}
