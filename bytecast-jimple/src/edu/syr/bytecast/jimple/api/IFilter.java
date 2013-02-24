/*
 * This file is part of Bytecast.
 *
 * Bytecast is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Bytecast is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bytecast.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package edu.syr.bytecast.jimple.api;

import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.jimple.beans.ParsedInstructionsSet;
import java.util.List;

public interface IFilter{
  boolean doTest(List<IInstruction> instList, int index);
    //void scan(List<IInstruction> unparsed_inst_list, List<ParsedInstructionsSet> parsed_inst_list, List<IFilterRule> rulesList);
    
}
