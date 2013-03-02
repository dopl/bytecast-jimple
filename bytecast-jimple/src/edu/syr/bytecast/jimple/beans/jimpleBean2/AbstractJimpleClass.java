/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean2;

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
/**
 *
 * @author mandy
 */
public abstract class AbstractJimpleClass {
    String classname;
    String modifier;
    SootClass sootclass;
    SootMethod sootmethod;
    JimpleBody jBody;
    PatchingChain<Unit> units;
    
    public AbstractJimpleClass(String className , String Modifier){
            }
    
   public abstract void createMethod();
   public abstract void createAssignment();
   public abstract void createCondition();
   public abstract void outPutJimpleFile();
}
