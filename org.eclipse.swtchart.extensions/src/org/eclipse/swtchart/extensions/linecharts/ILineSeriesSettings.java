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
package org.eclipse.swtchart.extensions.linecharts;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swtchart.LineStyle;
import org.eclipse.swtchart.extensions.core.IPointSeriesSettings;
import org.eclipse.swtchart.model.DrawingMode;

/**
 * Settings of a line series.
 * <p>
 * The values are transferred to the {@link org.eclipse.swtchart.ILineSeries} when the
 * series is created or updated. In addition to the line specific options, the symbol
 * options of {@link IPointSeriesSettings} are used to draw the data points.
 */
public interface ILineSeriesSettings extends IPointSeriesSettings {

	/**
	 * Returns the anti-aliasing value used to draw the line.
	 * The default value is <code>SWT.DEFAULT</code>.
	 *
	 * @return SWT.DEFAULT, SWT.ON or SWT.OFF
	 */
	int getAntialias();

	/**
	 * Sets the anti-aliasing value used to draw the line.
	 * <p>
	 * If the number of data points is large, the series is drawn as a collection of dots
	 * rather than lines. The anti-aliasing then has hardly any visual effect but degrades
	 * the drawing performance.
	 *
	 * @param antialias
	 *            SWT.DEFAULT, SWT.ON, SWT.OFF
	 */
	void setAntialias(int antialias);

	/**
	 * Returns whether the area below the line is filled.
	 */
	boolean isEnableArea();

	/**
	 * Sets whether the area below the line shall be filled.
	 */
	void setEnableArea(boolean enableArea);

	/**
	 * Returns whether the area display is constrained by its left and right min Y value.
	 */
	boolean isAreaStrict();

	/**
	 * Sets whether the area shall be displayed as is, constrained by its left and right
	 * minimum Y value. Only has an effect if the area is enabled, see
	 * {@link #setEnableArea(boolean)}.
	 */
	void setAreaStrict(boolean areaStrict);

	/**
	 * Returns the color used to draw the line.
	 */
	Color getLineColor();

	/**
	 * Sets the color used to draw the line.
	 */
	void setLineColor(Color lineColor);

	/**
	 * Returns the width of the line in pixels.
	 */
	int getLineWidth();

	/**
	 * Sets the width of the line in pixels. The width is also applied to the symbols that
	 * are drawn by lines.
	 */
	void setLineWidth(int lineWidth);

	/**
	 * Returns whether the series is stacked on top of the other stacked series.
	 */
	boolean isEnableStack();

	/**
	 * Sets whether the series shall be stacked on top of the other stacked series.
	 * Stacking requires a category axis and non-negative values.
	 */
	void setEnableStack(boolean enableStack);

	/**
	 * Returns whether the data points are connected by steps instead of straight lines.
	 */
	boolean isEnableStep();

	/**
	 * Sets whether the data points shall be connected by steps instead of straight lines.
	 */
	void setEnableStep(boolean enableStep);

	/**
	 * Returns the style used to draw the line.
	 */
	LineStyle getLineStyle();

	/**
	 * Sets the style used to draw the line. Use <code>LineStyle.NONE</code> to display the
	 * symbols only.
	 */
	void setLineStyle(LineStyle lineStyle);

	/**
	 * Returns the back-end that is used to draw the line.
	 */
	DrawingMode getDrawingMode();

	/**
	 * Sets the back-end that is used to draw the line, see {@link DrawingMode}.
	 */
	void setDrawingMode(DrawingMode drawingMode);
}