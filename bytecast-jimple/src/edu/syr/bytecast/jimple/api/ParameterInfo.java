/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.api;

import edu.syr.bytecast.amd64.impl.instruction.operand.OperandMemoryEffectiveAddress;

/**
 *
 * @author QSA
 */
public class ParameterInfo {

    private String p_name;
    private JavaParameterType parametertype;
    private JavaDataType t;
    private OperandMemoryEffectiveAddress paramStackAddress;

   
    public ParameterInfo()
    {
    }
    public ParameterInfo(String name)
    {
        p_name = name;
        t = JavaDataType.INT;
    }
   
    
    public void setType(JavaDataType t)
    {
        this.t = t;
    }
    
    public JavaDataType getType()    
    {
        return t;
    }
    
    public void setParameterName(String name)
    {
        p_name = name;
    }
    
    public String getParamaterName()
    {
        return p_name;
    }
    
     public JavaParameterType getParametertype() {
        return parametertype;
    }

    public void setParametertype(JavaParameterType parametertype) {
        this.parametertype = parametertype;
    }

    public OperandMemoryEffectiveAddress getParamStackAddress() {
        return paramStackAddress;
    }

    public void setParamStackAddress(OperandMemoryEffectiveAddress paramStackAddress) {
        this.paramStackAddress = paramStackAddress;
    }
    
    
}
