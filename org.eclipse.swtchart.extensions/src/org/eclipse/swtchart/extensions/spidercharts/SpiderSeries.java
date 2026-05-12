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
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtchart.extensions.spidercharts;

import java.util.Arrays;
import java.util.Objects;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swtchart.extensions.core.ISeriesData;

public class SpiderSeries implements ISeriesData {

	/*
	 * xSeries
	 * One value per axis, expected range [0..maxValue]
	 */
	private String label = null;
	private double[] xSeries = null;
	private double[] ySeries = new double[0];
	private Color color = null;

	/**
	 * Use a distinct label and one value per given axis.
	 * 
	 * @param label
	 * @param color
	 */
	public SpiderSeries(String label, double[] xSeries, Color color) {

		this.label = label;
		this.xSeries = xSeries;
		this.color = color;
	}

	@Override
	public double[] getXSeries() {

		return xSeries;
	}

	@Override
	public double[] getYSeries() {

		return ySeries;
	}

	@Override
	public String getId() {

		return label;
	}

	public Color getColor() {

		return color;
	}

	@Override
	public int hashCode() {

		return Objects.hash(label);
	}

	@Override
	public boolean equals(Object obj) {

		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		SpiderSeries other = (SpiderSeries)obj;
		return Objects.equals(label, other.label);
	}

	@Override
	public String toString() {

		return "SpiderSeries [label=" + label + ", values=" + Arrays.toString(xSeries) + ", color=" + color + "]";
	}
}