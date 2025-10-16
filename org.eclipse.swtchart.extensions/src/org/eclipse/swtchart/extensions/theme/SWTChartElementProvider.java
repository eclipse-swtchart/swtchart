/*******************************************************************************
 * Copyright (c) 2025 Lablicate GmbH.
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
package org.eclipse.swtchart.extensions.theme;

import org.eclipse.e4.ui.css.core.dom.IElementProvider;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.SWTElementProvider;
import org.eclipse.swtchart.extensions.core.ScrollableChart;
import org.w3c.dom.Element;

/**
 * Returns the CSS class which is responsible for styling a SWT widget
 *
 * Registered via the "org.eclipse.e4.ui.css.core.elementProvider" extension
 * point for the SWT widgets
 *
 *
 *
 * {@link IElementProvider} SWT implementation to retrieve w3c {@link Element} 
 * linked to SWT widget.
 *
 */
@SuppressWarnings("restriction")
public class SWTChartElementProvider implements IElementProvider {

	public static final IElementProvider INSTANCE = new SWTElementProvider();

	@Override
	public Element getElement(Object element, CSSEngine engine) {

		if(element instanceof ScrollableChart) {
			return new ScrollableChartElement((ScrollableChart)element, engine);
		}
		return null;
	}
}