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
package blackboard.birt.extensions.dotbar.ui;

import org.eclipse.birt.report.designer.ui.extensions.ReportItemBuilderUI;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.extension.IReportItem;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import blackboard.birt.controls.ControlEventHandler;
import blackboard.birt.extensions.dotbar.DotbarData;
import blackboard.birt.extensions.dotbar.DotbarItem;

/**
 * Builds the Dotbar dialog. Specified in the org.eclipse.birt.report.designer.ui.reportitemUI extension.
 * 
 * @author Steve Schafer / Innovent Solutions
 */
public class DotbarBuilder extends ReportItemBuilderUI {
	@Override
	public int open(ExtendedItemHandle handle) {
		try {
			IReportItem item = handle.getReportItem();

			if (item instanceof DotbarItem) {
				DotbarEditor editor = new DotbarEditor(Display.getCurrent()
						.getActiveShell(), (DotbarItem) item);
				return editor.open();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Window.CANCEL;
	}
}

class DotbarEditor extends TrayDialog {
	protected final DotbarItem dotbarItem;
	protected ControlGroup controlGroup = new ControlGroup();

	protected DotbarEditor(Shell shell, DotbarItem dotbarItem) {
		super(shell);
		this.dotbarItem = dotbarItem;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("DotBar Builder"); //$NON-NLS-1$
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = controlGroup.getGridLayout();
		gridLayout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		gridLayout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		gridLayout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		gridLayout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		composite.setLayout(gridLayout);
		controlGroup.build(composite, new ControlEventHandler() {
			public void updateModel(String propName) {
			}

			public ExtendedItemHandle getModelHandle() {
				return dotbarItem.getModelHandle();
			}
		}, parent.getBackground());
		applyDialogFont(composite);
		initValues();
		return composite;
	}

	protected void okPressed() {
		DotbarData.Editor editor = new DotbarData.Editor();
		controlGroup.save(editor);
		try {
			dotbarItem.setConfiguration(editor.create());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		super.okPressed();
	}

	private void initValues() {
		DotbarData config = dotbarItem.getConfiguration();
		controlGroup.load(config);
	}
}
