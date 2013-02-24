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

package edu.syr.bytecast.test.mockups;

import edu.syr.bytecast.interfaces.fsys.ExeObj;
import edu.syr.bytecast.interfaces.fsys.ExeObjIOUtils;
import edu.syr.bytecast.interfaces.fsys.IBytecastFsys;
import edu.syr.bytecast.util.Paths;

public class MockBytecastFsys implements IBytecastFsys {

  private String m_filePath;
  
  @Override
  public void setFilepath(String file_path) {
    m_filePath = file_path;
  }

  @Override
  public String getFilepath() {
    return m_filePath;
  }

  @Override
  public ExeObj parse() throws Exception {
    String path = Paths.v().getFsysMock1Path();
    ExeObj ret = ExeObjIOUtils.readFromFile(path);
    return ret;
  }  
}
