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

import org.junit.Test;

public class RangeRestriction_9_Test {

	@Test
	public void test1() {

		RangeRestriction rangeRestriction = new RangeRestriction(RangeRestriction.ZERO_X | RangeRestriction.ZERO_Y | RangeRestriction.RESTRICT_FRAME | RangeRestriction.RESTRICT_SELECT_X | RangeRestriction.RESTRICT_SELECT_Y);
		assertTrue(rangeRestriction.isZeroX());
		assertTrue(rangeRestriction.isZeroY());
		assertTrue(rangeRestriction.isRestrictFrame());
		assertTrue(rangeRestriction.isRestrictSelectX());
		assertTrue(rangeRestriction.isRestrictSelectY());
	}

	@Test
	public void test2() {

		RangeRestriction rangeRestriction = new RangeRestriction(RangeRestriction.ZERO_X | RangeRestriction.RESTRICT_FRAME);
		assertTrue(rangeRestriction.isZeroX());
		assertFalse(rangeRestriction.isZeroY());
		assertTrue(rangeRestriction.isRestrictFrame());
		assertFalse(rangeRestriction.isRestrictSelectX());
		assertFalse(rangeRestriction.isRestrictSelectY());
	}

	@Test
	public void test3() {

		RangeRestriction rangeRestriction = new RangeRestriction(RangeRestriction.ZERO_Y | RangeRestriction.RESTRICT_FRAME);
		assertFalse(rangeRestriction.isZeroX());
		assertTrue(rangeRestriction.isZeroY());
		assertTrue(rangeRestriction.isRestrictFrame());
		assertFalse(rangeRestriction.isRestrictSelectX());
		assertFalse(rangeRestriction.isRestrictSelectY());
	}

	@Test
	public void test4() {

		RangeRestriction rangeRestriction = new RangeRestriction(RangeRestriction.ZERO_X | RangeRestriction.ZERO_Y);
		assertTrue(rangeRestriction.isZeroX());
		assertTrue(rangeRestriction.isZeroY());
		assertFalse(rangeRestriction.isRestrictFrame());
		assertFalse(rangeRestriction.isRestrictSelectX());
		assertFalse(rangeRestriction.isRestrictSelectY());
	}

	@Test
	public void test5() {

		RangeRestriction rangeRestriction = new RangeRestriction(RangeRestriction.RESTRICT_SELECT_X | RangeRestriction.RESTRICT_SELECT_Y);
		assertFalse(rangeRestriction.isZeroX());
		assertFalse(rangeRestriction.isZeroY());
		assertFalse(rangeRestriction.isRestrictFrame());
		assertTrue(rangeRestriction.isRestrictSelectX());
		assertTrue(rangeRestriction.isRestrictSelectY());
	}

	@Test
	public void test6() {

		RangeRestriction rangeRestriction = new RangeRestriction(RangeRestriction.ZERO_X | RangeRestriction.RESTRICT_SELECT_X);
		assertTrue(rangeRestriction.isZeroX());
		assertFalse(rangeRestriction.isZeroY());
		assertFalse(rangeRestriction.isRestrictFrame());
		assertTrue(rangeRestriction.isRestrictSelectX());
		assertFalse(rangeRestriction.isRestrictSelectY());
	}
}
