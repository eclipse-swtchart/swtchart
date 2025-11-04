/*******************************************************************************
 * Copyright (c) 2022, 2025 Lablicate GmbH.
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
package org.eclipse.swtchart.extensions.dialogs;

import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtchart.ILineSeries.PlotSymbolType;
import org.eclipse.swtchart.extensions.core.IPointSeriesSettings;

public abstract class AbstractPointSeriesSettingsDialog<T extends IPointSeriesSettings> extends AbstractSeriesSettingsDialog<T> {

	private AtomicReference<ComboViewer> symbolTypeControl = new AtomicReference<>();
	private AtomicReference<Spinner> symbolSizeControl = new AtomicReference<>();
	private AtomicReference<Text> symbolColorControl = new AtomicReference<>();

	public AbstractPointSeriesSettingsDialog(Shell parentShell, T settings) {

		super(parentShell, settings);
	}

	@Override
	protected void createInputSection(Composite parent) {

		super.createInputSection(parent);
		createSymbolTypeSection(parent);
		createSymbolSizeSection(parent);
		createSymbolColorSection(parent);
	}

	@Override
	protected void initialize() {

		super.initialize();
		IPointSeriesSettings settings = getSettings();
		if(settings != null) {
			symbolTypeControl.get().setSelection(new StructuredSelection(settings.getSymbolType()));
			symbolSizeControl.get().setSelection(settings.getSymbolSize());
			symbolColorControl.get().setBackground(settings.getSymbolColor());
		}
	}

	private void createSymbolTypeSection(Composite parent) {

		String title = "Symbol Type";
		createSectionLabel(parent, title);

		ComboViewer comboViewer = createComboViewer(parent, title, PlotSymbolType.values(), PlotSymbolType.NONE, getGridData(GridData.FILL_HORIZONTAL, 2), object -> {

			IPointSeriesSettings settings = getSettings();
			if(settings != null) {
				if(object instanceof PlotSymbolType) {
					settings.setSymbolType((PlotSymbolType)object);
				}
			}
		});

		symbolTypeControl.set(comboViewer);
	}

	private void createSymbolSizeSection(Composite parent) {

		String title = "Symbol Size";
		createSectionLabel(parent, title);

		Spinner spinner = createSpinner(parent, title, 1, 50, 1, getGridData(GridData.FILL_HORIZONTAL, 2), selection -> {

			IPointSeriesSettings settings = getSettings();
			if(settings != null) {
				settings.setSymbolSize(selection);
			}
		});

		symbolSizeControl.set(spinner);
	}

	private void createSymbolColorSection(Composite parent) {

		String title = "Symbol Color";
		createSectionLabel(parent, title);

		Text text = createColorChoser(parent, title, getGridData(GridData.FILL_HORIZONTAL, 1), color -> {

			IPointSeriesSettings settings = getSettings();
			if(settings != null) {
				settings.setSymbolColor(color);
			}
		});

		symbolColorControl.set(text);
	}
}
