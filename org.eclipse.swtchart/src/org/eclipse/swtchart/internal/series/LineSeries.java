/*******************************************************************************
 * Copyright (c) 2008, 2026 SWTChart project.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * yoshitaka - initial API and implementation
 * Christoph Läubrich - add support for datamodel
 * Frank Buloup = Internationalization
 * Philip Wenig - series settings mappings, Bresenham drawing mode
 *******************************************************************************/
package org.eclipse.swtchart.internal.series;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtchart.Chart;
import org.eclipse.swtchart.IAxis.Direction;
import org.eclipse.swtchart.ILineSeries;
import org.eclipse.swtchart.LineStyle;
import org.eclipse.swtchart.Range;
import org.eclipse.swtchart.internal.LineDrawingBresenham;
import org.eclipse.swtchart.internal.axis.Axis;
import org.eclipse.swtchart.internal.compress.CompressLineSeries;
import org.eclipse.swtchart.internal.compress.CompressScatterSeries;
import org.eclipse.swtchart.model.CartesianSeriesModel;
import org.eclipse.swtchart.model.DoubleArraySeriesModel;
import org.eclipse.swtchart.model.DrawingMode;

public class LineSeries<T> extends Series<T> implements ILineSeries<T> {

	private static final int ALPHA = 50;
	private static final LineStyle DEFAULT_LINE_STYLE = LineStyle.SOLID;
	private static final int DEFAULT_LINE_WIDTH = 1;
	private static final int DEFAULT_LINE_COLOR = SWT.COLOR_BLUE;
	private static final int DEFAULT_SYMBOL_COLOR = SWT.COLOR_DARK_GRAY;
	private static final int DEFAULT_SIZE = 4;
	private static final PlotSymbolType DEFAULT_SYMBOL_TYPE = PlotSymbolType.CIRCLE;
	private static final int DEFAULT_ANTIALIAS = SWT.DEFAULT;
	private static final int MARGIN_AT_MIN_MAX_PLOT = 6;

	private int symbolSize = 4;
	private Color symbolColor = Display.getDefault().getSystemColor(DEFAULT_SYMBOL_COLOR);
	private Color[] symbolColors = new Color[0];
	private PlotSymbolType symbolType = DEFAULT_SYMBOL_TYPE;
	private DrawingMode drawingMode = DrawingMode.CLASSIC;
	private LineStyle lineStyle = DEFAULT_LINE_STYLE;
	private Color lineColor = Display.getDefault().getSystemColor(DEFAULT_LINE_COLOR);
	private int lineWidth = DEFAULT_LINE_WIDTH;
	private boolean areaEnabled = false;
	private boolean areaStrict = false;
	private boolean stepEnabled = false;
	private int antialias = DEFAULT_ANTIALIAS;
	private String extendedSymbolType = "😂"; //$NON-NLS-1$

	protected LineSeries(Chart chart, String id) {

		super(chart, id);
		compressor = new CompressLineSeries();
	}

	@Override
	public DrawingMode getDrawingMode() {

		return drawingMode;
	}

	@Override
	public void setDrawingMode(DrawingMode mode) {

		drawingMode = mode != null ? mode : DrawingMode.CLASSIC;
	}

	@Override
	public LineStyle getLineStyle() {

		return lineStyle;
	}

	@Override
	public void setLineStyle(LineStyle style) {

		if(style == null) {
			this.lineStyle = DEFAULT_LINE_STYLE;
			return;
		}
		this.lineStyle = style;
		if(compressor instanceof CompressScatterSeries compressScatterSeries) {
			compressScatterSeries.setLineVisible(style != LineStyle.NONE);
		}
	}

	@Override
	public Color getLineColor() {

		if(lineColor.isDisposed()) {
			lineColor = Display.getDefault().getSystemColor(DEFAULT_LINE_COLOR);
		}
		return lineColor;
	}

	@Override
	public void setLineColor(Color color) {

		if(color != null && color.isDisposed()) {
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		}
		if(color == null) {
			this.lineColor = Display.getDefault().getSystemColor(DEFAULT_LINE_COLOR);
		} else {
			this.lineColor = color;
		}
	}

