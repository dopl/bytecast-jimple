/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;

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
import soot.util.JasminOutputStream;

/**
 *
 * @author col
 */
public class JimpleMethod {
    private ArrayList<String> parameters_type;
    private String methodName;
    private String returnType;
    private int modifier;
    private JimpleBody jBody;
    private PatchingChain<Unit> units;
    
    /**
     * 
     * @param methodName
     * @param returnType
     * @param modifier (public:1 , private:2 , static:8 ( if u want to use (public+static) :value will be 9))
     * @param parameters_type 
     */
    public JimpleMethod(String methodName, String returnType,
            int modifier, ArrayList<String> parameters_type){
    
        this.methodName = methodName;
        this.modifier = modifier;
        this.returnType = returnType;
        this.parameters_type = parameters_type;
        
        
    }
    
    public void createMethod(String methodName, String returnType,
            int modifier, ArrayList<String> parameters_type) {
        
        this.methodName = methodName;
        this.modifier = modifier;
        this.returnType = returnType;
        this.parameters_type = parameters_type;
        
           List<Type> parameters = new ArrayList<Type>();
        for (String tp : parameters_type) {
            parameters.add(getTypeByString(tp));
        }
        
        SootMethod myMethod = new SootMethod(methodName, parameters, 
                        this.getTypeByString(returnType), modifier);
        
          //mySootclass.addMethod(myMethod);

        //create jimple body
        jBody = Jimple.v().newBody(myMethod);
        myMethod.setActiveBody(jBody);

        units = jBody.getUnits();
                 
    }
  
  public JimpleBody  getJimpleBody(){
       return  jBody;
  }
          
  public PatchingChain<Unit>   getJimpleUnits(){
      
      return units;
  }
 
        private Type getTypeByString(String name) {
        if (name.equals("String")) return RefType.v("java.lang.String");
        else if (name.equals("String[]")) 
            return ArrayType.v(RefType.v("java.lang.String"), 1);
        else if (name.equals("int")) return IntType.v();
        else if (name.equals("")) return VoidType.v();
        else return RefType.v("java.lang.Object");
    }
    
    
}
