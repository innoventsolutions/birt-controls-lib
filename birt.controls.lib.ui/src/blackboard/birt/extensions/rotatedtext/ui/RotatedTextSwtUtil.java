/*******************************************************************************
 * Copyright (c) 2008-Present Innovent Solutions, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://eclipse.org/org/documents/epl-2.0/EPL-2.0.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *  Blackboard, Inc. - significant modification / refactor
 *  Steve Schafer - Innovent Solutions, Inc. - significant modification / refactor
 *  
 *******************************************************************************/
package blackboard.birt.extensions.rotatedtext.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Display;

import blackboard.birt.controls.SwtUtil;
import blackboard.birt.extensions.rotatedtext.RotatedTextData;
import blackboard.birt.extensions.util.Util;

/**
 * SwtGraphicsUtil
 */
public class RotatedTextSwtUtil {
	public static Color getSwtFontColor(final Device device,
			final RotatedTextData data) {
		return SwtUtil.getSwtColor(device, data.fontColor);
	}

	public static Font getSwtFont(final Device device,
			final RotatedTextData data) {
		final FontData fontData = new FontData();
		fontData.setHeight(Util.getFontPointSize(data.fontSize, 96));
		fontData.setName(data.fontFamily);
		int style = SWT.NORMAL;
		if (data.fontBold)
			style |= SWT.BOLD;
		if (data.fontItalic)
			style |= SWT.ITALIC;
		fontData.setStyle(style);
		final Font font = new Font(device, fontData);
		return font;
	}

	public static Image createRotatedTextImage(final RotatedTextData data) {
		GC gc = null;
		try {
			if (data.text == null || data.text.trim().length() == 0)
				return null;

			final String text = data.text;
			// String text;
			// {
			// StringBuilder sb = new StringBuilder();
			// sb.append("\u4e2a\u4e13\u4e2a\u4e13");
			// sb.append(data.text);
			// text = sb.toString();
			// }
			final Display display = Display.getCurrent();

			final Font font = RotatedTextSwtUtil.getSwtFont(display, data);
			gc = new GC(display);
			gc.setFont(font);

			final Point pt = gc.textExtent(text);
			gc.dispose();

			final TextLayout tl = new TextLayout(display);
			if (font != null)
				tl.setFont(font);
			tl.setText(text);

			return createRotatedImage(tl, pt.x, pt.y, data.angle,
					getSwtFontColor(display, data));
		} catch (final Exception e) {
			e.printStackTrace();

			if (gc != null && !gc.isDisposed())
				gc.dispose();
		}

		return null;
	}

	/**
	 * @return Returns as [rotatedWidth, rotatedHeight, xOffset, yOffset]
	 */
	public static double[] computedRotatedInfo(final int width,
			final int height, int angle) {
		angle = angle % 360;

		if (angle < 0)
			angle += 360;

		if (angle == 0)
			return new double[] { width, height, 0, 0 };

		if (angle == 90)
			return new double[] { height, width, -width, 0 };

		if (angle == 180)
			return new double[] { width, height, -width, -height };

		if (angle == 270)
			return new double[] { height, width, 0, -height };

		if (angle > 0 && angle < 90) {
			final double angleInRadians = ((-angle * Math.PI) / 180.0);
			final double cosTheta = Math.abs(Math.cos(angleInRadians));
			final double sineTheta = Math.abs(Math.sin(angleInRadians));

			final int dW = (int) (width * cosTheta + height * sineTheta);
			final int dH = (int) (width * sineTheta + height * cosTheta);

			return new double[] { dW, dH, -width * sineTheta * sineTheta,
					width * sineTheta * cosTheta };
		}

		if (angle > 90 && angle < 180) {
			final double angleInRadians = ((-angle * Math.PI) / 180.0);
			final double cosTheta = Math.abs(Math.cos(angleInRadians));
			final double sineTheta = Math.abs(Math.sin(angleInRadians));

			final int dW = (int) (width * cosTheta + height * sineTheta);
			final int dH = (int) (width * sineTheta + height * cosTheta);

			return new double[] { dW, dH,
					-(width + height * sineTheta * cosTheta), -height / 2 };
		}

		if (angle > 180 && angle < 270) {
			final double angleInRadians = ((-angle * Math.PI) / 180.0);
			final double cosTheta = Math.abs(Math.cos(angleInRadians));
			final double sineTheta = Math.abs(Math.sin(angleInRadians));

			final int dW = (int) (width * cosTheta + height * sineTheta);
			final int dH = (int) (width * sineTheta + height * cosTheta);

			return new double[] { dW, dH, -(width * cosTheta * cosTheta),
					-(height + width * cosTheta * sineTheta) };
		}

		if (angle > 270 && angle < 360) {
			final double angleInRadians = ((-angle * Math.PI) / 180.0);
			final double cosTheta = Math.abs(Math.cos(angleInRadians));
			final double sineTheta = Math.abs(Math.sin(angleInRadians));

			final int dW = (int) (width * cosTheta + height * sineTheta);
			final int dH = (int) (width * sineTheta + height * cosTheta);

			return new double[] { dW, dH, (height * cosTheta * sineTheta),
					-(height * sineTheta * sineTheta) };
		}

		return new double[] { width, height, 0, 0 };
	}

	private static Image createRotatedImage(final Object src, final int width,
			final int height, int angle, final Color fontColor) {
		angle = angle % 360;

		if (angle < 0)
			angle += 360;

		final double[] info = computedRotatedInfo(width, height, angle);
		return renderRotatedObject(src, -angle, (int) info[0], (int) info[1],
				info[2], info[3], fontColor);
	}

	private static Image renderRotatedObject(final Object src,
			final double angle, final int width, final int height,
			final double tx, final double ty, final Color fontColor) {
		final Display display = Display.getCurrent();

		Image dest = null;
		GC gc = null;
		Transform tf = null;

		try {
			dest = new Image(Display.getCurrent(), width, height);
			gc = new GC(dest);

			gc.setAdvanced(true);
			gc.setAntialias(SWT.ON);
			gc.setTextAntialias(SWT.ON);
			gc.setForeground(fontColor);

			tf = new Transform(display);
			tf.rotate((float) angle);
			tf.translate((float) tx, (float) ty);

			gc.setTransform(tf);

			if (src instanceof TextLayout) {
				final TextLayout tl = (TextLayout) src;
				tl.draw(gc, 0, 0);
			} else if (src instanceof Image) {
				gc.drawImage((Image) src, 0, 0);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			if (gc != null && !gc.isDisposed()) {
				gc.dispose();
			}

			if (tf != null && !tf.isDisposed()) {
				tf.dispose();
			}
		}

		return dest;
	}
}
