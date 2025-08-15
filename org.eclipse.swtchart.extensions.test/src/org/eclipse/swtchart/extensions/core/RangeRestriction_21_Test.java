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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class RangeRestriction_21_Test {

	private RangeRestriction rangeRestriction;

	@Before
	public void setUp() {

		rangeRestriction = new RangeRestriction(RangeRestriction.ZERO_X | RangeRestriction.ZERO_Y | RangeRestriction.RESTRICT_FRAME | RangeRestriction.FORCE_ZERO_MIN_Y);
	}

	@Test
	public void test1() {

		assertTrue(rangeRestriction.isZeroX());
		assertTrue(rangeRestriction.isZeroY());
		assertTrue(rangeRestriction.isRestrictFrame());
		assertTrue(rangeRestriction.isForceZeroMinY());
	}

	@Test
	public void test2() {

		rangeRestriction.setZeroX(false);
		assertFalse(rangeRestriction.isZeroX());
		assertTrue(rangeRestriction.isZeroY());
		assertTrue(rangeRestriction.isRestrictFrame());
		assertTrue(rangeRestriction.isForceZeroMinY());
	}

	@Test
	public void test3() {

		rangeRestriction.setZeroY(false);
		assertTrue(rangeRestriction.isZeroX());
		assertFalse(rangeRestriction.isZeroY());
		assertTrue(rangeRestriction.isRestrictFrame());
		assertTrue(rangeRestriction.isForceZeroMinY());
	}

	@Test
	public void test4() {

		rangeRestriction.setRestrictFrame(false);
		assertTrue(rangeRestriction.isZeroX());
		assertTrue(rangeRestriction.isZeroY());
		assertFalse(rangeRestriction.isRestrictFrame());
		assertTrue(rangeRestriction.isForceZeroMinY());
	}

	@Test
	public void test5() {

		rangeRestriction.setForceZeroMinY(false);
		assertTrue(rangeRestriction.isZeroX());
		assertTrue(rangeRestriction.isZeroY());
		assertTrue(rangeRestriction.isRestrictFrame());
		assertFalse(rangeRestriction.isForceZeroMinY());
	}
}
