/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SampleResources;

import java.util.Arrays;
import soot.ArrayType;
import soot.Local;
import soot.Modifier;
import soot.PatchingChain;
import soot.RefType;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Type;
import soot.Unit;
import soot.VoidType;
import soot.jimple.Jimple;
import soot.jimple.JimpleBody;
import soot.jimple.StringConstant;

/**
 *
 * @author Peike Dai
 */
public class TestMore {
    public void testone() {
//        args = new String[]{"0"};
        //Resolution Step
        Scene.v().loadClassAndSupport("java.lang.Object");
        Scene.v().loadClassAndSupport("java.lang.System");

    //Create SootClass
    SootClass testClass = new SootClass("helloWorldClass", Modifier.PUBLIC);
    //make the class extend java.lang.Object
    //testClass.setSuperclass(Scene.v().getSootClass("java.lang.Object"));
    //Add testClass to Scene object
    Scene.v().addClass(testClass);
    //Define Method
    SootMethod mainMethod = new SootMethod("main", Arrays.asList(new Type[] {ArrayType.v(RefType.v("java.lang.String"), 1)}), VoidType.v(), Modifier.PUBLIC | Modifier.STATIC);
    //Add Method
    testClass.addMethod(mainMethod);
    //create jimple body
    JimpleBody jBody = Jimple.v().newBody(mainMethod);
    mainMethod.setActiveBody(jBody);
    PatchingChain<Unit> units = jBody.getUnits();
    
    Local arg = Jimple.v().newLocal("r0", ArrayType.v(RefType.v("java.lang.String"), 1));
    jBody.getLocals().add(arg);
    
    Local i0 = Jimple.v().newLocal("$i0", RefType.v("int"));
    jBody.getLocals().add(i0);
    
    Local tmpRef1 = Jimple.v().newLocal("$r1", RefType.v("java.io.PrintStream"));
    Local tmpRef2 = Jimple.v().newLocal("$r2", RefType.v("java.io.PrintStream"));
    jBody.getLocals().add(tmpRef1);
    jBody.getLocals().add(tmpRef2);
    
    Local r2 = Jimple.v().newLocal("$r2", RefType.v("java.lang.String"));
    jBody.getLocals().add(r2);
    
    units.add(Jimple.v().newAssignStmt(arg,
            Jimple.v().newStaticFieldRef( Scene.v().getField("<java.lang.System: java.io.PrintStream out>").makeRef())));
    SootMethod toCall = Scene.v().getMethod("<java.io.PrintStream: void println(java.lang.String)>");
    Unit iftarget = Jimple.v().newInvokeStmt(Jimple.v().newVirtualInvokeExpr(tmpRef1, toCall.makeRef(), StringConstant.v("Hello world!")));
    
    Unit ifunit = Jimple.v().newIfStmt(i0, iftarget);
    }
}
