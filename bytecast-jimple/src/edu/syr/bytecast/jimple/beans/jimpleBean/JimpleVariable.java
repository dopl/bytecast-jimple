/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;

import soot.Local;
import soot.jimple.Jimple;
import soot.util.Switchable;

/**
 *
 * @author col
 */
public class JimpleVariable extends JimpleElement {
    
    private Local asVariable;
    
    public JimpleVariable(String name, String type) {
        this.asVariable = Jimple.v().newLocal(name, JimpleUtil.getTypeByString(type));
    }
    
    public void setVariable(String name, String type) {
        this.asVariable = Jimple.v().newLocal(name, JimpleUtil.getTypeByString(type));
    }
    
    public Local getVariable() {
        if (asVariable != null) {
            return asVariable;
        } else {
            return null;
        }
    }
    
    @Override
    protected Switchable getElement() {
        return asVariable;
    }
}
