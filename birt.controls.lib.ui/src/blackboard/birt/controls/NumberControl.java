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
package blackboard.birt.controls;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class NumberControl {
	private final Text text;
	private double value = 0;
	private final List<BbModifyListener> modifyListeners = new ArrayList<BbModifyListener>();

	public NumberControl(final Composite parent, final int style,
			final boolean allowNegative) {
		text = new Text(parent, style);
		text.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent event) {
				event.doit = acceptCharacter(event.character);
			}

			private boolean acceptCharacter(char c) {
				final String string = text.getText();
				if (string.length() == 0 && allowNegative && c == '-')
					return true;
				if (string.indexOf('.') < 0 && c == '.')
					return true;
				if (Character.isDigit(c))
					return true;
				if (Character.isISOControl(c))
					return true;
				return false;
			}

			public void keyReleased(KeyEvent event) {
				final String string = text.getText();
				double value;
				try {
					value = Double.parseDouble(string);
				} catch (NumberFormatException e) {
					value = 0;
				}
				if (NumberControl.this.value != value) {
					NumberControl.this.value = value;
					for (BbModifyListener listener : modifyListeners)
						listener.onModified();
				}
			}
		});
	}

	public void setLayoutData(GridData gridData) {
		text.setLayoutData(gridData);
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		if (this.value != value) {
			this.value = value;
			text.setText(String.valueOf(value));
		}
	}

	public void addFocusListener(FocusListener listener) {
		text.addFocusListener(listener);
	}

	public void removeFocusListener(FocusListener listener) {
		text.removeFocusListener(listener);
	}

	public void addModifyListener(BbModifyListener listener) {
		modifyListeners.add(listener);
	}

	public void removeModifyListener(BbModifyListener listener) {
		modifyListeners.remove(listener);
	}
}
