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

import org.junit.Test;

public class RangeRestriction_1_Test {

	private RangeRestriction rangeRestriction = new RangeRestriction();

	@Test
	public void test1() {

		assertFalse(rangeRestriction.isZeroX());
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
