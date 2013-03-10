/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;


import java.io.*;
import java.util.Arrays;
import soot.*;
import soot.jimple.AddExpr;
import soot.jimple.IntConstant;
import soot.jimple.JasminClass;
import soot.jimple.Jimple;
import soot.jimple.JimpleBody;
import soot.jimple.StringConstant;
import soot.options.Options;
import soot.util.JasminOutputStream;;
import soot.util.Switchable;

/**
 *
 * @author Xirui Wang
 */
public class JimpleAssign extends JimpleElement{
    private Unit a_assign;
    
    
    public JimpleAssign(){
        
    }
    
    //@Overload  JimpleAssin
    public void JimpleAssign(JimpleVariable jVariable1 ,JimpleVariable jVariable2){ 
    
         a_assign = Jimple.v().newAssignStmt( jVariable1.getVariable() , jVariable2.getVariable());
    
    }
      
       public void JimpleAssign(JimpleVariable jVariable1 ,int jVariable2 ){ 
    
         a_assign = Jimple.v().newAssignStmt( jVariable1.getVariable() , IntConstant.v(jVariable2));
    
    }
          public void JimpleAssign(JimpleVariable jVariable1 ,String jVariable2){ 
    
         a_assign = Jimple.v().newAssignStmt( jVariable1.getVariable() , StringConstant.v(jVariable2));
    
    }
    
    
    
    @Override
    protected Switchable getElement() {
        return a_assign;
    }
    
}
