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

import org.eclipse.birt.report.designer.ui.extensions.ReportItemBuilderUI;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.extension.IReportItem;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import blackboard.birt.extensions.rotatedtext.RotatedTextData;
import blackboard.birt.extensions.rotatedtext.RotatedTextItem;

/**
 * RotatedTextBuilder
 */
public class RotatedTextBuilder extends ReportItemBuilderUI {
	public int open(ExtendedItemHandle handle) {
		try {
			IReportItem item = handle.getReportItem();

			if (item instanceof RotatedTextItem) {
				RotatedTextEditor editor = new RotatedTextEditor(Display
						.getCurrent().getActiveShell(), (RotatedTextItem) item);
				return editor.open();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Window.CANCEL;
	}
}

/**
 * RotatedTextEditor
 */
class RotatedTextEditor extends TrayDialog {
	protected RotatedTextItem textItem;
	protected ControlGroup controlGroup = new ControlGroup();

	protected RotatedTextEditor(Shell shell, RotatedTextItem textItem) {
		super(shell);
		this.textItem = textItem;
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Rotated Text Builder"); //$NON-NLS-1$
	}

	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = controlGroup.getGridLayout();
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		controlGroup.build(composite,
				new blackboard.birt.controls.ControlEventHandler() {
					public void updateModel(String propName) {
					}

					public ExtendedItemHandle getModelHandle() {
						return textItem.getModelHandle();
					}
				}, parent.getBackground());
		applyDialogFont(composite);
		initValues();
		return composite;
	}

	private void initValues() {
		controlGroup.load(new RotatedTextData(textItem));
	}

	protected void okPressed() {
		try {
			controlGroup.save(textItem);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		super.okPressed();
	}
}
