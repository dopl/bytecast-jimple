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
import soot.jimple.IntConstant;
import soot.jimple.JasminClass;
import soot.jimple.Jimple;
import soot.jimple.JimpleBody;
import soot.jimple.StringConstant;
import soot.options.Options;
import soot.util.JasminOutputStream;

public class JimpleClass extends AbstractJimpleClass {

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



    public boolean createMethod(String methodName, String returnType, ArrayList<Object> modifier, ArrayList<String> parameters_type) {
        
        List<Type> parameters = new ArrayList<Type>();
        parameters.add(ArrayType.v(RefType.v("java.lang.String"), 1));
        parameters.add(IntType.v());
        SootMethod myMethod = new SootMethod("main", parameters, VoidType.v(), Modifier.PUBLIC | Modifier.STATIC);


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
