/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.jimple.api.MethodInfo;
import edu.syr.bytecast.jimple.api.Methods;
import edu.syr.bytecast.jimple.beans.JInstructionInfo;
import edu.syr.bytecast.jimple.beans.ParsedInstructionsSet;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import soot.*;
import soot.JastAddJ.ReferenceType;
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
    
    private SootClass sClass = new SootClass("TestClass", Modifier.PUBLIC);
    private ArrayList<SootMethod> sootMethodList = new ArrayList<SootMethod>();
    
    public boolean jimple(ArrayList<ParsedInstructionsSet> pis_list, String fileName) throws FileNotFoundException, IOException
    {
        createClassSkeleton();
        createClassFile(fileName);
        return true;
    }
    
    public void createMethodsBody(ArrayList<ParsedInstructionsSet> pis_list)
    {
        //Create class and Methods Skeleton
        List<MethodInfo> methods = Methods.methods;
        //Arrays.sort(pis_list);
        for(int i=1;i<methods.size();i++)
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
                String localName = "l"+i+k;
                //if(minfo.)
                Local arg = Jimple.v().newLocal(localName, IntType.v());
                body.getLocals().add(arg);
                                
                
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
    
    public void createClassFile(String fileName) throws FileNotFoundException, IOException
    {
        //String fileName = SourceLocator.v().getFileNameFor(sClass, Options.output_format_class);
        OutputStream streamOut = new JasminOutputStream(new FileOutputStream(fileName));
        PrintWriter writerOut = new PrintWriter(new OutputStreamWriter(streamOut));
        JasminClass jasminClass = new soot.jimple.JasminClass(sClass);
        jasminClass.print(writerOut);
        writerOut.flush();
        streamOut.close();
    }
    
    public void createClassSkeleton()//ArrayList<SootMethod> sootMethodList, SootClass sClass)
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
