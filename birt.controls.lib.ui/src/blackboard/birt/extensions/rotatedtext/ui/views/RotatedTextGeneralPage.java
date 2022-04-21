/*******************************************************************************
 * Copyright (c) 2008-2015  Innovent Solutions, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://eclipse.org/org/documents/epl-2.0/EPL-2.0.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *  Blackboard, Inc. - significant modification / refactor
 *  Steve Schafer - Innovent Solutions, Inc. - significant modification / refactor
 *  
 *******************************************************************************/
package blackboard.birt.extensions.rotatedtext.ui.views;

import java.util.List;

import org.eclipse.birt.report.designer.ui.views.attributes.AttributesUtil;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.extension.ExtendedElementException;
import org.eclipse.birt.report.model.api.extension.IReportItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;

import blackboard.birt.controls.ControlEventHandler;
import blackboard.birt.extensions.rotatedtext.RotatedTextData;
import blackboard.birt.extensions.rotatedtext.RotatedTextItem;
import blackboard.birt.extensions.rotatedtext.ui.ControlGroup;

/**
 * RotatedTextGeneralPage
 */
public class RotatedTextGeneralPage extends AttributesUtil.PageWrapper {
	protected FormToolkit toolkit;
	protected RotatedTextItem rotatedTextItem;
	protected Composite contentpane;
	private final ControlGroup controlGroup = new ControlGroup();

	@Override
	public void buildUI(final Composite parent) {
		if (toolkit == null) {
			toolkit = new FormToolkit(Display.getCurrent());
			toolkit.setBorderStyle(SWT.NULL);
		}

		final Control[] children = parent.getChildren();
		if (children != null && children.length > 0) {
			contentpane = (Composite) children[children.length - 1];
			{
				final GridLayout gridLayout = controlGroup.getGridLayout();
				gridLayout.marginLeft = 8;
				gridLayout.verticalSpacing = 4;
				contentpane.setLayout(gridLayout);
			}
			final Color backgroundColor = new Color(contentpane.getDisplay(),
					0xFF, 0xFF, 0xFF);
			controlGroup.build(contentpane, new ControlEventHandler() {
				public void updateModel(final String propName) {
					try {
						RotatedTextGeneralPage.this.updateModel(propName);
					} catch (final SemanticException e) {
						e.printStackTrace();
					}
				}

				public ExtendedItemHandle getModelHandle() {
					return rotatedTextItem.getModelHandle();
				}
			}, backgroundColor);
		}
	}

	@Override
	public void setInput(final Object input) {
		final RotatedTextItem item = getItemFromInput(input);
		if (item != null)
			rotatedTextItem = item;
	}

	@SuppressWarnings("unchecked")
	public static RotatedTextItem getItemFromInput(final Object input) {
		if (input instanceof List) {
			final List list = (List) input;
			for (final Object object : list) {
				if (object instanceof ExtendedItemHandle) {
					final ExtendedItemHandle extendedItemHandle = (ExtendedItemHandle) object;
					try {
						final IReportItem reportItem = extendedItemHandle
								.getReportItem();
						if (reportItem instanceof RotatedTextItem) {
							return (RotatedTextItem) reportItem;
						}
					} catch (final ExtendedElementException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	@Override
	public void dispose() {
		if (toolkit != null) {
			toolkit.dispose();
		}
	}

	private void adaptFormStyle(final Composite comp) {
		final Control[] children = comp.getChildren();
		for (final Control element : children) {
			if (element instanceof Composite) {
				adaptFormStyle((Composite) element);
			}
		}

		toolkit.paintBordersFor(comp);
		toolkit.adapt(comp);
	}

	@Override
	public void refresh() {
		if (contentpane != null && !contentpane.isDisposed()) {
			if (toolkit == null) {
				toolkit = new FormToolkit(Display.getCurrent());
				toolkit.setBorderStyle(SWT.NULL);
			}

			adaptFormStyle(contentpane);

			updateUI();
		}
	}

	@Override
	public void postElementEvent() {
		if (contentpane != null && !contentpane.isDisposed()) {
			updateUI();
		}
	}

	private void updateModel(final String propName) throws SemanticException {
		if (rotatedTextItem != null)
			controlGroup.updateModel(rotatedTextItem, propName);
	}

	protected void updateUI() {
		if (rotatedTextItem != null) {
			final RotatedTextData data = new RotatedTextData(rotatedTextItem);
			controlGroup.load(data);
		}
	}
}
