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
		this.text = textItem.getText();
		this.angle = textItem.getRotationAngle();
		this.wrapPoint = textItem.getWrapPoint();
		this.fontFamily = textItem.getFontFamily();
		this.fontSize = textItem.getFontSize();
		this.fontBold = textItem.isFontBold();
		this.fontItalic = textItem.isFontItalic();
		this.fontColor = textItem.getFontColor();
		this.linkURL = textItem.getLinkURL();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RotatedTextData))
			return false;
		RotatedTextData that = (RotatedTextData) obj;
		if (this.text == null ? that.text != null : !this.text
				.equals(that.text))
			return false;
		if (this.angle != that.angle)
			return false;
		if (this.wrapPoint != that.wrapPoint)
			return false;
		if (this.fontFamily == null ? that.fontFamily != null
				: !this.fontFamily.equalsIgnoreCase(that.fontFamily))
			return false;
		if (this.fontSize == null ? that.fontSize != null : !this.fontSize
				.equalsIgnoreCase(that.fontSize))
			return false;
		if (this.fontBold != that.fontBold)
			return false;
		if (this.fontItalic != that.fontItalic)
			return false;
		if (this.fontColor == null ? that.fontColor != null : !this.fontColor
				.equals(that.fontColor))
			return false;
		if (this.linkURL == null ? that.linkURL != null : !this.linkURL
				.equals(that.linkURL))
			return false;
		return true;
	}
}
