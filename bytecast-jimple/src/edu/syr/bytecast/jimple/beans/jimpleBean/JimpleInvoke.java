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
  public JimpleInvoke(JimpleVariable baseobj, JimpleMethod method2Call, List<JimpleVariable> paraVal,
          JimpleVariable returnTo) {

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

  // specificly for "println"
  public JimpleInvoke(String nativemethod, ArrayList<String> paraVal, JimpleVariable returnTo) {
    SootMethod toCall = null;
    if (nativemethod.equals("println")) {
      baseObject = Jimple.v().newLocal("print_line",
              JimpleUtil.getTypeByString("println"));
      toCall = Scene.v().getMethod("<java.io.PrintStream: void println(java.lang.String)>");
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
  }

  // need to be modified
  public void userInvoke(JimpleVariable usrobj, JimpleMethod method2Call, ArrayList<String> paraVal) {
  }

  @Override
  protected Unit getElement() {
    return invokestmt;
  }
}
