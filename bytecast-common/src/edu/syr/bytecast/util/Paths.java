package edu.syr.bytecast.util;

import java.io.File;

public class Paths {
    
    private static Paths m_instance;
    private static final String m_FsysMock1Path = "";
    
    public static synchronized Paths v(){
        if(m_instance == null){
            m_instance = new Paths();
        }
        return m_instance;
      
    }
    String m_absolute_root;
    public void setRoot(String root_dir)
    {
        File new_file = new File(root_dir);
        m_absolute_root = new_file.getAbsolutePath();
    }
    
    public String getRoot()
    {
        return m_absolute_root;
    }
    
    public String getFsysMock1Path()
    {
        return m_FsysMock1Path;
    }
    
}
