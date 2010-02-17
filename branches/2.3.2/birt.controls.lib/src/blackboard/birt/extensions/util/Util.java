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

public class Util {
	public static final int XX_LARGE = 18;
	public static final int X_LARGE = 16;
	public static final int LARGE = 14;
	public static final int MEDIUM = 12;
	public static final int SMALL = 10;
	public static final int X_SMALL = 8;
	public static final int XX_SMALL = 6;

	/**
	 * Parses a string and converts it to an integer point size.
	 * 
	 * @param string
	 * @param resolution
	 * @return
	 */
	public static int getFontPointSize(String string, final int resolution) {
		string = string.trim().toLowerCase();
		if ("xx-large".equalsIgnoreCase(string))
			return XX_LARGE;
		if ("x-large".equalsIgnoreCase(string))
			return X_LARGE;
		if ("large".equalsIgnoreCase(string))
			return LARGE;
		if ("medium".equalsIgnoreCase(string))
			return MEDIUM;
		if ("small".equalsIgnoreCase(string))
			return SMALL;
		if ("x-small".equalsIgnoreCase(string))
			return X_SMALL;
		if ("xx-small".equalsIgnoreCase(string))
			return XX_SMALL;
		if (string.endsWith("pt"))
			try {
				return Integer.parseInt(string
						.substring(0, string.length() - 2));
			} catch (NumberFormatException e) {
				return MEDIUM;
			}
		if (string.endsWith("in"))
			try {
				return (int) (Double.parseDouble(string.substring(0, string
						.length() - 2)) * 72.0);
			} catch (NumberFormatException e) {
				return MEDIUM;
			}
		if (string.endsWith("px"))
			try {
				return (int) (Double.parseDouble(string.substring(0, string
						.length() - 2))
						/ resolution * 72.0);
			} catch (NumberFormatException e) {
				return MEDIUM;
			}
		return MEDIUM;
	}

	/**
	 * Removes quotes from a quoted string.
	 * 
	 * @param string
	 * @return
	 */
	public static String removeQuotes(String string) {
		if (string != null && string.length() >= 2 && string.startsWith("\"") //$NON-NLS-1$
				&& string.endsWith("\"")) //$NON-NLS-1$
		{
			return string.substring(1, string.length() - 1);
		}
		return string;
	}
}
