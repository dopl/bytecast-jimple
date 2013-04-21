/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SampleResources;

import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.output.IExecutableFile;
import edu.syr.bytecast.amd64.api.output.ISection;
import edu.syr.bytecast.amd64.api.output.MemoryInstructionPair;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.util.AMD64MockGenerator;
import edu.syr.bytecast.jimple.api.JavaDataType;
import edu.syr.bytecast.jimple.api.ParameterDataTypeIdentifier;
import edu.syr.bytecast.jimple.api.ParameterInfo;
import edu.syr.bytecast.test.mockups.MockBytecastFsys;
import edu.syr.bytecast.util.Paths;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hapan
 */
public class TestParameterDataTypeIdentifier {

  public static void main(String a[]) throws Exception {
    IExecutableFile ex = null;
    Set<String> exclusion = new HashSet<String>();
    Paths.v().setRoot("/home/hapan/code/bytecast");
    try {
      Paths.v().parsePathsFile();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    exclusion.add("<_IO_printf>");
    AMD64MockGenerator gen =
            new AMD64MockGenerator(new MockBytecastFsys(),
            "/home/hapan/code/bytecast/bytecast-documents/AsciiManip01Prototype/a.out.static.objdump",
            "<main>", exclusion);
    try {
      ex = gen.generate().buildInstructionObjects();

    } catch (FileNotFoundException ex1) {
      Logger.getLogger(AMD64MockGenerator.class.getName()).log(Level.SEVERE, null, ex1);
    } catch (IOException ex1) {
      Logger.getLogger(AMD64MockGenerator.class.getName()).log(Level.SEVERE, null, ex1);
    }

    if (ex == null) {
      return;
    }
    List<ISection> sections = ex.getSectionsWithInstructions();
    List<MemoryInstructionPair> instruction = sections.get(0).getAllInstructionObjects();
    //<main>    start from [0]  to [31]
    //<dostuff> start from [32] to [50]
    //<sum>     start from [51] to [59]
    //<halve>   start from [60] to [69]
    List<MemoryInstructionPair> ins = instruction.subList(32, 50);
    ParameterInfo info = new ParameterInfo();
    info.setParamStackAddress(new OperandMemoryEffectiveAddress(RegisterType.RBP, null, 1, -0x4));
    JavaDataType jdt = ParameterDataTypeIdentifier.getDataType(info, ins);
  }
}
