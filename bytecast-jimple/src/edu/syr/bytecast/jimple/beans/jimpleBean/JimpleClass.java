/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import soot.*;
import soot.jimple.IdentityStmt;
import soot.jimple.IntConstant;
import soot.jimple.JasminClass;
import soot.jimple.Jimple;
import soot.jimple.JimpleBody;
import soot.jimple.StringConstant;
import soot.options.Options;
import soot.util.Chain;
import soot.util.JasminOutputStream;

/**
 *
 * @author Peike Dai
 */
public class JimpleClass {

    private String classname;
    private int modifier;
    private String classReturnType;
    //String isStatic;
    private SootClass mySootClass;

//    public JimpleClass() {
//        this(null, null, null);
//    }

    /**
     *     
     * @param className
     * @param modifier
     *      PRIVATE = 2;
            PROTECTED = 4;
            PUBLIC = 1;
            STATIC = 8;
     * @param classReturnType 
     */
    public JimpleClass(String className, int modifier) {
        // java.lang.Objects
        if (className != null && modifier > 0 ) {
            
            this.classname = className;
            this.modifier = modifier;
           
            
            this.createClass(className, modifier);
            this.init();
        } else {
            System.out.println("Please check the function prameters. "
                    + "It should  be class name , modifier , returnType");
        }

        // java.lang.System
    }
    
    private void init() {
        /**
         *  void <init>()
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

    public boolean createClass(String className, int modifier) {
        if (className == null || modifier <= 0 ) {
            return false;
        }
        mySootClass = new SootClass(classname, modifier);
        mySootClass.setSuperclass(Scene.v().getSootClass("java.lang.Object"));
        return true;
    }

    public void addMethod(JimpleMethod jm) {
        mySootClass.addMethod(jm.getMethod());
    }
    // return a jimple class
    protected SootClass getSClass() {
        return mySootClass;
    }
}
