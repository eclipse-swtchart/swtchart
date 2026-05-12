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
 *******************************************************************************/
package org.eclipse.swtchart.export.menu;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swtchart.customcharts.core.MassSpectrumChart;
import org.eclipse.swtchart.export.SeriesConverter;
import org.eclipse.swtchart.export.images.ImageFactory;
import org.eclipse.swtchart.extensions.barcharts.BarSeriesData;
import org.eclipse.swtchart.extensions.barcharts.IBarSeriesData;
import org.eclipse.swtchart.extensions.barcharts.IBarSeriesSettings;
import org.eclipse.swtchart.extensions.core.ISeriesData;
import org.junit.jupiter.api.Test;

public class ImageFactory_2_UITest {

	@Test
	public void test1() {

		assertFalse(GraphicsEnvironment.isHeadless(), "UI tests can't be executed on a headless build server.");
	}

	@Test
	public void test2() throws InstantiationException, IllegalAccessException, NumberFormatException, IOException {

		/*
		 * Create the factory.
		 */
		ImageFactory<MassSpectrumChart> imageFactory = new ImageFactory<>(MassSpectrumChart.class, 800, 600);
		/*
		 * Modify the chart.
		 */
		MassSpectrumChart massSpectrumChart = imageFactory.getChart();
		massSpectrumChart.setBackground(massSpectrumChart.getBaseChart().getDisplay().getSystemColor(SWT.COLOR_WHITE));
		List<IBarSeriesData> barSeriesDataList = new ArrayList<>();
		ISeriesData seriesData = SeriesConverter.getSeriesXY("testData/files/import/BarSeries1");

		IBarSeriesData barSeriesData = new BarSeriesData(seriesData);
		IBarSeriesSettings barSeriesSettings = barSeriesData.getSettings();
		barSeriesSettings.setDescription("");
		barSeriesDataList.add(barSeriesData);
		massSpectrumChart.addSeriesData(barSeriesDataList);
		/*
		 * Export the images.
		 */
		String prefix = "BarSeries1";

		String png = "testData/files/export" + File.separator + prefix + ".png";
		imageFactory.saveImage(png, SWT.IMAGE_PNG);
		File filePng = new File(png);
		assertTrue(filePng.exists());
		filePng.delete();

		String jpg = "testData/files/export" + File.separator + prefix + ".jpg";
		imageFactory.saveImage(jpg, SWT.IMAGE_JPEG);
		File fileJpg = new File(jpg);
		assertTrue(fileJpg.exists());
		fileJpg.delete();

		String bmp = "testData/files/export" + File.separator + prefix + ".bmp";
		imageFactory.saveImage(bmp, SWT.IMAGE_BMP);
		File fileBmp = new File(bmp);
		assertTrue(fileBmp.exists());
		fileBmp.delete();

		imageFactory.closeShell();
	}
}
