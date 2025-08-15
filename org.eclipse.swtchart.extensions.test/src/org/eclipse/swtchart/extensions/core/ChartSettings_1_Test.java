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

import org.eclipse.swtchart.extensions.events.MouseDownEvent;
import org.eclipse.swtchart.extensions.events.MouseMoveSelectionEvent;
import org.eclipse.swtchart.extensions.events.MouseMoveShiftEvent;
import org.eclipse.swtchart.extensions.events.MouseUpEvent;
import org.junit.Before;
import org.junit.Test;

public class ChartSettings_1_Test {

	private ChartSettings chartSettings = new ChartSettings();

	@Before
	public void setUp() {

		chartSettings.clearHandledEventProcessors();
		chartSettings.addHandledEventProcessor(new MouseDownEvent());
		chartSettings.addHandledEventProcessor(new MouseMoveSelectionEvent());
		chartSettings.addHandledEventProcessor(new MouseUpEvent());
	}

	@Test
	public void test1() {

		assertNotNull(chartSettings.getHandledEventProcessorByClass(MouseDownEvent.class));
	}

	@Test
	public void test2() {

		assertNotNull(chartSettings.getHandledEventProcessorByClass(MouseMoveSelectionEvent.class));
	}

	@Test
	public void test3() {

		assertNotNull(chartSettings.getHandledEventProcessorByClass(MouseUpEvent.class));
	}

	@Test
	public void test4() {

		assertNull(chartSettings.getHandledEventProcessorByClass(MouseMoveShiftEvent.class));
	}
}