	@Override
	public int getLineWidth() {

		return lineWidth;
	}

	@Override
	public void setLineWidth(int width) {

		if(width <= 0) {
			this.lineWidth = DEFAULT_LINE_WIDTH;
		} else {
			this.lineWidth = width;
		}
	}

	@Override
	public PlotSymbolType getSymbolType() {

		return symbolType;
	}

	@Override
	public void setSymbolType(PlotSymbolType type) {

		if(type == null) {
			this.symbolType = DEFAULT_SYMBOL_TYPE;
		} else {
			this.symbolType = type;
		}
	}

	@Override
	public String getExtendedPlotSymbolType() {

		return extendedSymbolType;
	}

	@Override
	public void setExtendedPlotSymbolType(String type) {

		extendedSymbolType = type;
	}

	@Override
	public int getSymbolSize() {

		return symbolSize;
	}

	@Override
	public void setSymbolSize(int size) {

		if(size <= 0) {
			this.symbolSize = DEFAULT_SIZE;
		} else {
			this.symbolSize = size;
		}
	}

	@Override
	public Color getSymbolColor() {

		return symbolColor;
	}

	@Override
	public void setSymbolColor(Color color) {

		if(color != null && color.isDisposed()) {
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		}
		if(color == null) {
			this.symbolColor = Display.getDefault().getSystemColor(DEFAULT_SYMBOL_COLOR);
		} else {
			this.symbolColor = color;
		}
	}

	@Override
	public Color[] getSymbolColors() {

		Color[] copiedSymbolColors = new Color[symbolColors.length];
		System.arraycopy(symbolColors, 0, copiedSymbolColors, 0, symbolColors.length);
		return copiedSymbolColors;
	}

	@Override
	public void setSymbolColors(Color[] colors) {

		if(colors == null) {
			symbolColors = new Color[0];
			return;
		}
		for(Color color : colors) {
			if(color.isDisposed()) {
				SWT.error(SWT.ERROR_INVALID_ARGUMENT);
			}
		}
		symbolColors = new Color[colors.length];
		System.arraycopy(colors, 0, symbolColors, 0, colors.length);
	}

	@Override
	protected void setCompressor() {

		CartesianSeriesModel<T> dataModel = getDataModel();
		if(dataModel instanceof DoubleArraySeriesModel doubleArraySeriesModel) {
			if(doubleArraySeriesModel.isXMonotoneIncreasing()) {
				compressor = new CompressLineSeries();
				return;
			}
		}
		compressor = new CompressScatterSeries();
		((CompressScatterSeries)compressor).setLineVisible(getLineStyle() != LineStyle.NONE);
	}

	@Override
	public void enableArea(boolean enabled) {

		areaEnabled = enabled;
	}

	@Override
	public boolean isAreaEnabled() {

		return areaEnabled;
	}

	@Override
	public boolean isAreaStrict() {

		return areaStrict;
	}

	@Override
	public void setAreaStrict(boolean areaStrict) {

		this.areaStrict = areaStrict;
	}

	@Override
	public void enableStep(boolean enabled) {

		stepEnabled = enabled;
	}

	@Override
	public boolean isStepEnabled() {

		return stepEnabled;
	}

	@Override
	public Range getAdjustedRange(Axis axis, int length) {

		Range range;
		if(axis.getDirection() == Direction.X) {
			range = getXRange();
		} else {
			range = getYRange();
		}
		int lowerPlotMargin = getSymbolSize() + MARGIN_AT_MIN_MAX_PLOT;
		int upperPlotMargin = getSymbolSize() + MARGIN_AT_MIN_MAX_PLOT;
		return getRangeWithMargin(lowerPlotMargin, upperPlotMargin, length, axis, range);
	}

	@Override
	public int getAntialias() {

		return antialias;
	}

	@Override
	public void setAntialias(int antialias) {

		if(antialias != SWT.DEFAULT && antialias != SWT.ON && antialias != SWT.OFF) {
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		}
		this.antialias = antialias;
	}

