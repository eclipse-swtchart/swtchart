/*******************************************************************************
 * Copyright (c) 2008, 2025 SWTChart project.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * yoshitaka - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtchart;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Rectangle;

/**
 * A legend for chart.
 */
public interface ILegend {

	/**
	 * Sets legend visible.
	 * 
	 * @param visible
	 *            the visibility state
	 */
	void setVisible(boolean visible);

	/**
	 * Gets the visibility state.
	 * 
	 * @return true if legend is visible
	 */
	boolean isVisible();

	/**
	 * Sets the background color of legend.
	 * 
	 * @param color
	 *            the background color
	 */
	void setBackground(Color color);

	/**
	 * Gets the background color of legend.
	 * 
	 * @return background color of legend.
	 */
	Color getBackground();

	/**
	 * Sets the foreground color of legend.
	 * 
	 * @param color
	 *            the foreground color
	 */
	void setForeground(Color color);

	/**
	 * Gets the foreground color of legend.
	 * 
	 * @return foreground color of legend.
	 */
	Color getForeground();

	/**
	 * Gets the font.
	 * 
	 * @return the font
	 */
	Font getFont();

	/**
	 * Sets the font.
	 * 
	 * @param font
	 *            the font
	 */
	void setFont(Font font);

	/**
	 * Gets the position of legend.
	 * 
	 * @return the position of legend.
	 */
	int getPosition();

	/**
	 * Sets the position of legend. If the position is <code>SWT.LEFT</code> or
	 * <code>SWT.RIGHT</code>, the orientation of series on legend will be vertical.
	 * If the position is <code>SWT.TOP</code> or <code>SWT.BOTTOM</code>, the
	 * orientation will be horizontal.
	 * 
	 * @param position
	 *            the position of legend that can be <code>SWT.LEFT</code>,
	 *            <code>SWT.RIGHT</code>, <code>SWT.TOP</code> or <code>SWT.BOTTOM</code>.
	 */
	void setPosition(int position);

	/**
	 * Gets the rectangle associated with the given series id on legend. This
	 * method is typically used for mouse listener to check whether mouse cursor
	 * is on legend for a certain series.
	 * <p>
	 * Mouse listener can be added by casting <code>ILegend</code> to
	 * <code>Control</code>.
	 * 
	 * <pre>
	 * Control legend = (Control) chart.getLegend();
	 * legend.addMouseListener(...);
	 * </pre>
	 * 
	 * @param seriesId
	 *            the series id
	 * @return the rectangle associated with the given series id in pixels.
	 */
	Rectangle getBounds(String seriesId);
}