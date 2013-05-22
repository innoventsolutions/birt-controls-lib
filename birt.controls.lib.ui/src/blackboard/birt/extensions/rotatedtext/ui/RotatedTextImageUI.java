/*******************************************************************************
 * Copyright (c) 2008 Actuate Corporation.
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

import org.eclipse.birt.report.designer.ui.extensions.IReportItemImageProvider;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.extension.ExtendedElementException;
import org.eclipse.birt.report.model.api.extension.IReportItem;
import org.eclipse.swt.graphics.Image;

import blackboard.birt.extensions.rotatedtext.RotatedTextData;
import blackboard.birt.extensions.rotatedtext.RotatedTextItem;

/**
 * RotatedTextImageUI
 */
public class RotatedTextImageUI implements IReportItemImageProvider {
	public void disposeImage(final ExtendedItemHandle handle, final Image image) {
		if (image != null && !image.isDisposed())
			image.dispose();
	}

	public Image getImage(final ExtendedItemHandle handle) {
		try {
			final IReportItem item = handle.getReportItem();

			if (item instanceof RotatedTextItem) {
				final RotatedTextItem textItem = (RotatedTextItem) item;
				final RotatedTextData data = new RotatedTextData(textItem);
				return RotatedTextSwtUtil.createRotatedTextImage(data);
			}
		} catch (final ExtendedElementException e) {
			e.printStackTrace();
		}
		return null;
	}

}
