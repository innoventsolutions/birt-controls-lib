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
package blackboard.birt.extensions.rotatedtext.ui.views;

import org.eclipse.birt.report.designer.ui.views.IPageGenerator;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.core.runtime.IAdapterFactory;

/**
 * RotatedTextPageGeneratorFactory
 */
public class RotatedTextPageGeneratorFactory implements IAdapterFactory
{

  @SuppressWarnings("unchecked")
  public Object getAdapter( Object adaptableObject, Class adapterType )
  {
    if ( adaptableObject instanceof ExtendedItemHandle )
    {
      ExtendedItemHandle extendedItemHandle = (ExtendedItemHandle) adaptableObject;
      String extensionName = extendedItemHandle.getExtensionName();
      if("RotatedText".equals(extensionName))
        return new RotatedTextPageGenerator();
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  public Class[] getAdapterList()
  {
    return new Class[] { IPageGenerator.class };
  }

}
