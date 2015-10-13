/*
 * Copyright (c) 2008-2015  Innovent Solutions, Inc.
 * 
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms 
 * of the Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * 	Scott Rosenbaum - Innovent Solutions
 *  Steve Schafer - Innovent Solutions
 * 				 
 */
package blackboard.birt.controls;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class RadioGroup extends Composite {
	private final Button[] buttons;
	public int value;

	public RadioGroup(final Composite parent, final String[] buttonLabels,
			final int initialValue, final Color backgroundColor) {
		super(parent, SWT.NONE);
		this.value = initialValue;
		GridLayout layout = new GridLayout(buttonLabels.length, false);
		this.setLayout(layout);
		final List<Button> list = new ArrayList<Button>();
		for (final String buttonText : buttonLabels) {
			final Button button = new Button(this, SWT.RADIO);
			button.setText(buttonText);
			button.setBackground(backgroundColor);
			button
					.addSelectionListener(new RadioSelectionListener(list
							.size()));
			list.add(button);
		}
		this.buttons = list.toArray(new Button[0]);
	}

	private final class RadioSelectionListener implements SelectionListener {
		public final int index;

		public RadioSelectionListener(int index) {
			this.index = index;
		}

		public void widgetDefaultSelected(SelectionEvent arg0) {
		}

		public void widgetSelected(SelectionEvent arg0) {
			if (RadioGroup.this.value != this.index) {
				RadioGroup.this.value = this.index;
				notifyListeners(SWT.Modify, null);
			}
		}

	}

	public void setValue(int i) {
		buttons[value].setSelection(false);
		value = i;
		buttons[value].setSelection(true);
	}

	public String getButtonName() {
		return buttons[value].getText();
	}
}
