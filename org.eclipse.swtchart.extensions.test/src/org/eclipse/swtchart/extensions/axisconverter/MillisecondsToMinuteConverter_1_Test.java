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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MillisecondsToMinuteConverter_1_Test {

	private MillisecondsToMinuteConverter millisecondsToMinuteConverter = new MillisecondsToMinuteConverter();

	@Test
	public void test1() {

		assertEquals(1.0d, millisecondsToMinuteConverter.convertToSecondaryUnit(60000.0d), 0);
	}

	@Test
	public void test2() {

		assertEquals(60000.0d, millisecondsToMinuteConverter.convertToPrimaryUnit(1.0d), 0);
	}
}
