package SampleResources;

//Tutorial available on http://www.sable.mcgill.ca/soot/tutorial/createclass/   ---QSA

import java.io.*;
import java.util.Arrays;
import soot.*;
import soot.jimple.IntConstant;
import soot.jimple.JasminClass;
import soot.jimple.Jimple;
import soot.jimple.JimpleBody;
import soot.jimple.StringConstant;
import soot.options.Options;
import soot.util.JasminOutputStream;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author QSA
 */
public class Test {
    
    public static void main(String[] args) throws FileNotFoundException, IOException   
    {
        args = new String[]{"1"};
        //Resolution Step
        Scene.v().loadClassAndSupport("java.lang.Object");
        Scene.v().loadClassAndSupport("java.lang.System");

    //Create SootClass
    SootClass testClass = new SootClass("helloWorldClass", Modifier.PUBLIC);
    
    //make the class extend java.lang.Object
    testClass.setSuperclass(Scene.v().getSootClass("java.lang.Object"));
    
    //Add testClass to Scene object
    Scene.v().addClass(testClass);
    
    //Define Method
    SootMethod mainMethod = new SootMethod("main", Arrays.asList(new Type[] {ArrayType.v(RefType.v("java.lang.String"), 1)}), VoidType.v(), Modifier.PUBLIC | Modifier.STATIC);
    
    //Add Method
    testClass.addMethod(mainMethod);
    
    //create jimple body
    JimpleBody jBody = Jimple.v().newBody(mainMethod);
    mainMethod.setActiveBody(jBody);
    
    //Add local
    PatchingChain<Unit> units = jBody.getUnits();
    Local arg = Jimple.v().newLocal("l0", ArrayType.v(RefType.v("java.lang.String"), 1));
    jBody.getLocals().add(arg);
    //Assign locals the method parameters
    //Unit u = new Units(java.util.Iterator())
    //List<Unit> units = new ArrayList<Unit>();// = getUnits(mainMethod.getActiveBody());
    units.add(Jimple.v().newIdentityStmt(arg, Jimple.v().newParameterRef(ArrayType.v(RefType.v("java.lang.String"), 1), 0)));
    
    Local tmpVar = Jimple.v().newLocal("a", IntType.v());
    Unit a_assign = Jimple.v().newAssignStmt(tmpVar, IntConstant.v(0));
    units.add(a_assign);
    
    Local tmpRef = Jimple.v().newLocal("local1", RefType.v("java.io.PrintStream"));
   
    jBody.getLocals().add(tmpVar);
    jBody.getLocals().add(tmpRef);   
    
    SootMethod toCall = Scene.v().getMethod("<java.io.PrintStream: void println(java.lang.String)>");
  
   //<-------------show how to realize a if statement in Jimple formate----------->
   //<--------------units.add(unit) will decide the sequences of the output jimple code--->
    
    //-----------1.declare a local  2.use assigment statement to assgian a value and return a unit 
    //-----------3.add the unit to the units.  
    Unit returnUnit = Jimple.v().newReturnVoidStmt();  //create a return unit
    
    
    //----------get a value(like bool) by using comparision expression----
    Value Condition = Jimple.v().newGeExpr(tmpVar, IntConstant.v(1)); 
     
    //using  if statement 
    Unit if_result = Jimple.v().newIfStmt(Condition, returnUnit);
    
    //add to units
    units.add(if_result);
    
     
    
    units.add(Jimple.v().newAssignStmt(tmpRef, Jimple.v().newStaticFieldRef( Scene.v().getField("<java.lang.System: java.io.PrintStream out>").makeRef())));
  
    units.add(Jimple.v().newInvokeStmt(Jimple.v().newVirtualInvokeExpr(tmpRef, toCall.makeRef(), StringConstant.v("Hello world!"))));
  
   //the return unit must be at the end of the code 
    units.add(returnUnit);
    
    if(args[0].equals("1"))
    {
        String fileName = SourceLocator.v().getFileNameFor(testClass, Options.output_format_class);
        OutputStream streamOut = new JasminOutputStream(
                                    new FileOutputStream(fileName));
        PrintWriter writerOut = new PrintWriter(
                                    new OutputStreamWriter(streamOut));
        JasminClass jasminClass = new soot.jimple.JasminClass(testClass);
        jasminClass.print(writerOut);
        writerOut.flush();
        streamOut.close();

    }
    else
    {
        String fileName = SourceLocator.v().getFileNameFor(testClass, Options.output_format_jimple);
        OutputStream streamOut = new FileOutputStream(fileName);
        System.out.println(fileName);
        PrintWriter writerOut = new PrintWriter(new OutputStreamWriter(streamOut));
        Printer.v().printTo(testClass, writerOut);
        writerOut.flush();
        streamOut.close();
    }
    }
}
