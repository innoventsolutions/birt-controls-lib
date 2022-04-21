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
	@Override
	public int open(final ExtendedItemHandle handle) {
		try {
			final IReportItem item = handle.getReportItem();

			if (item instanceof RotatedTextItem) {
				final RotatedTextEditor editor = new RotatedTextEditor(Display
						.getCurrent().getActiveShell(), (RotatedTextItem) item);
				return editor.open();
			}
		} catch (final Exception e) {
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

	protected RotatedTextEditor(final Shell shell,
			final RotatedTextItem textItem) {
		super(shell);
		this.textItem = textItem;
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Rotated Text Builder"); //$NON-NLS-1$
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout layout = this.controlGroup.getGridLayout();
		layout.marginHeight = this
				.convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = this
				.convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = this
				.convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = this
				.convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		this.controlGroup.build(composite,
				new blackboard.birt.controls.ControlEventHandler() {
					public void updateModel(final String propName) {
					}

					public ExtendedItemHandle getModelHandle() {
						return RotatedTextEditor.this.textItem.getModelHandle();
					}
				}, parent.getBackground());
		applyDialogFont(composite);
		this.initValues();
		return composite;
	}

	private void initValues() {
		this.controlGroup.load(new RotatedTextData(this.textItem));
	}

	@Override
	protected void okPressed() {
		try {
			this.controlGroup.save(this.textItem);
		} catch (final Exception ex) {
			ex.printStackTrace();
		}

		super.okPressed();
	}
}
