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

package blackboard.birt.extensions.dotbar;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.elements.DesignChoiceConstants;
import org.eclipse.birt.report.model.api.metadata.DimensionValue;
import org.eclipse.birt.report.model.api.metadata.PropertyValueException;
import org.eclipse.birt.report.model.api.util.DimensionUtil;

import blackboard.birt.extensions.util.ColorSpec;
import blackboard.birt.extensions.util.DimensionValueUtil;

/**
 * Encapsulates all control properties in one object.
 * 
 * @author Steve Schafer / Innovent Solutions
 */
public class DotbarData {
	public final String valueExpression;
	public final int displayValue;
	public final DimensionValue dotWidth;
	public final DimensionValue dotHeight;
	public final DimensionValue dotSpacing;
	public final boolean vertical;
	public final boolean hasBorder;
	public final ColorSpec borderColor;
	public final boolean hasFill;
	public final ColorSpec fillColor;
	public final DotShape dotShape;
	public final NumberPosition numberPosition;
	public final DimensionValue numberWidth;
	public final DimensionValue numberHeight;
	public final int wrapPoint;
	public final String fontSize;
	public final String fontFamily;
	public final boolean fontItalic;
	public final boolean fontBold;
	public final ColorSpec fontColor;

	public DotbarData(final Editor editor) {
		valueExpression = editor.valueExpression;
		displayValue = editor.displayValue;
		dotWidth = editor.dotWidth;
		dotHeight = editor.dotHeight;
		dotSpacing = editor.dotSpacing;
		vertical = editor.vertical;
		hasBorder = editor.hasBorder;
		borderColor = editor.borderColor;
		hasFill = editor.hasFill;
		fillColor = editor.fillColor;
		dotShape = editor.dotShape;
		numberPosition = editor.numberPosition;
		numberWidth = editor.numberWidth;
		numberHeight = editor.numberHeight;
		wrapPoint = editor.wrapPoint;
		fontSize = editor.fontSize;
		fontFamily = editor.fontFamily;
		fontItalic = editor.fontItalic;
		fontBold = editor.fontBold;
		fontColor = editor.fontColor;
	}

	/**
	 * A mutable version of DotbarData.
	 * 
	 * @author Steve Schafer / Innovent Solutions
	 */
	public static final class Editor {
		public String valueExpression = null;
		public int displayValue = 3;
		public DimensionValue dotWidth = new DimensionValue(11, "pt");
		public DimensionValue dotHeight = new DimensionValue(11, "pt");
		public DimensionValue dotSpacing = new DimensionValue(5, "pt");
		public boolean vertical = false;
		public boolean hasBorder = true;
		public ColorSpec borderColor = new ColorSpec(0, 0, 0);
		public boolean hasFill = true;
		public ColorSpec fillColor = new ColorSpec(255, 255, 255);
		public DotShape dotShape = DotShape.OVAL;
		public NumberPosition numberPosition = NumberPosition.HIDDEN;
		public DimensionValue numberWidth = new DimensionValue(15, "pt");
		public DimensionValue numberHeight = new DimensionValue(11, "pt");
		public int wrapPoint = 0;
		public String fontSize = null;
		public String fontFamily = null;
		public boolean fontItalic = false;
		public boolean fontBold = false;
		public ColorSpec fontColor = new ColorSpec(0, 0, 0);

		public DotbarData create() {
			return new DotbarData(this);
		}
	}

	/**
	 * Calculates various dimension values in pixels.
	 * 
	 * @author Steve Schafer / Innovent Solutions
	 */
	public final class Calculator {
		public final double baseSize;
		public final String baseSizeUnits;
		public final int dpi;

		public Calculator(final double baseSize, final String baseSizeUnits,
				final int dpi) {
			this.baseSize = baseSize;
			this.baseSizeUnits = baseSizeUnits;
			this.dpi = dpi;
		}

		public int convertToPixels(final DimensionValue value) {
			final double doubleValue = DimensionUtil.convertTo(value, "in",
					"in", baseSize, baseSizeUnits, dpi)
					* dpi;
			return (int) doubleValue;
		}

