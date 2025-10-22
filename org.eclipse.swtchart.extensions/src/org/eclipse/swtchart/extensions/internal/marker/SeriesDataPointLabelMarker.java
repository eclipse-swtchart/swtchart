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
 * Lorenz Gerber - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtchart.extensions.internal.marker;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swtchart.ISeries;
import org.eclipse.swtchart.ISeriesSet;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.core.IPointSeriesSettings;
import org.eclipse.swtchart.extensions.core.ISeriesSettings;
import org.eclipse.swtchart.extensions.marker.AbstractBaseChartPaintListener;
import org.eclipse.swtchart.extensions.marker.IBaseChartPaintListener;

public class SeriesDataPointLabelMarker extends AbstractBaseChartPaintListener implements IBaseChartPaintListener {

	public SeriesDataPointLabelMarker(BaseChart baseChart) {

		super(baseChart);
	}

	@Override
	public void paintControl(PaintEvent e) {

		if(isDraw()) {
			if(isDraw()) {
				BaseChart baseChart = getBaseChart();
				ISeriesSet seriesSet = baseChart.getSeriesSet();
				ISeries<?>[] series = seriesSet.getSeries();
				for(ISeries<?> serie : series) {
					String id = serie.getId();
					/*
					 * get label series here
					 */
					String[] labels = serie.getLabels();
					ISeriesSettings seriesSettings = baseChart.getSeriesSettings(id);
					if(seriesSettings.isVisible()) {
						/*
						 * Only draw is series is visible.
						 */
						int symbolSize = 1;
						if(seriesSettings instanceof IPointSeriesSettings) {
							symbolSize = ((IPointSeriesSettings)seriesSettings).getSymbolSize();
						}
						/*
						 * Draw the label
						 */
						e.gc.setForeground(getForegroundColor());
						for(int i = 0; i < serie.getXSeries().length; i++) {
							Point point = serie.getPixelCoordinates(i);
							if(labels != null && labels.length != 0) {
								Point labelSize = e.gc.textExtent(labels[i]);
								e.gc.drawText(labels[i], (int)(point.x - labelSize.x / 2.0d), (int)(point.y - labelSize.y - symbolSize / 2.0d), true);
							}
						}
					}
				}
			}
		}
	}
}
