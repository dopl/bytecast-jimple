/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;

import soot.Local;
import soot.jimple.Jimple;
import soot.Type;
import soot.Unit;

/**
 *
 * @author col
 */
public class JimpleVariable extends JimpleElement {

    private Local asVariable;

    public JimpleVariable(String name, String type) {
        this(name, type, null);
    }

    public JimpleVariable(String name, String type, JimpleMethod baseMethod) {
        this.asVariable = Jimple.v().newLocal(name, JimpleUtil.getTypeByString(type));
        if (baseMethod != null) {
            baseMethod.getMethod().getActiveBody().getLocals().add(asVariable);
        }
    }

//    public void setVariable(String name, String type) {
//        this.asVariable = Jimple.v().newLocal(name, JimpleUtil.getTypeByString(type));
//    }
    @Override
    protected Local getVariable() {
        if (asVariable != null) {
            return asVariable;
        } else {
            return null;
        }
    }

    protected Type getType() {
        if (asVariable != null) {
            return asVariable.getType();
        } else {
            return null;
        }
    }

    @Override
    protected Unit getElement() {
        return null;
    }
}