		/**
		 * Calculates the width in pixels of the area within which the textual
		 * representation of the number is displayed.
		 * 
		 * @return
		 */
		public int getNumberCellWidth() {
			int size = getNumberWidth();
			final int dotWidthPixels = getDotWidth();
			if (vertical && size < dotWidthPixels)
				size = dotWidthPixels;
			return size;
		}

		/**
		 * Calculates the height in pixels of the area within which the textual
		 * representation of the number is displayed.
		 * 
		 * @return
		 */
		public int getNumberCellHeight() {
			int size = getNumberHeight();
			final int dotHeightPixels = getDotHeight();
			if (!vertical && size < dotHeightPixels)
				size = dotHeightPixels;
			return size;
		}

		/**
		 * Calculates the width in pixels of the dot area.
		 * 
		 * @return
		 */
		public int getDotCellWidth() {
			int size = getDotWidth();
			final int numberWidthPixels = getNumberWidth();
			if (vertical && size < numberWidthPixels)
				size = numberWidthPixels;
			return size;
		}

		/**
		 * Calculates the height in pixels of the dot area.
		 * 
		 * @return
		 */
		public int getDotCellHeight() {
			int size = getDotHeight();
			final int numberHeightPixels = getNumberHeight();
			if (!vertical && size < numberHeightPixels)
				size = numberHeightPixels;
			return size;
		}

		/**
		 * Calculates the width in pixels of the entire control based on the
		 * given value.
		 * 
		 * @param value
		 * @return
		 */
		public int computeBarWidth(final int value) {
			final int numberWidthPixels = getNumberWidth();
			final int dotWidthPixels = getDotWidth();
			final int dotSpacingPixels = getDotSpacing();
			int size = 0;
			if (!vertical) {
				if (!NumberPosition.HIDDEN.equals(numberPosition))
					size += numberWidthPixels;
				if (value > 0) {
					int effectiveValue = value;
					if (wrapPoint > 0 && effectiveValue > wrapPoint)
						effectiveValue = wrapPoint;
					size += (dotWidthPixels + dotSpacingPixels)
							* effectiveValue;
				}
			} else {
				size += getDotCellWidth();
				if (value > 0 && wrapPoint > 0) {
					int layers = value / wrapPoint;
					if (value > layers * wrapPoint)
						layers += 1;
					size += (dotWidthPixels + dotSpacingPixels) * (layers - 1);
				}
			}
			size += 4; // fudge
			return size;
		}

		/**
		 * Calculates the height in pixels of the entire control based on the
		 * given value.
		 * 
		 * @param value
		 * @return
		 */
		public int computeBarHeight(final int value) {
			final int numberHeightPixels = getNumberHeight();
			final int dotHeightPixels = getDotHeight();
			final int dotSpacingPixels = getDotSpacing();
			int size = 0;
			if (vertical) {
				if (numberPosition.index != NumberPosition.INDEX_HIDDEN)
					size += numberHeightPixels;
				if (value > 0) {
					int effectiveValue = value;
					if (wrapPoint > 0 && effectiveValue > wrapPoint)
						effectiveValue = wrapPoint;
					size += (dotHeightPixels + dotSpacingPixels)
							* effectiveValue;
				}
			} else {
				size += getDotCellHeight();
				if (value > 0 && wrapPoint > 0) {
					int layers = value / wrapPoint;
					if (value > layers * wrapPoint)
						layers += 1;
					size += (dotHeightPixels + dotSpacingPixels) * (layers - 1);
				}
			}
			size += 4; // fudge
			return size;
		}

