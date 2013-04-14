/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.jimple.api.MethodInfo;
import edu.syr.bytecast.jimple.api.Methods;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author pahuja
 */
public class InitializeVariables {
    
    public void init()
    {
        ArrayList<MethodInfo> methods = Methods.methods;
        List<MemoryInstructionPair> mipList = null;
        MethodInfo m1 = methods.get(0);
        long startAddress = m1.getStartMemeAddress();
        long endAddress = m1.getEndMemeAddress();
        int si = m1.getStartIndex();
        int ei = m1.getEndIndex();
        Map<String, String> memreg = new HashMap<String, String>();
        for(int i=si;i<ei;i++)
        {
            IInstruction inst = mipList.get(i).getInstruction();
            if(inst.getInstructiontype() == InstructionType.MOV){
                memreg.put(inst.getOperands().get(1).getOperandValue().toString(), inst.getOperands().get(0).getOperandValue().toString());
            }
        }
    }
}
