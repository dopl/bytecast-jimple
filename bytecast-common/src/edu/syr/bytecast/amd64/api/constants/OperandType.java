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
package edu.syr.bytecast.amd64.api.constants;

public enum OperandType {

  REGISTER,
  CONSTANT,
  NUMBER,
  /**
   * refer to Section 1.1.1 in AMD64 Volume 2
   */
  MEMORY_LOGICAL_ADDRESS,
  /**
   * refer to Section 1.1.1 in AMD64 Volume 2
   */
  MEMORY_EFFECITVE_ADDRESS,
  /**
   * refer to Section 1.1.1 in AMD64 Volume 2
   */
  MEMORY_LINEAR_ADDRESS,
  /**
   * refer to Section 1.1.1 in AMD64 Volume 2
   */
  MEMORY_PHYSICAL_ADDRESS,
  SECTION_NAME
}