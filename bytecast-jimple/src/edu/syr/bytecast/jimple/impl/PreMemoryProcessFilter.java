/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.jimple.beans.jimpleBean.ParsedInstructionsSet;
import edu.syr.bytecast.jimple.beans.jimpleBean.JInstructionInfo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fei Qi
 */

//this file is used to filter the pre-memory process part

public class PreMemoryProcessFilter extends Filter{
    public boolean doTest(List<IInstruction> instList, ParsedInstructionsSet parsed_set)
    {
        for(int index = 0; index < instList.size(); index++)
        {
            IInstruction ins = instList.get(index);
            int count = 0;
            if( ins.getInstructiontype() == InstructionType.PUSH
                && ins.getOperands().get(0).getOperandType() == OperandType.REGISTER
                && ins.getOperands().get(0).getOperandValue() == "%rbp" )
            {
                count++;
                ins = instList.get(index + count);
                if( ins.getInstructiontype() == InstructionType.MOV
                    && ins.getOperands().get(0).getOperandType() == OperandType.REGISTER
                    && ins.getOperands().get(0).getOperandValue() == "%rsp"
                    && ins.getOperands().get(1).getOperandType() == OperandType.REGISTER
                    && ins.getOperands().get(1).getOperandValue() == "%rbp") //SectionName
                {
                    count++;
                    ins = instList.get(index + count);
                    if( ins.getInstructiontype() == InstructionType.SUB
                        && ins.getOperands().get(0).getOperandType() == OperandType.CONSTANT
                        && ins.getOperands().get(1).getOperandType() == OperandType.REGISTER
                        && ins.getOperands().get(1).getOperandValue() == "%rsp")
                    {
                        count++;
                        JInstructionInfo info = new JInstructionInfo();
                        info.setInstruction_Name("PreMemoryProcess");
                        info.setInstructions_Count(count);
                        info.setStart_Index(index);
                        parsed_set.setInfo(info);
                        List<IInstruction> temp = new ArrayList<IInstruction>();
                        temp.add(instList.get(index));
                        temp.add(instList.get(index + 1));
                        temp.add(instList.get(index + 2));
                        parsed_set.setInstructions_List(temp);
                        return true;
                    }
                }
            }

        }
        return false;
    } 
}
