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

    void doTest() {
        //create a class          
        //declare function
        //declare variable
        //assign variable
        //
        JimpleDoc jDoc = new JimpleDoc();

        JimpleClass jClass = new JimpleClass("test", 1);
        jDoc.addClass(jClass);
      
        //create parameterList of main method 
        ArrayList<String> parameter_list_main = new ArrayList<String>();
        parameter_list_main.add("String[]");
        
   
      //create main method
        JimpleMethod jMainMethod = new JimpleMethod("main", "void", jClass, 9, parameter_list_main);
        jClass.addMethod(jMainMethod);
//        
        // create variable int a;
        JimpleVariable r1 = new JimpleVariable("$r1" , "int" ,jMainMethod) ;
        
        
        
        
        // assign variable a = 0;
        JimpleAssign ja1 = new JimpleAssign();
        ja1.JimpleDirectAssign(r1, 0,jMainMethod);
//        jMainMethod.addElement(ja1);
        
        //create condition statement if (a < 1)
        JimpleCondition jc1 = new JimpleCondition("<", r1, 1, jMainMethod);
        
        // set target (if else)(must be added to method after condition been added
        ArrayList<String> parameter_print = new ArrayList<String>();
        parameter_print.add("hello");
        
        //use system call
        JimpleInvoke ji1 = new JimpleInvoke("println", parameter_print, null);
        JimpleElement[] paras = { ji1 };
       
        jc1.setTargets(paras);
        
//        jMainMethod.addElement(jc1);
//        jMainMethod.addElement(ji1);
        
      

     //parameter_type for sum method 
        ArrayList<String>  parameter_type_sum = new ArrayList<String>();
        parameter_type_sum.add("int");
        parameter_type_sum.add("int");

        //create sum method    
        JimpleMethod sumMethod = new JimpleMethod("sum", "int", jClass, 1, parameter_type_sum);
        jClass.addMethod(sumMethod);
        List<JimpleVariable> summethod_parameters = new ArrayList<JimpleVariable>();
        List<String> paratypes = sumMethod.getParameterTypes();
        for (int i=0; i<paratypes.size(); ++i) {          
            JimpleVariable newjv = new JimpleVariable("l"+Integer.toString(i), paratypes.get(i),sumMethod);
            JimpleAssign ja = new JimpleAssign();
            ja.JimpleParameterAssign(newjv, paratypes.get(i), i, sumMethod);
            summethod_parameters.add(newjv);
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        // finish creating sum method
         // create variable int $r2;
        JimpleVariable r2 = new JimpleVariable("$r2" , "int", jMainMethod);
         
        // create variable int $r3;
        JimpleVariable r3 = new JimpleVariable("$r3" , "int", jMainMethod);
        
        JimpleVariable rsum = new JimpleVariable("$rsum" , "int", jMainMethod);
        
        
        JimpleAssign ja2 = new JimpleAssign();
        ja2.JimpleDirectAssign(r2, 1, jMainMethod);
//        jMainMethod.addElement(ja2);
        
         JimpleAssign ja3 = new JimpleAssign();
        ja3.JimpleDirectAssign(r3, 2, jMainMethod);
//        jMainMethod.addElement(ja3);
        
        
//        ArrayList<Integer>  parameter_value_sum = new ArrayList<Integer>();
//        parameter_value_sum.add(1);
//        parameter_value_sum.add(2);
        
        ArrayList<JimpleVariable>  parameter_value_sum = new ArrayList<JimpleVariable>();
        parameter_value_sum.add(r2);
        parameter_value_sum.add(r3);
        
        JimpleVariable sumClassObj = new JimpleVariable("sumBase" , jClass.getJClassName(), jMainMethod);
        JimpleInvoke ji2 = new JimpleInvoke(sumClassObj, sumMethod, parameter_value_sum, rsum, jMainMethod); 
        
//        jMainMethod.addElement(ji2);
        
       
          
        jMainMethod.addElement(ji1);

        try {
            jDoc.printJimple(jClass.getJClassName());

        } catch (FileNotFoundException e) {
            System.out.println("file exception");
        } catch (IOException e) {
            System.out.println("IO exception");
        }


    }

    public static void main(String[] args) {
        TestJimple tJimple = new TestJimple();

        tJimple.doTest();


    }
}
