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
 *******************************************************************************/
package org.eclipse.swtchart.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.UUID;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtchart.Chart;
import org.eclipse.swtchart.IBarSeries;
import org.eclipse.swtchart.ICustomPaintListener;
import org.eclipse.swtchart.ILineSeries;
import org.eclipse.swtchart.IPlotArea;
import org.eclipse.swtchart.ISeries;
import org.eclipse.swtchart.ISeries.SeriesType;
import org.eclipse.swtchart.test.util.ChartTestCase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Test case for Chart.
 */
public class ChartTest extends ChartTestCase {

	private static final double[] xSeries = {1.0, 2.0, 3.0, 4.0, 5.0};
	private static final double[] ySeries1 = {0.1, 0.2, 0.3, 0.4, 0.5};
	private static final double[] ySeries2 = {1.0, 0.8, 0.6, 0.4, 0.2};
	private static final String[] categorySeries = {"Jan", "Feb", "Mar", "Apr", "May"};

	/**
	 * Test for background.
	 */
	@Test
	public void testBackground() {

		// check the default color
		showChart();
		Color color = chart.getBackground();
		assertEquals(Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND).getRGB(), color.getRGB());
		// set color
		Color syan = Display.getDefault().getSystemColor(SWT.COLOR_CYAN);
		chart.setBackground(syan);
		color = chart.getBackground();
		assertEquals(syan.getRGB(), color.getRGB());
		showChart();
		// set the disposed color
		Color disposed = new Color(0, 0, 0);
		disposed.dispose();
		assertThrows(IllegalArgumentException.class, () -> chart.setBackground(disposed));
		color = chart.getBackground();
		assertEquals(syan.getRGB(), color.getRGB());
		// set null
		chart.setBackground(null);
		color = chart.getBackground();
		assertEquals(Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND).getRGB(), color.getRGB());
		showChart();
	}

	/**
	 * Test for background in plot area
	 */
	@Test
	public void testBackgroundInPlotArea() {

		// check the default color
		showChart();
		Color color = chart.getPlotArea().getBackground();
		assertEquals(Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND).getRGB(), color.getRGB());
		// set color
		Color cyan = Display.getDefault().getSystemColor(SWT.COLOR_CYAN);
		chart.getPlotArea().setBackground(cyan);
		color = chart.getPlotArea().getBackground();
		assertEquals(cyan.getRGB(), color.getRGB());
		showChart();
		// set the disposed color
		Color disposed = new Color(0, 0, 0);
		disposed.dispose();
		assertThrows(IllegalArgumentException.class, () -> chart.getPlotArea().setBackground(disposed));
		color = chart.getPlotArea().getBackground();
		assertEquals(cyan.getRGB(), color.getRGB());
		// set null
		chart.getPlotArea().setBackground(null);
		color = chart.getPlotArea().getBackground();
		assertEquals(Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND).getRGB(), color.getRGB());
		showChart();
	}

	/**
	 * Test for chart orientation with line series
	 */
	@Test
	public void testOrientation1() {

		// create line series
		ILineSeries<?> lineSeries1 = (ILineSeries<?>)chart.getSeriesSet().createSeries(SeriesType.LINE, "line series 1");
		lineSeries1.setXSeries(xSeries);
		lineSeries1.setYSeries(ySeries1);
		lineSeries1.enableStack(true);
		lineSeries1.enableArea(true);
		ILineSeries<?> lineSeries2 = (ILineSeries<?>)chart.getSeriesSet().createSeries(SeriesType.LINE, "line series 2");
		lineSeries2.setXSeries(xSeries);
		lineSeries2.setYSeries(ySeries2);
		lineSeries2.setLineColor(Display.getDefault().getSystemColor(SWT.COLOR_GREEN));
		lineSeries2.enableStack(true);
		lineSeries2.enableArea(true);
		// set category series
		chart.getAxisSet().getXAxis(0).setCategorySeries(categorySeries);
		// check default value
		int orientation = chart.getOrientation();
		assertEquals(SWT.HORIZONTAL, orientation);
		// set illegal value
		chart.setOrientation(-1);
		orientation = chart.getOrientation();
		assertEquals(SWT.HORIZONTAL, orientation);
		// horizontal + x log scale
		chart.setOrientation(SWT.HORIZONTAL);
		orientation = chart.getOrientation();
		assertEquals(SWT.HORIZONTAL, orientation);
		chart.getAxisSet().getXAxis(0).enableLogScale(true);
		chart.getAxisSet().getYAxis(0).enableLogScale(false);
		chart.getAxisSet().adjustRange();
		showChart();
		// horizontal + y log scale
		chart.getAxisSet().getXAxis(0).enableCategory(false);
		chart.getAxisSet().getXAxis(0).enableLogScale(false);
		chart.getAxisSet().getYAxis(0).enableLogScale(true);
		chart.getAxisSet().adjustRange();
		showChart();
		// horizontal + x log scale + y log scale
		chart.getAxisSet().getXAxis(0).enableCategory(false);
		chart.getAxisSet().getXAxis(0).enableLogScale(true);
		chart.getAxisSet().getYAxis(0).enableLogScale(true);
		chart.getAxisSet().adjustRange();
		showChart();
		// horizontal + category + stack
		chart.getAxisSet().getXAxis(0).enableCategory(true);
		chart.getAxisSet().getXAxis(0).enableLogScale(false);
		chart.getAxisSet().getYAxis(0).enableLogScale(false);
		chart.getAxisSet().adjustRange();
		showChart();
		// horizontal + y log scale + category
		chart.getAxisSet().getXAxis(0).enableCategory(true);
		chart.getAxisSet().getXAxis(0).enableLogScale(false);
		chart.getAxisSet().getYAxis(0).enableLogScale(true);
		chart.getAxisSet().adjustRange();
		showChart();
		// horizontal + category
		lineSeries1.enableStack(false);
		lineSeries2.enableStack(false);
		chart.getAxisSet().getXAxis(0).enableLogScale(false);
		chart.getAxisSet().getYAxis(0).enableLogScale(false);
		chart.getAxisSet().getXAxis(0).enableCategory(true);
		chart.getAxisSet().adjustRange();
		showChart();
		// vertical
		chart.setOrientation(SWT.VERTICAL);
		orientation = chart.getOrientation();
		assertEquals(SWT.VERTICAL, orientation);
		chart.getAxisSet().getXAxis(0).enableCategory(false);
		chart.getAxisSet().adjustRange();
		showChart();
		// vertical + x log scale
		chart.getAxisSet().getXAxis(0).enableLogScale(true);
		chart.getAxisSet().getYAxis(0).enableLogScale(false);
		chart.getAxisSet().adjustRange();
		showChart();
		// vertical + y log scale
		lineSeries1.enableStack(true);
		lineSeries2.enableStack(true);
		chart.getAxisSet().getXAxis(0).enableCategory(false);
		chart.getAxisSet().getXAxis(0).enableLogScale(false);
		chart.getAxisSet().getYAxis(0).enableLogScale(true);
		chart.getAxisSet().adjustRange();
		showChart();
		// vertical + x log scale + y log scale
		chart.getAxisSet().getXAxis(0).enableCategory(false);
		chart.getAxisSet().getXAxis(0).enableLogScale(true);
		chart.getAxisSet().getYAxis(0).enableLogScale(true);
		chart.getAxisSet().adjustRange();
		showChart();
		// vertical + category + stack
		chart.getAxisSet().getXAxis(0).enableCategory(true);
		chart.getAxisSet().getXAxis(0).enableLogScale(false);
		chart.getAxisSet().getYAxis(0).enableLogScale(false);
		chart.getAxisSet().adjustRange();
		showChart();
		// vertical + y log scale + category
		chart.getAxisSet().getXAxis(0).enableCategory(true);
		chart.getAxisSet().getXAxis(0).enableLogScale(false);
		chart.getAxisSet().getYAxis(0).enableLogScale(true);
		chart.getAxisSet().adjustRange();
		showChart();
		// vertical + category
		lineSeries1.enableStack(false);
		lineSeries2.enableStack(false);
		chart.getAxisSet().getXAxis(0).enableLogScale(false);
		chart.getAxisSet().getYAxis(0).enableLogScale(false);
		chart.getAxisSet().getXAxis(0).enableCategory(true);
		chart.getAxisSet().adjustRange();
		showChart();
	}

	/**
	 * Test for chart orientation with bar series
	 */
	@Test
	public void testOrientation2() {

		// create bar series
		IBarSeries<?> barSeries1 = (IBarSeries<?>)chart.getSeriesSet().createSeries(SeriesType.BAR, "bar series 1");
		barSeries1.setXSeries(xSeries);
		barSeries1.setYSeries(ySeries1);
		barSeries1.enableStack(true);
		IBarSeries<?> barSeries2 = (IBarSeries<?>)chart.getSeriesSet().createSeries(SeriesType.BAR, "bar series 2");
		barSeries2.setXSeries(xSeries);
		barSeries2.setYSeries(ySeries2);
		barSeries2.setBarColor(Display.getDefault().getSystemColor(SWT.COLOR_GREEN));
		barSeries2.enableStack(true);
		// set category series
		chart.getAxisSet().getXAxis(0).setCategorySeries(categorySeries);
		// check default value
		showChart();
		int orientation = chart.getOrientation();
		assertEquals(SWT.HORIZONTAL, orientation);
		// set illegal value
		chart.setOrientation(-1);
		orientation = chart.getOrientation();
		assertEquals(SWT.HORIZONTAL, orientation);
		// horizontal + x log scale
		chart.setOrientation(SWT.HORIZONTAL);
		orientation = chart.getOrientation();
		assertEquals(SWT.HORIZONTAL, orientation);
		chart.getAxisSet().getXAxis(0).enableLogScale(true);
		chart.getAxisSet().getYAxis(0).enableLogScale(false);
		chart.getAxisSet().adjustRange();
		showChart();
		// horizontal + y log scale
		chart.getAxisSet().getXAxis(0).enableCategory(false);
		chart.getAxisSet().getXAxis(0).enableLogScale(false);
		chart.getAxisSet().getYAxis(0).enableLogScale(true);
		chart.getAxisSet().adjustRange();
		showChart();
		// horizontal + x log scale + y log scale
		chart.getAxisSet().getXAxis(0).enableCategory(false);
		chart.getAxisSet().getXAxis(0).enableLogScale(true);
		chart.getAxisSet().getYAxis(0).enableLogScale(true);
		chart.getAxisSet().adjustRange();
		showChart();
		// horizontal + category + stack
		chart.getAxisSet().getXAxis(0).enableCategory(true);
		chart.getAxisSet().getXAxis(0).enableLogScale(false);
		chart.getAxisSet().getYAxis(0).enableLogScale(false);
		chart.getAxisSet().adjustRange();
		showChart();
		// horizontal + y log scale + category
		chart.getAxisSet().getXAxis(0).enableCategory(true);
		chart.getAxisSet().getXAxis(0).enableLogScale(false);
		chart.getAxisSet().getYAxis(0).enableLogScale(true);
		chart.getAxisSet().adjustRange();
		showChart();
		// horizontal + category
		barSeries1.enableStack(false);
		barSeries2.enableStack(false);
		chart.getAxisSet().getXAxis(0).enableLogScale(false);
		chart.getAxisSet().getYAxis(0).enableLogScale(false);
		chart.getAxisSet().getXAxis(0).enableCategory(true);
		chart.getAxisSet().adjustRange();
		showChart();
		// vertical
		chart.setOrientation(SWT.VERTICAL);
		orientation = chart.getOrientation();
		assertEquals(SWT.VERTICAL, orientation);
		chart.getAxisSet().getXAxis(0).enableCategory(false);
		chart.getAxisSet().adjustRange();
		showChart();
		// vertical + x log scale
		chart.getAxisSet().getXAxis(0).enableLogScale(true);
		chart.getAxisSet().getYAxis(0).enableLogScale(false);
		chart.getAxisSet().adjustRange();
		showChart();
		// vertical + y log scale
		barSeries1.enableStack(true);
		barSeries2.enableStack(true);
		chart.getAxisSet().getXAxis(0).enableCategory(false);
		chart.getAxisSet().getXAxis(0).enableLogScale(false);
		chart.getAxisSet().getYAxis(0).enableLogScale(true);
		chart.getAxisSet().adjustRange();
		showChart();
		// vertical + x log scale + y log scale
		chart.getAxisSet().getXAxis(0).enableCategory(false);
		chart.getAxisSet().getXAxis(0).enableLogScale(true);
		chart.getAxisSet().getYAxis(0).enableLogScale(true);
		chart.getAxisSet().adjustRange();
		showChart();
		// vertical + category + stack
		chart.getAxisSet().getXAxis(0).enableCategory(true);
		chart.getAxisSet().getXAxis(0).enableLogScale(false);
		chart.getAxisSet().getYAxis(0).enableLogScale(false);
		chart.getAxisSet().adjustRange();
		showChart();
		// vertical + y log scale + category
		chart.getAxisSet().getXAxis(0).enableCategory(true);
		chart.getAxisSet().getXAxis(0).enableLogScale(false);
		chart.getAxisSet().getYAxis(0).enableLogScale(true);
		chart.getAxisSet().adjustRange();
		showChart();
		// vertical + category
		barSeries1.enableStack(false);
		barSeries2.enableStack(false);
		chart.getAxisSet().getXAxis(0).enableLogScale(false);
		chart.getAxisSet().getYAxis(0).enableLogScale(false);
		chart.getAxisSet().getXAxis(0).enableCategory(true);
		chart.getAxisSet().adjustRange();
		showChart();
	}

	/**
	 * Test for suspending update
	 */
	@Test
	public void testSuspendUpdate() {

		ISeries<?> series1 = chart.getSeriesSet().createSeries(SeriesType.LINE, "series1");
		series1.setYSeries(ySeries1);
		ISeries<?> series2 = chart.getSeriesSet().createSeries(SeriesType.LINE, "series2");
		series2.setYSeries(ySeries2);
		chart.getAxisSet().getXAxis(0).enableCategory(true);
		chart.getAxisSet().getXAxis(0).setCategorySeries(categorySeries);
		chart.getAxisSet().adjustRange();
		showChart();
		int y = series2.getPixelCoordinates(0).y;
		try {
			chart.suspendUpdate(true);
			series1.enableStack(true);
			series2.enableStack(true);
			// update is suspended, so series should not be updated yet
			assertEquals(y, series2.getPixelCoordinates(0).y);
		} finally {
			chart.suspendUpdate(false);
			// update is now resumed, so series should be updated
			assertTrue(y > series2.getPixelCoordinates(0).y);
		}
		showChart();
	}

	/**
	 * Test for saving to file
	 */
	@Test
	@Disabled("environment dependent")
	public void testSaveToFile() {

		ISeries<?> series = chart.getSeriesSet().createSeries(SeriesType.LINE, "series1");
		series.setYSeries(ySeries1);
		chart.getAxisSet().adjustRange();
		showChart();
		String fileName = "/tmp/" + UUID.randomUUID().toString() + ".png";
		try {
			chart.save(fileName, SWT.IMAGE_PNG);
		} finally {
			File file = new File(fileName);
			assertTrue(file.exists());
			file.delete();
		}
	}

	/**
	 * Test for SWT resources that are internally created in following cases.
	 * <ul>
	 * <li>Y axis title is drawn.
	 * <li>risers are drawn.
	 * <li>rotated axis tick labels are drawn.
	 * </ul>
	 */
	@Test
	public void testSwtResources() throws Throwable {

		ISeries<?> barSeries = chart.getSeriesSet().createSeries(SeriesType.BAR, "bar series");
		barSeries.setYSeries(ySeries1);
		chart.getAxisSet().getXAxis(0).getTick().setTickLabelAngle(45);
		chart.getAxisSet().adjustRange();
		startTrackingSwtResources();
		for(int i = 0; i < 2; i++) {
			chart.redraw();
			// give UI thread a chance to redraw chart
			long time = System.currentTimeMillis();
			while(System.currentTimeMillis() - time < 100) {
				Display.getDefault().readAndDispatch();
			}
		}
		assertEquals(0, getSwtResourceCount());
	}

	/**
	 * Test for the SWT resources of a chart which is destroyed along with its parent, instead of
	 * being disposed itself.
	 */
	@Test
	public void testSwtResourcesOnParentDisposal() throws Throwable {

		Shell parent = new Shell(Display.getDefault());
		try {
			Chart childChart = new Chart(parent, SWT.NONE);
			startTrackingSwtResources();
			// the text layout of a title is created along with its style ranges
			childChart.getTitle().setStyleRanges(new StyleRange[]{});
			childChart.getAxisSet().getXAxis(0).getTitle().setStyleRanges(new StyleRange[]{});
			childChart.getAxisSet().getYAxis(0).getTitle().setStyleRanges(new StyleRange[]{});
			assertTrue(getSwtResourceCount() > 0);
		} finally {
			parent.dispose();
		}
		assertEquals(0, getSwtResourceCount());
	}

	/**
	 * Test that the chart is repainted on mouse move only while a position marker is drawn.
	 */
	@Test
	public void testMouseMoveRedraw() {

		showChart();
		int[] paintCount = {0};
		chart.addPaintListener(_ -> paintCount[0]++);
		assertEquals(0, paintsOnMouseMove(paintCount), "repainted although no position marker is drawn");
		chart.getAxisSet().getXAxis(0).setDrawPositionMarker(true);
		assertTrue(paintsOnMouseMove(paintCount) > 0, "the position marker is not drawn");
		// the marker is drawn along the axis ticks only
		chart.getAxisSet().getXAxis(0).getTick().setVisible(false);
		assertEquals(0, paintsOnMouseMove(paintCount), "repainted although the axis ticks are hidden");
	}

	/**
	 * Test that adding or removing a custom paint listener repaints the plot area.
	 */
	@Test
	public void testCustomPaintListenerRedraw() {

		showChart();
		int[] paintCount = {0};
		IPlotArea plotArea = chart.getPlotArea();
		plotArea.getControl().addPaintListener(_ -> paintCount[0]++);
		ICustomPaintListener listener = _ -> {
		};
		waitForPendingPaints(paintCount);
		plotArea.addCustomPaintListener(listener);
		assertTrue(paintsWithin(paintCount) > 0, "not repainted when adding a custom paint listener");
		waitForPendingPaints(paintCount);
		plotArea.removeCustomPaintListener(listener);
		assertTrue(paintsWithin(paintCount) > 0, "not repainted when removing a custom paint listener");
		waitForPendingPaints(paintCount);
		plotArea.removeCustomPaintListener(listener);
		assertEquals(0, paintsWithin(paintCount), "repainted when removing a custom paint listener that isn't added");
	}

	/**
	 * Runs the event loop for a while and returns the repaints counted meanwhile.
	 */
	private int paintsWithin(int[] paintCount) {

		long time = System.currentTimeMillis();
		while(System.currentTimeMillis() - time < 200) {
			Display.getDefault().readAndDispatch();
		}
		return paintCount[0];
	}

	/**
	 * Moves the mouse over the plot area and counts the repaints of the chart it causes.
	 */
	private int paintsOnMouseMove(int[] paintCount) {

		waitForPendingPaints(paintCount);
		Event event = new Event();
		event.x = 20;
		event.y = 20;
		chart.getPlotArea().getControl().notifyListeners(SWT.MouseMove, event);
		return paintsWithin(paintCount);
	}

	/**
	 * Runs the event loop until the chart has not been repainted for a while, so that repaints
	 * requested earlier are not counted for the mouse move.
	 */
	private void waitForPendingPaints(int[] paintCount) {

		long quiet = System.currentTimeMillis();
		long timeout = quiet + 2000;
		while(System.currentTimeMillis() - quiet < 100 && System.currentTimeMillis() < timeout) {
			if(paintCount[0] != 0) {
				paintCount[0] = 0;
				quiet = System.currentTimeMillis();
			}
			Display.getDefault().readAndDispatch();
		}
		paintCount[0] = 0;
	}
}
