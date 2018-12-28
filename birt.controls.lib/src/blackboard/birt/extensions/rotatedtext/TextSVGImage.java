
package blackboard.birt.extensions.rotatedtext;

import java.awt.Color;
import java.awt.Dimension;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import blackboard.birt.extensions.rotatedtext.SwingGraphicsUtil.LineInfo;

/**
 * Text drawn on a SVG image.
 */
public class TextSVGImage extends TextBufferedImage {

  public TextSVGImage(LineInfo[] theLines, double theAngle, int theWidth, int theHeight, double theTx, double theTy,
    Color theColor) {
    super(theLines, theAngle, theWidth, theHeight, theTx, theTy, theColor);
  }
  
  @Override
  public InputStream getImage() {
    try {
      DOMImplementation domImpl =
        GenericDOMImplementation.getDOMImplementation();
      
      // Create an instance of org.w3c.dom.Document.
      String svgNS = "http://www.w3.org/2000/svg";
      Document document = domImpl.createDocument(svgNS, "svg", null);

      // Create an instance of the SVG Generator.
      SVGGraphics2D g2d = new SVGGraphics2D(document);
      g2d.setSVGCanvasSize(new Dimension(width, height));
      draw(g2d);
      
      // Finally, stream out SVG to the standard output using
      // UTF-8 encoding.
      boolean useCSS = true; // we want to use CSS style attributes
      ByteArrayOutputStream bo = new ByteArrayOutputStream();
      Writer out = new OutputStreamWriter(bo, "UTF-8");
      g2d.stream(out, useCSS);
      return new ByteArrayInputStream(bo.toByteArray());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

}
