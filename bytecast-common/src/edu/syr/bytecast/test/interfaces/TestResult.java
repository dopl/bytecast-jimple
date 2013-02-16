/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.test.interfaces;

/**
 *
 * @author dhrumin
 */
public class TestResult {
    
    private boolean passed = false;
    private StringBuffer message;
    
    public TestResult()
    {
        message = new StringBuffer();
    }
    
    public void setPassed(boolean passed)
    {
        this.passed = passed;                
    }
    
    public boolean getPassed()
    {
        return passed;
    }
    
    public void appendMessage(String message)
    {
        this.message.append(message);
    }
    
    public String getMessage()
    {
        return message.toString();
    }
}
