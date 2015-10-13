/*******************************************************************************
 * Copyright (c) 2008-2015  Innovent Solutions, Inc.
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

import org.eclipse.birt.report.designer.ui.extensions.IReportItemLabelProvider;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.extension.ExtendedElementException;
import org.eclipse.birt.report.model.api.extension.IReportItem;

import blackboard.birt.extensions.rotatedtext.RotatedTextItem;

/**
 * RotatedTextLabelUI
 */
public class RotatedTextLabelUI implements IReportItemLabelProvider {
	public String getLabel(final ExtendedItemHandle handle) {
		try {
			final IReportItem item = handle.getReportItem();
			if (item instanceof RotatedTextItem)
				return ((RotatedTextItem) item).getText();
		} catch (final ExtendedElementException e) {
			e.printStackTrace();
		}
		return null;
	}
}
