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
package blackboard.birt.extensions.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a color. Similar to org.eclipse.swt.graphics.RGB except that it's immutable and can be constructed from a string.
 * 
 * @author Steve Schafer / Innovent Solutions
 */
public final class ColorSpec {
	public final int r;
	public final int g;
	public final int b;

	private static final Pattern shortHexPattern = Pattern.compile(
			"#([0-9A-Fa-f])([0-9A-Fa-f])([0-9A-Fa-f])", 0);
	private static final Pattern longHexPattern = Pattern
			.compile(
					"#([0-9A-Fa-f][0-9A-Fa-f])([0-9A-Fa-f][0-9A-Fa-f])([0-9A-Fa-f][0-9A-Fa-f])",
					0);
	private static final Pattern rgbPattern = Pattern
			.compile(
					"[ ]*RGB[ ]*\\([ ]*([0-9]+)[ ]*,[ ]*([0-9]+)[ ]*,[ ]*([0-9]+)[ ]*\\)[ ]*",
					Pattern.CASE_INSENSITIVE);

	/**
	 * Construct a ColorSpec from the given red, green and blue components.
	 * 
	 * @param r
	 * @param g
	 * @param b
	 */
	public ColorSpec(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	/**
	 * Parses a string in one of the following forms: #XXXXXX, #XXX, RGB(n, n, n), where X is a hex digit and n is a decimal digit.
	 * 
	 * @param string
	 * @return
	 */
	public static ColorSpec getColor(final String string) {
		if (string == null)
			return new ColorSpec(0, 0, 0);
		{
			Matcher matcher = shortHexPattern.matcher(string);
			if (matcher.matches()) {
				String rString = matcher.group(1);
				String gString = matcher.group(2);
				String bString = matcher.group(3);
				int r = Integer.parseInt(rString, 16) * 16;
				int g = Integer.parseInt(gString, 16) * 16;
				int b = Integer.parseInt(bString, 16) * 16;
				return new ColorSpec(r, g, b);
			}
		}
		{
			Matcher matcher = longHexPattern.matcher(string);
			if (matcher.matches()) {
				String rString = matcher.group(1);
				String gString = matcher.group(2);
				String bString = matcher.group(3);
				int r = Integer.parseInt(rString, 16);
				int g = Integer.parseInt(gString, 16);
				int b = Integer.parseInt(bString, 16);
				return new ColorSpec(r, g, b);
			}
		}
		{
			Matcher matcher = rgbPattern.matcher(string);
			if (matcher.matches()) {
				String rString = matcher.group(1);
				String gString = matcher.group(2);
				String bString = matcher.group(3);
				int r = Integer.parseInt(rString);
				int g = Integer.parseInt(gString);
				int b = Integer.parseInt(bString);
				return new ColorSpec(r, g, b);
			}
		}
		// Note: colors are not saved using names so this code is unnecessary
		//    final ColorPropertyType type = (ColorPropertyType) MetaDataDictionary.getInstance().getPropertyType( PropertyType.COLOR_TYPE );
		//    final IChoiceSet choiceSet = type.getChoices();
		//    final IChoice[] choiceArray = choiceSet.getChoices();
		//    for ( IChoice choice : choiceArray )
		//    {
		//      final String colorName = choice.getName();
		//      if ( colorName.equalsIgnoreCase( string ) )
		//      {
		//        int rgb = ColorUtil.parsePredefinedColor( colorName );
		//        final int b = rgb & 0xFF;
		//        rgb >>= 8;
		//        final int g = rgb & 0xFF;
		//        rgb >>= 8;
		//        final int r = rgb & 0xFF;
		//        return new ColorSpec(r, g, b);
		//      }
		//    }
		return new ColorSpec(0, 0, 0);
	}

	/**
	 * Returns an AWT color object.
	 * 
	 * @return
	 */
	public java.awt.Color getAwtColor() {
		return new java.awt.Color(r, g, b);
	}

	/**
	 * Returns a string in the form RGB(n, n, n)
	 * 
	 * @return
	 */
	public String toRgbString() {
		StringBuilder sb = new StringBuilder();
		sb.append("RGB(");
		sb.append(r);
		sb.append(",");
		sb.append(g);
		sb.append(",");
		sb.append(b);
		sb.append(")");
		return sb.toString();
	}

	/**
	 * Returns a string in the form #XXXXXX
	 * 
	 * @return
	 */
	public String toHexString() {
		StringBuilder sb = new StringBuilder();
		sb.append("#");
		sb.append(toHexString(r));
		sb.append(toHexString(g));
		sb.append(toHexString(b));
		return sb.toString();
	}

	private static String toHexString(int i) {
		String string = Integer.toHexString(i);
		while (string.length() < 2)
			string = "0" + string;
		return string;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ColorSpec))
			return false;
		ColorSpec that = (ColorSpec) obj;
		return this.r == that.r && this.g == that.g && this.b == that.b;
	}

	@Override
	public int hashCode() {
		return r + g + b;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(r);
		sb.append(", ");
		sb.append(g);
		sb.append(", ");
		sb.append(b);
		return sb.toString();
	}
}