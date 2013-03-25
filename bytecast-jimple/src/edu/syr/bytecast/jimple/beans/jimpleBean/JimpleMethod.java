/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;

import java.util.ArrayList;
import java.util.List;
import soot.*;
import soot.jimple.IdentityStmt;
import soot.jimple.Jimple;
import soot.jimple.JimpleBody;
import soot.util.Chain;

/**
 *
 * @author col
 */
public class JimpleMethod {

    private ArrayList<String> parameters_type;
    private String methodName;
    private String returnType;
    private SootClass declaringClass;
    private int modifier;
    private JimpleBody jBody;
    private PatchingChain<Unit> units;
    private Chain<Local> locals;
    private SootMethod myMethod;
    /**
     *
     * @param methodName
     * @param returnType
     * @param modifier (public:1 , private:2 , static:8 ( if u want to use
     * (public+static) :value will be 9))
     * @param parameters_type
     */
    public JimpleMethod(String methodName, String returnType, 
            JimpleClass declaringClass, int modifier, ArrayList<String> parameters_type) {

        this.methodName = methodName;
        this.modifier = modifier;
        this.returnType = returnType;
        this.parameters_type = parameters_type;
        this.declaringClass = declaringClass.getSClass();
        
        createMethod();
    }

    private void createMethod() {

        List<Type> parameters = new ArrayList<Type>();
        if (parameters_type != null) {
            for (String tp : parameters_type) {
                parameters.add(JimpleUtil.getTypeByString(tp));
            }
        }

        myMethod = new SootMethod(methodName, parameters,
                JimpleUtil.getTypeByString(returnType), modifier);
        //mySootclass.addMethod(myMethod);
        
        //create jimple body
        jBody = Jimple.v().newBody(myMethod);
        myMethod.setActiveBody(jBody);

        units = jBody.getUnits();
        locals = jBody.getLocals();
        initMethod();
    }
    
    private void initMethod() {
        
        if(!methodName.equals("main")){
        // Class r0;
        Local thisref = Jimple.v().newLocal("r0", declaringClass.getType());
        locals.add(thisref);
        
        // r0 := @this: Class;
        Value this_rhs = Jimple.v().newThisRef(declaringClass.getType());
        IdentityStmt thistmt = Jimple.v().newIdentityStmt(thisref, this_rhs);
        units.add(thistmt);
        
        }
        
        // if has parameters, init them
//        if (this.parameters_type != null) {
//            int paranum = 0;
//            for (String typestr : this.parameters_type) {
//                // int r0, r1,...
//                Type paratype = JimpleUtil.getTypeByString(typestr);
//                Value prmtref = Jimple.v().newParameterRef(paratype, paranum);
//                Local paraLocal = Jimple.v().newLocal("l"+ String.valueOf(paranum), paratype);
//                //locals.add(paraLocal);
////                this.parameters.add(new JimpleVariable("l"));
//                // i0 := @parameter0: int/String;
//                Unit identitystmt = Jimple.v().newIdentityStmt(paraLocal, prmtref);
//                units.add(identitystmt);
//                
//                paranum++;
//            }
//        }
    }
    
    public List<String> getParameterTypes() {
        if (this.parameters_type != null) {
            return this.parameters_type;
            
        } else {
            return null;
        }
        
    }
    protected SootMethod getMethod(){
        
        return myMethod;
    }
    
    public void addElement(JimpleElement jm) {
      if (jm.getVariable() != null) {
        myMethod.getActiveBody().getLocals().add(jm.getVariable());
      }
      if (jm.getElement() != null) {
        units.add(jm.getElement());
      }
    }
    
    public String getReturnType() {
      return this.returnType;
    }
    
    public void setReturn(JimpleVariable returnvariable) {
      Unit returnstmt = Jimple.v().newReturnStmt(returnvariable.getVariable());
      units.add(returnstmt);
    }
}

