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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RangeRestriction_3_Test {

	private RangeRestriction rangeRestriction;

	@BeforeEach
	public void setUp() {

		rangeRestriction = new RangeRestriction(RangeRestriction.ZERO_X);
	}

	@Test
	public void test1() {

		assertTrue(rangeRestriction.isZeroX());
	}

	@Test
	public void test2() {

		assertFalse(rangeRestriction.isZeroY());
	}

	@Test
	public void test3() {

		assertFalse(rangeRestriction.isRestrictFrame());
	}

	@Test
	public void test4() {

		assertFalse(rangeRestriction.isRestrictSelectX());
	}

	@Test
	public void test5() {

		assertFalse(rangeRestriction.isRestrictSelectY());
	}

	@Test
	public void test6() {

		assertFalse(rangeRestriction.isForceZeroMinY());
	}
}