	private int[] getLinePoints(double[] xseries, double[] yseries, int[] indexes, int index, Axis xAxis, Axis yAxis) {

		int x1 = xAxis.getPixelCoordinate(xseries[index]);
		int x2 = xAxis.getPixelCoordinate(xseries[index + 1]);
		int x3 = x2;
		int x4 = x1;
		int y1 = yAxis.getPixelCoordinate(yseries[index]);
		int y2 = yAxis.getPixelCoordinate(yseries[index + 1]);
		int y3, y4;
		double baseYCoordinate = yAxis.getRange().lower > 0 ? yAxis.getRange().lower : 0;
		if(yAxis.isLogScaleEnabled()) {
			y3 = yAxis.getPixelCoordinate(yAxis.getRange().lower);
			y4 = y3;
		} else if(isValidStackSeries()) {
			y1 = yAxis.getPixelCoordinate(stackSeries[indexes[index]]);
			y2 = yAxis.getPixelCoordinate(stackSeries[indexes[index + 1]]);
			y3 = yAxis.getPixelCoordinate(stackSeries[indexes[index + 1]]) + Math.abs(yAxis.getPixelCoordinate(yseries[index + 1]) - yAxis.getPixelCoordinate(0)) * (xAxis.isHorizontalAxis() ? 1 : -1);
			y4 = yAxis.getPixelCoordinate(stackSeries[indexes[index]]) + Math.abs(yAxis.getPixelCoordinate(yseries[index]) - yAxis.getPixelCoordinate(0)) * (xAxis.isHorizontalAxis() ? 1 : -1);
		} else {
			y3 = yAxis.getPixelCoordinate(baseYCoordinate);
			y4 = y3;
		}
		if(xAxis.isHorizontalAxis()) {
			return new int[]{x1, y1, x2, y2, x3, y3, x4, y4};
		}
		return new int[]{y1, x1, y2, x2, y3, x3, y4, x4};
	}

	private boolean isUseAreaStrict() {

		return areaEnabled && areaStrict && !stepEnabled;
	}

	@Override
	protected void draw(GC gc, int width, int height, Axis xAxis, Axis yAxis) {

		if(drawingMode == DrawingMode.BRESENHAM) {
			drawBresenham(gc, width, height, xAxis, yAxis);
		} else {
			drawClassic(gc, width, height, xAxis, yAxis);
		}
	}

	private void drawClassic(GC gc, int width, int height, Axis xAxis, Axis yAxis) {

		int oldAntialias = gc.getAntialias();
		int oldLineWidth = gc.getLineWidth();
		gc.setAntialias(antialias);
		gc.setLineWidth(lineWidth);
		if(lineStyle != LineStyle.NONE) {
			drawLineAndAreaClassic(gc, width, height, xAxis, yAxis);
		}
		if(symbolType != PlotSymbolType.NONE || getLabel().isVisible() || getXErrorBar().isVisible() || getYErrorBar().isVisible()) {
			drawSymbolAndLabel(gc, width, height, xAxis, yAxis);
		}
		gc.setAntialias(oldAntialias);
		gc.setLineWidth(oldLineWidth);
	}

