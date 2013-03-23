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

//        JimpleVariable jVar1 = new JimpleVariable("r0" , "String[]");;
//        
//        
//        
//        jMethod.addElement(jVar1);
//        
//        JimpleVariable jVar2 = new JimpleVariable("r1" , "String[]");

        jClass.addMethod(jMethod);

        

        try {
            jDoc.printJimple();

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
