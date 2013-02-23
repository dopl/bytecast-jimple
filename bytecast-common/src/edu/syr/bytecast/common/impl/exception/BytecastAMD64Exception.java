/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.common.impl.exception;

import edu.syr.bytecast.common.api.constants.BytecastComponent;
import edu.syr.bytecast.common.api.exception.BytecastException;

/**
 *
 * @author bytecast
 */
public class BytecastAMD64Exception extends BytecastException{

    public BytecastAMD64Exception(String message) {
        super(BytecastComponent.BCC_AMD64,message);
    }
    
    public BytecastAMD64Exception(String message,Throwable cause) {
        super(BytecastComponent.BCC_AMD64,message,cause);
    }
    
   
}
