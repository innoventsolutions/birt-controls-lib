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

/**
 * An immutable enumeration of dot shapes.
 * 
 * @author Steve Schafer / Innovent Solutions
 */
public class DotShape {
	public final int index;

	private DotShape(final int index) {
		this.index = index;
	}

	/**
	 * The index for the oval shape
	 */
	public static final int INDEX_OVAL = 0;
	/**
	 * The index for the rectangle shape
	 */
	public static final int INDEX_RECTANGLE = 1;
	/**
	 * The index for the triangle shape.
	 */
	public static final int INDEX_TRIANGLE = 2;
	/**
	 * The oval shape.
	 */
	public static final DotShape OVAL = new DotShape(INDEX_OVAL);
	/**
	 * The rectangle shape.
	 */
	public static final DotShape RECTANGLE = new DotShape(INDEX_RECTANGLE);
	/**
	 * The triangle shape.
	 */
	public static final DotShape TRIANGLE = new DotShape(INDEX_TRIANGLE);
	private static final String[] names = { "Oval", "Rectangle", "Triangle" };
	private static final DotShape[] objects = { OVAL, RECTANGLE, TRIANGLE };

	/**
	 * Render the shape for report generation using awt/swing.
	 * 
	 * @param g2d
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param borderColor
	 * @param fillColor
	 */
	public void render(final java.awt.Graphics2D g2d, final int x, final int y,
			final int width, final int height,
			final java.awt.Color borderColor, final java.awt.Color fillColor) {
		switch (index) {
		case INDEX_OVAL:
			if (borderColor != null) {
				g2d.setColor(borderColor);
				g2d.drawOval(x, y, width, height);
			}
			if (fillColor != null) {
				g2d.setColor(fillColor);
				g2d.fillOval(x, y, width, height);
			}
			break;
		case INDEX_RECTANGLE:
			if (borderColor != null) {
				g2d.setColor(borderColor);
				g2d.drawRect(x, y, width, height);
			}
			if (fillColor != null) {
				g2d.setColor(fillColor);
				g2d.fillRect(x, y, width, height);
			}
			break;
		case INDEX_TRIANGLE:
			final int x1 = x + width / 2;
			final int y1 = y;
			final int x2 = x + width;
			final int y2 = y + height;
			final int x3 = x;
			final int y3 = y + height;
			final int[] xPoints = { x1, x2, x3 };
			final int[] yPoints = { y1, y2, y3 };
			final int nPoints = 3;
			if (borderColor != null) {
				g2d.setColor(borderColor);
				g2d.drawPolygon(xPoints, yPoints, nPoints);
			}
			if (fillColor != null) {
				g2d.setColor(fillColor);
				g2d.fillPolygon(xPoints, yPoints, nPoints);
			}
			break;
		}
	}

	/**
	 * Get one of the shapes.
	 * 
	 * @param string
	 * @return
	 */
	public static DotShape create(final String string) {
		for (int i = 0; i < names.length; i++)
			if (names[i].equalsIgnoreCase(string))
				return objects[i];
		return OVAL;
	}

	/**
	 * Get all the shape names.
	 * 
	 * @return
	 */
	public static String[] getNames() {
		final String[] newArray = new String[names.length];
		for (int i = 0; i < names.length; i++)
			newArray[i] = names[i];
		return newArray;
	}

	/**
	 * Returns the name of this shape
	 * 
	 * @return
	 */
	public String getName() {
		return names[index];
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof DotShape))
			return false;
		final DotShape that = (DotShape) obj;
		return index == that.index;
	}

	@Override
	public int hashCode() {
		return index;
	}

	@Override
	public String toString() {
		return names[index];
	}
}
