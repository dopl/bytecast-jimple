package edu.syr.bytecast.interfaces.fsys;
import java.util.*;
import java.io.*;

public class ExeObj {
    
    public long getEntryPointIndex() {
        return m_entryPointIndex;
    }
     
    public void setEntryPointIndex(long entry_point) {
        m_entryPointIndex = entry_point;
    }
    
    public List<ExeObjSegment> getSegments() {
        return m_segments;
    }
    
    public List<Byte> getBytesFromAddr(long start_addr, int size) throws Exception
    {
        for(int i = 0; i < m_segments.size(); i++)
        {
            if(start_addr >= m_segments.get(i).getStartAddress() &&
               start_addr+size <=  m_segments.get(i).getEndAddress())
            {
                int offset = (int)(start_addr - m_segments.get(i).getStartAddress());
                return m_segments.get(i).getBytes(offset,size);
            }
        }
        throw new Exception("Address range not found in any segment.");
    }
    public void setSegments(List<ExeObjSegment> segments) {
        m_segments=segments;
    }
 
    public void setFunctions(List<ExeObjFunction> functions){
        m_functions = functions;
    }
    
    public List<ExeObjFunction> getFunctions()
    {
        return m_functions;        
    }
    public void printExeObj(){        
        System.out.printf("entryPointIndex: %016x\n", m_entryPointIndex);
        System.out.println("::Segment Data::");
         
        for(int i = 0; i <  m_segments.size(); i++)
        {
            System.out.println("Label:  " + m_segments.get(i).getLabel());
            System.out.printf("StartAddress:  %016x\n", m_segments.get(i).getStartAddress());
            System.out.printf("Number of Bytes:  %016x\n\n", m_segments.get(i).getBytes().size());
        }         
    }
    
    public void writeToFile(String file_name) throws IOException
    {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        DataOutputStream dos = null;
        fos = new FileOutputStream(file_name);
        bos = new BufferedOutputStream(fos);
        dos = new DataOutputStream(bos);
        dos.writeLong(m_entryPointIndex);
        
        for(int i = 0; i < m_segments.size(); i++)
        {
            dos.writeUTF(m_segments.get(i).getLabel());
            dos.writeLong(m_segments.get(i).getStartAddress());
            dos.writeInt(m_segments.get(i).getSize());
            List<Byte> bytes = m_segments.get(i).getBytes();
            for(int j = 0; j < bytes.size(); j++)
            {
                dos.writeByte(bytes.get(j));
            }
            
            dos.close();
            bos.close();
            fos.close();
        }
        
    }
 
    public void readFromFile(String file_name) throws IOException
    {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        DataInputStream dis = null;
        
        fis = new FileInputStream(file_name);
        //bis = new 
    }
    
    private long m_entryPointIndex;
    private List<ExeObjSegment> m_segments;
    private List<ExeObjFunction> m_functions;
    
}
