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

public class ExeObjDependency {
    
    public enum ExeObjDependencyType {
        KERNEL, FILE
    }
    
    public String getDependencyName()
    {
        return m_name;
    }
    
    public void setDependencyName(String name)
    {
        m_name = name;
    }
    
    public ExeObjDependencyType getDependencyType(){
        return m_type;
    }
    
    public void setDependencyType(ExeObjDependencyType type)
    {
        m_type = type;
    }
    
    public String getDependencyPath()
    {
        return m_path;
    }
    
    public void setDependencyPath(String path)
    {
        m_path = path;
    }
    
    public long getStartOffset()
    {
        return m_startOffset;
    }
    
    public void setStartOffset(long start_offset)
    {
        m_startOffset = start_offset;
    }
    
    private String m_name;
    private String m_path;
    private long m_startOffset;
    private ExeObjDependencyType m_type;
}
