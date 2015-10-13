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
package blackboard.birt.extensions.dotbar;

import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.extension.IMessages;
import org.eclipse.birt.report.model.api.extension.IReportItem;
import org.eclipse.birt.report.model.api.extension.ReportItemFactory;

/**
 * A ReportItemFactory to instantiate the Dotbar report item. This class is
 * specified in the org.eclipse.birt.report.model.reportItemModel extension.
 * 
 * @author Steve Schafer / Innovent Solutions
 */
public class DotbarItemFactory extends ReportItemFactory {
	@Override
	public IReportItem newReportItem(
			final DesignElementHandle designElementHandle) {
		if (designElementHandle instanceof ExtendedItemHandle) {
			final ExtendedItemHandle extendedItemHandle = (ExtendedItemHandle) designElementHandle;
			if (DotbarItem.EXTENSION_NAME.equals(extendedItemHandle
					.getExtensionName()))
				return new DotbarItem(extendedItemHandle);
		}
		return null;
	}

	@Override
	public IMessages getMessages() {
		return null;
	}
}
