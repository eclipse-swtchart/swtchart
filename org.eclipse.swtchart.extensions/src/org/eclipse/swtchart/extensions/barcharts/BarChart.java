/*******************************************************************************
 * Copyright (c) 2017, 2026 Lablicate GmbH.
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
package org.eclipse.swtchart.extensions.barcharts;

import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swtchart.IBarSeries;
import org.eclipse.swtchart.IBarSeries.BarWidthStyle;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.core.ISeriesData;
import org.eclipse.swtchart.extensions.core.ScrollableChart;
import org.eclipse.swtchart.extensions.exceptions.SeriesException;
import org.eclipse.swtchart.extensions.linecharts.ICompressionSupport;

public class BarChart extends ScrollableChart implements ICompressionSupport {

	private static final int LENGTH_HINT_DATA_POINTS = 5000;

	public BarChart() {

		super();
		setData("org.eclipse.e4.ui.css.CssClassName", "BarChart");
	}

	public BarChart(Composite parent, int style) {

		super(parent, style);
		setData("org.eclipse.e4.ui.css.CssClassName", "BarChart");
	}

	public void addSeriesData(List<IBarSeriesData> barSeriesDataList) {

		addSeriesData(barSeriesDataList, NO_COMPRESSION);
	}

	/**
	 * The data is compressed to the given length.
	 * If you're unsure which length to set, then use one of the following variables:
	 *
	 * EXTREME_COMPRESSION
	 * HIGH_COMPRESSION
	 * MEDIUM_COMPRESSION
	 * LOW_COMPRESSION
	 * NO_COMPRESSION
	 *
	 * BarWidthStyle.STRETCHED will be used automatically instead of BarWidthStyle.FIXED
	 * if the series data is too large. This leads to a better performance.
	 *
	 * @param barSeriesDataList
	 * @param compressToLength
	 */
	public void addSeriesData(List<IBarSeriesData> barSeriesDataList, int compressToLength) {

		/*
		 * Suspend the update when adding new data to improve the performance.
		 */
		if(barSeriesDataList != null && !barSeriesDataList.isEmpty()) {
			BaseChart baseChart = getBaseChart();
			baseChart.suspendUpdate(true);
			for(IBarSeriesData barSeriesData : barSeriesDataList) {
				/*
				 * Get the series data and apply the settings.
				 */
				try {
					ISeriesData seriesData = barSeriesData.getSeriesData();
					ISeriesData optimizedSeriesData = calculateSeries(seriesData, compressToLength);
					IBarSeriesSettings barSeriesSettings = barSeriesData.getSettings();
					barSeriesSettings.getSeriesSettingsHighlight(); // Initialize
					IBarSeries<?> barSeries = (IBarSeries<?>)createSeries(optimizedSeriesData, barSeriesSettings);
					baseChart.applySeriesSettings(barSeries, barSeriesSettings);
					/*
					 * Automatically use stretched if it is a large data set.
					 */
					if(isLargeDataSet(optimizedSeriesData.getXSeries(), optimizedSeriesData.getYSeries(), LENGTH_HINT_DATA_POINTS)) {
						barSeries.setBarWidthStyle(BarWidthStyle.STRETCHED);
					} else {
						barSeries.setBarWidthStyle(barSeriesSettings.getBarWidthStyle());
					}
				} catch(SeriesException e) {

				}
			}
			baseChart.suspendUpdate(false);
			adjustRange(true);
			baseChart.redraw();
		}
	}
}