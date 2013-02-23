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

package edu.syr.bytecast.jimple.impl;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.jimple.api.IFilter;
import java.util.List;

public class CallingFilter implements IFilter {
    public boolean doTest(List<IInstruction> instList, int index)
    {
        //this is frame to define the calling section in future
        IInstruction ins = instList.get(index);
        //calling without parameter
        if( ins.getInstructiontype() == InstructionType.CALLQ 
                && ins.getOperands().get(0).getOperandType() == OperandType.MEMORY_ADDRESS 
                && ins.getOperands().get(1).getOperandValue() == OperandType.MEMORY_ADDRESS )
                //the value of the second operand should be the SectionName, it hasn't been set up in AMD64 api
        {
            return true;
        }
        //calling with parameter
        if( ins.getInstructiontype() == InstructionType.MOV
                && ins.getOperands().get(0).getOperandType() == OperandType.CONSTANT
                && ins.getOperands().get(1).getOperandValue() == OperandType.REGISTER )
        {
            ins = instList.get(index + 1);
            if( ins.getInstructiontype() == InstructionType.CALLQ
                    && ins.getOperands().get(0).getOperandType() == OperandType.MEMORY_ADDRESS
                    && ins.getOperands().get(1).getOperandValue() == OperandType.MEMORY_ADDRESS ) //SectionName
            {
                return true;
            }
        }
        return false;
    } 
    
}
