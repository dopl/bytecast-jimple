/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.beans.jimpleBean;

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
//    public void setValues(JimpleVariable leftv, JimpleVariable rightv) {
//        this.leftV = leftv.getVariable();
//        this.rightV = rightv.getVariable();
//    }
    
//    public void setLeftVal(JimpleVariable jv) {
//        this.leftV = jv.getVariable();
//    }
//    
//    public void setRightVal(JimpleElement[] jes) {
//        
//    }
    
//    public void setTarget(JimpleElement jelement) {
//        
//    }
    
    public void setTargets(JimpleElement[] jelements) {
      targets = Jimple.v().newStmtBox(jelements[0].getElement());
        for (JimpleElement je : jelements) {
            targets.setUnit(je.getElement());
        }
    }
    
    /**
     * if statement as default
     * need to modify when considering
     * other condition like switch
     * @return 
     */
    @Override
    protected Unit getElement() {
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
        
        Unit statement = Jimple.v().newIfStmt(condition, targets);
        return statement;
    }
    
    
}
