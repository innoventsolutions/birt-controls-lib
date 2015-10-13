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

	protected void buildItemContent(final CTabItem item) {
		if (this.itemMap.containsKey(item) && this.itemMap.get(item) == null) {
			final String title = this.tabFolder.getSelection().getText();

			if (CUSTOM_PAGE_TITLE.equals(title)) {
				final TabPage page = new RotatedTextCustomPage().getPage();

				if (page != null) {
					this.setPageInput(page);
					this.refresh(this.tabFolder, page, true);
					item.setControl(page.getControl());
					this.itemMap.put(item, page);
				}
			}
		} else if (this.itemMap.get(item) != null) {
			this.setPageInput(this.itemMap.get(item));
			this.refresh(this.tabFolder, this.itemMap.get(item), false);
		}
	}

	@Override
	public void refresh() {
		this.createTabItems(this.input);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void createTabItems(final List input) {
		final RotatedTextItem rotatedTextItem = RotatedTextGeneralPage
				.getItemFromInput(input);
		if (rotatedTextItem != null) {
			if (this.generalPage == null
					|| this.generalPage.getControl().isDisposed()) {
				this.tabFolder.setLayout(new FillLayout());
				final String[] categories = { null, AttributesUtil.BORDER,
						AttributesUtil.MARGIN, AttributesUtil.SECTION,
						AttributesUtil.VISIBILITY, AttributesUtil.TOC,
						AttributesUtil.BOOKMARK, AttributesUtil.USERPROPERTIES,
						AttributesUtil.NAMEDEXPRESSIONS,
						AttributesUtil.ADVANCEPROPERTY };
				final String[] customKeys = { "General" };
				final String[] customCategories = { "General" };
				final AttributesUtil.PageWrapper[] customPageWrappers = { new RotatedTextGeneralPage() };
				this.generalPage = AttributesUtil.buildGeneralPage(
						this.tabFolder, categories, customKeys,
						customCategories, customPageWrappers, input);
				final CTabItem tabItem = new CTabItem(this.tabFolder, SWT.NONE);
				tabItem.setText(ATTRIBUTESTITLE);
				tabItem.setControl(this.generalPage.getControl());
			}

			this.input = input;
			this.generalPage.setInput(input);
			this.addSelectionListener(this);
			((TabPage) this.generalPage).refresh();

			// createTabItem( CUSTOM_PAGE_TITLE, ATTRIBUTESTITLE );
			// if ( tabFolder.getSelection() != null )
			// {
			// buildItemContent( tabFolder.getSelection() );
			// }
		} else
			System.out.println("Wrong input: " + input);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void createControl(final Composite parent, final Object input) {
		final RotatedTextItem rotatedTextItem = RotatedTextGeneralPage
				.getItemFromInput(input);
		if (rotatedTextItem != null) {
			super.createControl(parent, input);
			if (input instanceof List)
				this.input = (List) input;
		} else
			System.out.println("Wrong input: " + input);
	}
}
