/*******************************************************************************
 * Copyright (c) 2025 SWTChart project.
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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtchart.extensions.core.IChartSettings;
import org.eclipse.swtchart.extensions.core.RangeRestriction;
import org.eclipse.swtchart.extensions.core.ScrollableChart;
import org.eclipse.swtchart.extensions.examples.parts.BarSeries_5_Part;

/*
 * X-Axis negative and positive values and mirrored
 */
public class DemoBarChart {

	public static void main(String args[]) {

		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("DemoBarChart");
		shell.setSize(500, 400);
		shell.setLayout(new FillLayout());

		ScrollableChart scrollableChart = new BarSeries_5_Part(shell);
		scrollableChart.setFileName("NegativeX");
		shell.open();
		/*
		 * Use this demo to test the buffer selection in different
		 * environments.
		 */
		IChartSettings chartSettings = scrollableChart.getChartSettings();
		RangeRestriction rangeRestriction = chartSettings.getRangeRestriction();
		rangeRestriction.setZeroX(false);
		rangeRestriction.setZeroY(false);
		rangeRestriction.setForceZeroMinY(false);
		chartSettings.setCreateMenu(true);
		chartSettings.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		chartSettings.setBackgroundChart(display.getSystemColor(SWT.COLOR_WHITE));
		chartSettings.setBackgroundPlotArea(display.getSystemColor(SWT.COLOR_WHITE));
		scrollableChart.applySettings(chartSettings);

		while(!shell.isDisposed()) {
			if(!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}