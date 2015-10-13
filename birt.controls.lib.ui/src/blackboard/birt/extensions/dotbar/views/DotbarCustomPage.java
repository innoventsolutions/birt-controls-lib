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
package blackboard.birt.extensions.dotbar.views;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

import blackboard.birt.extensions.dotbar.DotbarItem;

public class DotbarCustomPage extends DotbarGeneralPage {
	private Map<String, Label> map = new HashMap<String, Label>();

	public void buildUI(Composite parent) {
		if (toolkit == null) {
			toolkit = new FormToolkit(Display.getCurrent());
			toolkit.setBorderStyle(SWT.NULL);
		}

		Control[] children = parent.getChildren();

		if (children != null && children.length > 0) {
			contentpane = (Composite) children[children.length - 1];
			GridLayout layout = new GridLayout(2, false);
			layout.marginTop = 8;
			layout.marginLeft = 8;
			layout.verticalSpacing = 12;
			contentpane.setLayout(layout);

			createLabel("Value Expression:", DotbarItem.VALUE_EXPRESSION_PROP);
			createLabel("Display Value:", DotbarItem.DISPLAY_VALUE_PROP);
			createLabel("Dot Width:", DotbarItem.DOT_WIDTH_PROP);
			createLabel("Dot Height:", DotbarItem.DOT_HEIGHT_PROP);
			createLabel("Dot Spacing:", DotbarItem.DOT_SPACING_PROP);
			createLabel("Vertical:", DotbarItem.VERTICAL_PROP);
			createLabel("Has Border:", DotbarItem.HAS_BORDER_PROP);
			createLabel("Border Color:", DotbarItem.BORDER_COLOR_PROP);
			createLabel("Has Fill:", DotbarItem.HAS_FILL_PROP);
			createLabel("Fill Color:", DotbarItem.FILL_COLOR_PROP);
			createLabel("Dot Shape:", DotbarItem.DOT_SHAPE_PROP);
			createLabel("Number Position:", DotbarItem.NUMBER_POSITION_PROP);
			createLabel("Number Width:", DotbarItem.NUMBER_WIDTH_PROP);
			createLabel("Number Height:", DotbarItem.NUMBER_HEIGHT_PROP);
			createLabel("Font Color:", DotbarItem.FONT_COLOR_PROP);
		}
	}

	private void createLabel(String string, String propName) {
		toolkit.createLabel(contentpane, "Text Content:"); //$NON-NLS-1$
		Label label = toolkit.createLabel(contentpane, ""); //$NON-NLS-1$
		GridData gd = new GridData();
		gd.widthHint = 200;
		label.setLayoutData(gd);
		map.put(propName, label);
	}

	protected void updateUI() {
		if (dotbarItem != null) {
			for (String propName : map.keySet()) {
				Label label = map.get(propName);
				String string = dotbarItem.getString(propName);
				label.setText(string == null ? "" : string);
			}
		}
	}
}
