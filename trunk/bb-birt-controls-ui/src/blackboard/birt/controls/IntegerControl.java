/*******************************************************************************
 * Copyright (c) 2008 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *  Blackboard Learning Systems - significant modification / refactor
 *  Steve Schafer - Innovent Solutions, Inc. - significant modification / refactor
 *  
 *******************************************************************************/
package blackboard.birt.controls;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;


public final class IntegerControl
{
  private final Text text;
  private int value = 0;
  private final List<BbModifyListener> modifyListeners = new ArrayList<BbModifyListener>();

  public IntegerControl( final Composite parent, final int style, final boolean allowNegative )
  {
    text = new Text( parent, style );
    text.addKeyListener( new KeyListener()
      {
        public void keyPressed( KeyEvent event )
        {
          event.doit = acceptCharacter( event.character );
        }

        private boolean acceptCharacter( char c )
        {
          final String string = text.getText();
          if ( string.length() == 0 && allowNegative && c == '-' )
            return true;
          if ( Character.isDigit( c ) )
            return true;
          if ( Character.isISOControl( c ) )
            return true;
          return false;
        }

        public void keyReleased( KeyEvent event )
        {
          final String string = text.getText();
          int value;
          try
          {
            value = Integer.parseInt( string );
          }
          catch ( NumberFormatException e )
          {
            value = 0;
          }
          if ( IntegerControl.this.value != value )
          {
            IntegerControl.this.value = value;
            for ( BbModifyListener listener : modifyListeners )
            {
              listener.onModified();
            }
          }
        }
      } );
  }

  public void setLayoutData( GridData gridData )
  {
    text.setLayoutData( gridData );
  }

  public int getValue()
  {
    return this.value;
  }

  public void setValue( int value )
  {
    if ( this.value != value )
    {
      this.value = value;
      text.setText( String.valueOf( value ) );
    }
  }

  public void addFocusListener( FocusListener listener )
  {
    text.addFocusListener( listener );
  }

  public void removeFocusListener( FocusListener listener )
  {
    text.removeFocusListener( listener );
  }

  public void addModifyListener( BbModifyListener listener )
  {
    modifyListeners.add( listener );
  }

  public void removeModifyListener( BbModifyListener listener )
  {
    modifyListeners.remove( listener );
  }
}