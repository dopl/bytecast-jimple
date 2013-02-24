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

package edu.syr.bytecast.jimple.beans;

public class JInstructionInfo {
    private String instruction_name;
    private int instructions_count;
    private int start_index;
    
    public String getInstruction_Name()
    {
        return this.instruction_name;
    }
    
    public int getInstructions_Count()
    {
        return this.instructions_count;
    }
    
    public int getStart_Index()
    {
        return this.start_index;
    }
    
    public void setInstruction_Name(String name)
    {
        this.instruction_name = name;
    }
    
    public void setInstructions_Count(int count)
    {
        this.instructions_count = count;
    }
    
    public void setStart_Index(int index)
    {
        this.start_index = index;
    }
}
