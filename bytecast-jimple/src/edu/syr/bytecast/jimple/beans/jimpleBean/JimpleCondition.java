/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;

/**
 *
 * @author col
 */
public class JimpleCondition extends JimpleElement{
    private String type; // if or switch
    private String comparator; // < > <= >= or ==
    
    public JimpleCondition(String type) {
        this(type,null);
    }
    
    public JimpleCondition(String type, String comprt) {
        this.type = type;
        
        this.comparator = comprt != null ? comprt : null;
    }
    
    
    
    
}