	private void drawLineAndAreaClassic(GC gc, int width, int height, Axis xAxis, Axis yAxis) {

		double[] xseries = compressor.getCompressedXSeries();
		double[] yseries = compressor.getCompressedYSeries();
		if(xseries.length == 0 || yseries.length == 0) {
			return;
		}
		int[] indexes = compressor.getCompressedIndexes();
		if(xAxis.isValidCategoryAxis()) {
			for(int i = 0; i < xseries.length; i++) {
				xseries[i] = indexes[i];
			}
		}
		gc.setLineStyle(lineStyle.value());
		Color oldForeground = gc.getForeground();
		gc.setForeground(getLineColor());
		boolean isHorizontal = xAxis.isHorizontalAxis();
		if(stepEnabled || areaEnabled || stackEnabled) {
			boolean useAreaStrict = isUseAreaStrict();
			int length = xseries.length - 1;
			int numberValues = 4;
			int[] points = useAreaStrict ? new int[length * numberValues] : null;
			double[] x = getXSeries();
			double[] y = getYSeries();
			int[] idx = useAreaStrict ? IntStream.range(0, x.length - 1).toArray() : null;
			int[] p0 = useAreaStrict ? getLinePoints(x, y, idx, 0, xAxis, yAxis) : null;
			int[] pn = useAreaStrict ? getLinePoints(x, y, idx, idx.length - 1, xAxis, yAxis) : null;
			for(int i = 0; i < length; i++) {
				int[] p = getLinePoints(xseries, yseries, indexes, i, xAxis, yAxis);
				if(lineStyle != LineStyle.NONE) {
					if(stepEnabled) {
						if(isHorizontal) {
							gc.drawLine(p[0], p[1], p[2], p[1]);
							gc.drawLine(p[2], p[1], p[2], p[3]);
						} else {
							gc.drawLine(p[0], p[1], p[0], p[3]);
							gc.drawLine(p[0], p[3], p[2], p[3]);
						}
					} else {
						gc.drawLine(p[0], p[1], p[2], p[3]);
					}
				}
				if(areaEnabled) {
					if(useAreaStrict) {
						for(int j = 0; j < numberValues; j++) {
							points[i * numberValues + j] = p[j];
						}
					} else {
						drawArea(gc, p, isHorizontal);
					}
				}
			}
			if(useAreaStrict && points.length > 2) {
				points[0] = p0[0];
				points[1] = p0[1];
				points[points.length - 2] = pn[2];
				points[points.length - 1] = pn[3];
				drawAreaStrict(gc, points);
			}
		} else {
			if(lineStyle == LineStyle.SOLID) {
				drawLineGC(gc, xAxis, yAxis, xseries, yseries, isHorizontal);
			} else {
				drawLineWithStyle(gc, xAxis, yAxis, xseries, yseries, isHorizontal);
			}
		}
		gc.setForeground(oldForeground);
	}

	/*
	 * Optimized solid-line draw via GC. Collapses multiple data points at the
	 * same x-pixel into a single vertical line segment to avoid redundant overdraw.
	 * Kept separate from drawLineWithStyle() to avoid touching the workaround for
	 * Eclipse bug #243588 until that bug is resolved.
	 */
	private static void drawLineGC(GC gc, Axis xAxis, Axis yAxis, double[] xseries, double[] yseries, boolean isHorizontal) {

		double xLower = xAxis.getRange().lower;
		double xUpper = xAxis.getRange().upper;
		double yLower = yAxis.getRange().lower;
		double yUpper = yAxis.getRange().upper;
		int prevX = xAxis.getPixelCoordinate(xseries[0], xLower, xUpper);
		int prevY = yAxis.getPixelCoordinate(yseries[0], yLower, yUpper);
		boolean drawVerticalLine = false;
		int verticalLineYLower = 0;
		int verticalLineYUpper = 0;
		for(int i = 0; i < xseries.length - 1; i++) {
			int x = xAxis.getPixelCoordinate(xseries[i + 1], xLower, xUpper);
			int y = yAxis.getPixelCoordinate(yseries[i + 1], yLower, yUpper);
			if(x == prevX && i < xseries.length - 2) {
				if(drawVerticalLine) {
					verticalLineYLower = Math.min(verticalLineYLower, y);
					verticalLineYUpper = Math.max(verticalLineYUpper, y);
				} else {
					verticalLineYLower = Math.min(prevY, y);
					verticalLineYUpper = Math.max(prevY, y);
					drawVerticalLine = true;
				}
			} else {
				if(drawVerticalLine) {
					if(isHorizontal) {
						gc.drawLine(prevX, verticalLineYLower, prevX, verticalLineYUpper);
					} else {
						gc.drawLine(verticalLineYLower, prevX, verticalLineYUpper, prevX);
					}
					drawVerticalLine = false;
				}
				if(isHorizontal) {
					gc.drawLine(prevX, prevY, x, y);
				} else {
					gc.drawLine(prevY, prevX, y, x);
				}
			}
			prevX = x;
			prevY = y;
		}
	}

