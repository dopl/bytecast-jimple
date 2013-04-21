/*
 * 03/25/2013 - 1.0
 * 
 * this class writes jimple codes like:
 * int $r0;
 * java.io.PrintStream print_line; 
 * 
 * could pass in object of base JimpleMethod this variable
 * belongs to, which will do the job of addElement(). can't use both
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;

import soot.Local;
import soot.jimple.Jimple;
import soot.Type;
import soot.Unit;

/**
 *
 * @author Peike Dai
 */
public class JimpleVariable extends JimpleElement {

    private Local asVariable;
    private String variableName;
   

        public JimpleVariable(String name, String type, JimpleMethod baseMethod) {
        this.asVariable = Jimple.v().newLocal(name, JimpleUtil.getTypeByString(type));
        this.variableName = name;
        if (baseMethod != null) {
            baseMethod.getMethod().getActiveBody().getLocals().add(asVariable);
        }
    }

    @Override
    protected Local getVariable() {
        if (asVariable != null) {
            return asVariable;
        } else {
            return null;
        }
    }

    
    protected String getVariableName(){
        
        
        return this.getVariableName();
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
