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
 * An immutable enumeration of positions for the textual representation of the number.
 * 
 * @author Steve Schafer / Innovent Solutions
 */
public class NumberPosition {
	public final int index;

	private NumberPosition(int index) {
		this.index = index;
	}

	public static final int INDEX_HIDDEN = 0;
	public static final int INDEX_BEFORE = 1;
	public static final int INDEX_AFTER = 2;
	public static final NumberPosition HIDDEN = new NumberPosition(INDEX_HIDDEN);
	public static final NumberPosition BEFORE = new NumberPosition(INDEX_BEFORE);
	public static final NumberPosition AFTER = new NumberPosition(INDEX_AFTER);
	private static final String[] names = { "Hidden", "Before", "After" };
	private static final NumberPosition[] objects = { HIDDEN, BEFORE, AFTER };

	/**
	 * Returns one of the number positions.
	 * 
	 * @param string
	 * @return
	 */
	public static NumberPosition create(String string) {
		for (int i = 0; i < names.length; i++)
			if (names[i].equalsIgnoreCase(string))
				return objects[i];
		return HIDDEN;
	}

	public static String[] getNames() {
		final String[] newArray = new String[names.length];
		for (int i = 0; i < names.length; i++)
			newArray[i] = names[i];
		return newArray;
	}

	/**
	 * The name of this number position.
	 * 
	 * @return
	 */
	public String getName() {
		return names[index];
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof NumberPosition))
			return false;
		NumberPosition that = (NumberPosition) obj;
		return this.index == that.index;
	}

	@Override
	public int hashCode() {
		return index;
	}

	@Override
	public String toString() {
		return getName();
	}
}