	/*
	 * Workaround for Eclipse bug #243588: gc.setAdvanced(true) is required
	 * before drawing a polyline with a non-solid line style.
	 * Collapses same-x vertical sequences into a single vertical segment.
	 */
	private static void drawLineWithStyle(GC gc, Axis xAxis, Axis yAxis, double[] xseries, double[] yseries, boolean isHorizontal) {

		double xLower = xAxis.getRange().lower;
		double xUpper = xAxis.getRange().upper;
		double yLower = yAxis.getRange().lower;
		double yUpper = yAxis.getRange().upper;
		List<Integer> pointList = new ArrayList<>();
		int prevX = xAxis.getPixelCoordinate(xseries[0], xLower, xUpper);
		int prevY = yAxis.getPixelCoordinate(yseries[0], yLower, yUpper);
		addPoint(pointList, prevX, prevY, isHorizontal);
		boolean drawVerticalLine = false;
		int verticalLineYLower = 0;
		int verticalLineYUpper = 0;
		for(int i = 0; i < xseries.length - 1; i++) {
			int x = xAxis.getPixelCoordinate(xseries[i + 1], xLower, xUpper);
			int y = yAxis.getPixelCoordinate(yseries[i + 1], yLower, yUpper);
			if(x == prevX && i < xseries.length - 2) {
				if(drawVerticalLine) {
					verticalLineYLower = Math.min(verticalLineYLower, y);
					verticalLineYUpper = Math.max(verticalLineYUpper, y);
				} else {
					verticalLineYLower = Math.min(prevY, y);
					verticalLineYUpper = Math.max(prevY, y);
					drawVerticalLine = true;
				}
			} else {
				if(drawVerticalLine) {
					addPoint(pointList, prevX, verticalLineYLower, isHorizontal);
					addPoint(pointList, prevX, verticalLineYUpper, isHorizontal);
					addPoint(pointList, prevX, prevY, isHorizontal);
				}
				addPoint(pointList, x, y, isHorizontal);
				drawVerticalLine = false;
			}
			prevX = x;
			prevY = y;
		}
		int[] polyline = new int[pointList.size()];
		for(int i = 0; i < polyline.length; i++) {
			polyline[i] = pointList.get(i);
		}
		boolean advanced = gc.getAdvanced();
		gc.setAdvanced(true); // workaround for eclipse bug #243588
		gc.drawPolyline(polyline);
		gc.setAdvanced(advanced);
	}

	private static void addPoint(List<Integer> pointList, int x, int y, boolean isHorizontal) {

		if(isHorizontal) {
			pointList.add(Integer.valueOf(x));
			pointList.add(Integer.valueOf(y));
		} else {
			pointList.add(Integer.valueOf(y));
			pointList.add(Integer.valueOf(x));
		}
	}

	private void drawArea(GC gc, int[] p, boolean isHorizontal) {

		int alpha = gc.getAlpha();
		gc.setAlpha(ALPHA);
		Color oldBackground = gc.getBackground();
		gc.setBackground(getLineColor());
		int[] pointArray;
		if(stepEnabled) {
			if(isHorizontal) {
				pointArray = new int[]{p[0], p[1], p[2], p[1], p[4], p[7], p[6], p[7], p[0], p[1]};
			} else {
				pointArray = new int[]{p[0], p[1], p[0], p[3], p[6], p[5], p[6], p[7], p[0], p[1]};
			}
		} else {
			pointArray = new int[]{p[0], p[1], p[2], p[3], p[4], p[5], p[6], p[7], p[0], p[1]};
		}
		gc.fillPolygon(pointArray);
		gc.setAlpha(alpha);
		gc.setBackground(oldBackground);
	}

