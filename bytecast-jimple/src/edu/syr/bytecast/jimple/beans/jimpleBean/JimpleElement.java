/*
 * 03/25/2013 - 1.0
 * 
 * superclass for:
 * JimpleInvoke
 * JimpleCondition
 * JimpleVariable
 * JimpleAssign
 * 
 * 
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
    protected abstract Local getLocalForTarget();
    protected abstract Unit getAssStmtForTarget();
    protected abstract Unit getInvStmtForTarget();
}
