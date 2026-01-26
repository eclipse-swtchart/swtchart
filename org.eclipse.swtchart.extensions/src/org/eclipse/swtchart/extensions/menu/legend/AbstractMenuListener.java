/*******************************************************************************
 * Copyright (c) 2021, 2026 Lablicate GmbH.
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
package org.eclipse.swtchart.extensions.menu.legend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.swtchart.ISeries;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.core.SeriesListUI;

public abstract class AbstractMenuListener implements IMenuListener {

	private SeriesListUI seriesListUI;

	public AbstractMenuListener(SeriesListUI seriesListUI) {

		this.seriesListUI = seriesListUI;
	}

	protected SeriesListUI getSeriesListUI() {

		return seriesListUI;
	}

	protected List<ISeries<?>> getSelectedSeries() {

		List<ISeries<?>> selectedSeries = new ArrayList<>();
		if(seriesListUI != null) {
			Iterator<?> iterator = seriesListUI.getStructuredSelection().iterator();
			while(iterator.hasNext()) {
				Object object = iterator.next();
				if(object instanceof ISeries<?> series) {
					selectedSeries.add(series);
				}
			}
		}

		return selectedSeries;
	}

	protected BaseChart getBaseChart() {

		return seriesListUI.getBaseChart();
	}

	protected void refresh() {

		getBaseChart().redraw();
		getSeriesListUI().refresh();
	}
}
