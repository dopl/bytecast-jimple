/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;

import java.util.ArrayList;
import soot.Unit;
import soot.jimple.InvokeStmt;

/**
 *
 * @author Peike Dai
 */
public class JimpleInvoke extends JimpleElement {
    private InvokeStmt invokestmt;
    
    // need to be modified
    public JimpleInvoke(JimpleMethod method2Call, ArrayList<String> paraVal, 
            JimpleVariable returnTo, boolean syscall) {
        System.out.println("abc");
    }
    
    // need to be modified
    public void userInvoke(JimpleVariable usrobj, JimpleMethod method2Call, ArrayList<String> paraVal) {
        
    }
    
    @Override
    protected Unit getElement() {
        return invokestmt;
    }
    
}
