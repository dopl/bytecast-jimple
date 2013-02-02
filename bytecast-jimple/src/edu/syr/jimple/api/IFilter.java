/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.jimple.api;

import java.util.List;
import soot.Unit;

/**
 *
 * @author QSA
 */
public interface IFilter{
    public Unit filter(List<IInstructions> inst_list);
}
