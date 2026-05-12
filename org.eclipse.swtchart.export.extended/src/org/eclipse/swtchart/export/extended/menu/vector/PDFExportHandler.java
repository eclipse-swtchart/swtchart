/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.swtchart.export.extended.menu.vector;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtchart.export.core.AbstractSeriesExportHandler;
import org.eclipse.swtchart.export.core.VectorExportSettingsDialog;
import org.eclipse.swtchart.export.extended.awt.ChartToGraphics2D;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.core.ScrollableChart;
import org.eclipse.swtchart.vectorgraphics2d.core.Document;
import org.eclipse.swtchart.vectorgraphics2d.core.Processor;
import org.eclipse.swtchart.vectorgraphics2d.core.Processors;
import org.eclipse.swtchart.vectorgraphics2d.core.VectorGraphics2D;
import org.eclipse.swtchart.vectorgraphics2d.util.PageSize;

public class PDFExportHandler extends AbstractSeriesExportHandler {

	private static final String FILE_EXTENSION = "*.pdf"; //$NON-NLS-1$
	private static final String NAME = MessageFormat.format(Messages.getString(Messages.PDF), FILE_EXTENSION);
	private static final String TITLE = Messages.getString(Messages.SAVE_AS_PDF);

	@Override
	public String getName() {

		return NAME;
	}

	@Override
	public void execute(Shell shell, ScrollableChart scrollableChart) {

		FileDialog fileDialog = new FileDialog(shell, SWT.SAVE);
		fileDialog.setOverwrite(true);
		fileDialog.setText(NAME);
		fileDialog.setFilterExtensions(FILE_EXTENSION);
		fileDialog.setFileName(scrollableChart.getFileName());

		String fileName = fileDialog.open();
		if(fileName != null) {
			try {
				BaseChart baseChart = scrollableChart.getBaseChart();
				VectorExportSettingsDialog exportSettingsDialog = new VectorExportSettingsDialog(fileDialog.getParent(), baseChart);
				exportSettingsDialog.create();
				if(exportSettingsDialog.open() == Window.OK) {
					int indexAxisX = exportSettingsDialog.getIndexAxisSelectionX();
					int indexAxisY = exportSettingsDialog.getIndexAxisSelectionY();
					if(indexAxisX >= 0 && indexAxisY >= 0) {
						try {
							ProgressMonitorDialog monitorDialog = new ProgressMonitorDialog(fileDialog.getParent());
							monitorDialog.run(false, true, monitor -> {

								monitor.beginTask(Messages.getString(Messages.EXPORT_TO_PDF), IProgressMonitor.UNKNOWN);
								try (OutputStream output = new FileOutputStream(fileName)) {
									VectorGraphics2D graphics2D = new VectorGraphics2D();
									graphics2D = (VectorGraphics2D)new ChartToGraphics2D(baseChart, indexAxisX, indexAxisY, graphics2D).getGraphics2D();

									Processor processor = Processors.get("pdf"); //$NON-NLS-1$
									PageSize pageSize = new PageSize(0.0d, 0.0d, baseChart.getSize().x, baseChart.getSize().y);
									Document document = processor.getDocument(graphics2D.getCommands(), pageSize);
									document.writeTo(output);
									MessageDialog.openInformation(fileDialog.getParent(), TITLE, MESSAGE_OK);
								} catch(IOException e) {
									e.printStackTrace();
									String reason = e.getMessage() != null ? e.getMessage() : MESSAGE_ERROR;
									MessageDialog.openError(fileDialog.getParent(), TITLE, MessageFormat.format(Messages.getString(Messages.PDF_EXPORT_ERROR), reason));
								} finally {
									monitor.done();
								}
							});
						} catch(InterruptedException e) {
							e.printStackTrace();
							Thread.currentThread().interrupt();
							MessageDialog.openInformation(fileDialog.getParent(), TITLE, Messages.getString(Messages.EXPORT_TO_PDF_INTERRUPTED));
						}
					}
				}
			} catch(InvocationTargetException e) {
				MessageDialog.openInformation(shell, TITLE, MESSAGE_ERROR);
				e.getCause().printStackTrace();
			}
		}
	}
}
