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

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.birt.report.model.api.util.DimensionUtil;

import blackboard.birt.extensions.util.Util;

/**
 * SwingGraphicsUtil
 */
public class SwingGraphicsUtil {
	public static BufferedImage createRotatedTextImage(final String text,
			final RotatedTextData data, final int dpi, final double baseSize,
			final String baseSizeUnits) {
		Graphics2D g2d = null;
		try {
			if (text == null || text.trim().length() == 0)
				return create1pxImage();
			final Font font = getAwtFont(data, dpi);
			final BufferedImage stringImage = new BufferedImage(1, 1,
					BufferedImage.TYPE_INT_ARGB);
			g2d = (Graphics2D) stringImage.getGraphics();
			g2d.setFont(font);
			final LineInfo[] lines;
			{
				final double inches = DimensionUtil.convertTo(data.wrapPoint,
						"in", "in", baseSize, baseSizeUnits, dpi);
				final double pixels = inches * dpi;
				lines = wrapText(g2d, text, (int) pixels);
			}
			final FontRenderContext frc = g2d.getFontRenderContext();
			int width = 0;
			int height = 0;
			for (final LineInfo lineInfo : lines) {
				final int lineWidth = (int) Math.ceil(lineInfo.bounds
						.getWidth());
				if (width < lineWidth)
					width = lineWidth;
				height += (int) Math.ceil(lineInfo.bounds.getHeight());
				// note: TextLayout doesn't like a zero-length string
				lineInfo.textLayout = lineInfo.line.length() == 0 ? null
						: new TextLayout(lineInfo.line, font, frc);
			}
			// height += 6; // fudge
			g2d.dispose();
			g2d = null;
			return createRotatedImage(lines, width, height, data.angle,
					data.fontColor.getAwtColor());
		} catch (final Exception e) {
			e.printStackTrace();

			if (g2d != null)
				g2d.dispose();
		}

		return create1pxImage();
	}

	private static BufferedImage create1pxImage() {
		// note: can't create a 0x0 image
		final BufferedImage bufferedImage = new BufferedImage(1, 1,
				BufferedImage.TYPE_INT_ARGB);
		return bufferedImage;
	}

	private static BufferedImage createRotatedImage(final LineInfo[] lines,
			final int width, final int height, int angle, final Color color) {
		angle = angle % 360;

		if (angle < 0)
			angle += 360;

		if (angle == 0)
			return renderRotatedObject(lines, 0, width, height, 0, 0, color);

		if (angle == 90)
			return renderRotatedObject(lines, -Math.PI / 2, height, width,
					-width, 0, color);

		if (angle == 180)
			return renderRotatedObject(lines, Math.PI, width, height, -width,
					-height, color);

		if (angle == 270)
			return renderRotatedObject(lines, Math.PI / 2, height, width, 0,
					-height, color);

		if (angle > 0 && angle < 90) {
			final double angleInRadians = ((-angle * Math.PI) / 180.0);
			final double cosTheta = Math.abs(Math.cos(angleInRadians));
			final double sineTheta = Math.abs(Math.sin(angleInRadians));

			final int dW = (int) (width * cosTheta + height * sineTheta);
			final int dH = (int) (width * sineTheta + height * cosTheta);

			return renderRotatedObject(lines, angleInRadians, dW, dH, -width
					* sineTheta * sineTheta, width * sineTheta * cosTheta,
					color);
		}

		if (angle > 90 && angle < 180) {
			final double angleInRadians = ((-angle * Math.PI) / 180.0);
			final double cosTheta = Math.abs(Math.cos(angleInRadians));
			final double sineTheta = Math.abs(Math.sin(angleInRadians));

			final int dW = (int) (width * cosTheta + height * sineTheta);
			final int dH = (int) (width * sineTheta + height * cosTheta);

			return renderRotatedObject(lines, angleInRadians, dW, dH,
					-(width + height * sineTheta * cosTheta), -height / 2,
					color);
		}

		if (angle > 180 && angle < 270) {
			final double angleInRadians = ((-angle * Math.PI) / 180.0);
			final double cosTheta = Math.abs(Math.cos(angleInRadians));
			final double sineTheta = Math.abs(Math.sin(angleInRadians));

			final int dW = (int) (width * cosTheta + height * sineTheta);
			final int dH = (int) (width * sineTheta + height * cosTheta);

			return renderRotatedObject(lines, angleInRadians, dW, dH, -(width
					* cosTheta * cosTheta), -(height + width * cosTheta
					* sineTheta), color);
		}

		if (angle > 270 && angle < 360) {
			final double angleInRadians = ((-angle * Math.PI) / 180.0);
			final double cosTheta = Math.abs(Math.cos(angleInRadians));
			final double sineTheta = Math.abs(Math.sin(angleInRadians));

			final int dW = (int) (width * cosTheta + height * sineTheta);
			final int dH = (int) (width * sineTheta + height * cosTheta);

			return renderRotatedObject(lines, angleInRadians, dW, dH, (height
					* cosTheta * sineTheta), -(height * sineTheta * sineTheta),
					color);
		}

		return renderRotatedObject(lines, 0, width, height, 0, 0, color);
	}

