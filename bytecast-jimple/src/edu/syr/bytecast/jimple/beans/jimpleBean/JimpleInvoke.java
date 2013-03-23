/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;

import java.util.ArrayList;
import java.util.List;
import soot.Local;
import soot.Unit;
import soot.Value;
import soot.jimple.Jimple;
import soot.jimple.Stmt;

/**
 *
 * @author Peike Dai
 */
public class JimpleInvoke extends JimpleElement {
    private Stmt invokestmt;
    private Local baseObject;
    
    // need to be modified-statu
    public JimpleInvoke(JimpleVariable baseobj, JimpleMethod method2Call, List<JimpleVariable> paraVal, 
            JimpleVariable returnTo) {
        System.out.println("abc");
        
        baseObject = baseobj.getVariable();
        // Local to store return value
        
        Value invokeExpr;
        if (paraVal == null) {
          invokeExpr = Jimple.v().newVirtualInvokeExpr(baseObject, 
                  method2Call.getMethod().makeRef());
        } else {
          invokeExpr = Jimple.v().newVirtualInvokeExpr(baseObject, 
                  method2Call.getMethod().makeRef(), paraVal);
        }
        
        if (!method2Call.getReturnType().equals("void")) {
          this.invokestmt = Jimple.v().newAssignStmt(returnTo.getVariable(), invokeExpr);
        } else {
          this.invokestmt = Jimple.v().newInvokeStmt(invokeExpr);
        }
    }
    
    
    public JimpleInvoke(String nativemethod, ArrayList<String> paraVal, JimpleVariable returnTo) {
      
    }
    
    // need to be modified
    public void userInvoke(JimpleVariable usrobj, JimpleMethod method2Call, ArrayList<String> paraVal) {
        
    }
    
    @Override
    protected Unit getElement() {
        return invokestmt;
    }
    
}
