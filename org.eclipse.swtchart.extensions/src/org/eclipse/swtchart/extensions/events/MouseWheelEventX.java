/*******************************************************************************
 * Copyright (c) 2024, 2026 Lablicate GmbH.
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
package org.eclipse.swtchart.extensions.events;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtchart.IAxis;
import org.eclipse.swtchart.IAxisSet;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.support.RangeSupport;

public class MouseWheelEventX extends AbstractMouseEvent {

	@Override
	public int getStateMask() {

		return SWT.MOD1;
	}

	@Override
	protected void runAction(BaseChart baseChart, Event event) {

		if((event.stateMask & SWT.MOD3) == SWT.MOD3) {
			/*
			 * Zoom in/out X
			 */
			IAxisSet axisSet = baseChart.getAxisSet();
			IAxis xAxis = axisSet.getXAxis(BaseChart.ID_PRIMARY_X_AXIS);
			baseChart.zoomX(xAxis, event);
		} else {
			/*
			 * Slide X
			 */
			RangeSupport.applyHorizontalSlide(baseChart, 0.1d, event.count < 0);
		}
	}
}