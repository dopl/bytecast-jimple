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

public class Filter {
  private int inst_count;
  private String filter_name;
  
  public int getInst_Count()
  {
    return inst_count;
  }
  
  public void setInst_Count(int count)
  {
    inst_count = count;
  }
  
  public String getFilter_Name()
  {
    return filter_name;
  }
  
  public void setFilter_Name(String name)
  {
    filter_name = name; 
  }
}
