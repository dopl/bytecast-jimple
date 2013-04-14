/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package edu.syr.bytecast.jimple.api;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Fei Qi
 */

// used to combine the method content and method info
public class Method {
    private MethodInfo methodInfo;
    private List<MemoryInstructionPair> l_instruction;

    public MethodInfo getMethodInfo() {
        return methodInfo;
    }

    public void setMethodInfo(MethodInfo m_info) {
        this.methodInfo = m_info;
    }

    public List<MemoryInstructionPair> getL_instruction() {
        return l_instruction;
    }

    public void setL_instruction(List<MemoryInstructionPair> l_instruction) {
        this.l_instruction = l_instruction;
    }

}
