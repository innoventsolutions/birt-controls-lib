
package blackboard.birt.extensions.rotatedtext;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import blackboard.birt.extensions.rotatedtext.SwingGraphicsUtil.LineInfo;

/**
 * Text drawn on a raster image.
 */
public class TextBufferedImage implements TextImage {
  LineInfo[] lines;
  double angle;
  int width;
  int height;
  double tx;
  double ty;
  Color color;
  
  public TextBufferedImage(final LineInfo[] lines,
    final double angle, final int width, final int height,
    final double tx, final double ty, final Color color) {
    this.lines = lines;
    this.angle = angle;
    this.width = width;
    this.height = height;
    this.tx = tx;
    this.ty = ty;
    this.color = color;
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }
  
  protected void draw(Graphics2D g2d) {
    g2d.setColor(Color.black);
    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    final AffineTransform at = AffineTransform.getRotateInstance(angle);
    at.translate(tx, ty);
    g2d.setTransform(at);
    g2d.setColor(color);

    float y = 0;
    for (final LineInfo lineInfo : lines) {
      if (lineInfo.textLayout != null)
        g2d.setFont(lineInfo.font);
      lineInfo.textLayout.draw(g2d, 0,
        y + (int)Math.ceil(lineInfo.textLayout.getLeading() + lineInfo.textLayout.getAscent()));
      y += lineInfo.bounds.getHeight();
    }
  }

  public InputStream getImage() {
    final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    final Graphics2D g2d = (Graphics2D)bufferedImage.getGraphics();
    draw(g2d);
    g2d.dispose();
    return SwingGraphicsUtil.toStream(bufferedImage);
  }

}
