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
package blackboard.birt.extensions.dotbar.views;

import org.eclipse.birt.report.designer.ui.views.IPageGenerator;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.core.runtime.IAdapterFactory;

public class DotbarPageGeneratorFactory implements IAdapterFactory
{
  @SuppressWarnings("unchecked")
  public Object getAdapter( Object adaptableObject, Class adapterType )
  {
    if ( adaptableObject instanceof ExtendedItemHandle )
    {
      ExtendedItemHandle extendedItemHandle = (ExtendedItemHandle) adaptableObject;
      String extensionName = extendedItemHandle.getExtensionName();
      if("DotBar".equals(extensionName))
        return new DotbarPageGenerator();
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  public Class[] getAdapterList()
  {
    return new Class[] { IPageGenerator.class };
  }
}
