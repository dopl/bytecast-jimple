/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.jimple.api.MethodInfo;
import edu.syr.bytecast.jimple.api.Methods;
import edu.syr.bytecast.jimple.api.ParameterInfo;
import edu.syr.bytecast.jimple.beans.ParsedInstructionsSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author QSA
 */
public class ParameterScanner {
    
    public void getParameters(List<MemoryInstructionPair> mipList)//, List<ParsedInstructionsSet> pis_list, MethodInfo m_info)
    {
        HashSet registerSet = new HashSet(); 
        ArrayList<MethodInfo> methods = Methods.methods;
        int k=0;
        
        for(int i=0;i<mipList.size();i++)
        {
            MemoryInstructionPair mip = mipList.get(i);
            long memaddress = mip.getmInstructionAddress();
            MethodInfo method_Info = methods.get(k);
            IInstruction inst = mip.getInstruction();
            while(method_Info.getStartMemeAddress() <= memaddress && method_Info.getEndMemeAddress() > memaddress)
            {
                int op_index = 0;
                if(inst.getInstructiontype() == InstructionType.MOV) {
                if(inst.getOperands().get(op_index).getOperandType() == OperandType.REGISTER &&
                     inst.getOperands().get(++op_index).getOperandType() == OperandType.MEMORY_EFFECITVE_ADDRESS){
                        if(inst.getOperands().get(--op_index).getOperandValue() != RegisterType.EAX){
                            ParameterInfo para_Info = new ParameterInfo(inst.getOperands().get(0).getOperandValue().toString());
                           // para_Info.setValue(inst.getOperands().get(1).getOperandValue());
                            method_Info.parameters.add(para_Info);
                        }
                    }
                }
                /*
                if(inst.getInstructiontype() == InstructionType.MOV){
                    if(inst.getOperands().get(0).getOperandType() == OperandType.REGISTER){
                        registerSet.add(inst.getOperands().get(0).getOperandValue());
                    }
                }
                */
                i++;
                mip = mipList.get(i);
                memaddress = mip.getmInstructionAddress();
                inst = mip.getInstruction();
            }
            //if(inst.getInstructiontype() == InstructionType.NOP);
            //else
                k++;
            //if(k >= methods.size())
            //    break;
            //i--;
        }
        
        /*
        for(int i=0;i<methods.size();i++)
        {
            MethodInfo method_Info = methods.get(i);
            for(int j=k;j<mipList.size();j++)
            {
                MemoryInstructionPair mip = mipList.get(j);
                long memaddress = mip.getmInstructionAddress();
                IInstruction inst = mip.getInstruction();
                if(method_Info.getStartMemeAddress() == memaddress)
                {
                    
                }
            }
        }
        
        int list_size= pis_list.size();
        
        for(int i=0;i<list_size;i++)
        {
            ParsedInstructionsSet pis = pis_list.get(i);
            if(pis.getInfo().getInstruction_Name().equalsIgnoreCase("ParameterInit"))
            {
                //int count = pis.getInfo().getInstructions_Count();
                //
                IInstruction inst = pis.getInstructions_List().get(0);
                ParameterInfo info = new ParameterInfo((String)inst.getOperands().get(0).getOperandValue());
                m_info.parameters.add(info);
            }
        }*/
    }
    
}
