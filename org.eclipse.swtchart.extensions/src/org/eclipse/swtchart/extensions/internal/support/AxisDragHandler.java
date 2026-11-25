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
package org.eclipse.swtchart.extensions.internal.support;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swtchart.IAxis;
import org.eclipse.swtchart.IAxis.Direction;
import org.eclipse.swtchart.IAxisSet;
import org.eclipse.swtchart.IAxisTick;
import org.eclipse.swtchart.Range;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.support.RangeSupport;

/**
 * Handles mouse drags on the axes (outside of the plot area).
 * Left mouse button: move the axis range (the content follows the mouse).
 * Right mouse button: zoom in (move right/up) or zoom out (move left/down).
 */
public class AxisDragHandler implements Listener {

	private static final int BUTTON_LEFT = 1;
	private static final int BUTTON_RIGHT = 3;
	/*
	 * Pixels needed to trigger one zoom step.
	 */
	private static final int ZOOM_STEP_PIXELS = 15;

	private final BaseChart baseChart;

	private int button = 0;
	private Direction direction = null;
	private boolean horizontal = true;
	private int pixelStart = 0;
	private int pixelLast = 0;
	private int zoomAnchor = 0;

	public AxisDragHandler(BaseChart baseChart) {

		this.baseChart = baseChart;
	}

	public void register() {

		baseChart.addListener(SWT.MouseDown, this);
		baseChart.addListener(SWT.MouseMove, this);
		baseChart.addListener(SWT.MouseUp, this);
	}

	@Override
	public void handleEvent(Event event) {

		switch(event.type) {
			case SWT.MouseDown:
				handleMouseDown(event);
				break;
			case SWT.MouseMove:
				handleMouseMove(event);
				break;
			case SWT.MouseUp:
				handleMouseUp();
				break;
			default:
				break;
		}
	}

	private void handleMouseDown(Event event) {

		if(event.button != BUTTON_LEFT && event.button != BUTTON_RIGHT) {
			return;
		}

		IAxis axis = getAxis(event.x, event.y);
		if(axis != null) {
			button = event.button;
			direction = axis.getDirection();
			horizontal = isHorizontal(direction);
			pixelStart = getPixel(event);
			pixelLast = pixelStart;
			zoomAnchor = pixelStart;
			baseChart.setCursor(baseChart.getDisplay().getSystemCursor(horizontal ? SWT.CURSOR_SIZEWE : SWT.CURSOR_SIZENS));
		}
	}

	private void handleMouseMove(Event event) {

		if(direction == null) {
			return;
		}

		IAxis axis = getPrimaryAxis(direction);
		if(axis != null) {
			int pixel = getPixel(event);
			if(button == BUTTON_LEFT) {
				move(axis, pixel);
			} else if(button == BUTTON_RIGHT) {
				zoom(axis, pixel);
			}
		}
	}

	private void handleMouseUp() {

		if(direction != null) {
			button = 0;
			direction = null;
			baseChart.setCursor(null);
		}
	}

	private void move(IAxis axis, int pixel) {

		if(pixel == pixelLast) {
			return;
		}
		/*
		 * The content follows the mouse, hence the range is
		 * shifted in the opposite direction.
		 */
		double shift = axis.getDataCoordinate(pixelLast) - axis.getDataCoordinate(pixel);
		pixelLast = pixel;
		Range range = RangeSupport.calculateSlideRange(axis.getRange(), shift, getMin(), getMax());
		if(range != null) {
			baseChart.setRange(axis, range.lower, range.upper, true);
			update();
		}
	}

	private void zoom(IAxis axis, int pixel) {

		/*
		 * Right/Up = zoom in
		 * Left/Down = zoom out
		 */
		int delta = horizontal ? pixel - pixelStart : pixelStart - pixel;
		int steps = delta / ZOOM_STEP_PIXELS;
		if(steps == 0) {
			return;
		}

		pixelStart += (horizontal ? steps : -steps) * ZOOM_STEP_PIXELS;
		/*
		 * The zoom methods use either x or y, depending on the axis.
		 */
		Event event = new Event();
		event.x = zoomAnchor;
		event.y = zoomAnchor;
		event.count = steps > 0 ? 1 : -1;
		for(int i = 0; i < Math.abs(steps); i++) {
			if(direction == Direction.X) {
				baseChart.zoomX(axis, event);
			} else {
				baseChart.zoomY(axis, event);
			}
		}

		validateZoom(axis);
		update();
	}

	private void validateZoom(IAxis axis) {

		if(baseChart.getRangeRestriction().isRestrictFrame()) {
			Range range = axis.getRange();
			baseChart.setRange(axis, range.lower, range.upper, true);
		} else if(direction == Direction.X) {
			baseChart.adjustSecondaryXAxes();
		} else {
			baseChart.adjustSecondaryYAxes();
		}
	}

	private void update() {

		baseChart.fireUpdateCustomRangeSelectionHandlers(new Event());
		baseChart.redraw();
	}

	/**
	 * Returns the axis whose tick area contains the given position or null.
	 *
	 * @param x
	 * @param y
	 * @return IAxis
	 */
	private IAxis getAxis(int x, int y) {

		Rectangle boundsPlotArea = baseChart.getPlotArea().getBounds();
		if(boundsPlotArea.contains(x, y)) {
			return null;
		}

		for(IAxis axis : baseChart.getAxisSet().getAxes()) {
			IAxisTick axisTick = axis.getTick();
			if(axisTick.isVisible() && axisTick.getBounds().contains(x, y)) {
				return axis;
			}
		}

		return null;
	}

	private IAxis getPrimaryAxis(Direction direction) {

		IAxisSet axisSet = baseChart.getAxisSet();
		if(direction == Direction.X) {
			return axisSet.getXAxis(BaseChart.ID_PRIMARY_X_AXIS);
		} else {
			return axisSet.getYAxis(BaseChart.ID_PRIMARY_Y_AXIS);
		}
	}

	/**
	 * Returns the pixel along the axis, relative to the plot area.
	 *
	 * @param event
	 * @return int
	 */
	private int getPixel(Event event) {

		Rectangle boundsPlotArea = baseChart.getPlotArea().getBounds();
		return horizontal ? event.x - boundsPlotArea.x : event.y - boundsPlotArea.y;
	}

	private boolean isHorizontal(Direction direction) {

		boolean horizontalOrientation = RangeSupport.isOrientationHorizontal(baseChart);
		return (direction == Direction.X) == horizontalOrientation;
	}

	private double getMin() {

		return direction == Direction.X ? baseChart.getMinX() : baseChart.getMinY();
	}

	private double getMax() {

		return direction == Direction.X ? baseChart.getMaxX() : baseChart.getMaxY();
	}
}