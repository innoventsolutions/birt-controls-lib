/*
 * Copyright (c) 2008-Present Innovent Solutions, Inc.
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

import org.eclipse.birt.report.model.api.metadata.DimensionValue;

import blackboard.birt.extensions.util.ColorSpec;

/**
 * Encapsulates all control properties in one object.
 * 
 * @author Steve Schafer / Innovent Solutions
 */
public class RotatedTextData {
	public final String text;
	public final int angle;
	public final DimensionValue wrapPoint;
	public final String fontFamily;
	public final String fontSize;
	public final boolean fontBold;
	public final boolean fontItalic;
	public final ColorSpec fontColor;
	public final String linkURL;

	public RotatedTextData(final RotatedTextItem textItem) {
		text = textItem.getText();
		angle = textItem.getRotationAngle();
		wrapPoint = textItem.getWrapPoint();
		fontFamily = textItem.getFontFamily();
		fontSize = textItem.getFontSize();
		fontBold = textItem.isFontBold();
		fontItalic = textItem.isFontItalic();
		fontColor = textItem.getFontColor();
		linkURL = textItem.getLinkURL();
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof RotatedTextData))
			return false;
		final RotatedTextData that = (RotatedTextData) obj;
		if (text == null ? that.text != null : !text.equals(that.text))
			return false;
		if (angle != that.angle)
			return false;
		if (wrapPoint != that.wrapPoint)
			return false;
		if (fontFamily == null ? that.fontFamily != null : !fontFamily
				.equalsIgnoreCase(that.fontFamily))
			return false;
		if (fontSize == null ? that.fontSize != null : !fontSize
				.equalsIgnoreCase(that.fontSize))
			return false;
		if (fontBold != that.fontBold)
			return false;
		if (fontItalic != that.fontItalic)
			return false;
		if (fontColor == null ? that.fontColor != null : !fontColor
				.equals(that.fontColor))
			return false;
		if (linkURL == null ? that.linkURL != null : !linkURL
				.equals(that.linkURL))
			return false;
		return true;
	}
}
