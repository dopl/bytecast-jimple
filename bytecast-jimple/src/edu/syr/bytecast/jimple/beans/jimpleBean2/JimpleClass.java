/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean2;

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

public abstract class JimpleClass extends AbstractJimpleClass {

    String classname;
    String modifier;
    String classReturnType;
    //String isStatic;
    SootClass mySootclass;
    //SootMethod sootmethod;
    JimpleBody jBody;
    PatchingChain<Unit> units;
    
    public JimpleClass(String className, String modifier, String classReturnType) {
        this.classname = className;
        this.modifier = modifier;
        this.classReturnType = classReturnType;
        //this.isStatic = isStatic; 
    }

    public boolean createConstructor(ArrayList<String> paraType) {
        // default constructor
        if (paraType == null) { 
            SootMethod ctorMethod = new SootMethod("<init>", new ArrayList(), VoidType.v());
            ctorMethod.setDeclaringClass(mySootclass);
            // must be before ctorMethod.makeRef());
            mySootclass.addMethod(ctorMethod);

            JimpleBody ctBody = Jimple.v().newBody(ctorMethod);
            PatchingChain<Unit> ctunits = ctBody.getUnits();
            Chain<Local> locals = ctBody.getLocals();

            // newtest r0;
            Local thisref = Jimple.v().newLocal("r0", mySootclass.getType());
            locals.add(thisref);
            // r0 := @this: newtest;
            Value this_rhs = Jimple.v().newThisRef(mySootclass.getType());
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
        return true;
    }
    public boolean createJimpleClass() {
        Scene.v().loadClassAndSupport("java.lang.Object");
        Scene.v().loadClassAndSupport("java.lang.System");

        if (this.modifier == "public") {
            //Create SootClass
            mySootclass = new SootClass(classname, Modifier.PUBLIC);

        }
        if (this.modifier == "private") {
            //Create SootClass
            mySootclass = new SootClass(classname, Modifier.PRIVATE);
        } else {
            System.out.println("Please check your modifier");
            return false;
        }

        mySootclass.setSuperclass(Scene.v().getSootClass("java.lang.Object"));
        //Add testClass to Scene object
        Scene.v().addClass(mySootclass);
        return true;


    }

    private Type getTypeByString(String name) {
        if (name.equals("String")) return RefType.v("java.lang.String");
        else if (name.equals("String[]")) 
            return ArrayType.v(RefType.v("java.lang.String"), 1);
        else if (name.equals("int")) return IntType.v();
        else if (name.equals("")) return VoidType.v();
        else return RefType.v("java.lang.Object");
    }
    
    /**
     * 
     * @param methodName
     * @param returnType
     * @param modifier public:0 protect:1 private:2 static:4 abstract:8 
     * @param parameters_type
     * @return 
     */
    @Override
    public boolean createMethod(String methodName, String returnType,
            int modifier, ArrayList<String> parameters_type) {
        
        List<Type> parameters = new ArrayList<Type>();
        for (String tp : parameters_type) {
            parameters.add(getTypeByString(tp));
        }
        
        SootMethod myMethod = new SootMethod(methodName, parameters, 
                        this.getTypeByString(returnType), modifier);

        //Add Method
        mySootclass.addMethod(myMethod);

        //create jimple body
        jBody = Jimple.v().newBody(myMethod);
        myMethod.setActiveBody(jBody);

        units = jBody.getUnits();

        return true;

    }

    public boolean createAssignment() {
        return false;
    }

    public boolean outputJimpleFile() {

        return false;
    }

    public boolean createCondition() {
        return false;
    }

}
