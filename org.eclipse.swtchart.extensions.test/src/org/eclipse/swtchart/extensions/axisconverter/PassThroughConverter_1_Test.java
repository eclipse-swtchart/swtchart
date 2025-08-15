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

import org.junit.Test;

public class PassThroughConverter_1_Test {

	private PassThroughConverter passThroughConverter = new PassThroughConverter();

	@Test
	public void test1() {

		assertEquals(-1.0d, passThroughConverter.convertToSecondaryUnit(-1.0d), 0);
	}

	@Test
	public void test2() {

		assertEquals(0.0d, passThroughConverter.convertToSecondaryUnit(0.0d), 0);
	}

	@Test
	public void test3() {

		assertEquals(1.0d, passThroughConverter.convertToSecondaryUnit(1.0d), 0);
	}
}
