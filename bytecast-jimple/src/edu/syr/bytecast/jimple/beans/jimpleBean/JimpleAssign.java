/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;

import java.io.*;
import java.util.Arrays;
import soot.*;
import soot.jimple.AddExpr;
import soot.jimple.IntConstant;
import soot.jimple.JasminClass;
import soot.jimple.Jimple;
import soot.jimple.JimpleBody;
import soot.jimple.StringConstant;
import soot.options.Options;
import soot.util.JasminOutputStream;
;
import soot.util.Switchable;

/**
 *
 * @author Xirui Wang
 */


public class JimpleAssign extends JimpleElement {

  private Unit a_assign;

  public JimpleAssign() {
  }

  //@Overload  JimpleAssins
  public void JimpleAssign(JimpleVariable jVariable1, JimpleVariable jVariable2) {

    a_assign = Jimple.v().newAssignStmt(jVariable1.getVariable(), jVariable2.getVariable());

  }

  public void JimpleAssign(JimpleVariable jVariable1, int jVariable2) {

    a_assign = Jimple.v().newAssignStmt(jVariable1.getVariable(), IntConstant.v(jVariable2));

  }

  public void JimpleAssign(JimpleVariable jVariable1, String jVariable2) {

    a_assign = Jimple.v().newAssignStmt(jVariable1.getVariable(), StringConstant.v(jVariable2));

  }

  //overload for add assignment     
  public void JimpleAdd(JimpleVariable jVariable1, JimpleVariable jVariable2) {

    Value rhs = Jimple.v().newAddExpr(jVariable1.getVariable(), jVariable2.getVariable());
    Local add_lhs = Jimple.v().newLocal("add_lhs", jVariable1.getType());

    a_assign = Jimple.v().newAssignStmt(add_lhs, rhs);
  }

  public void JimpleAdd(JimpleVariable jVariable1, int jVariable2) {

    Value rhs = Jimple.v().newAddExpr(jVariable1.getVariable(), IntConstant.v(jVariable2));
    Local add_lhs = Jimple.v().newLocal("add_lhs", jVariable1.getType());
    a_assign = Jimple.v().newAssignStmt(add_lhs, rhs);
  }

  public void JimpleAdd(int jVariable1, int jVariable2) {

    Value rhs = Jimple.v().newAddExpr(IntConstant.v(jVariable1), IntConstant.v(jVariable2));
    Local add_lhs = Jimple.v().newLocal("add_lhs", IntType.v());
    a_assign = Jimple.v().newAssignStmt(add_lhs, rhs);
  }

  //overload for sub assignment     
  public void JimpleSub(JimpleVariable jVariable1, JimpleVariable jVariable2) {

    Value rhs = Jimple.v().newSubExpr(jVariable1.getVariable(), jVariable2.getVariable());
    Local add_lhs = Jimple.v().newLocal("add_lhs", jVariable1.getType());

    a_assign = Jimple.v().newAssignStmt(add_lhs, rhs);
  }

  public void JimpleSub(JimpleVariable jVariable1, int jVariable2) {

    Value rhs = Jimple.v().newSubExpr(jVariable1.getVariable(), IntConstant.v(jVariable2));
    Local add_lhs = Jimple.v().newLocal("add_lhs", jVariable1.getType());
    a_assign = Jimple.v().newAssignStmt(add_lhs, rhs);
  }

  public void JimpleSub(int jVariable1, int jVariable2) {

    Value rhs = Jimple.v().newSubExpr(IntConstant.v(jVariable1), IntConstant.v(jVariable2));
    Local add_lhs = Jimple.v().newLocal("add_lhs", IntType.v());
    a_assign = Jimple.v().newAssignStmt(add_lhs, rhs);
  }

  //overload for mul assignment     
  public void JimpleMul(JimpleVariable jVariable1, JimpleVariable jVariable2) {

    Value rhs = Jimple.v().newMulExpr(jVariable1.getVariable(), jVariable2.getVariable());
    Local add_lhs = Jimple.v().newLocal("add_lhs", jVariable1.getType());

    a_assign = Jimple.v().newAssignStmt(add_lhs, rhs);
  }

  public void JimpleMul(JimpleVariable jVariable1, int jVariable2) {

    Value rhs = Jimple.v().newMulExpr(jVariable1.getVariable(), IntConstant.v(jVariable2));
    Local add_lhs = Jimple.v().newLocal("add_lhs", jVariable1.getType());
    a_assign = Jimple.v().newAssignStmt(add_lhs, rhs);
  }

  public void JimpleMul(int jVariable1, int jVariable2) {

    Value rhs = Jimple.v().newMulExpr(IntConstant.v(jVariable1), IntConstant.v(jVariable2));
    Local add_lhs = Jimple.v().newLocal("add_lhs", IntType.v());
    a_assign = Jimple.v().newAssignStmt(add_lhs, rhs);
  }

  //overload for divide assignment     
  public void JimpleDiv(JimpleVariable jVariable1, JimpleVariable jVariable2) {

    Value rhs = Jimple.v().newDivExpr(jVariable1.getVariable(), jVariable2.getVariable());
    Local add_lhs = Jimple.v().newLocal("add_lhs", jVariable1.getType());

    a_assign = Jimple.v().newAssignStmt(add_lhs, rhs);
  }

  public void JimpleDiv(JimpleVariable jVariable1, int jVariable2) {

    Value rhs = Jimple.v().newDivExpr(jVariable1.getVariable(), IntConstant.v(jVariable2));
    Local add_lhs = Jimple.v().newLocal("add_lhs", jVariable1.getType());
    a_assign = Jimple.v().newAssignStmt(add_lhs, rhs);
  }

  public void JimpleDiv(int jVariable1, int jVariable2) {

    Value rhs = Jimple.v().newDivExpr(IntConstant.v(jVariable1), IntConstant.v(jVariable2));
    Local add_lhs = Jimple.v().newLocal("add_lhs", IntType.v());
    a_assign = Jimple.v().newAssignStmt(add_lhs, rhs);
  }

  @Override
  protected Unit getElement() {
    return a_assign;
  }
}
