/*******************************************************************************
 * Copyright (c) 2025, 2026 Lablicate GmbH.
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
package org.eclipse.swtchart.export.menu.vector.internal;

public enum PageUnit {

	MM("Millimeter [mm]"), //
	INCH("Inch [in]"); //

	private String label = "";

	private PageUnit(String label) {

		this.label = label;
	}

	public String label() {

		return label;
	}

	public static String[][] getOptions() {

		return getOptions(values());
	}

	static String[][] getOptions(Enum<PageUnit>[] values) {

		String[][] elements = new String[values.length][2];

		int counter = 0;
		for(Enum<PageUnit> value : values) {
			elements[counter][0] = value instanceof PageUnit label ? label.label() : value.toString();
			elements[counter][1] = value.name();
			counter++;
		}

		return elements;
	}
}