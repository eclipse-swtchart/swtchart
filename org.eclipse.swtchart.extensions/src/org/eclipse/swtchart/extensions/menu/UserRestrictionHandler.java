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
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtchart.extensions.menu;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtchart.IAxis;
import org.eclipse.swtchart.IAxisSet;
import org.eclipse.swtchart.Range;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.core.ResourceSupport;
import org.eclipse.swtchart.extensions.core.ScrollableChart;
import org.eclipse.swtchart.extensions.core.UserRestriction;

public class UserRestrictionHandler extends AbstractChartMenuEntry implements IChartMenuEntry {

	private static final String NAME_SELECT = "User Restriction (Select)";
	private static final String NAME_RESET = "User Restriction (Reset)";
	//
	private String category = "";
	private String name = NAME_SELECT;

	public UserRestrictionHandler() {

		this(IChartMenuCategories.STANDARD_OPERATION);
	}

	public UserRestrictionHandler(String category) {

		this.category = category;
	}

	@Override
	public String getCategory() {

		return category;
	}

	@Override
	public String getName() {

		return name;
	}

	@Override
	public Image getIcon() {

		return ResourceSupport.getImage(ResourceSupport.ICON_RESTRICT_RANGE);
	}

	@Override
	public void execute(Shell shell, ScrollableChart scrollableChart) {

		BaseChart baseChart = scrollableChart.getBaseChart();
		UserRestriction userRestriction = baseChart.getUserRestriction();
		if(userRestriction.isRestrictFrame()) {
			userRestriction.resetRestriction();
			name = NAME_SELECT;
		} else {
			IAxisSet axisSet = baseChart.getAxisSet();
			IAxis axisX = axisSet.getXAxis(BaseChart.ID_PRIMARY_X_AXIS);
			IAxis axisY = axisSet.getYAxis(BaseChart.ID_PRIMARY_Y_AXIS);
			Range rangeX = new Range(axisX.getRange());
			Range rangeY = new Range(axisY.getRange());
			userRestriction.setRange(rangeX, rangeY);
			name = NAME_RESET;
		}
	}
}