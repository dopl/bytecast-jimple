///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package edu.syr.bytecast.jimple.beans.jimpleBean;
//
//import edu.syr.bytecast.amd64.api.output.IExecutableFile;
//import edu.syr.bytecast.amd64.api.output.ISection;
//import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
//import edu.syr.bytecast.amd64.util.AMD64MockGenerator;
//import edu.syr.bytecast.test.mockups.MockBytecastFsys;
//import edu.syr.bytecast.util.Paths;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.*;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import edu.syr.bytecast.runtime.PrintfFilterByBlock;
///**
// *
// * @author invictus
// */
//public class TestRuntimeJimple {
//    
//    public static void main(String[] args) throws Exception {
//     
//         Set<String> exclusion = new HashSet<String>();
//         Paths.v().setRoot("/home/invictus/code/bytecast");                  
//        try {
//            Paths.v().parsePathsFile();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        exclusion.add("<_IO_printf>");
//        AMD64MockGenerator gen = 
//                new AMD64MockGenerator(new MockBytecastFsys(),
//                "/home/invictus/code/bytecast/bytecast-documents/AsciiManip01Prototype/a.out.static.objdump",
//                "<main>",exclusion);
//        try {
//            IExecutableFile ex = gen.generate().buildInstructionObjects();
//            System.out.print("");
//            List<ISection> ls=ex.getSectionsWithInstructions();
//            System.out.print("");
//            List<MemoryInstructionPair> mem_ls=new ArrayList<MemoryInstructionPair>();
//            ArrayList<ArrayList<MemoryInstructionPair>> mem=new ArrayList<ArrayList<MemoryInstructionPair>>();
//           
//               mem_ls=ls.get(0).getAllInstructionObjects();
//               System.out.print("");
//               PrintfFilterByBlock pf=new PrintfFilterByBlock();
//               ArrayList<MemoryInstructionPair> printf_list=pf.filter(mem_ls, 0);
//               System.out.print("");
//               RuntimeJimple jr=new RuntimeJimple();
//            
//        } catch (FileNotFoundException ex1) {
//            Logger.getLogger(AMD64MockGenerator.class.getName()).log(Level.SEVERE, null, ex1);
//        } catch (IOException ex1) {
//            Logger.getLogger(AMD64MockGenerator.class.getName()).log(Level.SEVERE, null, ex1);
//        }
//       
//    }
//}
