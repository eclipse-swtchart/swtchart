/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
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
package org.eclipse.swtchart.extensions.spidercharts;

import org.eclipse.swtchart.IEnumLabel;

public enum GridOption implements IEnumLabel {

	NONE("None"), //
	POLYGON("Polygon"), //
	CIRCLE("Circle"); //

	private String label;

	private GridOption(String label) {

		this.label = label;
	}

	@Override
	public String label() {

		return label;
	}
}