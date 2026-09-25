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
 * Dr. Philip Wenig - initial API and implementation
 * Christoph Läubrich - don't init color in constructor
 *******************************************************************************/
package org.eclipse.swtchart.extensions.marker;

import java.util.Objects;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swtchart.IPlotArea;
import org.eclipse.swtchart.extensions.core.BaseChart;

public abstract class AbstractBaseChartPaintListener implements IBaseChartPaintListener {

	private BaseChart baseChart;
	private Color foregroundColor;
	private Color backgroundColor;
	private boolean draw = true;

	public AbstractBaseChartPaintListener(BaseChart baseChart) {

		this.baseChart = baseChart;
	}

	@Override
	public BaseChart getBaseChart() {

		return baseChart;
	}

	@Override
	public boolean drawBehindSeries() {

		return false;
	}

	@Override
	public void setForegroundColor(Color foregroundColor) {

		if(!Objects.equals(this.foregroundColor, foregroundColor)) {
			this.foregroundColor = foregroundColor;
			redraw();
		}
	}

	protected Color getForegroundColor() {

		if(foregroundColor == null) {
			return getBaseChart().getDisplay().getSystemColor(SWT.COLOR_LIST_FOREGROUND);
		}
		return foregroundColor;
	}

	@Override
	public void setBackgroundColor(Color backgroundColor) {

		if(!Objects.equals(this.backgroundColor, backgroundColor)) {
			this.backgroundColor = backgroundColor;
			redraw();
		}
	}

	protected Color getBackgroundColor() {

		if(backgroundColor == null) {
			return getBaseChart().getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
		}
		return backgroundColor;
	}

	@Override
	public boolean isDraw() {

		return draw;
	}

	@Override
	public void setDraw(boolean draw) {

		if(this.draw != draw) {
			this.draw = draw;
			redraw();
		}
	}

	/**
	 * Requests the repaint of the plot area this listener draws on. Subclasses
	 * call it after changing the state they draw, as the chart isn't repainted
	 * on each mouse move.
	 */
	protected void redraw() {

		if(baseChart != null && !baseChart.isDisposed()) {
			IPlotArea plotArea = baseChart.getPlotArea();
			if(plotArea != null) {
				plotArea.redraw();
			}
		}
	}
}
