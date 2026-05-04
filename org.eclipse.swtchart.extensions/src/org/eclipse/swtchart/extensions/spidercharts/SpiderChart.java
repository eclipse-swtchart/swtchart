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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class SpiderChart extends Canvas {

	private SpiderChartSettings chartSettings = new SpiderChartSettings();
	private Set<SpiderSeries> seriesDataSet = new HashSet<>();

	public SpiderChart(Composite parent, int style) {

		super(parent, style | SWT.DOUBLE_BUFFERED);
		createControl();
	}

	public SpiderChartSettings getChartSettings() {

		return chartSettings;
	}

	public void applySettings(SpiderChartSettings chartSettings) {

		this.chartSettings.setMaxValue(chartSettings.getMaxValue());
		this.chartSettings.setCoordinateOption(chartSettings.getCoordinateOption());
		this.chartSettings.setGridOption(chartSettings.getGridOption());
		this.chartSettings.setGridLevels(chartSettings.getGridLevels());
		this.chartSettings.setShowLabels(chartSettings.isShowLabels());
		this.chartSettings.setShowLegend(chartSettings.isShowLegend());
		this.chartSettings.setFillPolygon(chartSettings.isFillPolygon());
		this.chartSettings.setLineWidth(chartSettings.getLineWidth());
		this.chartSettings.setColorGrid(chartSettings.getColorGrid());
		this.chartSettings.setColorAxisLine(chartSettings.getColorAxisLine());
		this.chartSettings.setColorBackground(chartSettings.getColorBackground());
		this.chartSettings.setColorLabelText(chartSettings.getColorLabelText());
		this.chartSettings.setLabelFont(chartSettings.getLabelFont());
		this.chartSettings.setLegendFont(chartSettings.getLegendFont());
		redraw();
	}

	public void clearSeries() {

		seriesDataSet.clear();
		redraw();
	}

	public void appendSeries(Set<SpiderSeries> seriesDataSet) {

		this.seriesDataSet.addAll(seriesDataSet);
		redraw();
	}

	private void createControl() {

		addPaintListener(this::onPaint);
	}

	private void onPaint(PaintEvent e) {

		GC gc = e.gc;
		Rectangle bounds = getClientArea();
		gc.setBackground(chartSettings.getColorBackground());
		gc.fillRectangle(bounds);

		if(chartSettings.getAxes().length < 3) {
			gc.setForeground(chartSettings.getColorLabelText());
			gc.drawText("At least 3 axes are required.", 10, 10, true);
			return;
		}

		/*
		 * Legend at the bottom.
		 */
		int legendHeight = (chartSettings.isShowLegend() && !seriesDataSet.isEmpty()) ? (seriesDataSet.size() * 20 + 10) : 0;
		int margin = 60;
		int diameter = Math.min(bounds.width - 2 * margin, bounds.height - 2 * margin - legendHeight);
		if(diameter < 10) {
			return;
		}

		int radius = diameter / 2;
		int cx = bounds.width / 2;
		int cy = (bounds.height - legendHeight) / 2;

		drawGrid(gc, cx, cy, radius);
		drawAxes(gc, cx, cy, radius);
		drawDatasets(gc, cx, cy, radius);
		if(chartSettings.isShowLegend()) {
			drawLegend(gc, bounds, legendHeight);
		}
	}

	private void drawGrid(GC gc, int cx, int cy, int radius) {

		Color colorGrid = chartSettings.getColorGrid();
		Font legendFont = chartSettings.getLegendFont();
		CoordinateOption coordinateOption = chartSettings.getCoordinateOption();
		GridOption gridOption = chartSettings.getGridOption();

		gc.setForeground(chartSettings.getColorGrid());
		gc.setLineWidth(1);
		gc.setLineStyle(SWT.LINE_DOT);

		int sizeAxes = chartSettings.getAxes().length;
		double maxValue = chartSettings.getMaxValue();
		int gridLevels = chartSettings.getGridLevels();
		for(int level = 1; level <= gridLevels; level++) {
			double r = radius * ((double)level / gridLevels);
			int rx = (int)r;
			/*
			 * Grid Lines
			 */
			switch(gridOption) {
				case POLYGON:
					int[] polygon = polygonPoints(cx, cy, r, sizeAxes, -Math.PI / 2);
					gc.drawPolygon(polygon);
					break;
				case CIRCLE:
					gc.drawOval(cx - rx, cy - rx, rx * 2, rx * 2);
					break;
				default:
					break;
			}
			/*
			 * Level value label (on the first axis direction)
			 */
			double labelValue = maxValue * level / gridLevels;
			String text = String.format("%.0f", labelValue);
			gc.setFont(legendFont);
			gc.setForeground(colorGrid);
			/*
			 * Coordinate System
			 */
			int lx;
			int ly;
			switch(coordinateOption) {
				case VERTICAL:
					lx = (int)(cx + r * Math.cos(-Math.PI / 2)) + 3;
					ly = (int)(cy + r * Math.sin(-Math.PI / 2)) - 8;
					gc.drawText(text, lx, ly, true);
					break;
				case HORIZONTAL:
					lx = cx + rx + 3;
					ly = cy - 8;
					gc.drawText(text, lx, ly, true);
					break;
				default:
					break;
			}
		}

		gc.setLineStyle(SWT.LINE_SOLID);
	}

	private void drawAxes(GC gc, int cx, int cy, int radius) {

		Color colorAxisLine = chartSettings.getColorAxisLine();
		Color colorLabelText = chartSettings.getColorLabelText();
		Font labelFont = chartSettings.getLabelFont();
		String[] axes = chartSettings.getAxes();

		int n = axes.length;
		gc.setForeground(colorAxisLine);
		gc.setLineWidth(1);
		gc.setFont(labelFont);

		for(int i = 0; i < n; i++) {
			double angle = axisAngle(i, n);
			int ex = (int)(cx + radius * Math.cos(angle));
			int ey = (int)(cy + radius * Math.sin(angle));
			gc.drawLine(cx, cy, ex, ey);
			if(chartSettings.isShowLabels()) {
				String label = axes[i];
				Point extent = gc.textExtent(label);
				int padding = 10;
				int lx = (int)(cx + (radius + padding) * Math.cos(angle)) - extent.x / 2;
				int ly = (int)(cy + (radius + padding) * Math.sin(angle)) - extent.y / 2;
				gc.setForeground(colorLabelText);
				gc.drawText(label, lx, ly, true);
				gc.setForeground(colorAxisLine);
			}
		}
	}

	private void drawDatasets(GC gc, int cx, int cy, int radius) {

		int axesLength = chartSettings.getAxes().length;
		gc.setLineWidth(chartSettings.getLineWidth());
		double maxValue = chartSettings.getMaxValue();
		int pointRadius = chartSettings.getPointRadius();

		for(SpiderSeries spiderSeries : seriesDataSet) {
			double[] xSeries = spiderSeries.getXSeries();
			Color color = spiderSeries.getColor();

			if(xSeries.length < axesLength) {
				continue;
			}

			int[] pts = new int[axesLength * 2];
			for(int i = 0; i < axesLength; i++) {
				double clamped = Math.max(0, Math.min(xSeries[i], maxValue));
				double r = radius * (clamped / maxValue);
				double angle = axisAngle(i, axesLength);
				pts[i * 2] = (int)(cx + r * Math.cos(angle));
				pts[i * 2 + 1] = (int)(cy + r * Math.sin(angle));
			}

			if(chartSettings.isFillPolygon()) {
				Color fill = blendWithWhite(gc.getDevice(), color, 50);
				gc.setBackground(fill);
				gc.setAlpha(80);
				gc.fillPolygon(pts);
				gc.setAlpha(255);
				fill.dispose();
			}
			gc.setForeground(color);
			gc.drawPolygon(pts);

			/*
			 * Data
			 */
			for(int i = 0; i < axesLength; i++) {
				gc.setBackground(color);
				gc.fillOval(pts[i * 2] - pointRadius, pts[i * 2 + 1] - pointRadius, pointRadius * 2, pointRadius * 2);
			}
		}
		gc.setLineWidth(1);
	}

	private void drawLegend(GC gc, Rectangle bounds, int legendHeight) {

		Color colorAxisLine = chartSettings.getColorAxisLine();
		Color colorLabelText = chartSettings.getColorLabelText();
		Font legendFont = chartSettings.getLegendFont();

		if(!seriesDataSet.isEmpty()) {
			gc.setFont(legendFont);
			int y = bounds.height - legendHeight + 5;
			int x = 20;
			int width = 14;
			int height = 14;

			for(SpiderSeries spiderSeries : seriesDataSet) {
				String label = spiderSeries.getId();
				Color color = spiderSeries.getColor();
				gc.setBackground(color);
				gc.fillRectangle(x, y + 2, width, height);
				gc.setForeground(colorAxisLine);
				gc.drawRectangle(x, y + 2, width, height);
				gc.setForeground(colorLabelText);
				gc.drawText(label, x + width + 6, y, true);
				x += width + gc.textExtent(label).x + 24;
			}
		}
	}

	private static double axisAngle(int i, int n) {

		return -Math.PI / 2 + (2 * Math.PI * i / n);
	}

	private static int[] polygonPoints(int cx, int cy, double r, int n, double startAngle) {

		int[] pts = new int[n * 2];
		for(int i = 0; i < n; i++) {
			double a = startAngle + 2 * Math.PI * i / n;
			pts[i * 2] = (int)(cx + r * Math.cos(a));
			pts[i * 2 + 1] = (int)(cy + r * Math.sin(a));
		}
		return pts;
	}

	private static Color blendWithWhite(Device device, Color c, int alpha) {

		int r = c.getRed() + (255 - c.getRed()) * (255 - alpha) / 255;
		int g = c.getGreen() + (255 - c.getGreen()) * (255 - alpha) / 255;
		int b = c.getBlue() + (255 - c.getBlue()) * (255 - alpha) / 255;

		return new Color(device, r, g, b);
	}
}