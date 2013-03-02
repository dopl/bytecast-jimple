/*
 * This file is part of Bytecast.
 *
 * Bytecast is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Bytecast is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bytecast.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package edu.syr.bytecast.interfaces.fsys;

import java.util.*;

public class ExeObjSegment {

    public long getStartAddress() {
        return m_startAddress;
    }

    public void setStartAddress(long start_address) {
        m_startAddress = start_address;
    }

    public String getLabel() {
        return m_label;
    }
    
    public void setLabel(String label){
        m_label = label;
    }

    public List<Byte> getBytes() {
        return m_bytes;
    }
    
    public List<Byte> getBytes(int offset, int size)
    {
        List<Byte> ret = new ArrayList<Byte>();
        for(int i = offset; i < offset+size; i++)
        {
            ret.add(m_bytes.get(i));
        }
        return ret;
    }
    public void setBytes(List<Byte> bytes)
    {
        m_bytes = bytes;
    }
    
    public int getSize()
    {
        return m_bytes.size();
    }
    
    public long getEndAddress()
    {
        return m_startAddress + m_bytes.size();
    }
    private long m_startAddress;
    private String m_label;
    private List<Byte> m_bytes;
}
