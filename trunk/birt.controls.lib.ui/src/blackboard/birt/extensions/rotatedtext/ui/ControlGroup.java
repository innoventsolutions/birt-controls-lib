/*
 * (C) Copyright Blackboard Inc. 1998-2009 - All Rights Reserved
 * 
 * Permission to use, copy, modify, and distribute this software without prior explicit written approval is 
 * strictly  prohibited. Please refer to the file "copyright.html" for further important copyright and licensing information.
 * 
 * BLACKBOARD MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, 
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. 
 * BLACKBOARD SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, 
 * MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 * 
 * authors: Scott Rosenbaum / Steve Schafer, Innovent Solutions, Inc.
 */
package blackboard.birt.extensions.rotatedtext.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.birt.report.designer.ui.views.attributes.providers.ChoiceSetFactory;
import org.eclipse.birt.report.designer.util.AlphabeticallyComparator;
import org.eclipse.birt.report.designer.util.DEUtil;
import org.eclipse.birt.report.model.api.StyleHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.elements.ReportDesignConstants;
import org.eclipse.birt.report.model.api.metadata.DimensionValue;
import org.eclipse.birt.report.model.api.metadata.IChoice;
import org.eclipse.birt.report.model.api.metadata.IChoiceSet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import blackboard.birt.controls.ColorControl;
import blackboard.birt.controls.ControlEventHandler;
import blackboard.birt.controls.DimensionControl;
import blackboard.birt.controls.BbModifyListener;
import blackboard.birt.controls.ExpressionControl;
import blackboard.birt.controls.FontSizeControl;
import blackboard.birt.controls.FontStyleControl;
import blackboard.birt.controls.IntegerControl;
import blackboard.birt.extensions.rotatedtext.RotatedTextData;
import blackboard.birt.extensions.rotatedtext.RotatedTextItem;
import blackboard.birt.extensions.util.ColorSpec;

public class ControlGroup
{
  private ExpressionControl valueExpression;
  private IntegerControl angle;
  private DimensionControl wrapPoint;
  private Combo fontFamily;
  private FontSizeControl fontSize;
  private FontStyleControl fontStyle;
  private ColorControl fontColor;
  private ExpressionControl linkUrlExpression;

  public ControlGroup()
  {
  }

  public void save( RotatedTextItem item ) throws SemanticException
  {
    item.setText( getValueExpression() );
    item.setRotationAngle( getAngle() );
    item.setWrapPoint( getWrapPoint() );
    item.setFontFamily( getFontFamily() );
    item.setFontSize( getFontSize() );
    item.setFontItalic( isFontItalic() );
    item.setFontBold( isFontBold() );
    item.setFontColor( getFontColor() );
    item.setLinkURL( getLinkURL() );
  }

  public void load( RotatedTextData data )
  {
    this.valueExpression.setText( data.text == null ? "" : data.text );
    this.angle.setValue( data.angle );
    this.wrapPoint.setValue( data.wrapPoint );
    this.fontFamily.setText( getFontDisplayName( data.fontFamily ) );
    this.fontSize.setFontSizeValue( data.fontSize );
    this.fontStyle.setItalic( data.fontItalic );
    this.fontStyle.setBold( data.fontBold );
    RGB fontRGB = new RGB( data.fontColor.r, data.fontColor.g, data.fontColor.b );
    this.fontColor.setRGB( fontRGB );
    this.linkUrlExpression.setText( data.linkURL == null ? "" : data.linkURL );
  }

  public GridLayout getGridLayout()
  {
    return new GridLayout( 4, false );
  }