		/**
		 * Renders an image using awt/swing. The swing image is displayed in the
		 * report
		 * 
		 * @param value
		 * @return
		 */
		public BufferedImage createSwingImage(final int value,
				final ExtendedItemHandle modelHandle) {
			final int dotWidthPixels = getDotWidth();
			final int dotHeightPixels = getDotHeight();
			final int dotSpacingPixels = getDotSpacing();
			// TODO padding
			java.awt.Graphics2D g2d = null;
			final int barWidth = computeBarWidth(value);
			final int barHeight = computeBarHeight(value);
			final int numberCellWidth = getNumberCellWidth();
			final int numberCellHeight = getNumberCellHeight();
			final int dotCellWidth = getDotCellWidth();
			final int dotCellHeight = getDotCellHeight();
			try {
				final BufferedImage bufferedImage = new BufferedImage(barWidth,
						barHeight, BufferedImage.TYPE_INT_ARGB);
				final Font font = getAwtFont();
				g2d = (Graphics2D) bufferedImage.getGraphics();
				g2d.setFont(font);
				final FontMetrics fontMetrics = g2d.getFontMetrics();
				final String text = String.valueOf(value);
				final Rectangle2D bounds = fontMetrics.getStringBounds(text,
						g2d);
				final double textHeight = bounds.getHeight();
				final double textWidth = bounds.getWidth();
				int x = 0;
				int y = 0;
				if (NumberPosition.BEFORE.equals(numberPosition)) {
					g2d.setColor(fontColor.getAwtColor());
					final double textX = x + numberCellWidth / 2.0 - textWidth
							/ 2.0;
					final double textY = y + numberCellHeight / 2.0
							+ textHeight / 2.0;
					g2d.drawString(text, (int) textX, (int) textY);
					// debug:
					// g2d.setColor( new java.awt.Color( 255, 0, 0 ) );
					// g2d.drawRect( position.x, position.y, numberWidth,
					// numberHeight );
					// g2d.setColor( new java.awt.Color( 0, 255, 0 ) );
					// g2d.drawRect( (int) textX, (int) textY - (int)
					// textHeight, (int) textWidth, (int) textHeight );
					if (vertical)
						y += numberCellHeight;
					else
						x += numberCellWidth;
				}
				final int startX = x;
				final int startY = y;
				if (fillColor != null)
					g2d.setBackground(fillColor.getAwtColor());
				if (borderColor != null)
					g2d.setColor(borderColor.getAwtColor());
				int dotCount = value;
				if (wrapPoint > 0 && dotCount > wrapPoint)
					dotCount = wrapPoint;
				int rowCount = 1;
				if (wrapPoint > 0)
					rowCount += value / wrapPoint;
				int remainingDots = value;
				for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
					final int dotLimit = remainingDots < dotCount ? remainingDots
							: dotCount;
					remainingDots -= dotCount;
					for (int dotIndex = 0; dotIndex < dotLimit; dotIndex++) {
						// center the dots
						final int dotX = x + dotCellWidth / 2 - dotWidthPixels
								/ 2;
						final int dotY = y + dotCellHeight / 2
								- dotHeightPixels / 2;
						// debug:
						// g2d.setColor( new java.awt.Color( 255, 0, 0 ) );
						// g2d.drawRect( position.x, position.y, dotAreaWidth,
						// dotAreaHeight );
						final java.awt.Color awtBorderColor = hasBorder ? borderColor
								.getAwtColor()
								: null;
						final java.awt.Color awtFillColor = hasFill ? fillColor
								.getAwtColor() : null;
						dotShape.render(g2d, dotX, dotY, dotWidthPixels,
								dotHeightPixels, awtBorderColor, awtFillColor);
						if (vertical)
							y += dotCellHeight + dotSpacingPixels;
						else
							x += dotCellWidth + dotSpacingPixels;
					}
					if (vertical) {
						y = startY;
						x += dotWidthPixels + dotSpacingPixels;
					} else {
						x = startX;
						y += dotHeightPixels + dotSpacingPixels;
					}
				}
				if (NumberPosition.AFTER.equals(numberPosition)) {
					if (vertical)
						x = 0;
					else
						y = 0;
					g2d.setColor(fontColor.getAwtColor());
					final double textX = x + numberCellWidth / 2.0 - textWidth
							/ 2.0;
					final double textY = y + numberCellHeight / 2.0
							+ textHeight / 2.0;
					g2d.drawString(text, (int) textX, (int) textY);
					if (vertical)
						y += numberCellHeight;
					else
						x += numberCellWidth;
				}
				return bufferedImage;
			} catch (final Exception e) {
				e.printStackTrace();
			} finally {
				if (g2d != null)
					g2d.dispose();
			}
			return null;
		}

