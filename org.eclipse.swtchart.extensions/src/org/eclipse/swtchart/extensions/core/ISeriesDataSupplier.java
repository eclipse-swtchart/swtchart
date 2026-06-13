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

/**
 * Supplies series data on demand for the current viewport.
 *
 * Register a supplier via LineChart/StepChart/BarChart.addSeriesData(List, ISeriesDataSupplier).
 * The chart calls this whenever the visible X range changes significantly (zoom or pan).
 * The supplier returns only the data relevant to the current viewport at the requested resolution,
 * enabling an Overview/Detail pattern without pre-loading all data.
 *
 * Example:
 * <pre>
 * lineChart.addSeriesData(overviewSeriesDataList, (id, xMin, xMax, maxPoints) -> {
 *     // Return raw slice for detail view, or aggregated data for overview
 *     return new SeriesData(fetchX(xMin, xMax, maxPoints), fetchY(xMin, xMax, maxPoints), id);
 * });
 * </pre>
 */
public interface ISeriesDataSupplier {

	/**
	 * Returns series data for the given viewport range.
	 *
	 * @param seriesId
	 *            the series identifier
	 * @param xMin
	 *            visible range lower bound
	 * @param xMax
	 *            visible range upper bound
	 * @param maxPoints
	 *            resolution hint — maximum number of points to return
	 * @return the series data to display, or null to leave the current data unchanged
	 */
	ISeriesData getData(String seriesId, double xMin, double xMax, int maxPoints);
}