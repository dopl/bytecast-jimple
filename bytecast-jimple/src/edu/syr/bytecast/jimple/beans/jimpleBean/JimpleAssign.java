/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;

import soot.jimple.Stmt;
import soot.util.Switchable;

/**
 *
 * @author Peike Dai
 */
public class JimpleAssign extends JimpleElement{
    private Stmt statement;
    public JimpleAssign() {
    }
    
    @Override
    protected Switchable getElement() {
        return statement;
    }
    
}
