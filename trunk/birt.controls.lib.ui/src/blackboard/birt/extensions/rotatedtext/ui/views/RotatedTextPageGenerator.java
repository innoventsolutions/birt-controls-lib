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

import java.util.List;

import org.eclipse.birt.report.designer.ui.extensions.IPropertyTabUI;
import org.eclipse.birt.report.designer.ui.views.attributes.AbstractPageGenerator;
import org.eclipse.birt.report.designer.ui.views.attributes.AttributesUtil;
import org.eclipse.birt.report.designer.ui.views.attributes.TabPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import blackboard.birt.extensions.rotatedtext.RotatedTextItem;

/**
 * RotatedTextPageGenerator
 */
public class RotatedTextPageGenerator extends AbstractPageGenerator {
	private static final String CUSTOM_PAGE_TITLE = "Custom"; //$NON-NLS-1$
	private IPropertyTabUI generalPage;

	protected void buildItemContent(CTabItem item) {
		if (itemMap.containsKey(item) && itemMap.get(item) == null) {
			String title = tabFolder.getSelection().getText();

			if (CUSTOM_PAGE_TITLE.equals(title)) {
				TabPage page = new RotatedTextCustomPage().getPage();

				if (page != null) {
					setPageInput(page);
					refresh(tabFolder, page, true);
					item.setControl(page.getControl());
					itemMap.put(item, page);
				}
			}
		} else if (itemMap.get(item) != null) {
			setPageInput(itemMap.get(item));
			refresh(tabFolder, itemMap.get(item), false);
		}
	}

	public void refresh() {
		createTabItems(input);
	}

	@SuppressWarnings("unchecked")
	public void createTabItems(List input) {
		RotatedTextItem rotatedTextItem = RotatedTextGeneralPage
				.getItemFromInput(input);
		if (rotatedTextItem != null) {
			if (generalPage == null || generalPage.getControl().isDisposed()) {
				tabFolder.setLayout(new FillLayout());
				String[] categories = { null, AttributesUtil.BORDER,
						AttributesUtil.MARGIN, AttributesUtil.SECTION,
						AttributesUtil.VISIBILITY, AttributesUtil.TOC,
						AttributesUtil.BOOKMARK, AttributesUtil.USERPROPERTIES,
						AttributesUtil.NAMEDEXPRESSIONS,
						AttributesUtil.ADVANCEPROPERTY };
				String[] customKeys = { "General" };
				String[] customCategories = { "General" };
				AttributesUtil.PageWrapper[] customPageWrappers = { new RotatedTextGeneralPage() };
				generalPage = AttributesUtil.buildGeneralPage(tabFolder,
						categories, customKeys, customCategories,
						customPageWrappers, input);
				CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
				tabItem.setText(ATTRIBUTESTITLE);
				tabItem.setControl(generalPage.getControl());
			}

			this.input = input;
			generalPage.setInput(input);
			addSelectionListener(this);
			((TabPage) generalPage).refresh();

			//      createTabItem( CUSTOM_PAGE_TITLE, ATTRIBUTESTITLE );
			//      if ( tabFolder.getSelection() != null )
			//      {
			//        buildItemContent( tabFolder.getSelection() );
			//      }
		} else
			System.out.println("Wrong input: " + input);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void createControl(Composite parent, Object input) {
		RotatedTextItem rotatedTextItem = RotatedTextGeneralPage
				.getItemFromInput(input);
		if (rotatedTextItem != null) {
			super.createControl(parent, input);
			if (input instanceof List)
				this.input = (List) input;
		} else
			System.out.println("Wrong input: " + input);
	}
}
