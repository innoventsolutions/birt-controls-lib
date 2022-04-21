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
package blackboard.birt.extensions.dotbar.views;

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
import blackboard.birt.extensions.dotbar.DotbarData;
import blackboard.birt.extensions.dotbar.DotbarItem;
import blackboard.birt.extensions.dotbar.ui.ControlGroup;

public class DotbarGeneralPage extends AttributesUtil.PageWrapper {
	protected FormToolkit toolkit;
	protected DotbarItem dotbarItem;
	protected Composite contentpane;
	private ControlGroup controlGroup = new ControlGroup();

	public void buildUI(Composite parent) {
		if (toolkit == null) {
			toolkit = new FormToolkit(Display.getCurrent());
			toolkit.setBorderStyle(SWT.NULL);
		}

		Control[] children = parent.getChildren();

		if (children != null && children.length > 0) {
			contentpane = (Composite) children[children.length - 1];
			{
				final GridLayout gridLayout = controlGroup.getGridLayout();
				gridLayout.marginLeft = 8;
				gridLayout.verticalSpacing = 4;
				contentpane.setLayout(gridLayout);
			}
			Color backgroundColor = new Color(contentpane.getDisplay(), 0xFF,
					0xFF, 0xFF);
			controlGroup.build(contentpane, new ControlEventHandler() {
				public void updateModel(String propName) {
					try {
						DotbarGeneralPage.this.updateModel(propName);
					} catch (SemanticException e) {
						e.printStackTrace();
					}
				}

				public ExtendedItemHandle getModelHandle() {
					return dotbarItem.getModelHandle();
				}
			}, backgroundColor);
		}
	}

	@Override
	public void setInput(Object input) {
		DotbarItem item = getItemFromInput(input);
		if (item != null)
			this.dotbarItem = item;
	}

	@SuppressWarnings("unchecked")
	public static DotbarItem getItemFromInput(Object input) {
		if (input instanceof List) {
			List list = (List) input;
			for (Object object : list) {
				if (object instanceof ExtendedItemHandle) {
					ExtendedItemHandle extendedItemHandle = (ExtendedItemHandle) object;
					try {
						IReportItem reportItem = extendedItemHandle
								.getReportItem();
						if (reportItem instanceof DotbarItem) {
							return (DotbarItem) reportItem;
						}
					} catch (ExtendedElementException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	public void dispose() {
		if (toolkit != null) {
			toolkit.dispose();
		}
	}

	private void adaptFormStyle(Composite comp) {
		Control[] children = comp.getChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i] instanceof Composite) {
				adaptFormStyle((Composite) children[i]);
			}
		}

		toolkit.paintBordersFor(comp);
		toolkit.adapt(comp);
	}

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

	public void postElementEvent() {
		if (contentpane != null && !contentpane.isDisposed()) {
			updateUI();
		}
	}

	private void updateModel(String propName) throws SemanticException {
		if (dotbarItem != null)
			controlGroup.updateModel(dotbarItem, propName);
	}

	protected void updateUI() {
		if (dotbarItem != null) {
			DotbarData config = dotbarItem.getConfiguration();
			controlGroup.load(config);
		}
	}
}
