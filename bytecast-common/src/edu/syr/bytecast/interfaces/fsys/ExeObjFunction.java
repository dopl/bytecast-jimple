
package edu.syr.bytecast.interfaces.fsys;

public class ExeObjFunction {
    private String m_name;
    private long   m_startAddress;
    private int    m_size;
    
    public void setName(String name)
    {
        m_name = name;
    }
    
    public void setStartAddress(long addr)
    {
        m_startAddress = addr;
    }
    
    public void setSize(int size)
    {
        m_size = size;
    }
    
    public String getName()
    {
        return m_name;
    }
    
    public long getStartAddress()
    {
        return m_startAddress;
    }
    
    public int getSize()
    {
        return m_size;
    }
}