		private Font getAwtFont() {
			int style = 0;
			if (fontBold)
				style |= Font.BOLD;
			if (fontItalic)
				style |= Font.ITALIC;
			final int size = getFontPointSize(fontSize, 96);
			final Font font = new Font(fontFamily, style, size);
			return font;
		}

		public int getDotWidth() {
			return convertToPixels(dotWidth);
		}

		public int getDotHeight() {
			return convertToPixels(dotHeight);
		}

		public int getDotSpacing() {
			return convertToPixels(dotSpacing);
		}

		public int getNumberWidth() {
			return convertToPixels(numberWidth);
		}

		public int getNumberHeight() {
			return convertToPixels(numberHeight);
		}
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof DotbarData))
			return false;
		final DotbarData that = (DotbarData) object;
		if (valueExpression == null ? that.valueExpression != null
				: !valueExpression.equals(that.valueExpression))
			return false;
		if (displayValue != that.displayValue)
			return false;
		if (dotWidth == null ? that.dotWidth != null : !dotWidth
				.equals(that.dotWidth))
			return false;
		if (dotHeight == null ? that.dotHeight != null : !dotHeight
				.equals(that.dotHeight))
			return false;
		if (dotSpacing == null ? that.dotSpacing != null : !dotSpacing
				.equals(that.dotSpacing))
			return false;
		if (vertical != that.vertical)
			return false;
		if (hasBorder != that.hasBorder)
			return false;
		if (!borderColor.equals(that.borderColor))
			return false;
		if (hasFill != that.hasFill)
			return false;
		if (!fillColor.equals(that.fillColor))
			return false;
		if (!dotShape.equals(that.dotShape))
			return false;
		if (!numberPosition.equals(that.numberPosition))
			return false;
		if (numberWidth == null ? that.numberWidth != null : !numberWidth
				.equals(that.numberWidth))
			return false;
		if (numberHeight == null ? that.numberHeight != null : !numberHeight
				.equals(that.numberHeight))
			return false;
		if (wrapPoint != that.wrapPoint)
			return false;
		if (fontSize == null ? that.fontSize != null : !fontSize
				.equals(that.fontSize))
			return false;
		if (fontFamily == null ? that.fontFamily != null : !fontFamily
				.equals(that.fontFamily))
			return false;
		if (fontItalic != that.fontItalic)
			return false;
		if (fontBold != that.fontBold)
			return false;
		if (fontColor == null ? that.fontColor != null : !fontColor
				.equals(that.fontColor))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int value = valueExpression == null ? 0 : valueExpression.hashCode();
		value += displayValue;
		if (dotWidth != null)
			value += dotWidth.hashCode();
		if (dotHeight != null)
			value += dotHeight.hashCode();
		if (dotSpacing != null)
			value += dotSpacing.hashCode();
		if (vertical)
			value += 1;
		if (hasBorder)
			value += 1;
		value += borderColor.hashCode();
		if (hasFill)
			value += 1;
		value += fillColor.hashCode();
		value += dotShape.hashCode();
		value += numberPosition.hashCode();
		if (numberWidth != null)
			value += numberWidth.hashCode();
		if (numberHeight != null)
			value += numberHeight.hashCode();
		value += wrapPoint;
		if (fontFamily != null)
			value += fontFamily.hashCode();
		if (fontSize != null)
			value += fontSize.hashCode();
		if (fontItalic)
			value += 1;
		if (fontBold)
			value += 1;
		return value;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("valueExpression=");
		sb.append(valueExpression);
		sb.append(", displayValue=");
		sb.append(displayValue);
		sb.append(", dotWidth=");
		sb.append(dotWidth);
		sb.append(", dotHeight=");
		sb.append(dotHeight);
		sb.append(", dotSpacing=");
		sb.append(dotSpacing);
		sb.append(", vertical=");
		sb.append(vertical);
		sb.append(", hasBorder=");
		sb.append(hasBorder);
		sb.append(", borderColor=");
		sb.append(borderColor);
		sb.append(", hasFill=");
		sb.append(hasFill);
		sb.append(", fillColor=");
		sb.append(fillColor);
		sb.append(", dotShape=");
		sb.append(dotShape);
		sb.append(", numberPosition=");
		sb.append(numberPosition);
		sb.append(", numberWidth=");
		sb.append(numberWidth);
		sb.append(", numberHeight=");
		sb.append(numberHeight);
		sb.append(", wrapPoint=");
		sb.append(wrapPoint);
		sb.append(", fontFamily=");
		sb.append(fontFamily);
		sb.append(", fontSize=");
		sb.append(fontSize);
		sb.append(", fontItalic=");
		sb.append(fontItalic);
		sb.append(", fontBold=");
		sb.append(fontBold);
		return sb.toString();
	}

	public static final Map<String, Integer> fontSizes;
	static {
		fontSizes = new HashMap<String, Integer>();
		fontSizes.put(DesignChoiceConstants.FONT_SIZE_XX_SMALL, Integer
				.valueOf(7));
		fontSizes.put(DesignChoiceConstants.FONT_SIZE_X_SMALL, Integer
				.valueOf(8));
		fontSizes
				.put(DesignChoiceConstants.FONT_SIZE_SMALL, Integer.valueOf(9));
		fontSizes.put(DesignChoiceConstants.FONT_SIZE_MEDIUM, Integer
				.valueOf(10));
		fontSizes.put(DesignChoiceConstants.FONT_SIZE_LARGE, Integer
				.valueOf(11));
		fontSizes.put(DesignChoiceConstants.FONT_SIZE_X_LARGE, Integer
				.valueOf(12));
		fontSizes.put(DesignChoiceConstants.FONT_SIZE_XX_LARGE, Integer
				.valueOf(13));
		fontSizes.put(DesignChoiceConstants.FONT_SIZE_SMALLER, Integer
				.valueOf(9));
		fontSizes.put(DesignChoiceConstants.FONT_SIZE_LARGER, Integer
				.valueOf(11));
	}

	public static int getFontPointSize(String string, final double pixelsPerInch) {
		string = string.trim().toLowerCase();
		final Integer fontSize = fontSizes.get(string);
		if (fontSize != null)
			return fontSize.intValue();
		DimensionValue dv;
		try {
			dv = DimensionValueUtil.doParse(string, true, null);
		} catch (final PropertyValueException e) {
			dv = new DimensionValue(12, DesignChoiceConstants.UNITS_PX);
			e.printStackTrace();
		}
		if (DimensionUtil.isAbsoluteUnit(dv.getUnits()))
			dv = DimensionUtil.convertTo(dv, DesignChoiceConstants.UNITS_PT,
					DesignChoiceConstants.UNITS_PT);
		else {
			final String units = dv.getUnits();
			final double measure = dv.getMeasure();
			if (DesignChoiceConstants.UNITS_PX.equals(units))
				dv = DimensionUtil.convertTo(measure / pixelsPerInch,
						DesignChoiceConstants.UNITS_IN,
						DesignChoiceConstants.UNITS_PT);
			else if (DesignChoiceConstants.UNITS_EM.equals(units))
				dv = new DimensionValue(measure * 10,
						DesignChoiceConstants.UNITS_PT);
			else if (DesignChoiceConstants.UNITS_EX.equals(units))
				dv = new DimensionValue(measure * 3.33,
						DesignChoiceConstants.UNITS_PT);
			else if (DesignChoiceConstants.UNITS_PERCENTAGE.equals(units))
				dv = new DimensionValue(measure * 0.1,
						DesignChoiceConstants.UNITS_PT);
		}
		return (int) dv.getMeasure();
	}
}
