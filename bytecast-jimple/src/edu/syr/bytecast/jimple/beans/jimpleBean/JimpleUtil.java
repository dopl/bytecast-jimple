/*
 * 03/25/2013 - 1.0
 * 
 * this class provides some commonly used methods
 * they are all static functions.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;

import soot.ArrayType;
import soot.BooleanType;
import soot.IntType;
import soot.RefType;
import soot.Type;
import soot.VoidType;

/**
 *
 * @author Peike Dai
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
      return RefType.v(name);
    }
  }
}
