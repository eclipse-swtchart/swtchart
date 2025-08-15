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

public class RangeRestriction_20_Test {

	private RangeRestriction rangeRestriction;

	@Before
	public void setUp() {

		rangeRestriction = new RangeRestriction(RangeRestriction.RESTRICT_SELECT_X | RangeRestriction.RESTRICT_SELECT_Y);
	}

	@Test
	public void test1() {

		assertTrue(rangeRestriction.isRestrictSelectX());
		assertTrue(rangeRestriction.isRestrictSelectY());
	}

	@Test
	public void test2() {

		rangeRestriction.setRestrictSelectX(false);
		assertFalse(rangeRestriction.isRestrictSelectX());
		assertTrue(rangeRestriction.isRestrictSelectY());
	}

	@Test
	public void test3() {

		rangeRestriction.setRestrictSelectY(false);
		assertTrue(rangeRestriction.isRestrictSelectX());
		assertFalse(rangeRestriction.isRestrictSelectY());
	}
}
