/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;

import soot.Local;
import soot.Unit;

/**
 *
 * @author Peike Dai
 */
// JimpleInvoke
// JimpleCondition
// JimpleVariable
// JimpleAssign
public abstract class JimpleElement {
    protected abstract Unit getElement();
    protected abstract Local getVariable();
}
