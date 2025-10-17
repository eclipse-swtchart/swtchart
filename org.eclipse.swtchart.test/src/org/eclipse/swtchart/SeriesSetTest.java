/*******************************************************************************
 * Copyright (c) 2008, 2025 SWTChart project.
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
package org.eclipse.swtchart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtchart.ISeries.SeriesType;
import org.eclipse.swtchart.util.ChartTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test case for series set.
 */
public class SeriesSetTest extends ChartTestCase {

	private ISeriesSet seriesSet;
	private static final double[] ySeries1 = {0.3, 0.3, 0.3, 0.3, 0.3};
	private static final double[] ySeries2 = {0.5, 0.4, 0.3, 0.2, 0.1};
	private static final double[] ySeries3 = {0.1, 0.2, 0.3, 0.4, 0.5};
	private static final String[] categorySeries = {"a", "b", "c", "d", "e"};

	@BeforeEach
	public void setUp()  {

		seriesSet = chart.getSeriesSet();
	}

	/**
	 * Test for creating series.
	 */
	@Test
	public void testCreateSeries()  {

		// create series with illegal key
		assertThrows(IllegalArgumentException.class, () -> seriesSet.createSeries(SeriesType.LINE, null));
		assertThrows(IllegalArgumentException.class, () -> seriesSet.createSeries(SeriesType.LINE, ""));
		assertThrows(IllegalArgumentException.class, () -> seriesSet.createSeries(SeriesType.LINE, " "));
		// create series with illegal line type
		assertThrows(IllegalArgumentException.class, () -> seriesSet.createSeries(null, "foo"));
		// create series
		showChart();
		ISeries<?> series1 = seriesSet.createSeries(SeriesType.LINE, "series1");
		assertEquals(0, series1.getXAxisId());
		assertEquals(0, series1.getYAxisId());
		assertEquals("series1", series1.getId());
		series1.setYSeries(ySeries1);
		ISeries<?> series2 = seriesSet.createSeries(SeriesType.BAR, "series2");
		assertEquals(0, series2.getXAxisId());
		assertEquals(0, series2.getYAxisId());
		assertEquals("series2", series2.getId());
		series2.setYSeries(ySeries2);
		chart.getAxisSet().adjustRange();
		showChart();
	}

	/**
	 * Test for getting series.
	 */
	@Test
	public void testGetSeries()  {

		// get series with illegal key
		assertThrows(IllegalArgumentException.class, () -> seriesSet.getSeries(null));
		// get series with unavailable key
		ISeries<?> series = seriesSet.getSeries("foo");
		assertNull(series);
		// get series
		ISeries<?>[] seriesArray = seriesSet.getSeries();
		assertEquals(0, seriesArray.length);
		ISeries<?> series1 = seriesSet.createSeries(SeriesType.LINE, "series1");
		series1.setYSeries(ySeries1);
		ISeries<?> series2 = seriesSet.createSeries(SeriesType.LINE, "series2");
		series2.setYSeries(ySeries2);
		series = seriesSet.getSeries("series1");
		double[] ySeries = series.getYSeries();
		assertEquals(ySeries1.length, ySeries.length);
		for(int i = 0; i < ySeries1.length; i++) {
			assertEquals(ySeries1[i], ySeries[i], 0.01);
		}
		seriesArray = seriesSet.getSeries();
		assertEquals(2, seriesArray.length);
	}

	/**
	 * Test for deleting series.
	 */
	@Test
	public void testDeleteSeries()  {

		// delete series with illegal key
		assertThrows(IllegalArgumentException.class, () -> seriesSet.deleteSeries(null));
		// delete series with unavailable key
		assertThrows(IllegalArgumentException.class, () -> seriesSet.deleteSeries("foo"));
		// delete series
		ISeries<?> series1 = seriesSet.createSeries(SeriesType.LINE, "series1");
		series1.setYSeries(ySeries1);
		ISeries<?> series2 = seriesSet.createSeries(SeriesType.LINE, "series2");
		series2.setYSeries(ySeries2);
		chart.getAxisSet().adjustRange();
		showChart();
		seriesSet.deleteSeries("series1");
		ISeries<?> series = seriesSet.getSeries("series1");
		assertNull(series);
		chart.getAxisSet().adjustRange();
		showChart();
	}

	/**
	 * Test for changing series order.
	 */
	@Test
	public void testSeriesOrder()  {

		IBarSeries<?> series1 = (IBarSeries<?>)seriesSet.createSeries(SeriesType.BAR, "series1");
		series1.setYSeries(ySeries1);
		series1.setBarColor(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
		IBarSeries<?> series2 = (IBarSeries<?>)seriesSet.createSeries(SeriesType.BAR, "series2");
		series2.setYSeries(ySeries2);
		series2.setBarColor(Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY));
		IBarSeries<?> series3 = (IBarSeries<?>)seriesSet.createSeries(SeriesType.BAR, "series3");
		series3.setYSeries(ySeries3);
		IAxis xAxis = chart.getAxisSet().getXAxis(0);
		xAxis.setCategorySeries(categorySeries);
		xAxis.enableCategory(true);
		series1.enableStack(true);
		series2.enableStack(true);
		series3.enableStack(true);
		chart.getAxisSet().adjustRange();
		ISeries<?>[] seriesArray = seriesSet.getSeries();
		assertEquals("series1", seriesArray[0].getId());
		assertEquals("series2", seriesArray[1].getId());
		assertEquals("series3", seriesArray[2].getId());
		showChart();
		seriesSet.bringForward("series1");
		seriesArray = seriesSet.getSeries();
		assertEquals("series2", seriesArray[0].getId());
		assertEquals("series1", seriesArray[1].getId());
		assertEquals("series3", seriesArray[2].getId());
		showChart();
		seriesSet.bringForward("series1");
		seriesArray = seriesSet.getSeries();
		assertEquals("series2", seriesArray[0].getId());
		assertEquals("series3", seriesArray[1].getId());
		assertEquals("series1", seriesArray[2].getId());
		showChart();
		seriesSet.bringForward("series1");
		seriesArray = seriesSet.getSeries();
		assertEquals("series2", seriesArray[0].getId());
		assertEquals("series3", seriesArray[1].getId());
		assertEquals("series1", seriesArray[2].getId());
		showChart();
		seriesSet.bringToFront("series2");
		seriesArray = seriesSet.getSeries();
		assertEquals("series3", seriesArray[0].getId());
		assertEquals("series1", seriesArray[1].getId());
		assertEquals("series2", seriesArray[2].getId());
		showChart();
		seriesSet.sendBackward("series2");
		seriesArray = seriesSet.getSeries();
		assertEquals("series3", seriesArray[0].getId());
		assertEquals("series2", seriesArray[1].getId());
		assertEquals("series1", seriesArray[2].getId());
		showChart();
		seriesSet.sendBackward("series2");
		seriesArray = seriesSet.getSeries();
		assertEquals("series2", seriesArray[0].getId());
		assertEquals("series3", seriesArray[1].getId());
		assertEquals("series1", seriesArray[2].getId());
		showChart();
		seriesSet.sendBackward("series2");
		seriesArray = seriesSet.getSeries();
		assertEquals("series2", seriesArray[0].getId());
		assertEquals("series3", seriesArray[1].getId());
		assertEquals("series1", seriesArray[2].getId());
		showChart();
		seriesSet.sendToBack("series1");
		seriesArray = seriesSet.getSeries();
		assertEquals("series1", seriesArray[0].getId());
		assertEquals("series2", seriesArray[1].getId());
		assertEquals("series3", seriesArray[2].getId());
		showChart();
	}
}
