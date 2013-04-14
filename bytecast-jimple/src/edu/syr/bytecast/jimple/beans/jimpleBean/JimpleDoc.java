/*
 * 03/25/2013 - 1.0
 * 
 * accept a JimpleClass and output a jimple file or a class file according 
 * to the type required
 * 
 * @author xirui wang
 * 
 * 
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import soot.Printer;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.SourceLocator;
import soot.jimple.JasminClass;
import soot.options.Options;
import soot.util.JasminOutputStream;

public class JimpleDoc {

    public JimpleDoc() {
        Scene.v().loadClassAndSupport("java.lang.Object");
        Scene.v().loadClassAndSupport("java.lang.System");
    }

    public void addClass(JimpleClass jclass) {
        Scene.v().addClass(jclass.getSClass());
    }

    public void printJimple(String classname, String type) throws FileNotFoundException, IOException {

        if (type.equals("jimple")) {
            String fileName = SourceLocator.v().getFileNameFor(Scene.v().getSootClass(classname), Options.output_format_jimple);
            OutputStream streamOut = new FileOutputStream(fileName);
            System.out.println("File Name = " + fileName);
            PrintWriter writerOut = new PrintWriter(new OutputStreamWriter(streamOut));
            try {

                Printer.v().printTo(Scene.v().getSootClass(classname), writerOut);

            } catch (Exception ex) {
                System.out.println("Error writing .jimple " + fileName);
                System.out.println("Error is :" +  ex.getMessage());
            } finally {
                writerOut.flush();
                streamOut.close();
            }



        } else if (type.equals("class")) {
            // FileOutputStream fos = null;
            String fileName = SourceLocator.v().getFileNameFor(Scene.v().getSootClass(classname), Options.output_format_class);
            // fos = new FileOutputStream(fileName);

            SootClass c = Scene.v().getSootClass(classname);
            List<String> before_sigs = getMethodSignatures(c);
            OutputStream streamOut = null;
            PrintWriter writerOut = null;
            FileOutputStream fos = null;

            try {
                fos = new FileOutputStream(fileName);

                streamOut = new JasminOutputStream(fos);
                System.out.println("File Name = " + fileName);
                writerOut = new PrintWriter(
                        new OutputStreamWriter(streamOut));
                JasminClass jasminClass = new soot.jimple.JasminClass(Scene.v().getSootClass(classname));
                jasminClass.print(writerOut);
            } catch (Exception ex) {
                System.out.println("Error writing .class: " + classname);
                ex.printStackTrace(System.out);
                List<String> after_sigs = getMethodSignatures(c);
                System.out.println("Before sigs: ");
                printMethodSigs(before_sigs);
                System.out.println("After sigs: ");
                printMethodSigs(after_sigs);
            } finally {
                writerOut.flush();
                writerOut.close();
                try {
                    fos.close();
                } catch (Exception ex) {
                }
            }
        }
        else {
            System.out.println("Type should be \"class\" or \"jimple\" ");
        }

    }
    private List<String> getMethodSignatures(SootClass c) {
        List<String> ret = new ArrayList<String>();
        List<SootMethod> methods = c.getMethods();
        for (SootMethod method : methods) {
            ret.add(method.getSignature());
        }
        return ret;
    }

    private void printMethodSigs(List<String> sigs) {
        for (String sig : sigs) {
            System.out.println("  " + sig);
        }
    }
}