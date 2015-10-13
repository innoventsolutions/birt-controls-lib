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

import java.util.List;

import org.eclipse.birt.report.designer.ui.extensions.IMenuBuilder;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.extension.ExtendedElementException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;

import blackboard.birt.extensions.rotatedtext.RotatedTextItem;

/**
 * RotatedTextMenuBuilder
 */
public class RotatedTextMenuBuilder implements IMenuBuilder {
	@SuppressWarnings("unchecked")
	public void buildMenu(final IMenuManager menu, final List selectedList) {
		if (selectedList != null && selectedList.size() == 1
				&& selectedList.get(0) instanceof ExtendedItemHandle) {
			final ExtendedItemHandle handle = (ExtendedItemHandle) selectedList
					.get(0);

			if (!RotatedTextItem.EXTENSION_NAME.equals(handle
					.getExtensionName()))
				return;

			RotatedTextItem item = null;
			try {
				item = (RotatedTextItem) handle.getReportItem();
			} catch (final ExtendedElementException e) {
				e.printStackTrace();
			}

			if (item == null)
				return;

			final Separator separator = new Separator("group.rotatedtext"); //$NON-NLS-1$
			if (menu.getItems().length > 0)
				menu.insertBefore(menu.getItems()[0].getId(), separator);
			else
				menu.add(separator);

			menu.appendToGroup(separator.getId(), new RotateAction(item, -90));
			menu.appendToGroup(separator.getId(), new RotateAction(item, 90));
			menu.appendToGroup(separator.getId(), new RotateAction(item, 0));
			menu.appendToGroup(separator.getId(), new RotateAction(item, 180));
		}
	}

	/**
	 * RotateAtction
	 */
	static class RotateAction extends Action {
		private final RotatedTextItem item;
		private final int angle;

		RotateAction(final RotatedTextItem item, final int angle) {
			this.item = item;
			this.angle = angle;
			this.setText("Rotate as " + angle + "\u00BA"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		@Override
		public void run() {
			try {
				this.item.setRotationAngle(this.angle);
			} catch (final SemanticException e) {
				e.printStackTrace();
			}
		}
	}
}
