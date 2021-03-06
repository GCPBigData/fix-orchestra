package io.fixprotocol.orchestra.transformers;

import java.io.File;
import java.io.InputStream;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import net.sf.saxon.TransformerFactoryImpl;

public class Unified2OrchestraTransformer{


  /**
   * 
   * @param args command line arguments
   * <ol>
   * <li>Name of Unified Repository file to transform</li>
   * <li>Name of Repository phrases file<li>
   * <li>Name of Orchestra file to create</li>
   * </ol>
   * @throws TransformerException  If an unrecoverable error occurs during the course of the transformation.
   */
  public static void main(String[] args) throws TransformerException {
    if (args.length < 3) {
      System.out.println(
          "Usage: Unified2OrchestraTransformer <input_xml_file_path> <phrases_file_path> <output_file_path>");
      return;
  }
    Unified2OrchestraTransformer transformer = new Unified2OrchestraTransformer();
    transformer.transform(args);
  }

  private void transform(String[] args) throws TransformerException {
    File inputXml = new File(args[0]);
    File phrasesFile = new File(args[1]);
    File outputXml = new File(args[2]);
    transform(inputXml, phrasesFile, outputXml);
  }


  public void transform(File inputXml, File phrasesFile, File outputXml)
      throws TransformerException {
    final File parentFile = outputXml.getParentFile();
    if (parentFile != null) {
      parentFile.mkdirs();
    }

    Source xmlSource = new javax.xml.transform.stream.StreamSource(inputXml);
    InputStream xsltFile = getClass().getClassLoader().getResourceAsStream("xsl/unified2orchestra.xslt");
    Source xsltSource = new javax.xml.transform.stream.StreamSource(xsltFile );
    Result result = new javax.xml.transform.stream.StreamResult(outputXml);

    TransformerFactory transFact = new TransformerFactoryImpl();
    Transformer trans = transFact.newTransformer(xsltSource);
    trans.setParameter("phrases-file", phrasesFile.toURI().toString());
    trans.transform(xmlSource, result);
  }

}
