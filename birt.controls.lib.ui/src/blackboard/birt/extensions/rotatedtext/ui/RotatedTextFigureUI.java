/*******************************************************************************
 * Copyright (c) 2008-Present Innovent Solutions, Inc.
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

import org.eclipse.birt.report.designer.ui.extensions.ReportItemFigureProvider;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.extension.ExtendedElementException;
import org.eclipse.birt.report.model.api.extension.IReportItem;
import org.eclipse.draw2d.IFigure;

import blackboard.birt.extensions.rotatedtext.RotatedTextItem;

/**
 * RotatedTextFigureUI
 */
public class RotatedTextFigureUI extends ReportItemFigureProvider {

	@Override
	public IFigure createFigure(final ExtendedItemHandle handle) {
		try {
			final IReportItem item = handle.getReportItem();
			if (item instanceof RotatedTextItem)
				return new RotatedTextFigure((RotatedTextItem) item);
		} catch (final ExtendedElementException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateFigure(final ExtendedItemHandle handle,
			final IFigure figure) {
		try {
			final IReportItem item = handle.getReportItem();
			if (item instanceof RotatedTextItem) {
				final RotatedTextFigure fig = (RotatedTextFigure) figure;
				fig.setRotatedTextItem((RotatedTextItem) item);
			}
		} catch (final ExtendedElementException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void disposeFigure(final ExtendedItemHandle handle,
			final IFigure figure) {
		((RotatedTextFigure) figure).dispose();
	}

}
