/*******************************************************************************
 * Copyright (c) 2019, 2025 Lablicate GmbH.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.DecimalFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtchart.IAxis.Position;
import org.eclipse.swtchart.LineStyle;
import org.junit.Test;

public class PrimaryAxisSettings_1_UITest {

	private PrimaryAxisSettings settings = new PrimaryAxisSettings("");

	@Test
	public void test1() {

		assertEquals("", settings.getTitle());
	}

	@Test
	public void test2() {

		assertEquals("", settings.getDescription());
	}

	@Test
	public void test3() {

		assertEquals(new DecimalFormat(), settings.getDecimalFormat());
	}

	@Test
	public void test4() {

		assertEquals(Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_FOREGROUND), settings.getColor());
	}

	@Test
	public void test5() {

		assertEquals(true, settings.isVisible());
	}

	@Test
	public void test6() {

		assertEquals(Position.Primary, settings.getPosition());
	}

	@Test
	public void test7() {

		assertEquals(Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BORDER), settings.getGridColor());
	}

	@Test
	public void test8() {

		assertEquals(LineStyle.DOT, settings.getGridLineStyle());
	}

	@Test
	public void test9() {

		assertEquals(false, settings.isEnableLogScale());
	}

	@Test
	public void test10() {

		assertEquals(false, settings.isReversed());
	}

	@Test
	public void test11() {

		assertEquals(25, settings.getExtraSpaceTitle());
	}

	@Test
	public void test12() {

		assertEquals(false, settings.isEnableCategory());
	}

	@Test
	public void test13() {

		assertEquals(0, settings.getCategorySeries().length);
	}

	@Test
	public void test14() {

		assertFalse(settings.isReversed());
	}

	@Test
	public void test15() {

		assertTrue(settings.isTitleVisible());
	}

	@Test
	public void test16() {

		assertNotNull(settings.getTitleFont());
	}
}
