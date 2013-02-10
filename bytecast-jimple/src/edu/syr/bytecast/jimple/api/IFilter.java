/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.api;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.jimple.beans.ParsedInstructionsSet;
import java.util.List;

/**
 *
 * @author QSA
 */
public interface IFilter {
    void scan(List<IInstruction> unparsed_inst_list, List<ParsedInstructionsSet> parsed_inst_list, List<IFilterRule> rulesList);
    
}
