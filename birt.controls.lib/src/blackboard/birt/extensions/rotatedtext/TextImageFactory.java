
package blackboard.birt.extensions.rotatedtext;

import java.awt.Color;

import blackboard.birt.extensions.rotatedtext.SwingGraphicsUtil.LineInfo;

/**
 * Factory for creating an image from the text info & image type.
 */
class TextImageFactory {
  
  static TextImageFactory instance = new TextImageFactory();
  
  private TextImageFactory() {
    
  }
  
  static TextImageFactory getInstance() {
    return instance;
  }
  
  TextImage createTextImage(String type, LineInfo[] lines,
     double angle,  int width,  int height,
     double tx,  double ty, Color color) {
    if ("image/svg+xml".equals(type)) {
      return new TextSVGImage(lines, angle, width, height, tx, ty, color);
    } else {
      return new TextBufferedImage(lines, angle, width, height, tx, ty, color);
    }
  }

}
