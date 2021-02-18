
package blackboard.birt.extensions.rotatedtext;

import java.io.InputStream;

/**
 * Text as an image.
 */
public interface TextImage {
  
  int getWidth();
  
  int getHeight();
  
  InputStream getImage();

}
