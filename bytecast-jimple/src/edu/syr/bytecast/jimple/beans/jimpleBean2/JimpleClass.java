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



public class JimpleClass  extends AbstractJimpleClass{
    
    String classname;
    String modifier;
    SootClass sootclass;
    SootMethod sootmethod;
    JimpleBody jBody;
    PatchingChain<Unit> units;
    
    public JimpleClass(String className , String modifier)        
    {
      this.classname = className;
      this.modifier = modifier;
    }
    
    public void createMethod()
    {
    
    }
    
      public void createAssignment()
    {
    
    }
      
        public void createCondition()
    {
    
    }
        
        
          public void outputJimpleFile()
    {
    
    }
  
    
    
    
}
