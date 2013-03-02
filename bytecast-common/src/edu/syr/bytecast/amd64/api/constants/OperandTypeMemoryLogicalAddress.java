/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.api.constants;

/**
 *
 * @author hapan
 */
public class OperandTypeMemoryLogicalAddress {

  private RegisterType m_segment;
  private OperandTypeMemoryEffectiveAddress m_effectiveAddress;

  /**
   *
   * @param segment
   * @param effectiveAddress
   */
  public OperandTypeMemoryLogicalAddress(RegisterType segment, OperandTypeMemoryEffectiveAddress effectiveAddress) {
    this.m_segment = segment;
    this.m_effectiveAddress = effectiveAddress;
  }

  public OperandTypeMemoryLogicalAddress(RegisterType segment, RegisterType base, RegisterType index, int scale, int offset) {
    this.m_segment = segment;
    this.m_effectiveAddress = new OperandTypeMemoryEffectiveAddress(base, index, scale, offset);
  }

  public OperandTypeMemoryEffectiveAddress getEffectiveAddress() {
    return m_effectiveAddress;
  }

  public RegisterType getSegment() {
    return m_segment;
  }
}
