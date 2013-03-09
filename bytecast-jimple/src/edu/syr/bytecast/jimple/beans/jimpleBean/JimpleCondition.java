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
    private Local rightV;
    private Stmt statement;
    public JimpleCondition(String type) {
        this(type,null);
    }
    
    public JimpleCondition(String comprt, JimpleVariable leftv, 
            JimpleVariable rightv) {
        this("if", comprt, leftv, rightv);
    }
    
    public JimpleCondition(String type, String comprt, 
            JimpleVariable leftv, JimpleVariable rightv) {
        this.type = type;
        
        this.comparator = comprt != null ? comprt : null;
        
        this.leftV = leftv.getVariable();
        this.rightV = rightv.getVariable();
    }
    
//    public void setValues(JimpleVariable leftv, JimpleVariable rightv) {
//        this.leftV = leftv.getVariable();
//        this.rightV = rightv.getVariable();
//    }
    
//    public void setLeftVal(JimpleVariable jv) {
//        this.leftV = jv.getVariable();
//    }
//    
//    public void setRightVal(JimpleElement[] jes) {
//        
//    }
    
    public void setTarget(JimpleElement jelement) {
        
    }
    
    public void setTargets(JimpleElement[] jelements) {
        
    }
    
    @Override
    protected Switchable getElement() {
        return statement;
    }
    
    
}