	private static BufferedImage renderRotatedObject(final LineInfo[] lines,
			final double angle, final int width, final int height,
			final double tx, final double ty, final Color color) {
		final BufferedImage bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);

		final Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
		g2d.setColor(Color.black);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		final AffineTransform at = AffineTransform.getRotateInstance(angle);
		at.translate(tx, ty);
		g2d.setTransform(at);
		g2d.setColor(color);

		float y = 0;
		for (final LineInfo lineInfo : lines) {
			if (lineInfo.textLayout != null)
				lineInfo.textLayout.draw(g2d, 0, y
						+ (int) Math.ceil(lineInfo.textLayout.getLeading()
								+ lineInfo.textLayout.getAscent()));
			y += lineInfo.bounds.getHeight();
		}
		g2d.dispose();
		return bufferedImage;
	}

	public static final class LineInfo {
		final String line;
		final Rectangle2D bounds;
		TextLayout textLayout = null;

		public LineInfo(final String line, final Rectangle2D bounds) {
			if (line == null)
				throw new NullPointerException("line");
			this.line = line;
			if (bounds == null)
				throw new NullPointerException("bounds");
			this.bounds = bounds;
		}
	}

	public static LineInfo[] wrapText(final Graphics2D g2d, final String text,
			final int wrapPoint) {
		final List<LineInfo> lines = new ArrayList<LineInfo>();
		final String[] hardLines = text.split("\\n");
		for (String hardLine : hardLines) {
			boolean hasMore = true;
			while (hasMore && hardLine.length() > 0) {
				final WrapInfo wrapInfo = getWrapInfo(g2d, hardLine, wrapPoint);
				lines.add(new LineInfo(wrapInfo.segment, wrapInfo.bounds));
				hardLine = wrapInfo.remainder;
				hasMore = wrapInfo.hasMore;
			}
		}
		return lines.toArray(new LineInfo[0]);
	}

	private static final class WrapInfo {
		final String segment;
		final String remainder;
		final Rectangle2D bounds;
		final boolean hasMore;

		public WrapInfo(final String segment, final String remainder,
				final Rectangle2D bounds, final boolean hasMore) {
			if (segment == null)
				throw new NullPointerException("segment");
			this.segment = segment;
			if (remainder == null)
				throw new NullPointerException("remainder");
			this.remainder = remainder;
			if (bounds == null)
				throw new NullPointerException("bounds");
			this.bounds = bounds;
			this.hasMore = hasMore;
		}
	}

	public static WrapInfo getWrapInfo(final Graphics2D g2d, final String line,
			final int wrapPoint) {
		int position = line.length();
		boolean spacesRemain = true;
		String segment = line;
		String remainder = "";
		boolean hasMore = false;
		final FontMetrics fm = g2d.getFontMetrics();
		Rectangle2D bounds = fm.getStringBounds(segment, g2d);
		if (wrapPoint > 0) {
			while (position > 1 && bounds.getWidth() > wrapPoint) {
				if (spacesRemain) {
					final int newPosition = findPotentialWrapPoint(segment);
					if (newPosition >= 0) {
						position = newPosition;
						segment = line.substring(0, position);
						remainder = line.substring(position + 1);
					} else {
						position -= 1;
						segment = line.substring(0, position);
						remainder = line.substring(position);
						spacesRemain = false;
					}
				} else {
					position -= 1;
					segment = line.substring(0, position);
					remainder = line.substring(position);
				}
				bounds = fm.getStringBounds(segment, g2d);
				hasMore = true;
			}
		}
		return new WrapInfo(trimTrailingWhitespace(segment),
				trimLeadingWhitespace(remainder), bounds, hasMore);
	}

	private static String trimLeadingWhitespace(final String string) {
		int pos = 0;
		while (pos < string.length()
				&& Character.isWhitespace(string.charAt(pos)))
			pos++;
		if (pos == 0)
			return string;
		return string.substring(pos);
	}

	private static String trimTrailingWhitespace(final String string) {
		int len = string.length();
		while (len > 0 && Character.isWhitespace(string.charAt(len - 1)))
			len--;
		if (len == string.length())
			return string;
		return string.substring(0, len);
	}

	private static int findPotentialWrapPoint(final String segment) {
		int len = segment.length();
		while (len > 0) {
			len--;
			final char c = segment.charAt(len);
			if (Character.isWhitespace(c))
				return len;
		}
		return -1;
	}

	private static Font getAwtFont(final RotatedTextData data, final int dpi) {
		// Font foundFont = findFont( data.fontFamily );
		int style = 0;
		if (data.fontBold)
			style |= Font.BOLD;
		if (data.fontItalic)
			style |= Font.ITALIC;
		// font = font.deriveFont( style );
		final int size = Util.getFontPointSize(data.fontSize, dpi);
		// font = font.deriveFont( (float) size );
		final Font font = new Font(data.fontFamily, style, size);
		return font;
	}

	public static Font findFont(final String fontFamily) {
		final GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		for (final Font font : ge.getAllFonts()) {
			final String family = font.getFamily();
			if (family.equals(fontFamily))
				return font;
		}
		return new Font("Dialog", 0, 1);
	}
}
