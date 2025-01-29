/*******************************************************************************
 * Copyright (c) 2025 Lablicate GmbH.
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

import org.eclipse.swtchart.Range;

public class UserRestriction {

	private Range rangeX = null;
	private Range rangeY = null;

	public void resetRestriction() {

		this.rangeX = null;
		this.rangeY = null;
	}

	public void setRange(Range rangeX, Range rangeY) {

		this.rangeX = rangeX;
		this.rangeY = rangeY;
	}

	public Range getRangeX() {

		return rangeX;
	}

	public Range getRangeY() {

		return rangeY;
	}

	public boolean isRestrictFrame() {

		return rangeX != null && rangeY != null;
	}
}