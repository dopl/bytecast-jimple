/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;
import java.util.List;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import edu.syr.bytecast.common.IRuntimeJimpleHelper.IRuntimeJimple;

import edu.syr.bytecast.runtime.PrintfFilterByBlock;
/**
 *
 * @author invictus
 */
public class RuntimeJimple {
    long get_address_prinft()
    {
        long val=(long)0x3278398;
        return val;
        
    }
    
    byte[] getbytes_rodata(long m_address)
    {
       byte[] data = new byte[50];
        return data;
    }
    
    ArrayList<MemoryInstructionPair> get_instructionlist_printf(ArrayList<MemoryInstructionPair> l)
    {
        RuntimeJimple rt=new RuntimeJimple();
        List<MemoryInstructionPair> objdump_lst=rt.get_ObjectDumpList();
        IRuntimeJimple rj=new PrintfFilterByBlock();
        ArrayList<MemoryInstructionPair> printf_filter=rj.filter(objdump_lst, 0);
        long printf_address;
      //  printf_address=getMem_printf(printf_filter);
       // return printf_filter;
        return l;
    }
    
    List<MemoryInstructionPair> get_ObjectDumpList()
    {
        long min_val=323;
        MemoryInstructionPair m=new MemoryInstructionPair(min_val, null);
        List<MemoryInstructionPair> lst=new ArrayList<MemoryInstructionPair>();
      
        return lst;
        
    }
        
    long getMem_printf(List<MemoryInstructionPair> l)
    {
        List<MemoryInstructionPair> ls=l;
         
            Long printf_add=ls.get(0).getmInstructionAddress();
        
            return printf_add;
    }
    
    ArrayList<List<Byte>> getbytes_instruction(ArrayList<MemoryInstructionPair> inst_list)
    { 
               
                List<Byte> printf_instructions_bytelist=new ArrayList<Byte>(); 
                List<Byte> l=new ArrayList<Byte>();
               ArrayList<List<Byte>> listOfByteList = new ArrayList<List<Byte>>();
              for(MemoryInstructionPair m:inst_list)
              {
                 
                  printf_instructions_bytelist.addAll(m.getInstruction().getBytes());
                  
                  listOfByteList.add(printf_instructions_bytelist);
              }
              return listOfByteList;
        
              
    }
    
}
