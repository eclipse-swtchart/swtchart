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
 * Philip Wenig - default font name
 *******************************************************************************/
package org.eclipse.swtchart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtchart.internal.Title;
import org.eclipse.swtchart.util.ChartTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test case for axis title.
 */
public class AxisTitleTest extends ChartTestCase {

	private ITitle xAxisTitle;
	private ITitle yAxisTitle;

	@BeforeEach
	public void setUp() {

		xAxisTitle = chart.getAxisSet().getXAxis(0).getTitle();
		yAxisTitle = chart.getAxisSet().getYAxis(0).getTitle();
	}

	/**
	 * Test for title text.
	 */
	@Test
	public void testText() throws Exception {

		// set null
		xAxisTitle.setText(null);
		String text = xAxisTitle.getText();
		assertEquals("X Axis", text);
		showChart();
		yAxisTitle.setText(null);
		text = yAxisTitle.getText();
		assertEquals("Y Axis", text);
		showChart();
		// set text
		xAxisTitle.setText("foo");
		text = xAxisTitle.getText();
		assertEquals("foo", text);
		showChart();
		yAxisTitle.setText("foo");
		text = yAxisTitle.getText();
		assertEquals("foo", text);
		showChart();
		// set long text
		final String LONG_TITLE = "fooooooooooooooooooooooooooooooooooooooooooooooooooooooo";
		xAxisTitle.setText(LONG_TITLE);
		text = xAxisTitle.getText();
		assertEquals(LONG_TITLE, text);
		showChart();
		yAxisTitle.setText(LONG_TITLE);
		text = yAxisTitle.getText();
		assertEquals(LONG_TITLE, text);
		showChart();
		// set blank
		xAxisTitle.setText("");
		text = xAxisTitle.getText();
		assertEquals("", text);
		int height = ((Title)xAxisTitle).getBounds().height;
		assertEquals(0, height);
		showChart();
		yAxisTitle.setText("");
		text = yAxisTitle.getText();
		assertEquals("", text);
		height = ((Title)yAxisTitle).getBounds().width;
		assertEquals(0, height);
		showChart();
	}

	/**
	 * Test for foreground.
	 */
	@Test
	public void testForeground() {

		// set null
		xAxisTitle.setForeground(null);
		Color color = xAxisTitle.getForeground();
		assertEquals(new RGB(0, 0, 255), color.getRGB());
		showChart();
		yAxisTitle.setForeground(null);
		color = yAxisTitle.getForeground();
		assertEquals(new RGB(0, 0, 255), color.getRGB());
		showChart();
		// set color
		Color syan = Display.getDefault().getSystemColor(SWT.COLOR_CYAN);
		xAxisTitle.setForeground(syan);
		color = xAxisTitle.getForeground();
		assertEquals(syan.getRGB(), color.getRGB());
		showChart();
		yAxisTitle.setForeground(syan);
		color = yAxisTitle.getForeground();
		assertEquals(syan.getRGB(), color.getRGB());
		showChart();
		// set the disposed color
		Color disposed = new Color(Display.getDefault(), 0, 0, 0);
		disposed.dispose();
		assertThrows(IllegalArgumentException.class, () -> xAxisTitle.setForeground(disposed));
		color = xAxisTitle.getForeground();
		assertEquals(syan.getRGB(), color.getRGB());
		Color disposed2 = new Color(Display.getDefault(), 0, 0, 0);
		disposed2.dispose();
		assertThrows(IllegalArgumentException.class, () -> yAxisTitle.setForeground(disposed2));
		color = yAxisTitle.getForeground();
		assertEquals(syan.getRGB(), color.getRGB());
	}

	/**
	 * Test for axis title font.
	 */
	@Test
	public void testFont() {

		// set null
		xAxisTitle.setFont(null);
		FontData fontData = xAxisTitle.getFont().getFontData()[0];
		Font font = new Font(Display.getCurrent(), Resources.DEFAULT_FONT_NAME, 13, SWT.BOLD);
		FontData defaultFontData = font.getFontData()[0];
		assertEquals(defaultFontData.getName(), fontData.getName());
		assertEquals(defaultFontData.getHeight(), fontData.getHeight());
		assertEquals(defaultFontData.getStyle(), fontData.getStyle());
		yAxisTitle.setFont(null);
		fontData = yAxisTitle.getFont().getFontData()[0];
		assertEquals(defaultFontData.getName(), fontData.getName());
		assertEquals(defaultFontData.getHeight(), fontData.getHeight());
		assertEquals(defaultFontData.getStyle(), fontData.getStyle());
		font.dispose();
		// set font
		font = new Font(Display.getCurrent(), Resources.DEFAULT_FONT_NAME, 15, SWT.ITALIC);
		xAxisTitle.setFont(font);
		fontData = xAxisTitle.getFont().getFontData()[0];
		assertEquals(Resources.DEFAULT_FONT_NAME, fontData.getName());
		assertEquals(15, fontData.getHeight());
		assertEquals(SWT.ITALIC, fontData.getStyle());
		showChart();
		yAxisTitle.setFont(font);
		fontData = yAxisTitle.getFont().getFontData()[0];
		assertEquals(Resources.DEFAULT_FONT_NAME, fontData.getName());
		assertEquals(15, fontData.getHeight());
		assertEquals(SWT.ITALIC, fontData.getStyle());
		showChart();
		// set the disposed font
		font.dispose();
		Font font2 = font;
		assertThrows(IllegalArgumentException.class, () -> xAxisTitle.setFont(font2));
		assertThrows(IllegalArgumentException.class, () -> yAxisTitle.setFont(font2));
		// set large font size
		font = new Font(Display.getCurrent(), Resources.DEFAULT_FONT_NAME, 64, SWT.ITALIC);
		xAxisTitle.setFont(font);
		fontData = xAxisTitle.getFont().getFontData()[0];
		assertEquals(64, fontData.getHeight());
		showChart();
		font.dispose();
		font = new Font(Display.getCurrent(), Resources.DEFAULT_FONT_NAME, 64, SWT.ITALIC);
		yAxisTitle.setFont(font);
		fontData = yAxisTitle.getFont().getFontData()[0];
		assertEquals(64, fontData.getHeight());
		showChart();
		font.dispose();
		// set tiny font size
		font = new Font(Display.getCurrent(), Resources.DEFAULT_FONT_NAME, 4, SWT.ITALIC);
		xAxisTitle.setFont(font);
		fontData = xAxisTitle.getFont().getFontData()[0];
		assertEquals(4, fontData.getHeight());
		showChart();
		font.dispose();
		font = new Font(Display.getCurrent(), Resources.DEFAULT_FONT_NAME, 4, SWT.ITALIC);
		yAxisTitle.setFont(font);
		font = yAxisTitle.getFont();
		fontData = font.getFontData()[0];
		assertEquals(4, fontData.getHeight());
		showChart();
		font.dispose();
	}

	/**
	 * Test for title visibility.
	 */
	@Test
	public void testVisibility() {

		// set visibility
		xAxisTitle.setVisible(false);
		assertFalse(xAxisTitle.isVisible());
		showChart();
		xAxisTitle.setVisible(true);
		assertTrue(xAxisTitle.isVisible());
		showChart();
		yAxisTitle.setVisible(false);
		assertFalse(yAxisTitle.isVisible());
		showChart();
		yAxisTitle.setVisible(true);
		assertTrue(yAxisTitle.isVisible());
		showChart();
	}
}