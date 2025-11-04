/*******************************************************************************
 * Copyright (c) 2022, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtchart.extensions.core;

import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class OS {

	public static boolean isWindows() {

		return (getOperatingSystem().indexOf("win") >= 0);
	}

	public static boolean isLinux() {

		return (getOperatingSystem().indexOf("linux") >= 0);
	}

	public static boolean isMac() {

		return (getOperatingSystem().indexOf("mac") >= 0);
	}

	public static boolean isUnix() {

		return (getOperatingSystem().indexOf("unix") >= 0);
	}

	public static boolean isWayland() {

		try {
			return System.getenv("WAYLAND_DISPLAY") != null;
		} catch(SecurityException e) {
			return false;
		}
	}

	public static boolean isWaylandInvalidImage(ImageData imageData) {

		if(imageData != null) {
			int width = imageData.width;
			int height = imageData.height;
			int size = width * height;
			if(size > 0) {
				RGB rgbBlack = Display.getDefault().getSystemColor(SWT.COLOR_BLACK).getRGB();
				int samples = (int)Math.round(Math.sqrt(size)) + 1;
				Random randomX = new Random(System.currentTimeMillis());
				Random randomY = new Random(System.currentTimeMillis());
				for(int i = 0; i < samples; i++) {
					int x = randomX.nextInt(0, width);
					int y = randomY.nextInt(0, height);
					RGB rgb = imageData.palette.getRGB(imageData.getPixel(x, y));
					if(!rgbBlack.equals(rgb)) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/*
	 * From: org.eclipse.chemclipse.support.ui.workbench.PreferencesSupport
	 * Workaround for https://bugs.eclipse.org/bugs/show_bug.cgi?id=564022
	 */
	public static boolean isDarkTheme() {

		if(isMac()) {
			Color background = Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
			RGB rgb = background.getRGB();
			return (rgb.red < 128 && rgb.green < 128 && rgb.blue < 128);
		} else {
			return Display.isSystemDarkTheme();
		}
	}

	private static String getOperatingSystem() {

		return System.getProperty("os.name").toLowerCase();
	}
}