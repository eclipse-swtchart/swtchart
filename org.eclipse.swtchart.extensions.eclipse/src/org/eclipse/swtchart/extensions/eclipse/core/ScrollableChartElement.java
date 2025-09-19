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
package org.eclipse.swtchart.extensions.eclipse.core;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.ControlElement;
import org.eclipse.swtchart.extensions.core.ScrollableChart;

@SuppressWarnings("restriction")
public class ScrollableChartElement extends ControlElement {

	public ScrollableChartElement(ScrollableChart scrollableChart, CSSEngine engine) {

		super(scrollableChart, engine);
	}

	@Override
	public void initialize() {

		super.initialize();
	}

	@Override
	public void dispose() {

		super.dispose();
	}

	protected ScrollableChart getScrollableChart() {

		return (ScrollableChart)getNativeWidget();
	}
}
