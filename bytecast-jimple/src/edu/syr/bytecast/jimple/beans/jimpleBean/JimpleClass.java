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
    private String modifier;
    private String classReturnType;
    //String isStatic;
    private SootClass mySootClass;
    
     
    public JimpleClass(){
        this(null,null,null);
    }
    
    
    public JimpleClass(String className, String modifier, String classReturnType) {
        // java.lang.Objects
       if(className != null && modifier!=null && classReturnType != null){
        
        this.classname = className;
        this.modifier = modifier;
        this.classReturnType = classReturnType;
       }
       
       else
           System.out.println("Please check the function prameters. It should  be class name , modifier , returnType");
          
        // java.lang.System
    }
    public boolean createClass (String className, String modifier, String classReturnType) {
        
           if (this.modifier == "public") {
            //Create SootClass
            mySootClass = new SootClass(classname, Modifier.PUBLIC);

        }
        if (this.modifier == "private") {
            //Create SootClass
            mySootClass = new SootClass(classname, Modifier.PRIVATE);
        } else {
            System.out.println("Please check your modifier");
            return false;
        }

        mySootClass.setSuperclass(Scene.v().getSootClass("java.lang.Object"));
        
        return true;
    }

       // return a jimple class
    public SootClass getJimpleClass(){
     
        return mySootClass;
    }
    
    
    
    
    
    
    
    
    
    
}
