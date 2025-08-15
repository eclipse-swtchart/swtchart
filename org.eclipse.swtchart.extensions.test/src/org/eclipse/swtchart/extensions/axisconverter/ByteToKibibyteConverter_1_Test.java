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
package org.eclipse.swtchart.extensions.axisconverter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ByteToKibibyteConverter_1_Test {

	private ByteToKibibyteConverter converter = new ByteToKibibyteConverter();

	@Test
	public void test1() {

		assertEquals(1.0d, converter.convertToSecondaryUnit(1024.0d), 0);
	}

	@Test
	public void test2() {

		assertEquals(1024.0d, converter.convertToPrimaryUnit(1.0d), 0);
	}
}
