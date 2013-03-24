/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;

import soot.ArrayType;
import soot.BooleanType;
import soot.IntType;
import soot.Local;
import soot.RefType;
import soot.Type;
import soot.VoidType;
import soot.jimple.Jimple;

/**
 *
 * @author col
 */
public class JimpleUtil {

  public static Type getTypeByString(String name) {
    if (name.equals("String")) {
      return RefType.v("java.lang.String");
    } else if (name.equals("String[]")) {
      return ArrayType.v(RefType.v("java.lang.String"), 1);
    } else if (name.equals("int")) {
      return IntType.v();
    } else if (name.equals("int[]")) {
      return ArrayType.v(IntType.v(), 1);
    } else if (name.equals("boolean")) {
      return BooleanType.v();
    } else if (name.equals("boolean[]")) {
      return ArrayType.v(BooleanType.v(), 1);
    } else if (name.equals("void")) {
      return VoidType.v();
    } else if (name.equals("println")) {
      return RefType.v("java.io.PrintStream");
    } else {
      return RefType.v("java.lang.Object");
    }
  }

  protected static int convertStringToInt(String tmpString) {

    return Integer.parseInt(tmpString);
  }
  public Local getBaseObject(String methodName) {
    if (methodName.equals("println")) {
      return Jimple.v().newLocal("local1", RefType.v("java.io.PrintStream"));
    } else {
      return Jimple.v().newLocal("local1", RefType.v("java.io.PrintStream"));
    }
  }
    
}
