/*******************************************************************************
 * Copyright (c) 2024, 2026 Lablicate GmbH.
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
package org.eclipse.swtchart.extensions.support;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtchart.IAxis;
import org.eclipse.swtchart.Range;
import org.eclipse.swtchart.extensions.core.BaseChart;

public class RangeSupport {

	/*
	 * Integer.MAX_VALUE doesn't work under Windows.
	 */
	private static final int HORIZONTAL_SCROLL_LENGTH = 1000000;
	private static final int VERTICAL_SCROLL_LENGTH = 1000000;

	public static void applyHorizontalSlide(BaseChart baseChart, double factor, boolean slidePrevious) {

		IAxis xAxis = baseChart.getAxisSet().getXAxis(BaseChart.ID_PRIMARY_X_AXIS);
		if(xAxis != null) {
			/*
			 * Slide to the previous or next block.
			 */
			double delta = (xAxis.getRange().upper - xAxis.getRange().lower) * factor;
			applyHorizontalShift(baseChart, slidePrevious ? -delta : delta);
		}
	}

	public static void applyVerticalSlide(BaseChart baseChart, double factor, boolean slidePrevious) {

		IAxis yAxis = baseChart.getAxisSet().getYAxis(BaseChart.ID_PRIMARY_Y_AXIS);
		if(yAxis != null) {
			/*
			 * Slide to the previous or next block.
			 */
			double delta = (yAxis.getRange().upper - yAxis.getRange().lower) * factor;
			applyVerticalShift(baseChart, slidePrevious ? delta : -delta);
		}
	}

	/**
	 * Shifts the primary x axis range by the given data delta.
	 * The width of the range is kept.
	 * 
	 * @param baseChart
	 * @param shift
	 */
	public static void applyHorizontalShift(BaseChart baseChart, double shift) {

		IAxis xAxis = baseChart.getAxisSet().getXAxis(BaseChart.ID_PRIMARY_X_AXIS);
		IAxis yAxis = baseChart.getAxisSet().getYAxis(BaseChart.ID_PRIMARY_Y_AXIS);

		if(xAxis != null && yAxis != null) {
			Range range = calculateSlideRange(xAxis.getRange(), shift, baseChart.getMinX(), baseChart.getMaxX());
			if(range != null) {
				applyHorizontalSlide(baseChart, xAxis, yAxis, range, new Event());
			}
		}
	}

	/**
	 * Shifts the primary y axis range by the given data delta.
	 * The width of the range is kept.
	 * 
	 * @param baseChart
	 * @param shift
	 */
	public static void applyVerticalShift(BaseChart baseChart, double shift) {

		IAxis xAxis = baseChart.getAxisSet().getXAxis(BaseChart.ID_PRIMARY_X_AXIS);
		IAxis yAxis = baseChart.getAxisSet().getYAxis(BaseChart.ID_PRIMARY_Y_AXIS);

		if(xAxis != null && yAxis != null) {
			Range range = calculateSlideRange(yAxis.getRange(), shift, baseChart.getMinY(), baseChart.getMaxY());
			if(range != null) {
				applyVerticalSlide(baseChart, xAxis, yAxis, range, new Event());
			}
		}
	}

	/**
	 * Returns the range shifted by the given delta. The width of the
	 * range is kept. The shift is limited by min/max.
	 * Returns null if the range can't be shifted.
	 * 
	 * @param range
	 * @param shift
	 * @param min
	 * @param max
	 * @return Range
	 */
	public static Range calculateSlideRange(Range range, double shift, double min, double max) {

		/*
		 * The range might exceed the data min/max values, e.g. if
		 * the range has been extended (scatter charts). Hence, the
		 * current range is used as limit too. Otherwise, the shift
		 * to the left/bottom would be rejected.
		 */
		double lowerLimit = Math.min(min, range.lower);
		double upperLimit = Math.max(max, range.upper);
		double delta;
		if(shift < 0) {
			delta = Math.max(shift, lowerLimit - range.lower);
		} else {
			delta = Math.min(shift, upperLimit - range.upper);
		}
		/*
		 * Keep the width of the range.
		 */
		if(delta != 0 && !Double.isNaN(delta) && !Double.isInfinite(delta)) {
			return new Range(range.lower + delta, range.upper + delta);
		}

		return null;
	}

	/**
	 * This method returns null if the range can't be calculated correctly.
	 * 
	 * @param baseChart
	 * @param range
	 * @param selection
	 * @param sliderOrientation
	 * @return Range
	 */
	public static Range calculateShiftedRange(BaseChart baseChart, Range range, double selection, int sliderOrientation) {

		int maxX = HORIZONTAL_SCROLL_LENGTH;
		int maxY = VERTICAL_SCROLL_LENGTH;
		/*
		 * Relative binding to Integer.MAX_VALUE.
		 */
		double deltaX = baseChart.getMaxX() - baseChart.getMinX();
		double deltaY = baseChart.getMaxY() - baseChart.getMinY();

		if(deltaX > 0 && deltaY > 0) {
			/*
			 * Shift calculation
			 */
			double coeffX = maxX / deltaX;
			double coeffY = maxY / deltaY;
			double shiftX = -coeffX * baseChart.getMinX();
			double shiftY = coeffY * baseChart.getMaxY();
			/*
			 * Validate
			 */
			if(coeffX != 0 && !Double.isNaN(shiftX) && coeffY != 0 && !Double.isNaN(shiftY)) {
				/*
				 * Get the current selection
				 */
				boolean isChartHorizontal = isOrientationHorizontal(baseChart);
				double max;
				double min;
				if(sliderOrientation == SWT.HORIZONTAL) {
					min = (isChartHorizontal ? (selection - shiftX) / coeffX : (shiftY - selection) / coeffY);
					max = (isChartHorizontal ? min + (range.upper - range.lower) : min - (range.upper - range.lower));
				} else {
					max = (!isChartHorizontal ? (selection - shiftX) / coeffX : (shiftY - selection) / coeffY);
					min = (!isChartHorizontal ? max + (range.upper - range.lower) : max - (range.upper - range.lower));
				}

				if(!Double.isNaN(min) && !Double.isNaN(max)) {
					return new Range(min, max);
				}
			}
		}

		return null;
	}

	public static boolean isOrientationHorizontal(BaseChart baseChart) {

		return (baseChart.getOrientation() == SWT.HORIZONTAL);
	}

	public static boolean applyHorizontalSlide(BaseChart baseChart, IAxis xAxis, IAxis yAxis, Range range, Event event) {

		if(xAxis != null && yAxis != null) {
			if(range != null) {
				if(RangeSupport.isOrientationHorizontal(baseChart)) {
					if(baseChart.isRangeValid(range)) {
						xAxis.setRange(range);
						baseChart.adjustMinMaxRange(xAxis);
						adjustSecondaryXAxes(baseChart);
					}
				} else {
					if(baseChart.isRangeValid(range)) {
						yAxis.setRange(range);
						baseChart.adjustMinMaxRange(yAxis);
						adjustSecondaryYAxes(baseChart);
					}
				}

				baseChart.update();
				return true;
			}
		}

		return false;
	}

	public static boolean applyVerticalSlide(BaseChart baseChart, IAxis xAxis, IAxis yAxis, Range range, Event event) {

		if(range != null) {
			if(RangeSupport.isOrientationHorizontal(baseChart)) {
				if(baseChart.isRangeValid(range)) {
					yAxis.setRange(range);
					baseChart.adjustMinMaxRange(yAxis);
					adjustSecondaryYAxes(baseChart);
				}
			} else {
				if(baseChart.isRangeValid(range)) {
					xAxis.setRange(range);
					baseChart.adjustMinMaxRange(xAxis);
					adjustSecondaryXAxes(baseChart);
				}
			}

			baseChart.redraw();
			return true;
		}

		return false;
	}

	private static void adjustSecondaryXAxes(BaseChart baseChart) {

		baseChart.adjustSecondaryXAxes();
	}

	private static void adjustSecondaryYAxes(BaseChart baseChart) {

		baseChart.adjustSecondaryYAxes();
	}
}