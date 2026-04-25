/*******************************************************************************
 * Copyright (c) 2017, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Frank Buloup - Internationalization
 *******************************************************************************/
package org.eclipse.swtchart.extensions.menu;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtchart.extensions.core.ResourceSupport;
import org.eclipse.swtchart.extensions.core.ScrollableChart;

public class UndoSelectionHandler extends AbstractChartMenuEntry {

	private String category = "";

	public UndoSelectionHandler() {

		this(IChartMenuCategories.RANGE_SELECTION);
	}

	public UndoSelectionHandler(String category) {

		this.category = category;
	}

	@Override
	public String getCategory() {

		return category;
	}

	@Override
	public String getName() {

		return Messages.getString(Messages.UNDO_SELECTION);
	}

	@Override
	public Image getIcon() {

		return ResourceSupport.getImage(ResourceSupport.ICON_UNDO);
	}

	@Override
	public void execute(Shell shell, ScrollableChart scrollableChart) {

		scrollableChart.getBaseChart().undoSelection();
		scrollableChart.redraw();
	}
}