/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtchart.extensions.examples.converter;

import org.eclipse.swtchart.extensions.core.AbstractAxisScaleConverter;

public class MillionConverter extends AbstractAxisScaleConverter {

	@Override
	public double convertToSecondaryUnit(double primaryValue) {

		return primaryValue / 1_000_000.0;
	}

	@Override
	public double convertToPrimaryUnit(double secondaryValue) {

		return secondaryValue * 1_000_000.0;
	}
}
