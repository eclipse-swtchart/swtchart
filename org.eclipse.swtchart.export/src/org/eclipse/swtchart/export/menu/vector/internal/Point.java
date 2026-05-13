/*******************************************************************************
 * Copyright (c) 2008, 2026 Lablicate GmbH.
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
package org.eclipse.swtchart.export.menu.vector.internal;

public class Point {

	private double x;
	private double y;

	public Point(double x, double y) {

		this.x = x;
		this.y = y;
	}

	public double getX() {

		return x;
	}

	public void setX(double x) {

		this.x = x;
	}

	public double getY() {

		return y;
	}

	public void setY(double y) {

		this.y = y;
	}

	@Override
	public boolean equals(Object otherObject) {

		if(this == otherObject) {
			return true;
		}
		if(otherObject == null) {
			return false;
		}
		if(getClass() != otherObject.getClass()) {
			return false;
		}
		Point other = (Point)otherObject;
		return getX() == other.getX() && getY() == other.getY();
	}

	@Override
	public int hashCode() {

		return Double.valueOf(x).hashCode() + Double.valueOf(y).hashCode();
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getName());
		builder.append("[");
		builder.append("x=" + x);
		builder.append(",");
		builder.append("y=" + y);
		builder.append("]");
		return builder.toString();
	}
}