  public void build( final Composite parent, final ControlEventHandler handler, final Color backgroundColor )
  {
    parent.setLayoutData( new GridData( GridData.FILL_BOTH ) );
    createLabel( parent, "Expression:", backgroundColor );
    valueExpression = new ExpressionControl( parent, SWT.None, handler );
    {
      createLabel( parent, "Angle:", backgroundColor );
      final IntegerControl integerControl = new IntegerControl( parent, SWT.None, false );
      {
        final GridData gridData = new GridData();
        gridData.widthHint = 32;
        gridData.heightHint = 17;
        integerControl.setLayoutData( gridData );
      }
      this.angle = integerControl;
    }
    this.fontFamily = createFontFamilyControl( parent, "Font:", backgroundColor );
    {
      GridData gridData = new GridData();
      this.fontFamily.setLayoutData( gridData );
    }
    {
      createLabel( parent, "Wrap Point:", backgroundColor );
      final DimensionControl builder = new DimensionControl( parent, SWT.None, RotatedTextItem.EXTENSION_NAME, RotatedTextItem.WRAP_POINT_PROP );
      {
        final GridData gridData = new GridData();
        builder.setLayoutData( gridData );
      }
      this.wrapPoint = builder;
    }
    this.fontSize = createFontSizeBuilder( parent, "Size:", backgroundColor );
    this.fontStyle = createFontStyleBuilder( parent, "Style:", backgroundColor );
    this.fontColor = createColor( parent, "Color:", backgroundColor );
    {
      GridData gridData = new GridData();
      this.fontColor.setLayoutData( gridData );
    }
    createLabel( parent, "Link URL:", backgroundColor );
    linkUrlExpression = new ExpressionControl( parent, SWT.None, handler );
    if ( handler != null )
    {
      this.valueExpression.addFocusListener( new FocusAdapter()
        {
          public void focusLost( org.eclipse.swt.events.FocusEvent e )
          {
            handler.updateModel( RotatedTextItem.TEXT_PROP );
          };
        } );
      valueExpression.addListener( SWT.Modify, new Listener()
        {
          public void handleEvent( Event arg0 )
          {
            handler.updateModel( RotatedTextItem.TEXT_PROP );
          }
        } );
      addModifyListener( this.angle, handler, RotatedTextItem.ROTATION_ANGLE_PROP );
      addFocusListener( this.angle, handler, RotatedTextItem.ROTATION_ANGLE_PROP );
      fontColor.addListener( SWT.Modify, new Listener()
        {
          public void handleEvent( Event arg0 )
          {
            handler.updateModel( RotatedTextItem.FONT_COLOR_PROP );
          }
        } );
      addFocusListener( this.fontFamily, handler, RotatedTextItem.FONT_FAMILY_PROP );
      addSelectionListener( this.fontFamily, handler, RotatedTextItem.FONT_FAMILY_PROP );
      fontSize.addListener( SWT.Modify, new Listener()
        {
          public void handleEvent( Event event )
          {
            handler.updateModel( RotatedTextItem.FONT_SIZE_PROP );
          }
        } );
      // addSelectionListener( this.fontSizeNumber, handler, RotatedTextItem.FONT_SIZE_PROP );
      this.fontStyle.addModifyListener( new BbModifyListener()
        {
          public void onModified()
          {
            handler.updateModel( RotatedTextItem.FONT_STYLE_PROP );
            handler.updateModel( RotatedTextItem.FONT_WEIGHT_PROP );
          }
        } );
      this.wrapPoint.addFocusListener( new FocusAdapter()
        {
          public void focusLost( org.eclipse.swt.events.FocusEvent e )
          {
            handler.updateModel( RotatedTextItem.WRAP_POINT_PROP );
          };
        } );
      wrapPoint.addListener( SWT.Modify, new Listener()
        {
          public void handleEvent( Event arg0 )
          {
            handler.updateModel( RotatedTextItem.WRAP_POINT_PROP );
          }
        } );
      this.linkUrlExpression.addFocusListener( new FocusAdapter()
        {
          public void focusLost( org.eclipse.swt.events.FocusEvent e )
          {
            handler.updateModel( RotatedTextItem.LINK_URL_PROP );
          };
        } );
      linkUrlExpression.addListener( SWT.Modify, new Listener()
        {
          public void handleEvent( Event arg0 )
          {
            handler.updateModel( RotatedTextItem.LINK_URL_PROP );
          }
        } );
    }
  }

  private final Map<String, String> fontDisplayNameMap = new HashMap<String, String>();
  private final Map<String, String> fontNameMap = new HashMap<String, String>();

