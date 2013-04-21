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
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.IExecutableFile;
import edu.syr.bytecast.amd64.api.output.ISection;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.amd64.test.DepcrecatedMock;
import edu.syr.bytecast.jimple.api.Method;
import edu.syr.bytecast.jimple.api.MethodInfo;
import edu.syr.bytecast.jimple.beans.*;
import edu.syr.bytecast.jimple.beans.jimpleBean.JimpleClass;
import edu.syr.bytecast.jimple.beans.jimpleBean.JimpleDoc;
import edu.syr.bytecast.jimple.beans.jimpleBean.JimpleMethod;
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
    
    // maintain a relation between variable and register
    // example:
    // edx : v1
    // -0x4(%rbp) : p1
    private Map<String, String> regToVar;
    
    public TestStep2(Map<Method, List<ParsedInstructionsSet>> temp_map) {
        this.method_map = temp_map;
        this.methods = temp_map.keySet();
        Map_jMethod = new HashMap<String, JimpleMethod>();
        jimple_doc = new JimpleDoc();
        jimple_class = new JimpleClass("test2", 1);
        jimple_doc.addClass(jimple_class);
        regToVar = new HashMap<String, String>();
    }

    public void createJimple() {



        initializeAllJimpleMethod();

        // implementJimpleMethod();
    }

    private void initializeAllJimpleMethod() {


        // List<ParsedInstructionsSet> main_ins = new ArrayList<ParsedInstructionsSet>();

        //create all jimple method
        for (Method m : methods) {

            MethodInfo m_info = m.getMethodInfo();
            ArrayList<String> parameter_type_sum = new ArrayList<String>();
            int para_num = m_info.getParameterCount();
            if (para_num != 0) {
                for (int i = 0; i < para_num; i++) {
                    parameter_type_sum.add("int");
                }
            }
            JimpleMethod jMethod = new JimpleMethod(1, "int", m_info.getMethodName(), parameter_type_sum, jimple_class);


            //test for method output

            Map_jMethod.put(m_info.getMethodName(), jMethod);
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

    private void implementSingleJimpleMethod(Method m, List<ParsedInstructionsSet> listPis) {
        int start_index_of_this, number_of_lines;
        int end_index_of_last = 0;
        for (ParsedInstructionsSet pis : listPis) {
            start_index_of_this = pis.getInfo().getStart_Index();
            number_of_lines = start_index_of_this - end_index_of_last;
            if (number_of_lines != 0) {
                for (int i = end_index_of_last; i < number_of_lines; i++) {
                    handleUnparsedLines(m.getL_instruction().get(i), m.getL_instruction().get(i - 1));
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
//    private void insAnalyze(ParsedInstructionsSet ins_set) {
//        // initialize the jimple file
//        JInstructionInfo info = ins_set.getInfo();
//        
//        List<MemoryInstructionPair> pair_list = ins_set.getInstructions_List();
//        String name = info.getInstruction_Name();
//
//
//        if (name.equals("PreMemoryProcess")) {
//            //ArrayList<String> parameter_list_main = new ArrayList<String>();
//            //parameter_list_main.add("String[]");
//            //create main method add initiate first few lines of main
//        } else if (name.equals("SetArgvAndArgc")) {
//            String argc =
//                    pair_list.get(0).getInstruction().getOperands().get(1).getOperandValue().toString();
//            String argv =
//                    pair_list.get(1).getInstruction().getOperands().get(1).getOperandValue().toString();
//            parameter.put(argc, "argc");
//            parameter.put(argv, "argv");
//        } else if (name.equals("If")) {
//            OperandType left_operand_type =
//                    pair_list.get(0).getInstruction().getOperands().get(0).getOperandType();
//            OperandType right_operand_type =
//                    pair_list.get(0).getInstruction().getOperands().get(1).getOperandType();
//            String left_operand =
//                    pair_list.get(0).getInstruction().getOperands().get(0).getOperandValue().toString();
//            String right_operand =
//                    pair_list.get(0).getInstruction().getOperands().get(1).getOperandValue().toString();
//
//            transferParameter(left_operand_type, left_operand, parameter);
//            transferParameter(right_operand_type, right_operand, parameter);
//            InstructionType ins_type = pair_list.get(1).getInstruction().getInstructiontype();
//            String symbol = judgeSymbolOfIfStatement(ins_type);
//            // write the Jimple if statement
//
//            JimpleCondition jcl;
//            if (left_operand_type == OperandType.CONSTANT && right_operand_type == OperandType.MEMORY_EFFECITVE_ADDRESS) {
//                JimpleVariable right_variable = new JimpleVariable(right_operand, "int", method_list.get(_index));
//                jcl = new JimpleCondition(symbol, right_variable, Integer.parseInt(left_operand), method_list.get(_index));
//            } else {
//                JimpleVariable left_variable = new JimpleVariable(left_operand, "int", method_list.get(_index));
//                JimpleVariable right_variable = new JimpleVariable(right_operand, "int", method_list.get(_index));
//                jcl = new JimpleCondition(symbol, right_variable, left_variable, method_list.get(_index));
//            }
//
//        } else if (name.equals("Add")) {
//            //in this test case  only sum method contains add assignment
//            // write the Jimple Add statement
//        } else if (name.equals("Calling")) {
//            int num_para = 0;
//            for (MemoryInstructionPair pair : pair_list) {
//                if (pair.getInstruction().getInstructiontype() != InstructionType.CALLQ) {
//                    num_para++;
//                }
//            }
//            ArrayList<String> parameter_list_jmethod = new ArrayList<String>();
//
//            // 0-num_para is used to find parameter list(type and value)
//            for (int i = 0; i < num_para; i++) {
//                OperandType operand_type =
//                        pair_list.get(i).getInstruction().getOperands().get(0).getOperandType();
//                String operand_value =
//                        pair_list.get(i).getInstruction().getOperands().get(0).getOperandValue().toString();
//                transferParameter(operand_type, operand_value, parameter);
//                parameter.put(pair_list.get(i).getInstruction().getOperands().get(1).getOperandValue().toString(), operand_value);
//                parameter_list_jmethod.add(operand_value);
//            }
//            //num_para is callq with function name that will jump into
//            String function_name = pair_list.get(num_para).getInstruction().getOperands().get(1).getOperandValue().toString();
//            // write the Jimple Calling statement
//
//
//            //check whether this function name is existed in the jmethod list
//            boolean judge = false;
//            for (JimpleMethod j_method : method_list) {
//                if (j_method.getMethodName().equals(function_name)) {
//                    judge = true;
//                    break;
//                }
//            }
//            if (judge == false) {
//                JimpleMethod jMethod = new JimpleMethod(1, "int", function_name, parameter_list_jmethod, jClass);
//                method_list.add(jMethod);
//            }
//        } else if (name.equals("Leave")) {
//            // write the Jimple Leave statement
//        }
//    }

    private void handleUnparsedLines(MemoryInstructionPair singleLine, MemoryInstructionPair lastSingleLine) {
        IInstruction ins = singleLine.getInstruction();
        if (ins.getInstructiontype().equals(InstructionType.MOV)
                && ins.getOperands().get(0).getOperandType().equals(OperandType.REGISTER)
                && ins.getOperands().get(1).getOperandValue().equals(RegisterType.EAX)) {
            // return 0;
        } else if (ins.getInstructiontype().equals(InstructionType.MOV)
                && ins.getOperands().get(0).getOperandValue().equals(RegisterType.EAX)) {
            IInstruction ins_last = lastSingleLine.getInstruction();
            if (ins_last.getInstructiontype().equals(InstructionType.CALLQ)) {
                String funcName = ins.getOperands().get(1).getOperandValue().toString();
                if (ins.getOperands().get(0).getOperandType().equals(OperandType.MEMORY_EFFECITVE_ADDRESS)//OperandType.MEMORY_PHYSICAL_ADDRESS )
                        && !funcName.contains("printf")) {
                    // put the result of function "eax" into map;
                }
            }
        }
    }

    private void prememoryFilterProcess(Method m, ParsedInstructionsSet ins_set) {
    }

    private void setArgFilterProcess(Method m, ParsedInstructionsSet ins_set) {
    }

    private void useArgFilterProcess(Method m, ParsedInstructionsSet ins_set) {
    }

    private void callingFilterProcess(Method m, ParsedInstructionsSet ins_set) {
    }

    private void leaveFilterProcess(Method m, ParsedInstructionsSet ins_set) {
    }

    private void addFilterProcess(Method m, ParsedInstructionsSet ins_set) {
      String augendRegName = ins_set.getInstructions_List().get(0).
              getInstruction().getOperands().get(1).getOperandValue().toString();
      String addend = ins_set.getInstructions_List().get(1).
              getInstruction().getOperands().get(1).getOperandValue().toString();
    }

    private void getOneParaFilterProcess(Method m, ParsedInstructionsSet ins_set) {
    }

    private void getTwoParaFilterProcess(Method m, ParsedInstructionsSet ins_set) {
    }

    private void ifFilterProcess(Method m, ParsedInstructionsSet ins_set) {
    }

    private void ifWith2VaribFilterProcess(Method m, ParsedInstructionsSet ins_set) {
    }

    private void divideBy2NFilterProcess(Method m, ParsedInstructionsSet ins_set) {
    }

    private boolean updateRegToVarMap(String regName, String varName) {
      //  rax is equal to eax
      if (regToVar.containsKey(regName)) {
        regToVar.put(regName, varName);
      } else {
        
      }
      return true;
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
