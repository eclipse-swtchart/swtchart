/*******************************************************************************
 * Copyright (c) 2024, 2025 SWTChart project.
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
package org.eclipse.swtchart.extensions.examples.charts;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtchart.extensions.core.IChartSettings;
import org.eclipse.swtchart.extensions.core.RangeRestriction;
import org.eclipse.swtchart.extensions.core.ScrollableChart;
import org.eclipse.swtchart.extensions.core.SeriesData;
import org.eclipse.swtchart.extensions.linecharts.ICompressionSupport;
import org.eclipse.swtchart.extensions.linecharts.ILineSeriesData;
import org.eclipse.swtchart.extensions.linecharts.ILineSeriesSettings;
import org.eclipse.swtchart.extensions.linecharts.LineChart;
import org.eclipse.swtchart.extensions.linecharts.LineSeriesData;
import org.eclipse.swtchart.extensions.menu.IChartMenuEntry;

public class DemoChart_427 {

	public static void main(String args[]) {

		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("DemoChart_427");
		shell.setSize(500, 400);
		shell.setLayout(new FillLayout());
		shell.setBackgroundMode(SWT.INHERIT_FORCE);

		LineChart lineChart = new LineChart(shell, SWT.NONE);
		lineChart.setFileName("DemoChart_427");
		lineChart.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
		shell.open();
		/*
		 * Settings
		 */
		IChartSettings chartSettings = lineChart.getChartSettings();
		RangeRestriction rangeRestriction = chartSettings.getRangeRestriction();
		rangeRestriction.setRestrictZoomX(true);
		rangeRestriction.setRestrictZoomY(true);
		/*
		 * You can completely remove all menu
		 * and event handler and replace them by
		 * your own implementation.
		 */
		// chartSettings.clearMenuEntries();
		// chartSettings.clearHandledEventProcessors();
		addMenuEntry(chartSettings);
		lineChart.applySettings(chartSettings);
		/*
		 * Data
		 */
		List<ILineSeriesData> lineSeriesDataList = new ArrayList<>();
		int size = 1000000;
		double[] xSeries = new double[size];
		double[] ySeries = new double[size];
		Random random = new Random();
		for(int i = 0; i < size; i++) {
			xSeries[i] = i;
			ySeries[i] = random.nextDouble();
		}
		SeriesData seriesData = new SeriesData(xSeries, ySeries, "Signals");
		LineSeriesData lineSeriesData = new LineSeriesData(seriesData);
		ILineSeriesSettings lineSeriesSettings = lineSeriesData.getSettings();
		lineSeriesSettings.setEnableArea(true);
		lineSeriesDataList.add(lineSeriesData);
		lineChart.addSeriesData(lineSeriesDataList, ICompressionSupport.HIGH_COMPRESSION);

		while(!shell.isDisposed()) {
			if(!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	private static void addMenuEntry(IChartSettings chartSettings) {

		chartSettings.addMenuEntry(new IChartMenuEntry() {

			@Override
			public String getName() {

				return "Action";
			}

			@Override
			public String getCategory() {

				return "Tools";
			}

			@Override
			public void execute(Shell shell, ScrollableChart scrollableChart) {

				System.out.println("This is your menu entry.");
			}
		});
	}
}
