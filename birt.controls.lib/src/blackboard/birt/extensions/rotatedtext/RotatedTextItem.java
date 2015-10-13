/*
 * Copyright (c) 2008-2015  Innovent Solutions, Inc.
 * 
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms 
 * of the Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * 	Scott Rosenbaum - Innovent Solutions
 *  Steve Schafer - Innovent Solutions
 * 				 
 */
package blackboard.birt.extensions.rotatedtext;

import org.eclipse.birt.report.model.api.DimensionHandle;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.extension.ReportItem;
import org.eclipse.birt.report.model.api.metadata.DimensionValue;
import org.eclipse.birt.report.model.elements.interfaces.IStyleModel;

import blackboard.birt.extensions.util.ColorSpec;
import blackboard.birt.extensions.util.Util;

/**
 * Provides an interface allowing all control properties to be loaded from and
 * saved to the report model.
 * 
 * @author Steve Schafer / Innovent Solutions
 */
public class RotatedTextItem extends ReportItem {
	public static final String EXTENSION_NAME = "RotatedText"; //$NON-NLS-1$
	public static final String TEXT_PROP = "text"; //$NON-NLS-1$
	public static final String ROTATION_ANGLE_PROP = "rotationAngle"; //$NON-NLS-1$
	public static final String WRAP_POINT_PROP = "wrapPoint"; //$NON-NLS-1$
	public static final String FONT_FAMILY_PROP = IStyleModel.FONT_FAMILY_PROP;
	public static final String FONT_SIZE_PROP = IStyleModel.FONT_SIZE_PROP;
	public static final String FONT_STYLE_PROP = IStyleModel.FONT_STYLE_PROP;
	public static final String FONT_WEIGHT_PROP = IStyleModel.FONT_WEIGHT_PROP;
	public static final String FONT_COLOR_PROP = IStyleModel.COLOR_PROP;
	public static final String LINK_URL_PROP = "linkURL"; //$NON-NLS-1$

	private final ExtendedItemHandle modelHandle;

	RotatedTextItem(final ExtendedItemHandle modelHandle) {
		this.modelHandle = modelHandle;
	}

	public ExtendedItemHandle getModelHandle() {
		return modelHandle;
	}

	public String getText() {
		return modelHandle.getStringProperty(TEXT_PROP);
	}

	public void setText(final String value) throws SemanticException {
		modelHandle.setProperty(TEXT_PROP, value);
	}

	public int getRotationAngle() {
		return modelHandle.getIntProperty(ROTATION_ANGLE_PROP);
	}

	public void setRotationAngle(final int value) throws SemanticException {
		modelHandle.setProperty(ROTATION_ANGLE_PROP, value);
	}

	public DimensionValue getWrapPoint() {
		final DimensionHandle dimensionHandle = modelHandle
				.getDimensionProperty(WRAP_POINT_PROP);
		final Object value = dimensionHandle.getValue();
		return (DimensionValue) value;
	}

	public void setWrapPoint(final DimensionValue value)
			throws SemanticException {
		modelHandle.setProperty(WRAP_POINT_PROP, value);
	}

	public String getFontFamily() {
		String fontFamily = modelHandle.getStringProperty(FONT_FAMILY_PROP);
		fontFamily = Util.removeQuotes(fontFamily);
		if (fontFamily.startsWith("@"))
			fontFamily = fontFamily.substring(1);
		return fontFamily;
	}

	public void setFontFamily(final String fontFamily) throws SemanticException {
		modelHandle
				.setProperty(FONT_FAMILY_PROP, Util.removeQuotes(fontFamily));
	}

	public String getFontSize() {
		return modelHandle.getStringProperty(FONT_SIZE_PROP);
	}

	public void setFontSize(final String fontSize) throws SemanticException {
		modelHandle.setProperty(FONT_SIZE_PROP, fontSize);
	}

	public boolean isFontItalic() {
		return "italic".equalsIgnoreCase(modelHandle
				.getStringProperty(FONT_STYLE_PROP));
	}

	public void setFontItalic(final boolean italic) throws SemanticException {
		modelHandle.setProperty(FONT_STYLE_PROP, italic ? "italic" : "normal");
	}

	public boolean isFontBold() {
		return "bold".equalsIgnoreCase(modelHandle
				.getStringProperty(FONT_WEIGHT_PROP));
	}

	public void setFontBold(final boolean bold) throws SemanticException {
		modelHandle.setProperty(FONT_WEIGHT_PROP, bold ? "bold" : "normal");
	}

	public ColorSpec getFontColor() {
		return ColorSpec.getColor(modelHandle
				.getStringProperty(FONT_COLOR_PROP));
	}

	public void setFontColor(final ColorSpec fontColor)
			throws SemanticException {
		modelHandle.setProperty(FONT_COLOR_PROP, fontColor.toRgbString());
	}

	public String getLinkURL() {
		return modelHandle.getStringProperty(LINK_URL_PROP);
	}

	public void setLinkURL(final String linkURL) throws SemanticException {
		modelHandle.setProperty(LINK_URL_PROP, linkURL);
	}
}
