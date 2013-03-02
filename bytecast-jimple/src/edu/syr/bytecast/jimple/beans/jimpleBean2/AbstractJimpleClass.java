/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean2;

import java.util.ArrayList;

/**
 *
 * @author mandy
 */
public abstract class AbstractJimpleClass {

    public AbstractJimpleClass(){};

   public abstract boolean createJimpleClass();
   public abstract boolean createMethod(String methodName, String returnType, ArrayList<Object> modifier,ArrayList<Object> parameters );
   public abstract boolean createAssignment();
   public abstract boolean createCondition();
   public abstract boolean outputJimpleFile();
}
