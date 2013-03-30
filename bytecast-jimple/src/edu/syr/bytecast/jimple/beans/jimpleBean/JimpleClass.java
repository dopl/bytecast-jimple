/*
 * 03/25/2013 - 1.0
 * 
 * this class provides a JimpleClass which can contain several JimpleMethods.
 * any JimpleClass object should be added to JimpleDoc.
 * 
 * default constructor of class in Jimple (<init> method) will be added automatically,
 * 
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;

import java.util.ArrayList;
import soot.*;
import soot.jimple.IdentityStmt;
import soot.jimple.Jimple;
import soot.jimple.JimpleBody;
import soot.util.Chain;

/**
 *
 * @author Peike Dai
 */
public class JimpleClass {

  private String classname;
  private int modifier;
  private SootClass mySootClass;

  /**
   *
   * @param className
   * @param modifier PRIVATE = 2; PROTECTED = 4; PUBLIC = 1; STATIC = 8;
   * @param classReturnType
   */
  public JimpleClass(String className, int modifier) {
    // java.lang.Objects
    if (className != null && modifier > 0) {

      this.classname = className;
      this.modifier = Modifier.PUBLIC;


      this.createClass();
      this.init();
    } else {
      System.out.println("Please check the function prameters. "
              + "It should  be class name , modifier , returnType");
    }

  }

  private void init() {
    /**
     * void <init>()
     */
    SootMethod ctorMethod = new SootMethod("<init>", new ArrayList(), VoidType.v());
    ctorMethod.setDeclaringClass(mySootClass);
    // must be before ctorMethod.makeRef());
    mySootClass.addMethod(ctorMethod);

    JimpleBody ctBody = Jimple.v().newBody(ctorMethod);
    PatchingChain<Unit> ctunits = ctBody.getUnits();
    Chain<Local> locals = ctBody.getLocals();

    // newtest r0;
    Local thisref = Jimple.v().newLocal("r0", mySootClass.getType());
    locals.add(thisref);
    // r0 := @this: newtest;
    Value this_rhs = Jimple.v().newThisRef(mySootClass.getType());
    IdentityStmt thistmt = Jimple.v().newIdentityStmt(thisref, this_rhs);
    ctunits.add(thistmt);
    // specialinvoke r0.<java.lang.Object: void <init>()>();
    SootClass obj_class = Scene.v().getSootClass("java.lang.Object");
    SootMethod obj_ctor = obj_class.getMethodByName("<init>");

    Value callCtor = Jimple.v().newSpecialInvokeExpr(thisref, obj_ctor.makeRef());
    Unit ctorUnit = Jimple.v().newInvokeStmt(callCtor);
    ctunits.add(ctorUnit);
    // return;
    Unit retVoid = Jimple.v().newReturnVoidStmt();
    ctunits.add(retVoid);

    ctorMethod.setActiveBody(ctBody);
  }

  private void createClass() {
    mySootClass = new SootClass(classname, modifier);
    mySootClass.setSuperclass(Scene.v().getSootClass("java.lang.Object"));
  }

//  public void addMethod(JimpleMethod jm) {
//    mySootClass.addMethod(jm.getMethod());
//  }
  // return a jimple class

  protected SootClass getSClass() {
    return mySootClass;
  }

  public String getJClassName() {
    return this.classname;
  }
}
