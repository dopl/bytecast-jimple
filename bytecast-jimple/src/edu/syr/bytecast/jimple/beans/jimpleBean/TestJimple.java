/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;

/**
 *
 * @author xirui Wang
 */
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

public class TestJimple {
    JimpleDoc jd = new JimpleDoc();
    
    void doTest(){
          //create a class          
          //declare function
          //declare variable
          //assign variable
          //
        JimpleDoc  jDoc = new JimpleDoc();
        
        JimpleClass jClass = new JimpleClass("test", 1); 
        jClass.createClass("test", 1);
        
        ArrayList<String>  parameter_list = new  ArrayList<String>();
        parameter_list.add("int");
        parameter_list.add("int");
        
        JimpleMethod jMethod = new JimpleMethod("sum", "int", jClass, 1,parameter_list);
        jMethod.createMethod();
        
        
    }
}
