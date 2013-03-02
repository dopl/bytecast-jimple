/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.amd64.api.constants;

/**
 *
 * @author hapan
 */
public class OperandTypeMemoryEffectiveAddress {

  private RegisterType m_base;
  private RegisterType m_index;
  private int m_scale;
  private int m_offset;

  /**
   *
   * @param base
   * @param index
   * @param scale
   * @param offset
   */
  public OperandTypeMemoryEffectiveAddress(RegisterType base, RegisterType index, int scale, int offset) {
    this.m_base = base;
    this.m_index = index;
    this.m_scale = scale;
    this.m_offset = offset;
  }

  public RegisterType getBase() {
    return m_base;
  }

  public RegisterType getIndex() {
    return m_index;
  }

  public int getScale() {
    return m_scale;
  }

  public int getOffset() {
    return m_offset;
  }
}
