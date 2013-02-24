/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.common.api.exception;

import edu.syr.bytecast.common.api.constants.BytecastComponent;

/**
 *
 * @author bytecast
 */
public class BytecastException extends Exception{
    
    private BytecastComponent m_component;
    private String m_message;

    public BytecastException(BytecastComponent m_component, String m_message) {
        this.m_component = m_component;
        this.m_message = m_message;
    }

    public BytecastException(BytecastComponent m_component, String m_message, Throwable cause) {
        super(cause);
        this.m_component = m_component;
        this.m_message = m_message;
    }

    @Override
    public String getMessage() {
        return getComponentName(m_component) + getMessage();
    }
    
    
    //Formats error message as 
    //"COMPONENT: Bytecast-AMD64   ERROR:Could not find a decoder for the instruction XYZ"
    
    private String errorMessage()
    {
        String errMessage = "COMPONENT:"+getComponentName(m_component)
                            + "   ERROR:"+m_message;
        return errMessage;
    }

   private String getComponentName(BytecastComponent component)
   {
       switch(component)
       {
           case BCC_AMD64: return "Bytecast-AMD64";
           case BCC_FSYS: return "Bytecast-Fsys";
           case BCC_JIMPLE: return  "Bytecast-Jimple";
           case BCC_RUNTIME: return "Bytecast-Runtime";
           default: return "Bytecast";
       }
   }
    
}
