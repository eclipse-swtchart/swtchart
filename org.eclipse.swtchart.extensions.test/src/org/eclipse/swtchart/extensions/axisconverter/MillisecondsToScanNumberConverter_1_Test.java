/*******************************************************************************
 * Copyright (c) 2017, 2025 Lablicate GmbH.
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
package org.eclipse.swtchart.extensions.axisconverter;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class MillisecondsToScanNumberConverter_1_Test {

	private MillisecondsToScanNumberConverter millisecondsToScanNumberConverter;

	@Before
	public void setUp() {

		millisecondsToScanNumberConverter = new MillisecondsToScanNumberConverter(500, 1000);
	}

	@Test
	public void test1() {

		assertEquals(0.0d, millisecondsToScanNumberConverter.convertToSecondaryUnit(0.0d), 0);
	}

	@Test
	public void test2() {

		assertEquals(0.0d, millisecondsToScanNumberConverter.convertToSecondaryUnit(499.0d), 0);
	}

	@Test
	public void test3() {

		assertEquals(1.0d, millisecondsToScanNumberConverter.convertToSecondaryUnit(500.0d), 0);
	}

	@Test
	public void test4() {

		assertEquals(2.0d, millisecondsToScanNumberConverter.convertToSecondaryUnit(1500.0d), 0);
	}

	@Test
	public void test5() {

		assertEquals(2.0d, millisecondsToScanNumberConverter.convertToSecondaryUnit(1501.0d), 0);
	}

	@Test
	public void test6() {

		assertEquals(2.0d, millisecondsToScanNumberConverter.convertToSecondaryUnit(2499.0d), 0);
	}

	@Test
	public void test7() {

		assertEquals(3.0d, millisecondsToScanNumberConverter.convertToSecondaryUnit(2500.0d), 0);
	}

	@Test
	public void test8() {

		assertEquals(0.0d, millisecondsToScanNumberConverter.convertToPrimaryUnit(0.0d), 0);
	}

	@Test
	public void test9() {

		assertEquals(500.0d, millisecondsToScanNumberConverter.convertToPrimaryUnit(1.0d), 0);
	}

	@Test
	public void test10() {

		assertEquals(1500.0d, millisecondsToScanNumberConverter.convertToPrimaryUnit(2.0d), 0);
	}

	@Test
	public void test11() {

		assertEquals(2500.0d, millisecondsToScanNumberConverter.convertToPrimaryUnit(3.0d), 0);
	}
}