  private Combo createFontFamilyControl( Composite parent, String string, Color backgroundColor )
  {
    createLabel( parent, string, backgroundColor );
    final Combo combo = new Combo( parent, SWT.DROP_DOWN | SWT.READ_ONLY );
    final IChoiceSet choiceSet = ChoiceSetFactory.getElementChoiceSet( ReportDesignConstants.STYLE_ELEMENT, StyleHandle.FONT_FAMILY_PROP );
    final List<IChoice> choiceList = new ArrayList<IChoice>();
    for ( IChoice choice : choiceSet.getChoices() )
    {
      String displayName = choice.getDisplayName();
      final Object choiceValue = choice.getValue();
      if ( choiceValue instanceof String )
      {
        final String fontName = (String) choiceValue;
        fontDisplayNameMap.put( displayName, fontName );
        fontNameMap.put( fontName, displayName );
      }
      else
      {
        fontDisplayNameMap.put( displayName, displayName );
        fontNameMap.put( displayName, displayName );
      }
      choiceList.add( choice );
    }
    final List<String> list = new ArrayList<String>();
    list.add( "Auto" );
    Collections.sort( choiceList, new AlphabeticallyComparator() );
    for ( IChoice choice : choiceList )
      list.add( choice.getDisplayName() );
    final String[] sysFont = DEUtil.getSystemFontNames();
    for ( String item : sysFont )
    {
      String name = DEUtil.removeQuote( item );
      list.add( name );
      fontDisplayNameMap.put( name, name );
      fontNameMap.put( name, name );
    }
    combo.setItems( list.toArray( new String[ 0 ] ) );
    combo.setText( combo.getItem( 0 ) );
    return combo;
  }

  private String getFontName( String fontDisplayName )
  {
    String fontName = fontDisplayNameMap.get( fontDisplayName );
    return fontName;
  }

  private String getFontDisplayName( String fontName )
  {
    String unquotedFontName = DEUtil.removeQuote( fontName );
    String displayName = fontNameMap.get( unquotedFontName );
    return displayName;
  }

  private FontStyleControl createFontStyleBuilder( Composite parent, String string, Color backgroundColor )
  {
    createLabel( parent, string, backgroundColor );
    FontStyleControl builder = new FontStyleControl( parent, SWT.NONE );
    {
      final GridData gridData = new GridData();
      builder.setLayoutData( gridData );
    }
    return builder;
  }

  private FontSizeControl createFontSizeBuilder( Composite parent, String string, Color backgroundColor )
  {
    createLabel( parent, string, backgroundColor );
    FontSizeControl builder = new FontSizeControl( parent, SWT.NONE );
    {
      GridData gridData = new GridData();
      gridData.widthHint = 200;
      builder.setLayoutData( gridData );
    }
    return builder;
  }

  private void addModifyListener( final IntegerControl control, final ControlEventHandler handler, final String prop )
  {
    control.addModifyListener( new BbModifyListener()
      {
        public void onModified()
        {
          handler.updateModel( prop );
        }
      } );
  }

  private void addFocusListener( final IntegerControl control, final ControlEventHandler handler, final String prop )
  {
    control.addFocusListener( new FocusAdapter()
      {
        public void focusLost( org.eclipse.swt.events.FocusEvent e )
        {
          handler.updateModel( prop );
        };
      } );
  }

  private void addFocusListener( final Control control, final ControlEventHandler handler, final String prop )
  {
    control.addFocusListener( new FocusAdapter()
      {
        public void focusLost( org.eclipse.swt.events.FocusEvent e )
        {
          handler.updateModel( prop );
        };
      } );
  }

  private void addSelectionListener( final Combo combo, final ControlEventHandler handler, final String prop )
  {
    combo.addSelectionListener( new SelectionListener()
      {
        public void widgetDefaultSelected( SelectionEvent arg0 )
        {
        }

        public void widgetSelected( SelectionEvent arg0 )
        {
          handler.updateModel( prop );
        }
      } );
  }

  private Label createLabel( final Composite parent, final String labelText, final Color backgroundColor )
  {
    final Label label = new Label( parent, SWT.None );
    label.setText( labelText );
    label.setBackground( backgroundColor );
    return label;
  }

  private ColorControl createColor( final Composite parent, final String labelText, final Color backgroundColor )
  {
    createLabel( parent, labelText, backgroundColor );
    final ColorControl colorBuilder = new ColorControl( parent, SWT.None );
    {
      final GridData gridData = new GridData();
      colorBuilder.setLayoutData( gridData );
    }
    {
      final IChoiceSet choiceSet = ChoiceSetFactory.getElementChoiceSet( ReportDesignConstants.STYLE_ELEMENT, StyleHandle.COLOR_PROP );
      colorBuilder.setChoiceSet( choiceSet );
    }
    return colorBuilder;
  }

