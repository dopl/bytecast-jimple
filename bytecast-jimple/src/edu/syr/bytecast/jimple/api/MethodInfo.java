/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.api;

import java.util.List;

/**
 *
 * @author QSA
 */
public class MethodInfo {
    
    public List<ParameterInfo> parameters;
    private String method_name;
    private long startMemAddress;
    private long endMemAddress;
    
    public int getParameterCount()
    {
        return parameters.size();
    }
    
    public long getStartMemeAddress()
    {
        return startMemAddress;
    }
    
    public long getEndMemeAddress()
    {
        return endMemAddress;
    }
    
    public void setStartMemAddress(long add)
    {
        startMemAddress = add;
    }
    
    public void setEndMemAddress(long add)
    {
        endMemAddress = add;
    }
    
    public void setMethodName(String name)
    {
        method_name = name;
    }
    
    public String getMethodName()
    {
        return method_name;
    }
}
