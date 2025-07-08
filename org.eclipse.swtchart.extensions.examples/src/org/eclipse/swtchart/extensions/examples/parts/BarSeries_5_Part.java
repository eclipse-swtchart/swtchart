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
package org.eclipse.swtchart.extensions.examples.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swtchart.extensions.barcharts.BarChart;
import org.eclipse.swtchart.extensions.barcharts.BarSeriesData;
import org.eclipse.swtchart.extensions.barcharts.IBarSeriesData;
import org.eclipse.swtchart.extensions.core.ISeriesData;
import org.eclipse.swtchart.extensions.examples.support.SeriesConverter;

import jakarta.inject.Inject;

public class BarSeries_5_Part extends BarChart {

	@Inject
	public BarSeries_5_Part(Composite parent) {

		super(parent, SWT.NONE);
		setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
		initialize();
	}

	private void initialize() {

		List<IBarSeriesData> barSeriesDataList = new ArrayList<IBarSeriesData>();
		addSeries(barSeriesDataList, SeriesConverter.BAR_SERIES_4_POSITIVE);
		addSeries(barSeriesDataList, SeriesConverter.BAR_SERIES_5_NEGATIVE);
		addSeriesData(barSeriesDataList);
	}

	private void addSeries(List<IBarSeriesData> barSeriesDataList, String seriesID) {

		ISeriesData seriesData = SeriesConverter.getSeriesXY(seriesID);
		IBarSeriesData barSeriesData = new BarSeriesData(seriesData);
		barSeriesDataList.add(barSeriesData);
	}
}