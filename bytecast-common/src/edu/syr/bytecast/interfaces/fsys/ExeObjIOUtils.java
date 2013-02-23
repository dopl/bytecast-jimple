/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.interfaces.fsys;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author shawn
 */
public class ExeObjIOUtils {
    public static void printExeObj(ExeObj obj){        
        System.out.printf("entryPointIndex: %016x\n", obj.getEntryPointIndex());
        System.out.println("::Segment Data::");
        
        List<ExeObjSegment> segs = obj.getSegments();
        
        for(int i = 0; i <  segs.size(); i++)
        {
            System.out.println("Label:  " + segs.get(i).getLabel());
            System.out.printf("StartAddress:  %016x\n", segs.get(i).getStartAddress());
            System.out.printf("Number of Bytes:  %016x\n\n", segs.get(i).getBytes().size());
        }         
    }
    
    public static void writeToFile(ExeObj obj, String file_name) throws IOException
    {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        DataOutputStream dos = null;
        
        fos = new FileOutputStream(file_name);
        bos = new BufferedOutputStream(fos);
        dos = new DataOutputStream(bos);
        
        dos.writeLong(obj.getEntryPointIndex());
        
        List<ExeObjSegment> segs = obj.getSegments();
        List<ExeObjFunction> funcs = obj.getFunctions();
        
        dos.writeInt(segs.size());
        dos.writeInt(funcs.size());
        
        for(int i = 0; i < segs.size(); i++)
        {
            dos.writeUTF(segs.get(i).getLabel());
            dos.writeLong(segs.get(i).getStartAddress());
            dos.writeInt(segs.get(i).getSize());
            List<Byte> bytes = segs.get(i).getBytes();
            for(int j = 0; j < bytes.size(); j++)
            {
                dos.writeByte(bytes.get(j));
            }

        }
        
        for(int i = 0; i < funcs.size(); i++)
        {
            dos.writeUTF(funcs.get(i).getName());
            dos.writeLong(funcs.get(i).getStartAddress());
            dos.writeInt(funcs.get(i).getSize());
        }
             
        dos.close();
        bos.close();
        fos.close();       
    }
 
    public static ExeObj readFromFile(String file_name) throws IOException
    {
        ExeObj ret = new ExeObj();
        
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        DataInputStream dis = null;
        
        fis = new FileInputStream(file_name);
        bis = new BufferedInputStream(fis);
        dis = new DataInputStream(bis);
        
        ret.setEntryPointIndex(dis.readLong());
        int num_segments = dis.readInt();
        int num_functions = dis.readInt();
        
        List<ExeObjSegment> segs = new ArrayList<ExeObjSegment>();
        for(int i = 0; i < num_segments; i++)
        {
            ExeObjSegment seg = new ExeObjSegment();
            seg.setLabel(dis.readUTF());
            seg.setStartAddress(dis.readLong());
            int seg_size = dis.readInt();
            
            List<Byte> bytes = new ArrayList<Byte>();
            
            for(int j = 0; j < seg_size; j++)
            {
                bytes.add(dis.readByte());            
            }
            seg.setBytes(bytes);
            segs.add(seg);
        }
        ret.setSegments(segs);
        
        List<ExeObjFunction> funcs = new ArrayList<ExeObjFunction>();
        for(int i = 0; i < num_functions; i++)
        {
            ExeObjFunction func = new ExeObjFunction();
            func.setName(dis.readUTF());
            func.setStartAddress(dis.readLong());
            func.setSize(dis.readInt());
            
            funcs.add(func);
            
        }
        ret.setFunctions(funcs);
        
        dis.close();
        bis.close();
        fis.close();
        
        return ret;
    }   
}
