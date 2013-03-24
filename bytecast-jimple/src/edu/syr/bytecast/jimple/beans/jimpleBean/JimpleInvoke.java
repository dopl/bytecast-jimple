/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;

import java.util.ArrayList;
import java.util.List;
import soot.Local;
import soot.Scene;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.jimple.IntConstant;
import soot.jimple.Jimple;
import soot.jimple.Stmt;
import soot.jimple.StringConstant;

/**
 *
 * @author Peike Dai
 */
public class JimpleInvoke extends JimpleElement {

  private Stmt invokestmt;
  private Local baseObject;
  // need to be modified-statu
  public JimpleInvoke(JimpleVariable baseobj, JimpleMethod method2Call, List paraVal,
          JimpleVariable returnTo, JimpleMethod basemethod) {
    // Local to store return value

    Value invokeExpr;
    if (paraVal == null) {
      invokeExpr = Jimple.v().newVirtualInvokeExpr(baseobj.getVariable(),
              method2Call.getMethod().makeRef());
    } else {
        // cast any parameter type to soot.Value
        List<Value> paraForJimple = new ArrayList<Value>();
        if (paraVal.get(0) instanceof JimpleVariable) {
            for (JimpleVariable jv : (List<JimpleVariable>) paraVal) {
                paraForJimple.add(jv.getVariable());
            }
        } else if (paraVal.get(0) instanceof String) {
            for (String str : (List<String>) paraVal) {
                paraForJimple.add(StringConstant.v(str));
            }
        } else if (paraVal.get(0) instanceof Integer) {
            for (int i : (List<Integer>) paraVal) {
                paraForJimple.add(IntConstant.v(i));
            }
        }
        invokeExpr = Jimple.v().newVirtualInvokeExpr(baseobj.getVariable(),
              method2Call.getMethod().makeRef(), paraForJimple);
      
    }

    if (!method2Call.getReturnType().equals("void")) {
      this.invokestmt = Jimple.v().newAssignStmt(returnTo.getVariable(), invokeExpr);
    } else {
      this.invokestmt = Jimple.v().newInvokeStmt(invokeExpr);
    }
    // add baseobject and returnto to Chain<Local> in JimpleBody
//    basemethod.getMethod().getActiveBody().getLocals().add(baseObject);
//    basemethod.getMethod().getActiveBody().getLocals().add(returnTo.getVariable());
    // add this invoke statement to Chain<Unit> in JimpleBody
    basemethod.getMethod().getActiveBody().getUnits().add(invokestmt);
  }

  public JimpleInvoke(String nativemethod, ArrayList<String> paraVal, 
          JimpleVariable returnTo) {
      this(nativemethod,paraVal,returnTo,null);
  }
  // specificly for "println"
  public JimpleInvoke(String nativemethod, ArrayList<String> paraVal, 
          JimpleVariable returnTo, JimpleMethod basemethod) {
    SootMethod toCall = null;
    
    if (nativemethod.equals("println")) {
      baseObject = Jimple.v().newLocal("print_line",
              JimpleUtil.getTypeByString("println"));
      toCall = Scene.v().getMethod("<java.io.PrintStream: void println(java.lang.String)>");
      if (basemethod != null) {
        basemethod.getMethod().getActiveBody().getLocals().add(baseObject);
      }
    }
    
    // Local to store return value

    // 
    Value invokeExpr = null;
    if (paraVal != null && toCall != null) {
      List<Value> sParaVals = new ArrayList<Value>();
      for (String str : paraVal) {
        sParaVals.add(StringConstant.v(str));
      }
      invokeExpr = Jimple.v().newVirtualInvokeExpr(baseObject,
              toCall.makeRef(), sParaVals);
    }

    if (invokeExpr != null) {
      this.invokestmt = Jimple.v().newInvokeStmt(invokeExpr);
    }
    if (basemethod != null) {
        basemethod.getMethod().getActiveBody().getUnits().add(invokestmt);
    }
  }

  // need to be modified
  public void userInvoke(JimpleVariable usrobj, JimpleMethod method2Call, ArrayList<String> paraVal) {
  }

  @Override
  protected Local getVariable() {
    return this.baseObject;
  }
  
  @Override
  protected Unit getElement() {
    return invokestmt;
  }
}