	private void drawAreaStrict(GC gc, int[] points) {

		int alpha = gc.getAlpha();
		gc.setAlpha(ALPHA);
		Color oldBackground = gc.getBackground();
		gc.setBackground(getLineColor());
		gc.fillPolygon(points);
		gc.setAlpha(alpha);
		gc.setBackground(oldBackground);
	}

	private void drawBresenham(GC gc, int width, int height, Axis xAxis, Axis yAxis) {

		int oldAntialias = gc.getAntialias();
		int oldLineWidth = gc.getLineWidth();
		gc.setAntialias(antialias);
		gc.setLineWidth(lineWidth);
		if(lineStyle != LineStyle.NONE) {
			if(lineStyle == LineStyle.SOLID) {
				/*
				 * Area must be drawn first so lines appear on top
				 */
				if(areaEnabled) {
					drawAreaBresenham(gc, width, height, xAxis, yAxis);
				}
				drawLinesBresenhamToGC(gc, width, height, xAxis, yAxis);
			} else {
				/*
				 * Non-solid line styles are not supported by the pixel rasterizer;
				 * delegate entirely to the GC path (area included)
				 */
				drawLineAndAreaClassic(gc, width, height, xAxis, yAxis);
			}
		}
		if(symbolType != PlotSymbolType.NONE || getLabel().isVisible() || getXErrorBar().isVisible() || getYErrorBar().isVisible()) {
			drawSymbolAndLabel(gc, width, height, xAxis, yAxis);
		}
		gc.setAntialias(oldAntialias);
		gc.setLineWidth(oldLineWidth);
	}

	/**
	 * Draws only the area fill (no lines) via GC so that the Bresenham line
	 * image can be blitted on top afterwards.
	 */
	private void drawAreaBresenham(GC gc, int width, int height, Axis xAxis, Axis yAxis) {

		double[] xseries = compressor.getCompressedXSeries();
		double[] yseries = compressor.getCompressedYSeries();
		if(xseries.length == 0 || yseries.length == 0) {
			return;
		}
		int[] indexes = compressor.getCompressedIndexes();
		if(xAxis.isValidCategoryAxis()) {
			for(int i = 0; i < xseries.length; i++) {
				xseries[i] = indexes[i];
			}
		}
		boolean isHorizontal = xAxis.isHorizontalAxis();
		boolean useAreaStrict = isUseAreaStrict();
		int length = xseries.length - 1;
		int numberValues = 4;
		int[] points = useAreaStrict ? new int[length * numberValues] : null;
		double[] x = getXSeries();
		double[] y = getYSeries();
		int[] idx = useAreaStrict ? IntStream.range(0, x.length - 1).toArray() : null;
		int[] p0 = useAreaStrict ? getLinePoints(x, y, idx, 0, xAxis, yAxis) : null;
		int[] pn = useAreaStrict ? getLinePoints(x, y, idx, idx.length - 1, xAxis, yAxis) : null;
		for(int i = 0; i < length; i++) {
			int[] p = getLinePoints(xseries, yseries, indexes, i, xAxis, yAxis);
			if(useAreaStrict) {
				for(int j = 0; j < numberValues; j++) {
					points[i * numberValues + j] = p[j];
				}
			} else {
				drawArea(gc, p, isHorizontal);
			}
		}
		if(useAreaStrict && points.length > 2) {
			points[0] = p0[0];
			points[1] = p0[1];
			points[points.length - 2] = pn[2];
			points[points.length - 1] = pn[3];
			drawAreaStrict(gc, points);
		}
	}

