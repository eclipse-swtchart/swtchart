/*******************************************************************************
 * Copyright (c) 2010, 2025 VectorGraphics2D project.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Erich Seifert - initial API and implementation
 * Michael Seifert - initial API and implementation
 * Philip Wenig - flexible handling of width/height
 *******************************************************************************/
package org.eclipse.swtchart.vectorgraphics2d.util;

import java.awt.geom.Rectangle2D;

/**
 * <p>
 * Class that represents a page with a specified origin and size (millimeter).
 * The class is immutable and can be initialized with coordinates and
 * dimensions or only dimensions:
 * </p>
 * <pre>PageSize A3 = new PageSize(0.0, 0.0, 297.0, 420.0);</pre>
 * <pre>PageSize A4 = new PageSize(210.0, 297.0);</pre>
 */
public class PageSize {

	public static final double MM_PER_INCH = 25.4d;
	public static final double INCH_PER_MM = 1.0d / MM_PER_INCH;

	private final double x;
	private final double y;
	private double width;
	private double height;

	public PageSize(double x, double y, double width, double height) {

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public PageSize(double width, double height) {

		this(0.0, 0.0, width, height);
	}

	public PageSize(Rectangle2D size) {

		this(size.getX(), size.getY(), size.getWidth(), size.getHeight());
	}

	public PageSize getPortrait() {

		if(width <= height) {
			return this;
		}
		return new PageSize(x, y, height, width);
	}

	public PageSize getLandscape() {

		if(width >= height) {
			return this;
		}
		return new PageSize(x, y, height, width);
	}

	public double getX() {

		return x;
	}

	public double getY() {

		return y;
	}

	public double getWidth() {

		return width;
	}

	public void setWidth(double width) {

		this.width = width;
	}

	public double getHeight() {

		return height;
	}

	public void setHeight(double height) {

		this.height = height;
	}
}