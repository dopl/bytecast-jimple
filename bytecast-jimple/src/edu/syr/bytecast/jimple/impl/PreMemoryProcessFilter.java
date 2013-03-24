/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.jimple.beans.jimpleBean.ParsedInstructionsSet;
import edu.syr.bytecast.jimple.beans.jimpleBean.JInstructionInfo;
import edu.syr.bytecast.jimple.api.IFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Fei Qi
 */

//this file is used to filter the pre-memory process part

public class PreMemoryProcessFilter implements IFilter{
    public boolean doTest(List<MemoryInstructionPair> instList, int index)
    {
        IInstruction ins = instList.get(index).getInstruction();
        int count = 0;
        if( ins.getInstructiontype() == InstructionType.PUSH
            && ins.getOperands().get(0).getOperandType() == OperandType.REGISTER
            && ins.getOperands().get(0).getOperandValue() == "%rbp" )
        {
            count++;
            ins = instList.get(index + count).getInstruction();
            if( ins.getInstructiontype() == InstructionType.MOV
                && ins.getOperands().get(0).getOperandType() == OperandType.REGISTER
                && ins.getOperands().get(0).getOperandValue() == "%rsp"
                && ins.getOperands().get(1).getOperandType() == OperandType.REGISTER
                && ins.getOperands().get(1).getOperandValue() == "%rbp") //SectionName
            {
                count++;
                ins = instList.get(index + count).getInstruction();
                if( ins.getInstructiontype() == InstructionType.SUB
                    && ins.getOperands().get(0).getOperandType() == OperandType.CONSTANT
                    && ins.getOperands().get(1).getOperandType() == OperandType.REGISTER
                    && ins.getOperands().get(1).getOperandValue() == "%rsp")
                {
                        count++;
//                        JInstructionInfo info = new JInstructionInfo();
//                        info.setInstruction_Name("PreMemoryProcess");
//                        info.setInstructions_Count(count);
//                        info.setStart_Index(index);
//                        parsed_set.setInfo(info);
//                        List<IInstruction> temp = new ArrayList<IInstruction>();
//                        temp.add(instList.get(index));
//                        temp.add(instList.get(index + 1));
//                        temp.add(instList.get(index + 2));
//                        parsed_set.setInstructions_List(temp);
                        return true;
                    }
                }
            }
        return false;
    } 
}
