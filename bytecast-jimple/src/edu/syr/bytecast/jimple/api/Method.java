/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.api;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import java.util.List;

/**
 *
 * @author peike
 */
public class Method {
  private MethodInfo methodInfo;
  private List<IInstruction> methodContent;

  public List<IInstruction> getMethodContent() {
    return methodContent;
  }

  public void setMethodContent(List<IInstruction> methodContent) {
    this.methodContent = methodContent;
  }

  public MethodInfo getMethodInfo() {
    return methodInfo;
  }

  public void setMethodInfo(MethodInfo methodInfo) {
    this.methodInfo = methodInfo;
  }
  
}
