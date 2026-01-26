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
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtchart.export;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtchart.extensions.core.ISeriesData;
import org.eclipse.swtchart.extensions.core.SeriesData;

public class SeriesConverter {

	public static ISeriesData getSeriesXY(String file) {

		int size = getNumberOfLines(file);
		double[] xSeries = new double[size];
		double[] ySeries = new double[size];

		try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of(file))) {
			String line;
			int i = 0;
			
			while((line = bufferedReader.readLine()) != null) {
				if(!line.startsWith("#")) {
					String[] values = line.split("\t");
					xSeries[i] = Double.parseDouble(values[0].trim());
					ySeries[i] = Double.parseDouble(values[1].trim());
					i++;
				}
			}
		} catch(Exception e) {

		}

		ISeriesData seriesData = new SeriesData(xSeries, ySeries, file);
		return seriesData;
	}

	public static ISeriesData getSeriesFromY(String fileName) {

		int size = getNumberOfLines(fileName);
		double[] ySeries = new double[size];

		try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of(fileName))) {
			String line;
			int i = 0;
			while((line = bufferedReader.readLine()) != null) {
				ySeries[i++] = Double.parseDouble(line.trim());
			}
		} catch(Exception e) {

		}

		ISeriesData seriesData = new SeriesData(ySeries, fileName);
		return seriesData;
	}

	public static List<ISeriesData> getSeriesScatter(String fileName) {

		List<ISeriesData> scatterSeriesList = new ArrayList<>();

		try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of(fileName))) {
			String line;
			while((line = bufferedReader.readLine()) != null) {
				String[] values = line.split("\t");
				String id = values[0].trim();
				double[] xSeries = new double[]{Double.parseDouble(values[1].trim())};
				double[] ySeries = new double[]{Double.parseDouble(values[2].trim())};
				ISeriesData seriesData = new SeriesData(xSeries, ySeries, id);
				scatterSeriesList.add(seriesData);
			}
		} catch(Exception e) {

		}
		return scatterSeriesList;
	}

	private static int getNumberOfLines(String file) {

		int i = 0;
		try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of(file))) {
			while((bufferedReader.readLine()) != null) {
				i++;
			}
		} catch(Exception e) {

		}
		return i;
	}
}
