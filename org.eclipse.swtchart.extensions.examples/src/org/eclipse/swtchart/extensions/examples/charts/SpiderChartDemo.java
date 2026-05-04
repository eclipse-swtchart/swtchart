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
package org.eclipse.swtchart.extensions.examples.charts;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtchart.extensions.spidercharts.CoordinateOption;
import org.eclipse.swtchart.extensions.spidercharts.GridOption;
import org.eclipse.swtchart.extensions.spidercharts.SpiderChart;
import org.eclipse.swtchart.extensions.spidercharts.SpiderChartSettings;
import org.eclipse.swtchart.extensions.spidercharts.SpiderSeries;

public class SpiderChartDemo {

	public static void main(String[] args) {

		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Spider Chart Demo");
		shell.setLayout(new FillLayout());
		shell.setSize(700, 550);
		/*
		 * Chart / Settings
		 */
		SpiderChart spiderChart = new SpiderChart(shell, SWT.NONE);
		SpiderChartSettings chartSettings = spiderChart.getChartSettings();
		chartSettings.setAxes(new String[]{"Sweet", "Bitter", "Umami", "Sour", "Salty"});
		chartSettings.setCoordinateOption(CoordinateOption.VERTICAL);
		chartSettings.setGridOption(GridOption.POLYGON);
		chartSettings.setMaxValue(100);
		chartSettings.setGridLevels(5);
		spiderChart.applySettings(chartSettings);
		/*
		 * Series Data
		 */
		Set<SpiderSeries> seriesDataSet = new HashSet<>();
		seriesDataSet.add(new SpiderSeries("Sample A", new double[]{80, 60, 90, 70, 50}, display.getSystemColor(SWT.COLOR_BLUE)));
		seriesDataSet.add(new SpiderSeries("Sample B", new double[]{50, 85, 40, 95, 75}, display.getSystemColor(SWT.COLOR_RED)));
		seriesDataSet.add(new SpiderSeries("Sample C", new double[]{65, 70, 75, 55, 90}, display.getSystemColor(SWT.COLOR_DARK_GREEN)));
		spiderChart.appendSeries(seriesDataSet);
		/*
		 * Show
		 */
		shell.open();
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}