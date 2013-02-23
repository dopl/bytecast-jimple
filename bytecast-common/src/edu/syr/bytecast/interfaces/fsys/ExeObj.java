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
    
    public List<ExeObjDependency> getDependencies(){
        return m_dependencies;
    }    
     
    public void setDependencies(List<ExeObjDependency> dependencies){
        m_dependencies = dependencies;
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
        System.out.println("::Dependency Data::");
        for(int i = 0; i <  m_dependencies.size(); i++)
        {
            System.out.println("Name:  " + m_dependencies.get(i).getDependencyName());
            System.out.println("DependencyPath: " + m_dependencies.get(i).getDependencyPath());
            System.out.printf("Type:  %016x\n", m_dependencies.get(i).getDependencyType());
            System.out.printf("StartOffset:  %016x\n\n", m_dependencies.get(i).getStartOffset());
        }  
        
    }
    private long m_entryPointIndex;
    private List<ExeObjSegment> m_segments;
    private List<ExeObjDependency> m_dependencies;
    
}
