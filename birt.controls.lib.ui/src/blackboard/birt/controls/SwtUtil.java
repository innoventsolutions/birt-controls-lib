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
package blackboard.birt.controls;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;

import blackboard.birt.extensions.util.ColorSpec;

public class SwtUtil {
	public static Color getSwtColor(Device device, ColorSpec color) {
		return new Color(device, color.r, color.g, color.b);
	}
}
