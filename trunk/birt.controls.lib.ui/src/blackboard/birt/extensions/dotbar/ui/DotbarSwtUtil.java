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

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.widgets.Display;

import blackboard.birt.controls.SwtUtil;
import blackboard.birt.extensions.dotbar.DotShape;
import blackboard.birt.extensions.dotbar.DotbarData;
import blackboard.birt.extensions.dotbar.DotbarItem;
import blackboard.birt.extensions.dotbar.NumberPosition;

public class DotbarSwtUtil {
	/**
	 * Renders a single dot using SWT.
	 * 
	 * @param dotShape
	 * @param gc
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param border
	 * @param fill
	 */
	public static void renderDotShape(final DotShape dotShape, final GC gc,
			int x, int y, int width, int height, boolean border, boolean fill) {
		switch (dotShape.index) {
		case DotShape.INDEX_OVAL:
			if (border)
				gc.drawOval(x, y, width, height);
			if (fill)
				gc.fillOval(x, y, width, height);
			break;
		case DotShape.INDEX_RECTANGLE:
			if (border)
				gc.drawRectangle(x, y, width, height);
			if (fill)
				gc.fillRectangle(x, y, width, height);
			break;
		case DotShape.INDEX_TRIANGLE:
			int x1 = x + width / 2;
			int y1 = y;
			int x2 = x + width;
			int y2 = y + height;
			int x3 = x;
			int y3 = y + height;
			int[] pointArray = { x1, y1, x2, y2, x3, y3 };
			if (border)
				gc.drawPolygon(pointArray);
			if (fill)
				gc.fillPolygon(pointArray);
			break;
		}
	}

	/**
	 * SWT image is displayed in the designer
	 * 
	 * @return
	 */
	public static Image createSwtImage(final DotbarItem dotbarItem) {
		final DotbarData config = dotbarItem.getConfiguration();
		// TODO padding
		GC gc = null;
		DotbarData.Calculator calculator = config.new Calculator(1, "in", 96);
		final int barWidth = calculator.computeBarWidth(config.displayValue);
		final int barHeight = calculator.computeBarHeight(config.displayValue);
		final int numberCellWidth = calculator.getNumberCellWidth();
		final int numberCellHeight = calculator.getNumberCellHeight();
		final int dotCellWidth = calculator.getDotCellWidth();
		final int dotCellHeight = calculator.getDotCellHeight();
		try {
			final Display display = Display.getCurrent();
			final Font font = getSwtFont(display, config);
			Image image = new Image(display, barWidth, barHeight);
			gc = new GC(image);
			gc.setFont(font);
			gc.setAdvanced(true);
			final String text = String.valueOf(config.displayValue);
			final TextLayout textLayout = new TextLayout(gc.getDevice());
			textLayout.setFont(font);
			textLayout.setText(text);
			final Rectangle bounds = textLayout.getBounds();
			int textWidth = bounds.width;
			int textHeight = bounds.height;
			int x = 0;
			int y = 0;
			final Color backgroundColor = gc.getBackground();
			if (NumberPosition.BEFORE.equals(config.numberPosition)) {
				gc.setForeground(getSwtFontColor(display, config));
				double textX = (double) x + (double) numberCellWidth / 2.0
						- textWidth / 2.0;
				double textY = (double) y + (double) numberCellHeight / 2.0
						- textHeight / 2.0;
				gc.drawText(text, (int) textX, (int) textY);
				if (config.vertical)
					y += numberCellHeight;
				else
					x += numberCellWidth;
			}
			int startX = x;
			int startY = y;
			if (config.fillColor != null)
				gc.setBackground(SwtUtil.getSwtColor(gc.getDevice(),
						config.fillColor));
			if (config.borderColor != null)
				gc.setForeground(SwtUtil.getSwtColor(gc.getDevice(),
						config.borderColor));
			int dotCount = config.displayValue;
			if (config.wrapPoint > 0 && dotCount > config.wrapPoint)
				dotCount = config.wrapPoint;
			int rowCount = 1;
			if (config.wrapPoint > 0)
				rowCount += config.displayValue / config.wrapPoint;
			int remainingDots = config.displayValue;
			int dotWidthPixels = calculator.getDotWidth();
			int dotHeightPixels = calculator.getDotHeight();
			int dotSpacingPixels = calculator.getDotSpacing();
			for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
				int dotLimit = remainingDots < dotCount ? remainingDots
						: dotCount;
				remainingDots -= dotCount;
				for (int dotIndex = 0; dotIndex < dotLimit; dotIndex++) {
					// center the dots
					int dotX = x + dotCellWidth / 2 - dotWidthPixels / 2;
					int dotY = y + dotCellHeight / 2 - dotHeightPixels / 2;
					renderDotShape(config.dotShape, gc, dotX, dotY,
							dotWidthPixels, dotHeightPixels, config.hasBorder,
							config.hasFill);
					if (config.vertical)
						y += dotCellHeight + dotSpacingPixels;
					else
						x += dotCellWidth + dotSpacingPixels;
				}
				if (config.vertical) {
					y = startY;
					x += dotWidthPixels + dotSpacingPixels;
				} else {
					x = startX;
					y += dotHeightPixels + dotSpacingPixels;
				}
			}
			if (NumberPosition.AFTER.equals(config.numberPosition)) {
				if (config.vertical) {
					x = 0;
					y = dotHeightPixels * dotCount + dotSpacingPixels
							* (dotCount - 1);
				} else {
					y = 0;
					x = dotWidthPixels * dotCount + dotSpacingPixels
							* (dotCount - 1);
				}
				gc.setForeground(getSwtFontColor(display, config));
				gc.setBackground(backgroundColor);
				double textX = (double) x + (double) numberCellWidth / 2.0
						- textWidth / 2.0;
				double textY = (double) y + (double) numberCellHeight / 2.0
						- textHeight / 2.0;
				gc.drawText(text, (int) textX, (int) textY);
				if (config.vertical)
					y += numberCellHeight;
				else
					x += numberCellWidth;
			}
			return image;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (gc != null && !gc.isDisposed())
				gc.dispose();
		}

		return null;
	}

	public static Font getSwtFont(final Device device, final DotbarData config) {
		final FontData fontData = new FontData();
		fontData.setHeight(DotbarData.getFontPointSize(config.fontSize, 96));
		fontData.setName(config.fontFamily);
		int style = SWT.NORMAL;
		if (config.fontBold)
			style |= SWT.BOLD;
		if (config.fontItalic)
			style |= SWT.ITALIC;
		fontData.setStyle(style);
		final Font font = new Font(device, fontData);
		return font;
	}

	public static Color getSwtFontColor(Device device, DotbarData config) {
		return SwtUtil.getSwtColor(device, config.fontColor);
	}
}
