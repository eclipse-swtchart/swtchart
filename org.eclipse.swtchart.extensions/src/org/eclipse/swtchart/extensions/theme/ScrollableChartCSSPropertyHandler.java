/*******************************************************************************
 * Copyright (c) 2025, 2026  Lablicate GmbH.
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

import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.WidgetElement;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtchart.extensions.core.ISecondaryAxisSettings;
import org.eclipse.swtchart.extensions.core.ScrollableChart;
import org.w3c.dom.css.CSSValue;

@SuppressWarnings("restriction")
public class ScrollableChartCSSPropertyHandler implements ICSSPropertyHandler {

	@Override
	public boolean applyCSSProperty(Object element, String property, CSSValue value, String pseudo, CSSEngine engine) throws Exception {

		if("grid-color".equals(property)) {
			applyCSSPropertyGridColor(element, value, engine);
		}
		return false;
	}

	private void applyCSSPropertyGridColor(Object element, CSSValue value, CSSEngine engine) throws Exception {

		Widget widget = (Widget)((WidgetElement)element).getNativeWidget();
		if(value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			Color color = (Color)engine.convert(value, Color.class, widget.getDisplay());
			ScrollableChart scrollableChart = (ScrollableChart)widget;
			scrollableChart.getChartSettings().getPrimaryAxisSettingsX().setGridColor(color);
			scrollableChart.getChartSettings().getPrimaryAxisSettingsY().setGridColor(color);
			for(ISecondaryAxisSettings secondaryAxisSettingX : scrollableChart.getChartSettings().getSecondaryAxisSettingsListX()) {
				secondaryAxisSettingX.setGridColor(color);
			}
			for(ISecondaryAxisSettings secondaryAxisSettingY : scrollableChart.getChartSettings().getSecondaryAxisSettingsListY()) {
				secondaryAxisSettingY.setGridColor(color);
			}
		}
	}
}
