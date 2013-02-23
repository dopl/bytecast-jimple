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
package edu.syr.bytecast.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RunProcess {
  
  private StreamEater m_stdout;
  private StreamEater m_stderr;
  
  public int exec(String command, File start_dir) throws IOException, InterruptedException {
    Process p = Runtime.getRuntime().exec(command, new String[0], start_dir);
    m_stdout = new StreamEater(p.getInputStream());
    m_stderr = new StreamEater(p.getErrorStream());
    return p.waitFor();
  }
  
  public List<String> getOutput(){
    return m_stdout.getLines();
  }
  
  public List<String> getError(){
    return m_stderr.getLines();
  }
  
  private class StreamEater implements Runnable {
   
    private BufferedReader m_reader;
    private List<String> m_lines;
    private volatile boolean m_done;
    
    public StreamEater(InputStream stream) {
      m_reader = new BufferedReader(new InputStreamReader(stream));
      m_lines = new ArrayList<String>();
      m_done = false;
      Thread thread = new Thread(this);
      thread.setDaemon(true);
      thread.start();
    }
  
    @Override
    public void run(){
      while(true){
        try {
          String line = m_reader.readLine(); 
          if(line == null){
            break;
          }
          m_lines.add(line);
        } catch(Exception ex){
          ex.printStackTrace(System.out);
          System.exit(0);
        }
      }
      m_done = true;
    }
    
    public List<String> getLines(){
      while(m_done == false){
        try {
          Thread.sleep(100);
        } catch(Exception ex){
          //ignore
        }
      }
      return m_lines;
    }
  }
}
