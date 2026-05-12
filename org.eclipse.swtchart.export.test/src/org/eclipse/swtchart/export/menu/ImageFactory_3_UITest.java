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
import org.eclipse.swtchart.ILineSeries.PlotSymbolType;
import org.eclipse.swtchart.customcharts.core.PCAChart;
import org.eclipse.swtchart.export.SeriesConverter;
import org.eclipse.swtchart.export.images.ImageFactory;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.core.ISeriesData;
import org.eclipse.swtchart.extensions.scattercharts.IScatterSeriesData;
import org.eclipse.swtchart.extensions.scattercharts.IScatterSeriesSettings;
import org.eclipse.swtchart.extensions.scattercharts.ScatterSeriesData;
import org.junit.jupiter.api.Test;

public class ImageFactory_3_UITest {

	private int SYMBOL_SIZE = 8;

	@Test
	public void test1() {

		assertFalse(GraphicsEnvironment.isHeadless(), "UI tests can't be executed on a headless build server.");
	}

	@Test
	public void test2() throws InstantiationException, IllegalAccessException, NumberFormatException, IOException {

		/*
		 * Create the factory.
		 */
		ImageFactory<PCAChart> imageFactory = new ImageFactory<>(PCAChart.class, 800, 600);
		/*
		 * Modify the chart.
		 */
		PCAChart pcaChart = imageFactory.getChart();
		BaseChart baseChart = pcaChart.getBaseChart();
		pcaChart.setBackground(baseChart.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		List<ISeriesData> scatterSeriesList = SeriesConverter.getSeriesScatter("testData/files/import/ScatterSeries1");
		List<IScatterSeriesData> scatterSeriesDataList = new ArrayList<>();

		for(ISeriesData seriesData : scatterSeriesList) {
			IScatterSeriesData scatterSeriesData = new ScatterSeriesData(seriesData);
			IScatterSeriesSettings scatterSeriesSettings = scatterSeriesData.getSettings();
			/*
			 * Set the color and symbol type.
			 */
			double x = seriesData.getXSeries()[0];
			double y = seriesData.getYSeries()[0];
			scatterSeriesSettings.setSymbolSize(SYMBOL_SIZE);

			if(x > 0 && y > 0) {
				scatterSeriesSettings.setSymbolColor(baseChart.getDisplay().getSystemColor(SWT.COLOR_RED));
				scatterSeriesSettings.setSymbolType(PlotSymbolType.SQUARE);
			} else if(x > 0 && y < 0) {
				scatterSeriesSettings.setSymbolColor(baseChart.getDisplay().getSystemColor(SWT.COLOR_BLUE));
				scatterSeriesSettings.setSymbolType(PlotSymbolType.TRIANGLE);
			} else if(x < 0 && y > 0) {
				scatterSeriesSettings.setSymbolColor(baseChart.getDisplay().getSystemColor(SWT.COLOR_MAGENTA));
				scatterSeriesSettings.setSymbolType(PlotSymbolType.DIAMOND);
			} else if(x < 0 && y < 0) {
				scatterSeriesSettings.setSymbolColor(baseChart.getDisplay().getSystemColor(SWT.COLOR_CYAN));
				scatterSeriesSettings.setSymbolType(PlotSymbolType.INVERTED_TRIANGLE);
			} else {
				scatterSeriesSettings.setSymbolColor(baseChart.getDisplay().getSystemColor(SWT.COLOR_GRAY));
				scatterSeriesSettings.setSymbolType(PlotSymbolType.CIRCLE);
			}

			scatterSeriesDataList.add(scatterSeriesData);
		}
		pcaChart.addSeriesData(scatterSeriesDataList);
		/*
		 * Export the images.
		 */
		String prefix = "ScatterSeries1";

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
