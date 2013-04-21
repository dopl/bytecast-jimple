/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;
import java.util.List;
import java.util.*;
import soot.Value;
import soot.jimple.StringConstant;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
/**
 *
 * @author invictus
 */
public class Converter {
    private long address_printf;
    private String rodata_str;
    String get_rodata_String()
    {
        String str;
        RuntimeJimple r=new RuntimeJimple();
         List<MemoryInstructionPair> printf_isnt_list=r.get_instructionlist_printf();
        address_printf=r.getMem_printf(printf_isnt_list);
        byte[] byte_data = r.getbytes_rodata(address_printf);
        StringBuilder sb = new StringBuilder(byte_data.length);
        for (byte b:byte_data) {
         sb.append(b+'0'); //offset the byte value with the value of char '0'
        }
         str = sb.toString();
        return str;
    }
    
    void Convert(JimpleMethod j_method)
    {
        RuntimeJimple r=new RuntimeJimple();
      rodata_str=get_rodata_String();
      Value v=StringConstant.v(rodata_str);
    // assign object for all kinds of assign
    JimpleAssign jim_ass = new JimpleAssign();
    // invoke object for all kinds of invoke
    JimpleInvoke jim_inv = new JimpleInvoke();
//    JimpleVariable v = new JimpleVariable
    JimpleVariable local_string=new JimpleVariable("l_str", "java.lang.String",j_method);
    jim_ass.JimpleDirectAssign(local_string, rodata_str, j_method);
    
    
    ArrayList<MemoryInstructionPair> array_printfinst=r.get_instructionlist_printf();
    ArrayList<List<Byte>> list_bytes_printf=r.getbytes_instruction(array_printfinst);
    int total_bytes=0;
    for(List<Byte> b:list_bytes_printf)
    {
        total_bytes+=b.size();
    }
    
    
   JimpleVariable byteArray = new JimpleVariable("code" , "byte[]" , j_method);
    
    // testByteArray = newarray (byte)[total_bytes];
    jim_ass.JimpleNewArray(byteArray, total_bytes, j_method);
    int i=0;
    for(List<Byte> l:list_bytes_printf)
    {
        for(;i<l.size();i++)
        {
            jim_ass.JimpleArrayAssign(byteArray, i, l.get(i), j_method);
            System.out.println(l.get(i));
        }
    }
    
    
}}
