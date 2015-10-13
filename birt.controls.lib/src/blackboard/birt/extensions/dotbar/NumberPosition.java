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
package blackboard.birt.extensions.dotbar;

/**
 * An immutable enumeration of positions for the textual representation of the
 * number.
 * 
 * @author Steve Schafer / Innovent Solutions
 */
public class NumberPosition {
	public final int index;

	private NumberPosition(final int index) {
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
	public static NumberPosition create(final String string) {
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
	public boolean equals(final Object obj) {
		if (!(obj instanceof NumberPosition))
			return false;
		final NumberPosition that = (NumberPosition) obj;
		return index == that.index;
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
