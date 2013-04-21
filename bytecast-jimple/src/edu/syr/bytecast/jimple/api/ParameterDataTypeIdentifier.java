/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.jimple.api;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.constants.OperandTypeMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParameterDataTypeIdentifier {

  public static JavaDataType getDataType(ParameterInfo info, List<MemoryInstructionPair> ins) {
    RegisterType currentRegister = null;
    for (MemoryInstructionPair in : ins) {
      if (!(in.getInstruction().getInstructiontype() == InstructionType.MOV
              || in.getInstruction().getInstructiontype() == InstructionType.MOVSX
              || in.getInstruction().getInstructiontype() == InstructionType.MOVZX)) {
        continue;
      }

      IOperand op1 = in.getInstruction().getOperands().get(0);
      IOperand op2 = in.getInstruction().getOperands().get(1);

      // always store the latest register that store the PsarameterInfo
      if (op1.equals(info.getParamStackAddress()) && op2.getOperandType() == OperandType.REGISTER) {
        currentRegister = (RegisterType) op2.getOperandValue();
      } else {
        if (currentRegister == null) {
          // skip if haven't got the currentRegister 
          continue;
        } else {
          // start track the flow of currentRegister
          if (containCurrentRegister(op1, currentRegister) && op2.getOperandType() == OperandType.REGISTER) {
            currentRegister = (RegisterType) op2.getOperandValue();
          }
        }
      }
    }

    if (REG8_L_LIST.contains(currentRegister)) {
      return JavaDataType.CHAR;
    } else if (REG8_H_LIST.contains(currentRegister)) {
      return JavaDataType.SHORT;
    } else if (REG16_LIST.contains(currentRegister)) {
      return JavaDataType.SHORT;
    } else if (REG32_LIST.contains(currentRegister)) {
      return JavaDataType.INT;
    } else if (REG64_LIST.contains(currentRegister)) {
      return JavaDataType.LONG;
    }

    return JavaDataType.INT;
  }

  private static boolean containCurrentRegister(IOperand operand, RegisterType register) {
    boolean ret = false;
    RegisterType reg = null;

    if (operand.getOperandType() == OperandType.REGISTER) {
      reg = (RegisterType) operand.getOperandValue();
      if (containRegister(reg, register)) {
        ret = true;
      }
    } else if (operand.getOperandType() == OperandType.MEMORY_EFFECITVE_ADDRESS) {
      OperandTypeMemoryEffectiveAddress addr = (OperandTypeMemoryEffectiveAddress) operand.getOperandValue();
      // check Base field
      if (addr.getBase() != null) {
        reg = (RegisterType) addr.getBase();
        if (containRegister(reg, register)) {
          ret = true;
        }
      }
      // check Index field
      if (addr.getIndex() != null) {
        reg = (RegisterType) addr.getIndex();
        if (containRegister(reg, register)) {
          ret = true;
        }
      }
    }

    return ret;
  }

  // check if the two register are of the same kind
  // for example, AL, AH, AX, EAX, RAX are of the same kind
  private static boolean containRegister(RegisterType register1, RegisterType register2) {
    boolean ret = false;
    int index1 = -1;
    int index2 = -1;

    if (register1 == null || register2 == null) {
      return false;
    }

    index1 = getIndex(register1);
    index2 = getIndex(register2);

    if (index1 == -1 || index2 == -1) {
      throw new IllegalArgumentException("Incorrect RegisterType value");
    }
    if (index1 == index2) {
      ret = true;
    }

    return ret;
  }

  // get the index of the register in either of the lists
  private static int getIndex(RegisterType register) {
    int ret = -1;

    if (REG8_L_LIST.contains(register)) {
      ret = REG8_L_LIST.indexOf(register);
    } else if (REG8_H_LIST.contains(register)) {
      ret = REG8_H_LIST.indexOf(register);
    } else if (REG16_LIST.contains(register)) {
      ret = REG16_LIST.indexOf(register);
    } else if (REG32_LIST.contains(register)) {
      ret = REG32_LIST.indexOf(register);
    } else if (REG64_LIST.contains(register)) {
      ret = REG64_LIST.indexOf(register);
    }

    return ret;
  }
  /**
   * See Table A-34, Figure 2-3, Volume 3, AMD64 Manual.
   */
  private static final List<RegisterType> REG8_L_LIST = new ArrayList<RegisterType>(Arrays.asList(new RegisterType[]{
    RegisterType.AL, RegisterType.CL, RegisterType.DL, RegisterType.BL,
    RegisterType.SPL, RegisterType.BPL, RegisterType.SIL, RegisterType.DIL,
    RegisterType.R8B, RegisterType.R9B, RegisterType.R10B, RegisterType.R11B,
    RegisterType.R12B, RegisterType.R13B, RegisterType.R14B, RegisterType.R15B}));
  /**
   * See Table A-34, Figure 2-3, Volume 3, AMD64 Manual.
   */
  private static final List<RegisterType> REG8_H_LIST = new ArrayList<RegisterType>(Arrays.asList(new RegisterType[]{
    RegisterType.AH, RegisterType.CH, RegisterType.DH, RegisterType.BH,
    RegisterType.SPL, RegisterType.BPL, RegisterType.SIL, RegisterType.DIL,
    RegisterType.R8B, RegisterType.R9B, RegisterType.R10B, RegisterType.R11B,
    RegisterType.R12B, RegisterType.R13B, RegisterType.R14B, RegisterType.R15B}));
  /**
   * See Table A-34, Figure 2-3, Volume 3, AMD64 Manual.
   */
  private static final List<RegisterType> REG16_LIST = new ArrayList<RegisterType>(Arrays.asList(new RegisterType[]{
    RegisterType.AX, RegisterType.CX, RegisterType.DX, RegisterType.BX,
    RegisterType.SP, RegisterType.BP, RegisterType.SI, RegisterType.DI,
    RegisterType.R8W, RegisterType.R9W, RegisterType.R10W, RegisterType.R11W,
    RegisterType.R12W, RegisterType.R13W, RegisterType.R14W, RegisterType.R15W}));
  /**
   * See Table A-34, Figure 2-3, Volume 3, AMD64 Manual.
   */
  private static final List<RegisterType> REG32_LIST = new ArrayList<RegisterType>(Arrays.asList(new RegisterType[]{
    RegisterType.EAX, RegisterType.ECX, RegisterType.EDX, RegisterType.EBX,
    RegisterType.ESP, RegisterType.EBP, RegisterType.ESI, RegisterType.EDI,
    RegisterType.R8D, RegisterType.R9D, RegisterType.R10D, RegisterType.R11D,
    RegisterType.R12D, RegisterType.R13D, RegisterType.R14D, RegisterType.R15D}));
  /**
   * See Table A-34, Figure 2-3, Volume 3, AMD64 Manual.
   */
  private static final List<RegisterType> REG64_LIST = new ArrayList<RegisterType>(Arrays.asList(new RegisterType[]{
    RegisterType.RAX, RegisterType.RCX, RegisterType.RDX, RegisterType.RBX,
    RegisterType.RSP, RegisterType.RBP, RegisterType.RSI, RegisterType.RDI,
    RegisterType.R8, RegisterType.R9, RegisterType.R10, RegisterType.R11,
    RegisterType.R12, RegisterType.R13, RegisterType.R14, RegisterType.R15}));
}
