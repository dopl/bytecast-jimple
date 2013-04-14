/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.jimple.api.MethodInfo;
import edu.syr.bytecast.jimple.api.Methods;
import edu.syr.bytecast.jimple.beans.JInstructionInfo;
import edu.syr.bytecast.jimple.beans.ParsedInstructionsSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import soot.*;
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
 * @author QSA
 */
public class CreateJimple {
    
    public void createMethodsBody(ArrayList<ParsedInstructionsSet> pis_list)
    {
        ArrayList<SootMethod> sootMethodList = new ArrayList<SootMethod>();
        SootClass sClass = new SootClass("TestClass", Modifier.PUBLIC);
        //Create class and Methods Skeleton
        createJimpleSkeleton(sootMethodList, sClass);
        List<MethodInfo> methods = Methods.methods;
        //Arrays.sort(pis_list);
        for(int i=0;i<methods.size();i++)
        {
            MethodInfo minfo = methods.get(i);
            int si = minfo.getStartIndex();
            int ei = minfo.getEndIndex();
            SootMethod method = sootMethodList.get(i);
            //Add locals that refer to parameters
            JimpleBody body = Jimple.v().newBody(method); 
            method.setActiveBody(body);
            Chain units = body.getUnits();
            for(int k=0;k<minfo.getParameterCount();k++)
            {
                String localName = "l"+k;
                if(k==0)
                {
                    Local arg = Jimple.v().newLocal(localName, ArrayType.v(RefType.v("java.lang.String"), 1));
                    body.getLocals().add(arg);
                    ParameterRef paramRef
                }
                else
                {
                    
                }
            }
            
            for(int j=0;j<pis_list.size();j++)
            {
                JInstructionInfo jinfo = pis_list.get(j).getInfo();
                int count = jinfo.getStart_Index();
                if(count >= si && (count + jinfo.getInstructions_Count()) <= ei)
                {
                    if(jinfo.getInstruction_Name().equalsIgnoreCase(null));
                }
            }
        }
        
    }
    
    public void createJimpleSkeleton(ArrayList<SootMethod> sootMethodList, SootClass sClass)
    {
        List<MethodInfo> methods = Methods.methods;
        //SootClass sClass;
        SootMethod method;
        Scene.v().loadClassAndSupport("java.lang.Object");
        Scene.v().loadClassAndSupport("java.lang.System");
        
        sClass.setSuperclass(Scene.v().getSootClass("java.lang.Object"));
        Scene.v().addClass(sClass);
        String method_name;
        
        for(int i=0;i<methods.size();i++)
        {
            method_name = methods.get(i).getMethodName();
            int num_para = methods.get(i).getParameterCount();
            if(i==0)
            {
                method = new SootMethod(method_name, Arrays.asList(new Type[] 
                {ArrayType.v(RefType.v("java.lang.String"), 1)}),
                VoidType.v(), Modifier.PUBLIC | Modifier.STATIC);
                
            }
            else
            {
                List<Type> types = new ArrayList<Type>();
                for(int j=0;j<num_para;j++){
                    types.add(IntType.v());
                }
                method = new SootMethod(method_name, types, IntType.v(), Modifier.PUBLIC);
            }
           sootMethodList.add(method);
        }
    }
}
