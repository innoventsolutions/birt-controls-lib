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
package blackboard.birt.extensions.rotatedtext.ui.views;

import org.eclipse.birt.report.designer.ui.views.IPageGenerator;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.core.runtime.IAdapterFactory;

/**
 * RotatedTextPageGeneratorFactory
 */
public class RotatedTextPageGeneratorFactory implements IAdapterFactory {

	@SuppressWarnings("unchecked")
	public Object getAdapter(final Object adaptableObject,
			final Class adapterType) {
		if (adaptableObject instanceof ExtendedItemHandle) {
			final ExtendedItemHandle extendedItemHandle = (ExtendedItemHandle) adaptableObject;
			final String extensionName = extendedItemHandle.getExtensionName();
			if ("RotatedText".equals(extensionName))
				return new RotatedTextPageGenerator();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Class[] getAdapterList() {
		return new Class[] { IPageGenerator.class };
	}

}
