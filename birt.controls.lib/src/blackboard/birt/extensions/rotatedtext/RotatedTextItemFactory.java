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
