/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.soot.api;

/**
 *
 * @author pahuja
 */
public interface ISootMembers {
    soot.jimple.ParameterRef getParRef(String value);
    soot.Local getLocal(String name, String type);
    soot.jimple.Stmt getAssignStmt(soot.Value v1, soot.Value v2);
    soot.jimple.Stmt getIdntStmt(soot.Value v1, soot.Value v2);
    //soot.jimple.Expr getExpr(soot.Value v1, soot.Value v2);
}
