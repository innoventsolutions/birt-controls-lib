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

import org.eclipse.birt.report.model.api.DimensionHandle;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.extension.ReportItem;
import org.eclipse.birt.report.model.api.metadata.DimensionValue;
import org.eclipse.birt.report.model.elements.interfaces.IStyleModel;

import blackboard.birt.extensions.util.ColorSpec;
import blackboard.birt.extensions.util.Util;

/**
 * Provides an interface allowing all control properties to be loaded from and saved to the report model.
 * 
 * @author Steve Schafer / Innovent Solutions
 */
public class DotbarItem extends ReportItem {
	public static final String EXTENSION_NAME = "DotBar"; //$NON-NLS-1$
	public static final String VALUE_EXPRESSION_PROP = "valueExpression";
	public static final String DISPLAY_VALUE_PROP = "displayValue";
	public static final String DOT_WIDTH_PROP = "dotWidth";
	public static final String DOT_HEIGHT_PROP = "dotHeight";
	public static final String DOT_SPACING_PROP = "dotSpacing";
	public static final String VERTICAL_PROP = "vertical";
	public static final String DOT_SHAPE_PROP = "dotShape";
	public static final String HAS_FILL_PROP = "hasFill";
	public static final String FILL_COLOR_PROP = "fillColor";
	public static final String HAS_BORDER_PROP = "hasBorder";
	public static final String BORDER_COLOR_PROP = "borderColor";
	public static final String NUMBER_POSITION_PROP = "numberPosition";
	public static final String NUMBER_WIDTH_PROP = "numberWidth";
	public static final String NUMBER_HEIGHT_PROP = "numberHeight";
	public static final String WRAP_POINT_PROP = "wrapPoint";
	public static final String FONT_FAMILY_PROP = IStyleModel.FONT_FAMILY_PROP;
	public static final String FONT_SIZE_PROP = IStyleModel.FONT_SIZE_PROP;
	public static final String FONT_STYLE_PROP = IStyleModel.FONT_STYLE_PROP;
	public static final String FONT_WEIGHT_PROP = IStyleModel.FONT_WEIGHT_PROP;
	public static final String FONT_COLOR_PROP = IStyleModel.COLOR_PROP;
	public static final String ALTERNATIVE_TEXT_PROP = "altText";
	private final ExtendedItemHandle modelHandle;

	DotbarItem(ExtendedItemHandle modelHandle) {
		this.modelHandle = modelHandle;
	}

	public ExtendedItemHandle getModelHandle() {
		return modelHandle;
	}

	public DotbarData getConfiguration() {
		DotbarData.Editor editor = new DotbarData.Editor();
		editor.valueExpression = getValueExpression();
		editor.displayValue = getDisplayValue();
		editor.dotWidth = getDotWidth();
		editor.dotHeight = getDotHeight();
		editor.dotSpacing = getDotSpacing();
		editor.vertical = isVertical();
		editor.dotShape = getDotShape();
		editor.hasBorder = hasBorder();
		editor.borderColor = getBorderColor();
		editor.hasFill = hasFill();
		editor.fillColor = getFillColor();
		editor.numberPosition = getNumberPosition();
		editor.numberWidth = getNumberWidth();
		editor.numberHeight = getNumberHeight();
		editor.wrapPoint = getWrapPoint();
		editor.fontSize = getFontSize();
		editor.fontFamily = getFontFamily();
		editor.fontItalic = isFontItalic();
		editor.fontBold = isFontBold();
		editor.fontColor = getFontColor();
		return editor.create();
	}

	private String getValueExpression() {
		return modelHandle.getStringProperty(VALUE_EXPRESSION_PROP);
	}

	private int getDisplayValue() {
		return modelHandle.getIntProperty(DISPLAY_VALUE_PROP);
	}

	private DimensionValue getDotWidth() {
		DimensionHandle handle = modelHandle
				.getDimensionProperty(DOT_WIDTH_PROP);
		return handle == null ? null : (DimensionValue) handle.getValue();
	}

	private DimensionValue getDotHeight() {
		DimensionHandle handle = modelHandle
				.getDimensionProperty(DOT_HEIGHT_PROP);
		return handle == null ? null : (DimensionValue) handle.getValue();
	}

	private DimensionValue getDotSpacing() {
		DimensionHandle handle = modelHandle
				.getDimensionProperty(DOT_SPACING_PROP);
		return handle == null ? null : (DimensionValue) handle.getValue();
	}

	private boolean isVertical() {
		return modelHandle.getBooleanProperty(VERTICAL_PROP);
	}

	private DotShape getDotShape() {
		return DotShape.create(modelHandle.getStringProperty(DOT_SHAPE_PROP));
	}

	private boolean hasBorder() {
		return modelHandle.getBooleanProperty(HAS_BORDER_PROP);
	}

	private ColorSpec getBorderColor() {
		return ColorSpec.getColor(modelHandle
				.getStringProperty(BORDER_COLOR_PROP));
	}

	private boolean hasFill() {
		return modelHandle.getBooleanProperty(HAS_FILL_PROP);
	}

	private ColorSpec getFillColor() {
		return ColorSpec.getColor(modelHandle
				.getStringProperty(FILL_COLOR_PROP));
	}

	private NumberPosition getNumberPosition() {
		return NumberPosition.create(modelHandle
				.getStringProperty(NUMBER_POSITION_PROP));
	}

	private DimensionValue getNumberWidth() {
		DimensionHandle handle = modelHandle
				.getDimensionProperty(NUMBER_WIDTH_PROP);
		return handle == null ? null : (DimensionValue) handle.getValue();
	}

