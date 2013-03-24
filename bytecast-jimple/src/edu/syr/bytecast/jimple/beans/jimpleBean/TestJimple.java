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

        ArrayList<String> parameter_list = new ArrayList<String>();
        parameter_list.add("String[]");


        JimpleMethod jMethod = new JimpleMethod("main", "void", jClass, 9, parameter_list);

//        
        // int a;
        JimpleVariable r1= new JimpleVariable("$r1" , "int");
        // a = 0;
        JimpleAssign ja1 = new JimpleAssign();
        ja1.JimpleAssign(r1, 0);
        jMethod.addElement(ja1);
        // if (a < 1)
        JimpleCondition jc1 = new JimpleCondition("<", r1, 1);
        // set target
        ArrayList<String> toPrint = new ArrayList<String>();
        toPrint.add("hello");
        JimpleInvoke ji1 = new JimpleInvoke("println", toPrint, null);
        JimpleElement[] paras = {ji1};
        jc1.setTargets(paras);
        jMethod.addElement(ji1);
        
        
        jClass.addMethod(jMethod);

        

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