	/**
	 * Draws all line segments into an ImageData using Bresenham's algorithm,
	 * then blits the result onto the GC. Uses palette index 2 for line pixels
	 * and index 0 (white) as the transparent background.
	 */
	private void drawLinesBresenhamToGC(GC gc, int width, int height, Axis xAxis, Axis yAxis) {

		double[] xseries = compressor.getCompressedXSeries();
		double[] yseries = compressor.getCompressedYSeries();
		if(xseries.length == 0 || yseries.length == 0) {
			return;
		}
		int[] indexes = compressor.getCompressedIndexes();
		if(xAxis.isValidCategoryAxis()) {
			for(int i = 0; i < xseries.length; i++) {
				xseries[i] = indexes[i];
			}
		}
		PaletteData paletteData = new PaletteData(new RGB[]{new RGB(255, 255, 255), getSymbolColor().getRGB(), getLineColor().getRGB()});
		ImageData imageData = new ImageData(width, height, 2, paletteData);
		imageData.transparentPixel = 0;
		int pixelSize = Math.max(1, lineWidth);
		LineDrawingBresenham gcb = new LineDrawingBresenham(width, height, pixelSize);
		gcb.setColorIndex(2);
		boolean isHorizontal = xAxis.isHorizontalAxis();
		if(stepEnabled || areaEnabled || stackEnabled) {
			for(int i = 0; i < xseries.length - 1; i++) {
				int[] p = getLinePoints(xseries, yseries, indexes, i, xAxis, yAxis);
				if(stepEnabled) {
					if(isHorizontal) {
						gcb.drawLine(imageData, p[0], p[1], p[2], p[1]);
						gcb.drawLine(imageData, p[2], p[1], p[2], p[3]);
					} else {
						gcb.drawLine(imageData, p[0], p[1], p[0], p[3]);
						gcb.drawLine(imageData, p[0], p[3], p[2], p[3]);
					}
				} else {
					gcb.drawLine(imageData, p[0], p[1], p[2], p[3]);
				}
			}
		} else {
			drawLineBresenham(imageData, xAxis, yAxis, xseries, yseries, isHorizontal, gcb);
		}
		Image image = new Image(Display.getCurrent(), imageData);
		gc.drawImage(image, 0, 0);
		image.dispose();
	}

	/*
	 * Bresenham solid-line draw into ImageData. Collapses same-x-pixel vertical
	 * sequences into a single vertical segment, matching the behaviour of
	 * drawLineGC().
	 */
	private static void drawLineBresenham(ImageData imageData, Axis xAxis, Axis yAxis, double[] xseries, double[] yseries, boolean isHorizontal, LineDrawingBresenham gcb) {

		double xLower = xAxis.getRange().lower;
		double xUpper = xAxis.getRange().upper;
		double yLower = yAxis.getRange().lower;
		double yUpper = yAxis.getRange().upper;
		int prevX = xAxis.getPixelCoordinate(xseries[0], xLower, xUpper);
		int prevY = yAxis.getPixelCoordinate(yseries[0], yLower, yUpper);
		boolean drawVerticalLine = false;
		int verticalLineYLower = 0;
		int verticalLineYUpper = 0;
		for(int i = 0; i < xseries.length - 1; i++) {
			int x = xAxis.getPixelCoordinate(xseries[i + 1], xLower, xUpper);
			int y = yAxis.getPixelCoordinate(yseries[i + 1], yLower, yUpper);
			if(x == prevX && i < xseries.length - 2) {
				if(drawVerticalLine) {
					verticalLineYLower = Math.min(verticalLineYLower, y);
					verticalLineYUpper = Math.max(verticalLineYUpper, y);
				} else {
					verticalLineYLower = Math.min(prevY, y);
					verticalLineYUpper = Math.max(prevY, y);
					drawVerticalLine = true;
				}
			} else {
				if(drawVerticalLine) {
					if(isHorizontal) {
						gcb.drawLine(imageData, prevX, verticalLineYLower, prevX, verticalLineYUpper);
					} else {
						gcb.drawLine(imageData, verticalLineYLower, prevX, verticalLineYUpper, prevX);
					}
					drawVerticalLine = false;
				}
				if(isHorizontal) {
					gcb.drawLine(imageData, prevX, prevY, x, y);
				} else {
					gcb.drawLine(imageData, prevY, prevX, y, x);
				}
			}
			prevX = x;
			prevY = y;
		}
	}

