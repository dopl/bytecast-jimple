/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.api;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author QSA
 */
public class MethodInfo {
    
    public enum ReturnType {INT, DOUBLE, LONG, STRING, VOID, OBJECT}
    public List<ParameterInfo> parameters = new ArrayList<ParameterInfo>();
    private String method_name;
    private long startMemAddress;
    private long endMemAddress;
    private int startIndex;
    private int endIndex;
    public ReturnType returnType;
    
    public MethodInfo()
    {
        method_name = "DEFAULT";
        returnType = ReturnType.INT;
    }
    
    public int getStartIndex()
    {
        return startIndex;
    }
    
    public void setStartIndex(int index)
    {
        startIndex = index;
    }
    
    public int getEndIndex()
    {
        return endIndex;
    }
    
    public void setEndIndex(int index)
    {
        endIndex = index;
    }

    
    public int getParameterCount()
    {http://www.aljazeera.com/
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
