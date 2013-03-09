/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;

import soot.Local;
import soot.jimple.Stmt;
import soot.util.Switchable;

/**
 *
 * @author col
 */
public class JimpleCondition extends JimpleElement{
    private String type; // if or switch
    private String comparator; // < > <= >= or ==
    private Local leftV;
    private Stmt statement;
    public JimpleCondition(String type) {
        this(type,null);
    }
    
    public JimpleCondition(String type, String comprt) {
        this.type = type;
        
        this.comparator = comprt != null ? comprt : null;
        
        
    }
    
    public void setLeftVal(JimpleVariable jv) {
        this.leftV = jv.getVariable();
    }
    
    public void setRightVal(JimpleElement[] jes) {
        
    }
    
    public void setTarget(JimpleElement jelement) {
        
    }
    
    @Override
    protected Switchable getElement() {
        return statement;
    }
    
    
}