	private void drawSymbolAndLabel(GC gc, int width, int height, Axis xAxis, Axis yAxis) {

		double[] xseries = compressor.getCompressedXSeries();
		double[] yseries = compressor.getCompressedYSeries();
		int[] indexes = compressor.getCompressedIndexes();
		if(xAxis.isValidCategoryAxis()) {
			boolean isValidStackSeries = isValidStackSeries();
			for(int i = 0; i < xseries.length; i++) {
				xseries[i] = indexes[i];
				if(isValidStackSeries) {
					yseries[i] = stackSeries[indexes[i]];
				}
			}
		}
		for(int i = 0; i < xseries.length; i++) {
			Color color;
			if(symbolColors.length > indexes[i]) {
				color = symbolColors[indexes[i]];
			} else {
				color = getSymbolColor();
			}
			int h, v;
			if(xAxis.isHorizontalAxis()) {
				h = xAxis.getPixelCoordinate(xseries[i]);
				v = yAxis.getPixelCoordinate(yseries[i]);
			} else {
				v = xAxis.getPixelCoordinate(xseries[i]);
				h = yAxis.getPixelCoordinate(yseries[i]);
			}
			if(getSymbolType() != PlotSymbolType.NONE) {
				drawSeriesSymbol(gc, h, v, color);
			}
			seriesLabel.draw(gc, h, v, yseries[i], indexes[i], SWT.BOTTOM);
			xErrorBar.draw(gc, h, v, xAxis, indexes[i]);
			yErrorBar.draw(gc, h, v, yAxis, indexes[i]);
		}
	}

	/**
	 * Draws the symbol for a single data point.
	 *
	 * @param gc
	 *            the GC object
	 * @param h
	 *            the horizontal coordinate
	 * @param v
	 *            the vertical coordinate
	 * @param color
	 *            the symbol color
	 */
	public void drawSeriesSymbol(GC gc, int h, int v, Color color) {

		int oldAntialias = gc.getAntialias();
		gc.setAntialias(SWT.ON);
		Color oldForeground = gc.getForeground();
		gc.setForeground(color);
		Color oldBackground = gc.getBackground();
		gc.setBackground(color);
		switch(symbolType) {
			case CIRCLE:
				gc.fillOval(h - symbolSize, v - symbolSize, symbolSize * 2, symbolSize * 2);
				break;
			case SQUARE:
				gc.fillRectangle(h - symbolSize, v - symbolSize, symbolSize * 2, symbolSize * 2);
				break;
			case DIAMOND:
				int[] diamondArray = {h, v - symbolSize, h + symbolSize, v, h, v + symbolSize, h - symbolSize, v};
				gc.fillPolygon(diamondArray);
				break;
			case TRIANGLE:
				int[] triangleArray = {h, v - symbolSize, h + symbolSize, v + symbolSize, h - symbolSize, v + symbolSize};
				gc.fillPolygon(triangleArray);
				break;
			case INVERTED_TRIANGLE:
				int[] invertedTriangleArray = {h, v + symbolSize, h + symbolSize, v - symbolSize, h - symbolSize, v - symbolSize};
				gc.fillPolygon(invertedTriangleArray);
				break;
			case CROSS:
				gc.setLineStyle(SWT.LINE_SOLID);
				gc.drawLine(h - symbolSize, v - symbolSize, h + symbolSize, v + symbolSize);
				gc.drawLine(h - symbolSize, v + symbolSize, h + symbolSize, v - symbolSize);
				break;
			case PLUS:
				gc.setLineStyle(SWT.LINE_SOLID);
				gc.drawLine(h, v - symbolSize, h, v + symbolSize);
				gc.drawLine(h - symbolSize, v, h + symbolSize, v);
				break;
			case EMOJI:
				String extendedSymbol = getExtendedPlotSymbolType();
				Point extendedSymbolSize = gc.textExtent(extendedSymbol);
				gc.drawText(extendedSymbol, h - extendedSymbolSize.x / 2, v - extendedSymbolSize.y / 2, true);
				break;
			case NONE:
			default:
				break;
		}
		gc.setAntialias(oldAntialias);
		gc.setBackground(oldBackground);
		gc.setForeground(oldForeground);
	}
}