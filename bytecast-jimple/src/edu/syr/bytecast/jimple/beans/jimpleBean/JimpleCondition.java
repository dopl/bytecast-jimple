/*
 * 03/25/2013 - 1.0
 * 
 * this class writes jimple like:
 * if $r0 < 3 goto label0;
 * 
 * only support "if" currently
 * 
 * 
 * "targets" is where program will jump if condition is true
 * targets must be added to JimpleMethod by addElement() method
 * could be added to anywhere, but normally added at the last of
 * current method definition
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;

import soot.Local;
import soot.SootMethod;
import soot.Unit;
import soot.UnitBox;
import soot.Value;
import soot.jimple.IntConstant;
import soot.jimple.Jimple;
import soot.jimple.StringConstant;

/**
 *
 * @author col
 */
public class JimpleCondition extends JimpleElement{
    private String type; // if or switch
    private String comparator; // < > <= >= or ==
    private Value leftV;
    private Value rightV;
    private Value val;
    private UnitBox targets;
    private Unit stmt;
    private SootMethod baseMethod;
    
    
    public JimpleCondition(String comprt, JimpleVariable leftv, 
            JimpleVariable rightv) {
        this("if", comprt, leftv, rightv);
    }
    
    public JimpleCondition(String comprt, JimpleVariable leftv,
            boolean rightv) {
        int val = rightv? 1 : 0;
        init("if","==", leftv.getVariable(), IntConstant.v(val));
    }
    
    public JimpleCondition(String comprt, JimpleVariable leftv,
            int rightv) {
        this("if", comprt, leftv.getVariable(), IntConstant.v(rightv));
    }
    
    public JimpleCondition(String comprt, JimpleVariable leftv,
            int rightv, JimpleMethod basemethod) {
        init2("if", comprt, leftv.getVariable(), IntConstant.v(rightv), basemethod.getMethod());
    }
    
    public JimpleCondition(String comprt, JimpleVariable leftv,
            String rightv) {
        this("if", comprt, leftv.getVariable(), StringConstant.v(rightv));
    }
    
    public JimpleCondition(String type, String comprt, 
            JimpleVariable leftv, JimpleVariable rightv) {
        this(type, comprt, leftv.getVariable(), rightv.getVariable());
    }
    
    private JimpleCondition(String type, String comprt, 
            Value leftv, Value rightv) {
        init(type, comprt, leftv, rightv);    
    }
    
    private void init(String type, String comprt, Value leftv, Value rightv){
        this.type = type;
        this.comparator = comprt != null ? comprt : null;
        this.leftV = leftv;
        this.rightV = rightv;
    }
    
    /**
     * version 1.1
     * added for passing JimpleMethod object.
     * no need to addElement outside
     * @param type
     * @param comprt
     * @param leftv
     * @param rightv
     * @param baseMethod 
     */
    private void init2(String type, String comprt, Value leftv, Value rightv, SootMethod basemethod) {
        this.type = type;
        this.comparator = comprt != null ? comprt : null;
        this.leftV = leftv;
        this.rightV = rightv;
        this.baseMethod = basemethod;
        
    }
    
    private void initStatement() {
        Value condition = null;
        if (this.comparator.equals("<")) {
            condition = Jimple.v().newLtExpr(this.leftV, this.rightV);
        } else if (this.comparator.equals("<=")) {
            condition = Jimple.v().newLeExpr(this.leftV, this.rightV);
        } else if (this.comparator.equals("==")) {
            condition = Jimple.v().newEqExpr(this.leftV, this.rightV);
        } else if (this.comparator.equals(">=")) {
            condition = Jimple.v().newGeExpr(this.leftV, this.rightV);
        } else if (this.comparator.equals(">")) {
            condition = Jimple.v().newGtExpr(this.leftV, this.rightV);
        } 
        
        stmt = Jimple.v().newIfStmt(condition, targets);
        if (baseMethod != null) {
            baseMethod.getActiveBody().getUnits().add(stmt);
        }
//        baseMethod.getActiveBody().getUnits().add(baseobject);
    }
    
    // initialize and set UnitBox targets
    public void setTargets(JimpleElement[] jelements) {
        if (jelements != null) {
            targets = Jimple.v().newStmtBox(jelements[0].getElement());
            for (int i=1; i<jelements.length; ++i) {
              targets.setUnit(jelements[i].getElement());
            }
        }
      initStatement();
    }
    
    /**
     * if statement as default
     * need to modify when considering
     * other condition like switch
     * @return 
     */
    
    @Override
    protected Local getVariable() {
      return null;
    }
    @Override
    protected Unit getElement() {
        return stmt;
    }
}
