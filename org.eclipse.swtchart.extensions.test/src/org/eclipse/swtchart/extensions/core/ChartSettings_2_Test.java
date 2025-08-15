/*******************************************************************************
 * Copyright (c) 2020, 2025 Lablicate GmbH.
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
package org.eclipse.swtchart.extensions.core;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.eclipse.swtchart.extensions.menu.toggle.TogglePlotCenterMarkerHandler;
import org.eclipse.swtchart.extensions.menu.toggle.TogglePositionMarkerHandler;
import org.eclipse.swtchart.extensions.menu.toggle.ToggleSeriesLegendHandler;
import org.junit.Before;
import org.junit.Test;

public class ChartSettings_2_Test {

	private ChartSettings chartSettings = new ChartSettings();

	@Before
	public void setUp() {

		chartSettings.clearMenuEntries();
		chartSettings.addMenuEntry(new TogglePositionMarkerHandler());
		chartSettings.addMenuEntry(new ToggleSeriesLegendHandler());
	}

	@Test
	public void test1() {

		assertNotNull(chartSettings.getChartMenuEntryByClass(TogglePositionMarkerHandler.class));
	}

	@Test
	public void test2() {

		assertNotNull(chartSettings.getChartMenuEntryByClass(ToggleSeriesLegendHandler.class));
	}

	@Test
	public void test3() {

		assertNull(chartSettings.getChartMenuEntryByClass(TogglePlotCenterMarkerHandler.class));
	}
}
