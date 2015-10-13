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

import org.eclipse.birt.report.designer.ui.ReportPlatformUIImages;
import org.eclipse.birt.report.designer.ui.views.attributes.providers.AttributeConstant;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class FontStyleControl {
	private final Composite composite;
	private final Button boldButton;
	private final Button italicButton;
	private final List<BbModifyListener> listeners = new ArrayList<BbModifyListener>();

	public FontStyleControl(final Composite parent, final int style) {
		this.composite = new Composite(parent, style);
		{
			final GridLayout gridLayout = new GridLayout(2, false);
			composite.setLayout(gridLayout);
		}
		this.boldButton = createBoldButton();
		this.italicButton = createItalicButton();
	}

	private Button createBoldButton() {
		return createButton(AttributeConstant.FONT_WIDTH);
	}

	private Button createItalicButton() {
		return createButton(AttributeConstant.FONT_STYLE);
	}

	private Button createButton(String name) {
		Button button = new Button(composite, SWT.TOGGLE);
		button.setImage(ReportPlatformUIImages.getImage(name));
		button.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(final SelectionEvent event) {
			}

			public void widgetSelected(final SelectionEvent event) {
				for (BbModifyListener listener : listeners)
					listener.onModified();
			}
		});
		return button;
	}

	public void addModifyListener(final BbModifyListener listener) {
		this.listeners.add(listener);
	}

	public void removeModifyListener(final BbModifyListener listener) {
		this.listeners.remove(listener);
	}

	public boolean isBold() {
		return boldButton.getSelection();
	}

	public void setBold(boolean bold) {
		boldButton.setSelection(bold);
	}

	public boolean isItalic() {
		return italicButton.getSelection();
	}

	public void setItalic(boolean italic) {
		italicButton.setSelection(italic);
	}

	public String getStyle() {
		return isItalic() ? "italic" : "normal";
	}

	public String getWeight() {
		return isBold() ? "bold" : "normal";
	}

	public void setStyle(final String fontStyle) {
		italicButton.setSelection("italic".equalsIgnoreCase(fontStyle));
	}

	public void setWeight(final String fontWeight) {
		boldButton.setSelection("bold".equalsIgnoreCase(fontWeight));
	}

	public void setLayoutData(GridData gridData) {
		composite.setLayoutData(gridData);
	}

}
