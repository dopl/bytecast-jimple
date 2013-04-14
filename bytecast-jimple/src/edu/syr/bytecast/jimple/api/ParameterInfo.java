/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.api;

/**
 *
 * @author QSA
 */
public class ParameterInfo {
    public enum type {DEFAULT, INT, DOUBLE, FLOAT, LONG, SHORT, STRING}
    public ParameterInfo()
    {
    }
    public ParameterInfo(String name)
    {
        p_name = name;
        t = type.DEFAULT;
    }
    
    private String p_name;
    private Object value;
    private type t;
    
    public Object getValue()
    {
        return value;
    }
    
    public void setValue(Object val)
    {
        value = val;
    }
    
    public void setType(type t)
    {
        this.t = t;
    }
    
    public type getType()    
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
    
}