	private DimensionValue getNumberHeight() {
		DimensionHandle handle = modelHandle
				.getDimensionProperty(NUMBER_HEIGHT_PROP);
		return handle == null ? null : (DimensionValue) handle.getValue();
	}

	private int getWrapPoint() {
		return modelHandle.getIntProperty(WRAP_POINT_PROP);
	}

	private String getFontSize() {
		return modelHandle.getStringProperty(FONT_SIZE_PROP);
	}

	private String getFontFamily() {
		return Util.removeQuotes(modelHandle
				.getStringProperty(FONT_FAMILY_PROP));
	}

	private boolean isFontItalic() {
		return "italic".equalsIgnoreCase(modelHandle
				.getStringProperty(FONT_STYLE_PROP));
	}

	private boolean isFontBold() {
		return "bold".equalsIgnoreCase(modelHandle
				.getStringProperty(FONT_WEIGHT_PROP));
	}

	private ColorSpec getFontColor() {
		return ColorSpec.getColor(modelHandle
				.getStringProperty(FONT_COLOR_PROP));
	}

	public void setConfiguration(DotbarData config) throws SemanticException {
		setValueExpression(config.valueExpression);
		setDisplayValue(config.displayValue);
		setDotWidth(config.dotWidth);
		setDotHeight(config.dotHeight);
		setDotSpacing(config.dotSpacing);
		setVertical(config.vertical);
		setDotShape(config.dotShape);
		setFill(config.hasFill);
		setFillColor(config.fillColor);
		setBorder(config.hasBorder);
		setBorderColor(config.borderColor);
		setNumberPosition(config.numberPosition);
		setNumberWidth(config.numberWidth);
		setNumberHeight(config.numberHeight);
		setWrapPoint(config.wrapPoint);
		setFontFamily(config.fontFamily);
		setFontSize(config.fontSize);
		setFontItalic(config.fontItalic);
		setFontBold(config.fontBold);
		setFontColor(config.fontColor);
	}

	public void set(String propName, Object text) throws SemanticException {
		modelHandle.setProperty(propName, text);
	}

	public String getString(String propName) {
		String string = modelHandle.getStringProperty(propName);
		return string;
	}

	public int getInt(String propName) {
		int value = modelHandle.getIntProperty(propName);
		return value;
	}

	public boolean getBoolean(String propName) {
		boolean value = modelHandle.getBooleanProperty(propName);
		return value;
	}

	public void setValueExpression(String valueExpression)
			throws SemanticException {
		modelHandle.setProperty(VALUE_EXPRESSION_PROP, valueExpression);
	}

	public void setDisplayValue(int displayValue) throws SemanticException {
		modelHandle.setProperty(DISPLAY_VALUE_PROP, displayValue);
	}

	public void setDotWidth(DimensionValue dotWidth) throws SemanticException {
		modelHandle.setProperty(DOT_WIDTH_PROP, dotWidth);
	}

	public void setDotHeight(DimensionValue dotHeight) throws SemanticException {
		modelHandle.setProperty(DOT_HEIGHT_PROP, dotHeight);
	}

	public void setDotSpacing(DimensionValue dotSpacing)
			throws SemanticException {
		modelHandle.setProperty(DOT_SPACING_PROP, dotSpacing);
	}

	public void setVertical(boolean vertical) throws SemanticException {
		modelHandle.setProperty(VERTICAL_PROP, vertical);
	}

	public void setDotShape(DotShape dotShape) throws SemanticException {
		modelHandle.setProperty(DOT_SHAPE_PROP, dotShape.getName());
	}

	public void setFill(boolean hasFill) throws SemanticException {
		modelHandle.setProperty(HAS_FILL_PROP, hasFill);
	}

	public void setFillColor(ColorSpec fillColor) throws SemanticException {
		modelHandle.setProperty(FILL_COLOR_PROP, fillColor.toRgbString());
	}

	public void setBorder(boolean hasBorder) throws SemanticException {
		modelHandle.setProperty(HAS_BORDER_PROP, hasBorder);
	}

	public void setBorderColor(ColorSpec borderColor) throws SemanticException {
		modelHandle.setProperty(BORDER_COLOR_PROP, borderColor.toRgbString());
	}

	public void setNumberPosition(NumberPosition numberPosition)
			throws SemanticException {
		modelHandle.setProperty(NUMBER_POSITION_PROP, numberPosition.getName());
	}

	public void setNumberWidth(DimensionValue numberWidth)
			throws SemanticException {
		modelHandle.setProperty(NUMBER_WIDTH_PROP, numberWidth);
	}

	public void setNumberHeight(DimensionValue numberHeight)
			throws SemanticException {
		modelHandle.setProperty(NUMBER_HEIGHT_PROP, numberHeight);
	}

	public void setWrapPoint(int wrapPoint) throws SemanticException {
		modelHandle.setProperty(WRAP_POINT_PROP, wrapPoint);
	}

	public void setFontFamily(String fontFamily) throws SemanticException {
		modelHandle.setProperty(FONT_FAMILY_PROP, fontFamily);
	}

	public void setFontSize(String fontSize) throws SemanticException {
		modelHandle.setProperty(FONT_SIZE_PROP, fontSize);
	}

	public void setFontItalic(boolean italic) throws SemanticException {
		modelHandle.setProperty(FONT_STYLE_PROP, italic ? "italic" : "normal");
	}

	public void setFontBold(boolean bold) throws SemanticException {
		modelHandle.setProperty(FONT_WEIGHT_PROP, bold ? "bold" : "normal");
	}

	public void setFontColor(ColorSpec fontColor) throws SemanticException {
		modelHandle.setProperty(FONT_COLOR_PROP, fontColor.toRgbString());
	}
}
