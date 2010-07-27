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
