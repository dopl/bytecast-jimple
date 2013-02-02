/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.jimple.api;
import com.bytecast.amd64.api.instruction.IInstruction;
/**
 *
 * @author QSA
 */
public interface IInstructions extends IInstruction{
    public boolean isUsed();
    public void setUsed(boolean flag);
}
