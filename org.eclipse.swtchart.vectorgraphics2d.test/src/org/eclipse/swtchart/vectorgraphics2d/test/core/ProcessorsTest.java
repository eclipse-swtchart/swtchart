/*******************************************************************************
 * Copyright (c) 2010, 2025 VectorGraphics2D project.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Erich Seifert - initial API and implementation
 * Michael Seifert - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtchart.vectorgraphics2d.test.core;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.eclipse.swtchart.vectorgraphics2d.core.Processor;
import org.eclipse.swtchart.vectorgraphics2d.core.Processors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ProcessorsTest {

	@Test
	public void testGetThrowsNullPointerExceptionWhenNullIsPassed() {

		assertThrows(NullPointerException.class, () -> Processors.get(null));
	}

	@Test
	public void testGetThrowsIllegalArgumentExceptionWhenFormatIsUnknown() {

		assertThrows(IllegalArgumentException.class, () -> Processors.get("UnknownFormat"));
	}

	@ParameterizedTest
	@ValueSource(strings = {"eps", "pdf", "svg"})
	public void testGetReturnsNonNullWhenFormatIsKnown(String format) {

		Processor processor = Processors.get(format);
		assertThat(processor, is(notNullValue()));
	}
}
