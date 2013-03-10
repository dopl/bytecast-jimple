/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 * @author col
 */
class JimpleUtil {
    protected static Type getTypeByString(String name) {
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
        } else if (name.equals("")) {
            return VoidType.v();
        } else {
            return RefType.v("java.lang.Object");
        }
    }
    
  
    protected static int convertStringToInt(String tmpString){
        
         return  Integer.parseInt(tmpString);
    }
    
}
