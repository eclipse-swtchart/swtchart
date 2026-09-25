/*******************************************************************************
 * Copyright (c) 2026 Aleksandar Kurtakov and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Aleksandar Kurtakov - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtchart.extensions.marker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test that the markers repaint the plot area when what they draw changes, as the chart isn't
 * repainted on each mouse move.
 */
public class AbstractBaseChartPaintListener_1_UITest {

	private Shell shell;
	private BaseChart baseChart;
	private int[] paintCount = {0};

	@BeforeEach
	public void setUp() {

		shell = new Shell(Display.getDefault());
		shell.setSize(400, 300);
		shell.setLayout(new FillLayout());
		baseChart = new BaseChart(shell, SWT.NONE);
		shell.open();
		baseChart.getPlotArea().getControl().addPaintListener(_ -> paintCount[0]++);
	}

	@AfterEach
	public void tearDown() {

		shell.dispose();
	}

	@Test
	public void testSetDraw() {

		TestMarker marker = new TestMarker(baseChart);
		baseChart.getPlotArea().addCustomPaintListener(marker);
		waitForPendingPaints();
		marker.setDraw(false);
		assertTrue(paintsWithin() > 0, "not repainted when hiding the marker");
		waitForPendingPaints();
		marker.setDraw(false);
		assertEquals(0, paintsWithin(), "repainted although the marker was hidden already");
		waitForPendingPaints();
		marker.setDraw(true);
		assertTrue(paintsWithin() > 0, "not repainted when showing the marker");
	}

	@Test
	public void testSetForegroundColor() {

		TestMarker marker = new TestMarker(baseChart);
		baseChart.getPlotArea().addCustomPaintListener(marker);
		waitForPendingPaints();
		marker.setForegroundColor(Display.getDefault().getSystemColor(SWT.COLOR_RED));
		assertTrue(paintsWithin() > 0, "not repainted when changing the color");
		waitForPendingPaints();
		marker.setForegroundColor(Display.getDefault().getSystemColor(SWT.COLOR_RED));
		assertEquals(0, paintsWithin(), "repainted although the color is the same");
	}

	@Test
	public void testLabelMarker() {

		LabelMarker marker = new LabelMarker(baseChart);
		baseChart.getPlotArea().addCustomPaintListener(marker);
		waitForPendingPaints();
		marker.setLabels(List.of("a", "b"), 0, SWT.HORIZONTAL);
		assertTrue(paintsWithin() > 0, "not repainted when setting the labels");
		waitForPendingPaints();
		marker.clear();
		assertTrue(paintsWithin() > 0, "not repainted when clearing the labels");
	}

	/**
	 * Runs the event loop for a while and returns the repaints counted meanwhile.
	 */
	private int paintsWithin() {

		long time = System.currentTimeMillis();
		while(System.currentTimeMillis() - time < 200) {
			Display.getDefault().readAndDispatch();
		}
		return paintCount[0];
	}

	/**
	 * Runs the event loop until the plot area has not been repainted for a while, so that
	 * repaints requested earlier are not counted.
	 */
	private void waitForPendingPaints() {

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

	private static class TestMarker extends AbstractBaseChartPaintListener {

		public TestMarker(BaseChart baseChart) {

			super(baseChart);
		}

		@Override
		public void paintControl(PaintEvent e) {

		}
	}
}
