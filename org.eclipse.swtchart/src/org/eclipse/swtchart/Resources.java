/*******************************************************************************
 * Copyright (c) 2008, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * yoshitaka - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtchart;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

public class Resources {

	/*
	 * It was Tahoma before, but Verdana is also available on
	 * other systems than Microsoft and looks similar.
	 */
	public static final String DEFAULT_FONT_NAME = "Verdana";

	public static final int LARGE_FONT_SIZE = 13;
	public static final int MEDIUM_FONT_SIZE = 11;
	public static final int SMALL_FONT_SIZE = 9;
	public static final String RGB_DELIMITER = ",";

	private static final Map<String, Font> fontMap = new HashMap<>();

	/*
	 * Only static methods are used here.
	 */
	protected Resources() {

	}

	public static Color getColorDefault() {

		return getDisplay().getSystemColor(SWT.COLOR_RED);
	}

	public static Color getColor(String color) {

		if(color != null && !color.isEmpty()) {
			String[] values = color.split(RGB_DELIMITER);
			if(values.length == 3) {
				int red = Integer.parseInt(values[0]);
				int green = Integer.parseInt(values[1]);
				int blue = Integer.parseInt(values[2]);

				return new Color(red, green, blue);
			}
		}

		return getColorDefault();
	}

	public static String getColor(Color color) {

		if(color == null) {
			color = getColorDefault();
		}

		StringBuilder builder = new StringBuilder();

		builder.append(color.getRed());
		builder.append(RGB_DELIMITER);
		builder.append(color.getGreen());
		builder.append(RGB_DELIMITER);
		builder.append(color.getBlue());

		return builder.toString();
	}

	public static Font getFont(FontData fontData) {

		return getFont(fontData.getName(), fontData.getHeight(), fontData.getStyle());
	}

	public static Font getFont(String name, int height, int style) {

		String key = getFontKey(name, height, style);
		Font font = fontMap.get(key);
		if(font == null) {
			font = new Font(getDisplay(), name, height, style);
			fontMap.put(key, font);
		}

		return font;
	}

	@Override
	protected void finalize() throws Throwable {

		/*
		 * Fonts
		 */
		for(Font font : fontMap.values()) {
			if(font != null && !font.isDisposed()) {
				font.dispose();
			}
		}
	}

	private static String getFontKey(String name, int height, int style) {

		StringBuilder builder = new StringBuilder();
		builder.append(name);
		builder.append("_");
		builder.append(height);
		builder.append("_");
		builder.append(style);

		return builder.toString();
	}

	protected static Display getDisplay() {

		return Display.getDefault();
	}
}