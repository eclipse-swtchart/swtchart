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
package org.eclipse.swtchart.extensions.spidercharts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;

public class SpiderChartSettings {

	private String[] axes = new String[0];
	private CoordinateOption coordinateOption = CoordinateOption.VERTICAL;
	private GridOption gridOption = GridOption.POLYGON;
	private double maxValue = 100.0;
	private int gridLevels = 5; // concentric rings
	private boolean showLabels = true;
	private boolean showLegend = true;
	private boolean fillPolygon = true;
	private int lineWidth = 2;
	private int pointRadius = 4;
	private Color colorGrid = new Color(Display.getDefault(), 180, 180, 180);
	private Color colorAxisLine = new Color(Display.getDefault(), 100, 100, 100);
	private Color colorBackground = new Color(Display.getDefault(), 255, 255, 255);
	private Color colorLabelText = new Color(Display.getDefault(), 40, 40, 40);
	private Font labelFont = new Font(Display.getDefault(), "Arial", 9, SWT.BOLD);
	private Font legendFont = new Font(Display.getDefault(), "Arial", 8, SWT.NORMAL);

	public String[] getAxes() {

		return axes;
	}

	public void setAxes(String[] axes) {

		this.axes = axes != null ? axes : new String[0];
	}

	public CoordinateOption getCoordinateOption() {

		return coordinateOption;
	}

	public void setCoordinateOption(CoordinateOption coordinateOption) {

		this.coordinateOption = coordinateOption;
	}

	public GridOption getGridOption() {

		return gridOption;
	}

	public void setGridOption(GridOption gridOption) {

		this.gridOption = gridOption;
	}

	public double getMaxValue() {

		return maxValue;
	}

	public void setMaxValue(double maxValue) {

		this.maxValue = maxValue > 0 ? maxValue : 100;
	}

	public int getGridLevels() {

		return gridLevels;
	}

	public void setGridLevels(int gridLevels) {

		this.gridLevels = Math.max(1, gridLevels);
	}

	public boolean isShowLabels() {

		return showLabels;
	}

	public void setShowLabels(boolean showLabels) {

		this.showLabels = showLabels;
	}

	public boolean isShowLegend() {

		return showLegend;
	}

	public void setShowLegend(boolean showLegend) {

		this.showLegend = showLegend;
	}

	public boolean isFillPolygon() {

		return fillPolygon;
	}

	public void setFillPolygon(boolean fillPolygon) {

		this.fillPolygon = fillPolygon;
	}

	public int getLineWidth() {

		return lineWidth;
	}

	public void setLineWidth(int lineWidth) {

		this.lineWidth = Math.max(1, lineWidth);
	}

	public int getPointRadius() {

		return pointRadius;
	}

	public void setPointRadius(int pointRadius) {

		this.pointRadius = pointRadius;
	}

	public Color getColorGrid() {

		return colorGrid;
	}

	public void setColorGrid(Color colorGrid) {

		this.colorGrid = colorGrid;
	}

	public Color getColorAxisLine() {

		return colorAxisLine;
	}

	public void setColorAxisLine(Color colorAxisLine) {

		this.colorAxisLine = colorAxisLine;
	}

	public Color getColorBackground() {

		return colorBackground;
	}

	public void setColorBackground(Color colorBackground) {

		this.colorBackground = colorBackground;
	}

	public Color getColorLabelText() {

		return colorLabelText;
	}

	public void setColorLabelText(Color colorLabelText) {

		this.colorLabelText = colorLabelText;
	}

	public Font getLabelFont() {

		return labelFont;
	}

	public void setLabelFont(Font labelFont) {

		this.labelFont = labelFont;
	}

	public Font getLegendFont() {

		return legendFont;
	}

	public void setLegendFont(Font legendFont) {

		this.legendFont = legendFont;
	}
}