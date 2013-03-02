/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;

/**
 *
 * @author QSA
 */
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
