/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;

import soot.Local;
import soot.RefType;
import soot.Unit;
import soot.jimple.Jimple;

/**
 *
 * @author Peike Dai
 */
public abstract class JimpleElement {
    private Local asParameter;
    
    
    public abstract Unit getElement();
    
    public void setParameter(String name, String type) {
        this.asParameter = Jimple.v().newLocal(name, RefType.v("java.io.PrintStream"));
    }
    
    public Local getParameter() {
        if (asParameter != null)
            return asParameter;
        else return null;
    }
}
