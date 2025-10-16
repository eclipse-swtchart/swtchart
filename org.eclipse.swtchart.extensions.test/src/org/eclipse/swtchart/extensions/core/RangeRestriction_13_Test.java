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
package org.eclipse.swtchart.extensions.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RangeRestriction_13_Test {

	private RangeRestriction rangeRestriction = new RangeRestriction();

	@Test
	public void test1() {

		assertEquals(0.0d, rangeRestriction.getExtendMinX(), 0);
	}

	@Test
	public void test2() {

		assertEquals(0.0d, rangeRestriction.getExtendMaxX(), 0);
	}

	@Test
	public void test3() {

		assertEquals(0.0d, rangeRestriction.getExtendMinY(), 0);
	}

	@Test
	public void test4() {

		assertEquals(0.0d, rangeRestriction.getExtendMaxY(), 0);
	}
}