  private interface Updater
  {
    void update( RotatedTextItem textItem, ControlGroup controlGroup ) throws SemanticException;
  }

  private static final Map<String, Updater> UPDATERS;
  static
  {
    UPDATERS = new HashMap<String, Updater>();
    UPDATERS.put( RotatedTextItem.TEXT_PROP, new Updater()
      {
        public void update( final RotatedTextItem textItem, final ControlGroup controlGroup ) throws SemanticException
        {
          textItem.setText( controlGroup.getValueExpression() );
        }
      } );
    UPDATERS.put( RotatedTextItem.ROTATION_ANGLE_PROP, new Updater()
      {
        public void update( final RotatedTextItem textItem, final ControlGroup controlGroup ) throws SemanticException
        {
          textItem.setRotationAngle( controlGroup.getAngle() );
        }
      } );
    UPDATERS.put( RotatedTextItem.WRAP_POINT_PROP, new Updater()
      {
        public void update( final RotatedTextItem textItem, final ControlGroup controlGroup ) throws SemanticException
        {
          textItem.setWrapPoint( controlGroup.getWrapPoint() );
        }
      } );
    UPDATERS.put( RotatedTextItem.FONT_FAMILY_PROP, new Updater()
      {
        public void update( final RotatedTextItem textItem, final ControlGroup controlGroup ) throws SemanticException
        {
          textItem.setFontFamily( controlGroup.getFontFamily() );
        }
      } );
    UPDATERS.put( RotatedTextItem.FONT_SIZE_PROP, new Updater()
      {
        public void update( final RotatedTextItem textItem, final ControlGroup controlGroup ) throws SemanticException
        {
          textItem.setFontSize( controlGroup.getFontSize() );
        }
      } );
    UPDATERS.put( RotatedTextItem.FONT_STYLE_PROP, new Updater()
      {
        public void update( final RotatedTextItem textItem, final ControlGroup controlGroup ) throws SemanticException
        {
          textItem.setFontItalic( controlGroup.isFontItalic() );
        }
      } );
    UPDATERS.put( RotatedTextItem.FONT_WEIGHT_PROP, new Updater()
      {
        public void update( final RotatedTextItem textItem, final ControlGroup controlGroup ) throws SemanticException
        {
          textItem.setFontBold( controlGroup.isFontBold() );
        }
      } );
    UPDATERS.put( RotatedTextItem.FONT_COLOR_PROP, new Updater()
      {
        public void update( final RotatedTextItem textItem, final ControlGroup controlGroup ) throws SemanticException
        {
          textItem.setFontColor( controlGroup.getFontColor() );
        }
      } );
    UPDATERS.put( RotatedTextItem.LINK_URL_PROP, new Updater()
      {
        public void update( final RotatedTextItem textItem, final ControlGroup controlGroup ) throws SemanticException
        {
          textItem.setLinkURL( controlGroup.getLinkURL() );
        }
      } );
  }

  public void updateModel( final RotatedTextItem textItem, final String propName ) throws SemanticException
  {
    final Updater updater = UPDATERS.get( propName );
    if ( updater != null )
      updater.update( textItem, this );
    else
      System.out.println( "No updater found for " + propName );
  }

  public String getValueExpression()
  {
    return valueExpression.getText();
  }

  public int getAngle()
  {
    return angle.getValue();
  }

  public DimensionValue getWrapPoint()
  {
    return wrapPoint.getValue();
  }

  public String getFontFamily()
  {
    return getFontName( this.fontFamily.getText() );
  }

  public String getFontSize()
  {
    return fontSize.getFontSizeValue();
  }

  public boolean isFontItalic()
  {
    return fontStyle.isItalic();
  }

  public boolean isFontBold()
  {
    return fontStyle.isBold();
  }

  public ColorSpec getFontColor()
  {
    RGB fontRGB = this.fontColor.getRGB();
    return new ColorSpec( fontRGB.red, fontRGB.green, fontRGB.blue );
  }

  public String getLinkURL()
  {
    final String text = linkUrlExpression.getText();
    return text == null ? null : text.length() == 0 ? null : text;
  }
}
