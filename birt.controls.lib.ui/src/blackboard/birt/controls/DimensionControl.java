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

import org.eclipse.birt.report.designer.ui.views.attributes.providers.ChoiceSetFactory;
import org.eclipse.birt.report.designer.util.DEUtil;
import org.eclipse.birt.report.model.api.metadata.DimensionValue;
import org.eclipse.birt.report.model.api.metadata.IChoice;
import org.eclipse.birt.report.model.api.metadata.IChoiceSet;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class DimensionControl extends Composite {
	private final NumberControl numberControl;
	private final Combo unitCombo;
	private IChoiceSet units;
	private final String elementName;
	private final String propertyName;
	private DimensionValue dimensionValue;

	public DimensionControl(final Composite parent, final int style,
			final String elementName, final String propertyName) {
		super(parent, style);
		this.elementName = elementName;
		this.propertyName = propertyName;
		final GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.horizontalSpacing = 2;
		this.setLayout(gridLayout);
		this.numberControl = new NumberControl(this, SWT.NONE, false);
		{
			final GridData gridData = new GridData();
			gridData.widthHint = 50;
			gridData.heightHint = 17;
			numberControl.setLayoutData(gridData);
		}
		this.unitCombo = new Combo(this, SWT.DROP_DOWN | SWT.READ_ONLY);
		{
			final GridData gridData = new GridData();
			if (Platform.getOS().equals(Platform.OS_LINUX))
				gridData.widthHint = 90;
			else
				gridData.widthHint = 50;

			gridData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_END;
			unitCombo.setLayoutData(gridData);
		}
		unitCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				processAction();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				processAction();
			}
		});
		numberControl.addModifyListener(new BbModifyListener() {
			public void onModified() {
				processAction();
			}
		});
		initChoice();
	}

	protected void initChoice() {
		units = ChoiceSetFactory.getDimensionChoiceSet(elementName,
				propertyName);
		final String[] cvs = ChoiceSetFactory
				.getDisplayNamefromChoiceSet(units);

		for (int i = 0; i < cvs.length; i++)
			unitCombo.add(cvs[i]);
	}

	protected void processAction() {
		double val = numberControl.getValue();
		final DimensionValue newValue = new DimensionValue(val, DEUtil
				.resolveNull(getUnitsValue(unitCombo.getText())));

		if (!newValue.equals(dimensionValue)) {
			dimensionValue = newValue;
			notifyListeners(SWT.Modify, null);
		}
	}

	private String getUnitsValue(final String value) {
		final IChoice choice = units.findChoiceByDisplayName(value);

		if (choice != null)
			return choice.getName();

		return null;
	}

	public DimensionValue getValue() {
		return this.dimensionValue;
	}

	public void setValue(final DimensionValue value) {
		if (this.dimensionValue == null ? value != null : !this.dimensionValue
				.equals(value)) {
			this.dimensionValue = value;
			this.numberControl.setValue(value.getMeasure());
			IChoice choice = units.findChoice(value.getUnits());
			this.unitCombo.setText(choice == null ? "" : choice
					.getDisplayName());
		}
	}
}
