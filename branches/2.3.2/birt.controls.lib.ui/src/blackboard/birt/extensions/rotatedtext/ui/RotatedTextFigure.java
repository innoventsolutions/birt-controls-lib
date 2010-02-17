/*******************************************************************************
 * Copyright (c) 2008 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *  Blackboard, Inc. - significant modification / refactor
 *  Steve Schafer - Innovent Solutions, Inc. - significant modification / refactor
 *  
 *******************************************************************************/
package blackboard.birt.extensions.rotatedtext.ui;

import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

import blackboard.birt.extensions.rotatedtext.RotatedTextData;
import blackboard.birt.extensions.rotatedtext.RotatedTextItem;

/**
 * RotatedTextFigure
 */
public class RotatedTextFigure extends Figure
{
  private RotatedTextData lastData;
  private Image cachedImage;
  private RotatedTextItem textItem;

  RotatedTextFigure( RotatedTextItem textItem )
  {
    super();
    this.textItem = textItem;

    addMouseListener( new MouseListener.Stub()
      {
        public void mousePressed( MouseEvent me )
        {
          if ( me.button == 2 )
          {
            try
            {
              RotatedTextFigure.this.textItem.setRotationAngle( normalize( RotatedTextFigure.this.textItem.getRotationAngle() + 45 ) );
            }
            catch ( SemanticException e )
            {
              e.printStackTrace();
            }
          }
        }
      } );
  }

  private int normalize( int angle )
  {
    angle = angle % 360;

    if ( angle < 0 )
      angle += 360;

    return angle;
  }

  public Dimension getMinimumSize( int hint, int hint2 )
  {
    return getPreferredSize( hint, hint2 );
  }

  public Dimension getPreferredSize( int hint, int hint2 )
  {
    Display display = Display.getCurrent();
    GC gc = null;

    try
    {
      final String text = truncateText( textItem.getText() );
      final int angle = textItem.getRotationAngle();
      gc = new GC( display );
      final Font font = RotatedTextSwtUtil.getSwtFont( display, new RotatedTextData( textItem ) );
      gc.setFont( font );
      final Point pt = gc.textExtent( text );
      double[] info = RotatedTextSwtUtil.computedRotatedInfo( pt.x, pt.y, angle );

      if ( getBorder() != null )
      {
        Insets bdInsets = getBorder().getInsets( this );
        return new Dimension( (int) info[ 0 ] + bdInsets.getWidth(), (int) info[ 1 ] + bdInsets.getHeight() );
      }
      return new Dimension( (int) info[ 0 ], (int) info[ 1 ] );
    }
    finally
    {
      if ( gc != null && !gc.isDisposed() )
        gc.dispose();
    }
  }

  private String truncateText( String text )
  {
    if ( text == null )
      return "";
    if ( text.length() > 28 )
      return text.substring( 0, 25 ) + "...";
    return text;
  }

  protected void paintClientArea( Graphics graphics )
  {
    final Rectangle r = getClientArea().getCopy();
    RotatedTextData data = new RotatedTextData( textItem );

    if ( !data.equals( lastData ) || cachedImage == null || cachedImage.isDisposed() )
    {
      lastData = data;

      if ( cachedImage != null && !cachedImage.isDisposed() )
        cachedImage.dispose();

      cachedImage = RotatedTextSwtUtil.createRotatedTextImage( data );
    }

    if ( cachedImage != null && !cachedImage.isDisposed() )
      graphics.drawImage( cachedImage, r.x, r.y );
  }

  void setRotatedTextItem( RotatedTextItem item )
  {
    this.textItem = item;
  }

  void dispose()
  {
    if ( cachedImage != null && !cachedImage.isDisposed() )
    {
      cachedImage.dispose();
    }
  }
}
