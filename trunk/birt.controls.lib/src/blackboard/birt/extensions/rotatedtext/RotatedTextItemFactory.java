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
package blackboard.birt.extensions.rotatedtext;

import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.extension.IMessages;
import org.eclipse.birt.report.model.api.extension.IReportItem;
import org.eclipse.birt.report.model.api.extension.ReportItemFactory;

/**
 * A ReportItemFactory to instantiate the rotated text report item. This class
 * is specified in the org.eclipse.birt.report.model.reportItemModel extension.
 * 
 * @author Steve Schafer / Innovent Solutions
 */
public class RotatedTextItemFactory extends ReportItemFactory {

	@Override
	public IReportItem newReportItem(final DesignElementHandle modelHanlde) {
		if (modelHanlde instanceof ExtendedItemHandle
				&& RotatedTextItem.EXTENSION_NAME
						.equals(((ExtendedItemHandle) modelHanlde)
								.getExtensionName())) {
			return new RotatedTextItem((ExtendedItemHandle) modelHanlde);
		}
		return null;
	}

	@Override
	public IMessages getMessages() {
		return null;
	}

}
