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
package blackboard.birt.extensions.dotbar.ui;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;

import blackboard.birt.extensions.dotbar.DotbarData;
import blackboard.birt.extensions.dotbar.DotbarItem;

/**
 * Renders the dotbar in the designer using SWT. Created by DotbarFigureUI.
 * 
 * @author Steve Schafer / Innovent Solutions
 */
public class DotbarFigure extends Figure {
	private DotbarData lastConfig = null;
	private Image cachedImage;
	private DotbarItem dotbarItem;

	DotbarFigure(DotbarItem dotbarItem) {
		super();
		this.dotbarItem = dotbarItem;
	}

	@Override
	protected void paintClientArea(Graphics graphics) {
		final Rectangle r = getClientArea().getCopy();
		DotbarData config = dotbarItem.getConfiguration();
		if ((lastConfig == null ? config != null : !lastConfig.equals(config))
				|| cachedImage == null || cachedImage.isDisposed()) {
			lastConfig = config;
			if (cachedImage != null && !cachedImage.isDisposed())
				cachedImage.dispose();
			cachedImage = DotbarSwtUtil.createSwtImage(dotbarItem);
		}
		if (cachedImage != null && !cachedImage.isDisposed())
			graphics.drawImage(cachedImage, r.x, r.y);
	}

	public void setDotbarItem(DotbarItem dotbarItem) {
		this.dotbarItem = dotbarItem;
	}

	public void dispose() {
		if (cachedImage != null && !cachedImage.isDisposed())
			cachedImage.dispose();
	}

	@Override
	public Dimension getMinimumSize(int hint, int hint2) {
		return getPreferredSize(hint, hint2);
	}

	@Override
	public Dimension getPreferredSize(int hint, int hint2) {
		DotbarData config = dotbarItem.getConfiguration();
		DotbarData.Calculator calculator = config.new Calculator(1, "in", 96);
		int width = calculator.computeBarWidth(config.displayValue);
		int height = calculator.computeBarHeight(config.displayValue);
		return new Dimension(width, height);
	}
}
