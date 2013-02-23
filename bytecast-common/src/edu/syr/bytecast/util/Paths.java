package edu.syr.bytecast.util;

import java.io.File;

public class Paths {
    
    private static Paths m_instance;
    private static final String m_fsysMock1Path = "bytecast-common/bytecast-common/test_input_files/fsys_mock1.eobj";
    private String m_root;
    public static synchronized Paths v(){
        if(m_instance == null){
            m_instance = new Paths();
        }
        return m_instance;
      
    }
    public void setRoot(String root_dir)
    {
        File new_file = new File(root_dir);
        m_root = new_file.getAbsolutePath();
    }
    
    public String getRoot()
    {
        return m_root;
    }
    
    public String getFsysMock1Path()
    {
        return m_root + "/" + m_fsysMock1Path;
    }
    
}
