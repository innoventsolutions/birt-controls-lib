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

import java.util.List;

import org.eclipse.birt.report.designer.ui.extensions.IPropertyTabUI;
import org.eclipse.birt.report.designer.ui.views.attributes.AbstractPageGenerator;
import org.eclipse.birt.report.designer.ui.views.attributes.AttributesUtil;
import org.eclipse.birt.report.designer.ui.views.attributes.TabPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import blackboard.birt.extensions.dotbar.DotbarItem;

public class DotbarPageGenerator extends AbstractPageGenerator
{
  private static final String CUSTOM_PAGE_TITLE = "Custom"; //$NON-NLS-1$
  private IPropertyTabUI generalPage;

  @SuppressWarnings("unchecked")
  protected void buildItemContent( CTabItem item )
  {
    if ( itemMap.containsKey( item ) && itemMap.get( item ) == null )
    {
      String title = tabFolder.getSelection().getText();

      if ( CUSTOM_PAGE_TITLE.equals( title ) )
      {
        TabPage page = new DotbarCustomPage().getPage();

        if ( page != null )
        {
          setPageInput( page );
          refresh( tabFolder, page, true );
          item.setControl( page.getControl() );
          itemMap.put( item, page );
        }
      }
    }
    else if ( itemMap.get( item ) != null )
    {
      setPageInput( itemMap.get( item ) );
      refresh( tabFolder, itemMap.get( item ), false );
    }
  }

  public void refresh()
  {
    createTabItems( input );
    // already set in createTabItems: generalPage.setInput( input );
    // already done in createTabItems: addSelectionListener( this );
    // already done in createTabItems: ( (TabPage) generalPage ).refresh();

  }

  @SuppressWarnings("unchecked")
  public void createTabItems( List input )
  {
    DotbarItem dotbarItem = DotbarGeneralPage.getItemFromInput( input );
    if ( dotbarItem != null )
    {
      if ( generalPage == null || generalPage.getControl().isDisposed() )
      {
        tabFolder.setLayout( new FillLayout() );
        String[] categories = { null, AttributesUtil.BORDER, AttributesUtil.MARGIN, AttributesUtil.SECTION, AttributesUtil.VISIBILITY,
                               AttributesUtil.TOC, AttributesUtil.BOOKMARK, AttributesUtil.USERPROPERTIES, AttributesUtil.NAMEDEXPRESSIONS,
                               AttributesUtil.ADVANCEPROPERTY };
        String[] customKeys = { "General" };
        String[] customCategories = { "General" };
        AttributesUtil.PageWrapper[] customPageWrappers = { new DotbarGeneralPage() };
        generalPage = AttributesUtil.buildGeneralPage( tabFolder, categories, customKeys, customCategories, customPageWrappers, input );
        CTabItem tabItem = new CTabItem( tabFolder, SWT.NONE );
        tabItem.setText( ATTRIBUTESTITLE );
        tabItem.setControl( generalPage.getControl() );
      }

      this.input = input;
      generalPage.setInput( input );
      addSelectionListener( this );
      ( (TabPage) generalPage ).refresh();

//      createTabItem( CUSTOM_PAGE_TITLE, ATTRIBUTESTITLE );
//      if ( tabFolder.getSelection() != null )
//        buildItemContent( tabFolder.getSelection() );
    }
    else
      System.out.println( "Wrong input: " + input );
  }

  @SuppressWarnings("unchecked")
  @Override
  public void createControl( Composite parent, Object input )
  {
    DotbarItem dotbarItem = DotbarGeneralPage.getItemFromInput( input );
    if ( dotbarItem != null )
    {
      super.createControl( parent, input );
      if ( input instanceof List )
        this.input = (List) input;
    }
    else
      System.out.println( "Wrong input: " + input );
  }
}
