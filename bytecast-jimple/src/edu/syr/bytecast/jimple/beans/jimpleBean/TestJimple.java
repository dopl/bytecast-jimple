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
import java.util.List;

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
    // int a;
    JimpleVariable r1 = new JimpleVariable("$r1", "int", jMainMethod);
    // a = 0;
    JimpleAssign ja1 = new JimpleAssign();
    ja1.JimpleDirectAssign(r1, 0, jMainMethod);
    // if (a < 1)
    JimpleCondition jc1 = new JimpleCondition("<", r1, 1, jMainMethod);

    // set target (if else)(must be added to method after condition been added
    // target MUST be added to JimpleMethod using addElement() explicitly
    // but could be added anywhere you want
    /// virtualinvoke print_line.<java.io.PrintStream: void println(java.lang.String)>("hello");
    ArrayList<String> parameter_print = new ArrayList<String>();
    parameter_print.add("hello");
    JimpleInvoke ji1 = new JimpleInvoke("println", parameter_print, null);
    JimpleElement[] paras = {ji1};
    jc1.setTargets(paras);
    // ----------------------create sum method----------------------
    ArrayList<String> parameter_type_sum = new ArrayList<String>();
    parameter_type_sum.add("int");
    parameter_type_sum.add("int");
   
    JimpleMethod sumMethod = new JimpleMethod("sum", "int", jClass, 1, parameter_type_sum);
//    jClass.addMethod(sumMethod);
    // assign pass-in value to local variable
    // like l0 := @parameter0 int
    List<JimpleVariable> summethod_parameters = new ArrayList<JimpleVariable>();
    List<String> paratypes = sumMethod.getParameterTypes();
    for (int i = 0; i < paratypes.size(); ++i) {
      JimpleVariable newjv = new JimpleVariable("l" + Integer.toString(i), paratypes.get(i), sumMethod);
      JimpleAssign ja = new JimpleAssign();
      ja.JimpleParameterAssign(newjv, paratypes.get(i), i, sumMethod);
      summethod_parameters.add(newjv);
    }

    // int sum;
    JimpleVariable sumtoreturn = new JimpleVariable("sum", "int", sumMethod);
    // sum = l0 + l1;
    JimpleAssign getsum = new JimpleAssign();
    getsum.JimpleAdd(sumtoreturn, summethod_parameters.get(0),
            summethod_parameters.get(1), sumMethod);
    // return sum;
    sumMethod.setReturn(sumtoreturn);

    //--------- finish creating sum method-------------------
    // int $r2;
    JimpleVariable r2 = new JimpleVariable("$r2", "int", jMainMethod);

    // int $r3;
    JimpleVariable r3 = new JimpleVariable("$r3", "int", jMainMethod);

    JimpleVariable rsum = new JimpleVariable("$rsum", "int", jMainMethod);

    // $r2 = 1;
    JimpleAssign ja2 = new JimpleAssign();
    ja2.JimpleDirectAssign(r2, 1, jMainMethod);
    // $r3 = 2;
    JimpleAssign ja3 = new JimpleAssign();
    ja3.JimpleDirectAssign(r3, 2, jMainMethod);
    // $rsum = virtualinvoke sumBase.<test: int sum(int,int)>($r2, $r3);
    ArrayList<JimpleVariable> parameter_value_sum = new ArrayList<JimpleVariable>();
    parameter_value_sum.add(r2);
    parameter_value_sum.add(r3);

    JimpleVariable sumClassObj = new JimpleVariable("sumBase", jClass.getJClassName(), jMainMethod);
    JimpleInvoke ji2 = new JimpleInvoke(sumClassObj, sumMethod, parameter_value_sum, rsum, jMainMethod);

    // add lable0
    jMainMethod.addElement(ji1);

    try {
      jDoc.printJimple(jClass.getJClassName() , "class");

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
