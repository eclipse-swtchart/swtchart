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
package org.eclipse.swtchart.extensions.clipboard;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swtchart.Chart;
import org.eclipse.swtchart.extensions.core.BaseChart;

public class ImageSupplier {

	/**
	 * Save image data representation to a file with specific format (BMP, PNG or JPG)
	 * 
	 * @param imageData
	 *            the image data representation
	 * @param fileName
	 *            the file (relative or full path)
	 * @param format
	 *            SWT.IMAGE_BMP, SWT.IMAGE_PNG or SWT.IMAGE_JPEG
	 */
	public void saveImage(ImageData imageData, String fileName, int format) {

		ImageLoader imageLoader = new ImageLoader();
		imageLoader.data = new ImageData[]{imageData};
		imageLoader.save(fileName, format);
	}

	/**
	 * Return image data representation of provided chart
	 * 
	 * @param baseChart
	 *            the chart from which is create image data representation
	 * @return image data representation
	 */
	public ImageData getImageData(BaseChart baseChart) {

		return getImageData((Chart)baseChart);
	}

	/**
	 * Return image data representation of provided chart
	 * 
	 * @param chart
	 *            the chart from which is create image data representation
	 * @return image data representation
	 */
	public ImageData getImageData(Chart chart) {

		/*
		 * Force to redraw chart immediately to be sure that any
		 * previous Shell dialog won't be a part of the copied image
		 */
		chart.redraw();
		chart.update();
		/*
		 * Surround main stuff with try/finally to prevent memory leakage
		 */
		Image image = null;
		GC gc = null;
		try {
			/*
			 * Copy chart into the image
			 */
			Rectangle bounds = chart.getBounds();
			image = new Image(chart.getDisplay(), bounds.width, bounds.height);
			gc = new GC(chart);
			chart.print(gc);
			gc.copyArea(image, 0, 0);
			/*
			 * Retrieve image data
			 */
			ImageData imageData = image.getImageData();
			return imageData;
		} finally {
			if(gc != null && !gc.isDisposed()) {
				gc.dispose();
			}
			if(image != null && !image.isDisposed()) {
				image.dispose();
			}
		}
	}
}