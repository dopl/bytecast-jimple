/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.api.instruction;

import edu.syr.bytecast.amd64.api.constants.OperandType;

/**
 *
 * @author harsh
 */
public interface IOperand {
    
    public OperandType getOperandType();
    
    public Object getOperandValue();
    
}
