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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.FieldPosition;
import java.text.ParsePosition;

public class MillionDecimalFormat extends DecimalFormat {

	private static final long serialVersionUID = 1L;

	public MillionDecimalFormat(String pattern, DecimalFormatSymbols symbols) {

		super(pattern, symbols);
	}

	@Override
	public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {

		return super.format(number / 1_000_000.0, toAppendTo, pos);
	}

	@Override
	public Number parse(String source, ParsePosition parsePosition) {

		Number result = super.parse(source, parsePosition);
		if(result != null) {
			return result.doubleValue() * 1_000_000.0;
		}
		return result;
	}
}
