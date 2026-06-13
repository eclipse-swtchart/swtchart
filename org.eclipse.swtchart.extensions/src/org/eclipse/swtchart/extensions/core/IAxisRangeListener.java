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
package org.eclipse.swtchart.extensions.core;

public interface IAxisRangeListener {

	/**
	 * Called when the primary X axis range changes (zoom, pan, range selection).
	 *
	 * @param xMin
	 *            lower bound of the new visible range
	 * @param xMax
	 *            upper bound of the new visible range
	 */
	void rangeChanged(double xMin, double xMax);
}