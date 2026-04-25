/*******************************************************************************
 * Copyright (c) 2025, 2026 Lablicate GmbH.
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
package org.eclipse.swtchart.extensions.internal.marker;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.marker.AbstractBaseChartPaintListener;

public class UserRestrictionMarker extends AbstractBaseChartPaintListener {

	public UserRestrictionMarker(BaseChart baseChart) {

		super(baseChart);
	}

	@Override
	public void paintControl(PaintEvent e) {

		if(isDraw()) {
			if(getBaseChart().getUserRestriction().isRestrictFrame()) {
				GC gc = e.gc;
				gc.setForeground(getForegroundColor());
				gc.setLineStyle(SWT.LINE_DASH);
				gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
				gc.setLineWidth(2);
				gc.drawRectangle(5, 5, e.width - 10, e.height - 10);
			}
		}
	}
}