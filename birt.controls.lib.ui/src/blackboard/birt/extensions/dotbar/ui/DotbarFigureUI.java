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

import org.eclipse.birt.report.designer.ui.extensions.ReportItemFigureProvider;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.extension.ExtendedElementException;
import org.eclipse.birt.report.model.api.extension.IReportItem;
import org.eclipse.draw2d.IFigure;

import blackboard.birt.extensions.dotbar.DotbarItem;

/**
 * Provides the dotbar figure. The dotbar figure is capable of rendering the dotbar in the designer using SWT. Specified in the
 * org.eclipse.birt.report.designer.ui.reportitemUI extension.
 * 
 * @author Steve Schafer / Innovent Solutions
 */
public class DotbarFigureUI extends ReportItemFigureProvider {
	@Override
	public IFigure createFigure(ExtendedItemHandle handle) {
		try {
			IReportItem item = handle.getReportItem();
			if (item instanceof DotbarItem)
				return new DotbarFigure((DotbarItem) item);
		} catch (ExtendedElementException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void disposeFigure(ExtendedItemHandle handle, IFigure figure) {
		((DotbarFigure) figure).dispose();
	}

	@Override
	public void updateFigure(ExtendedItemHandle handle, IFigure figure) {
		try {
			IReportItem item = handle.getReportItem();

			if (item instanceof DotbarItem) {
				DotbarFigure dotbarFigure = (DotbarFigure) figure;
				dotbarFigure.setDotbarItem((DotbarItem) item);
			}
		} catch (ExtendedElementException e) {
			e.printStackTrace();
		}
	}
}
