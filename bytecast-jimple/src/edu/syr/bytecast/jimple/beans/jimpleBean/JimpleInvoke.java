/*
 * 03/25/2013 - 1.0
 * 
 * two kinds of method could be invoked:
 * 1. user defined method
 *    $rsum = virtualinvoke sumBase.<test: int sum(int,int)>($r2, $r3);
 * 2. Java native method
 *    System.out.println:
 *        virtualinvoke print_line.<java.io.PrintStream: 
 *                 void println(java.lang.String)>("hello");
 *    [].length (to be developed)
 *    String.charAt()   (to be developed)
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
  private boolean isTarget;
  private Unit nativeAssi;

  public JimpleInvoke() {
    this.isTarget = false;
  }

  public void invokeUserDefined(JimpleMethod method2Call, List paraVal,
          JimpleVariable returnTo, JimpleMethod basemethod) {
    Value invokeExpr;
    if (paraVal == null) {
      invokeExpr = Jimple.v().newVirtualInvokeExpr(basemethod.getThisRef(),
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
      invokeExpr = Jimple.v().newVirtualInvokeExpr(basemethod.getThisRef(),
              method2Call.getMethod().makeRef(), paraForJimple);

    }

    if (!method2Call.getReturnType().equals("void") && returnTo != null) {
      this.invokestmt = Jimple.v().newAssignStmt(returnTo.getVariable(), invokeExpr);
    } else {
      this.invokestmt = Jimple.v().newInvokeStmt(invokeExpr);
    }
    basemethod.getMethod().getActiveBody().getUnits().add(invokestmt);
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  public void invokeUserDefined(JimpleVariable baseobj, JimpleMethod method2Call, List paraVal,
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

    if (!method2Call.getReturnType().equals("void") && returnTo != null) {
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

  public void invokeNative(JimpleVariable baseobj, String method2Call,
          List paraVal, JimpleVariable returnTo, JimpleMethod callFromMethod) {
//    this.i
  }

  public void invokeNative(String nativemethod, ArrayList<String> paraVal,
          JimpleVariable returnTo, JimpleMethod basemethod) {
    if (nativemethod.equals("println")) {
      invokePrintln(paraVal, basemethod);
    }
  }

  private void invokePrintln(ArrayList<String> paraVal, JimpleMethod basemethod) {
    // java.io.PrintStream print_line;
    baseObject = Jimple.v().newLocal("print_line",
            JimpleUtil.getTypeByString("println"));
    // print_line = <java.lang.System: java.io.PrintStream out>;
    nativeAssi = Jimple.v().newAssignStmt(baseObject,
            Jimple.v().newStaticFieldRef(
            Scene.v().getField("<java.lang.System: java.io.PrintStream out>").makeRef()));
    
    SootMethod toCall = Scene.v().getMethod("<java.io.PrintStream: void println(java.lang.String)>");
    Value invokeExpr;
    if (paraVal != null) {
      List<Value> sParaVals = new ArrayList<Value>();
      for (String str : paraVal) {
        sParaVals.add(StringConstant.v(str));
      }
      invokeExpr = Jimple.v().newVirtualInvokeExpr(baseObject,
              toCall.makeRef(), sParaVals);
    } else {
      invokeExpr = Jimple.v().newVirtualInvokeExpr(baseObject,
              toCall.makeRef());
    }

    // virtualinvoke print_line.<java.io.PrintStream: void println(java.lang.String)>("hello");
    this.invokestmt = Jimple.v().newInvokeStmt(invokeExpr);
    if (!isTarget && basemethod != null) {
      basemethod.getMethod().getActiveBody().getLocals().add(baseObject);
      basemethod.getMethod().getActiveBody().getUnits().add(nativeAssi);
      basemethod.getMethod().getActiveBody().getUnits().add(invokestmt);
    }
  }

  public void setAsTarget() {
    this.isTarget = true;
  }

  @Override
  protected Local getVariable() {
    return null;
  }

  @Override
  protected Unit getElement() {
    return invokestmt;
  }

  @Override
  protected Local getLocalForTarget() {
    return this.baseObject;
  }

  @Override
  protected Unit getAssStmtForTarget() {
    return this.nativeAssi;
  }

  @Override
  protected Unit getInvStmtForTarget() {
    return this.invokestmt;
  }
}
