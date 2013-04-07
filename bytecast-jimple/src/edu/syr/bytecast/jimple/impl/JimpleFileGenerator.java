/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.output.ISection;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.jimple.beans.jimpleBean.JInstructionInfo;
import edu.syr.bytecast.jimple.beans.jimpleBean.JimpleClass;
import edu.syr.bytecast.jimple.beans.jimpleBean.JimpleCondition;
import edu.syr.bytecast.jimple.beans.jimpleBean.JimpleDoc;
import edu.syr.bytecast.jimple.beans.jimpleBean.JimpleMethod;
import edu.syr.bytecast.jimple.beans.jimpleBean.JimpleVariable;
import edu.syr.bytecast.jimple.beans.jimpleBean.ParsedInstructionsSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author nick
 */
public class JimpleFileGenerator {
    
    // declare the JimpleDocument 
    private JimpleDoc jDoc;
    
    // declare the JimpleDocument 
    private JimpleMethod jMainMethod;
    
    // used to store the parameter in the assembly language
    private Map<String, String> parameter;
    
    public void doJimpleCreate(Map<ISection, List<ParsedInstructionsSet>> filter_result)
    {
        // sort the order of set in each ParsedInstructionSet
        sortSetInISection(filter_result);
        // used to store the parameter in the assembly language
        parameter = new HashMap<String, String>();
        // create the object of jimple file
        // implement the Jimple Document
        jDoc = new JimpleDoc();
        Set<ISection> sections = filter_result.keySet();
        for(ISection section : sections)
        {
            
            List<ParsedInstructionsSet> temp_set_list = filter_result.get(section);
            for(ParsedInstructionsSet parsed_set : temp_set_list)
            {
                createJimpleFile(parsed_set);
            }
        }
    }
    
    // sort utility to sort ParsedInstructionSet without creating new ParsedInstructionSet
    class sortParsedInstructionSet implements Comparator<ParsedInstructionsSet> {
        @Override
        public int compare(ParsedInstructionsSet pis1, ParsedInstructionsSet pis2) {
            if (pis1.getInfo().getStart_Index() > pis2.getInfo().getStart_Index()) {
                return 1;
            } else if (pis1.getInfo().getStart_Index() == pis2.getInfo().getStart_Index()) {
                return 0;
            } else {
                return -1;
            }
        }
    }
    
    // sort the order of the set in section
    private void sortSetInISection(Map<ISection, List<ParsedInstructionsSet>> filter_result)
    {
        // get all section in list
        Set<ISection> sections = filter_result.keySet();
        for(ISection section : sections)
        {
            // sort the set in the list in the order of the start_index
            List<ParsedInstructionsSet> ordering_set = filter_result.get(section);
            Collections.sort(ordering_set, new sortParsedInstructionSet());
            filter_result.put(section, ordering_set);
        }
    }
    
    private void createJimpleFile(ParsedInstructionsSet ins_set){
        // initialize the jimple file
        JInstructionInfo info = ins_set.getInfo();
        List<MemoryInstructionPair> pair_list = ins_set.getInstructions_List();
        String name = info.getInstruction_Name();
        
        JimpleClass jClass = new JimpleClass("Test", 1);
        jDoc.addClass(jClass);
        if(name.equals("PreMemoryProcess"))
        {
            ArrayList<String> parameter_list_main = new ArrayList<String>();
            parameter_list_main.add("String[]");

            //create main method add initiate first few lines of main
            jMainMethod = new JimpleMethod(9, "void","main", parameter_list_main, jClass);
        }
        else if(name.equals("SetArgvAndArgc"))
        {
            String argc = 
                    pair_list.get(0).getInstruction().getOperands().get(1).getOperandValue().toString();
            String argv = 
                    pair_list.get(1).getInstruction().getOperands().get(1).getOperandValue().toString();
            parameter.put(argc, "argc");
            parameter.put(argv, "argv");
        }
        else if(name.equals("If"))
        {
            OperandType left_operand_type =
                    pair_list.get(0).getInstruction().getOperands().get(0).getOperandType();
            OperandType right_operand_type =
                    pair_list.get(0).getInstruction().getOperands().get(1).getOperandType();
            String left_operand = 
                    pair_list.get(0).getInstruction().getOperands().get(0).getOperandValue().toString();
            String right_operand =
                    pair_list.get(0).getInstruction().getOperands().get(1).getOperandValue().toString();
            
            transferParameter(left_operand_type, left_operand, parameter);
            transferParameter(right_operand_type, right_operand, parameter);
            InstructionType ins_type = pair_list.get(1).getInstruction().getInstructiontype();
            String symbol = judgeSymbolOfIfStatement(ins_type);
            // write the Jimple if statement
            
            JimpleCondition jcl;
            if(left_operand_type == OperandType.CONSTANT && right_operand_type == OperandType.MEMORY_EFFECITVE_ADDRESS)
            {
                JimpleVariable right_variable = new JimpleVariable(right_operand, "int", jMainMethod);
                jcl = new JimpleCondition(symbol, right_variable, Integer.parseInt(left_operand), jMainMethod);
            }
            else
            {
                JimpleVariable left_variable = new JimpleVariable(left_operand, "int", jMainMethod);
                JimpleVariable right_variable = new JimpleVariable(right_operand, "int", jMainMethod);
                jcl = new JimpleCondition(symbol, right_variable, left_variable, jMainMethod);
            }
            
        }
        else if(name.equals("Add"))
        {
            // write the Jimple Add statement
        }
        else if(name.equals("Calling"))
        {
            int num_para = 0;
            for(MemoryInstructionPair pair : pair_list)
            {
                if( pair.getInstruction().getInstructiontype() != InstructionType.CALLQ)
                {
                    num_para ++;
                }
            }
            List<String> parameterForFunction = new ArrayList<String>();
            for(int i = 0; i < num_para; i ++)
            {
                OperandType operand_type =
                    pair_list.get(i).getInstruction().getOperands().get(0).getOperandType();
                String operand_value = 
                    pair_list.get(i).getInstruction().getOperands().get(0).getOperandValue().toString();
                transferParameter(operand_type, operand_value, parameter);
                parameter.put(pair_list.get(i).getInstruction().getOperands().get(1).getOperandValue().toString(), operand_value);
                parameterForFunction.add(operand_value);
            }
            String function_name = pair_list.get(num_para).getInstruction().getOperands().get(1).getOperandValue().toString();
            // write the Jimple Calling statement
        }
        else if(name.equals("Leave"))
        {
            // write the Jimple Leave statement
        }
    }
    
    // transfer the parameter in need
    private void transferParameter(OperandType type, String value, Map<String, String> parameter)
    {
        if( type == OperandType.CONSTANT)
        {
            value = value.substring(3);
            int temp = Integer.parseInt(value);
            value = Integer.toOctalString(temp);
        }
        else if( type == OperandType.MEMORY_EFFECITVE_ADDRESS)
        {
            value = parameter.get(value);
        }
    }
    
    // judge the symbol of the judgement statement
    private String judgeSymbolOfIfStatement(InstructionType ins_type)
    {
        String symbol = "";
        if(ins_type == InstructionType.JNE)
                symbol = "!=";
        else if(ins_type == InstructionType.JE)
                symbol = "==";
        else if(ins_type == InstructionType.JLE)
                symbol = "<=";
        else if(ins_type == InstructionType.JGE)
                symbol = ">=";
        else if(ins_type == InstructionType.JL)
                symbol = "<";
        else if(ins_type == InstructionType.JG)
                symbol = ">";
            // there are a lot of other situation, u can add "else if" statement to handle other situation
        return symbol;
    }
    
    
    
}
