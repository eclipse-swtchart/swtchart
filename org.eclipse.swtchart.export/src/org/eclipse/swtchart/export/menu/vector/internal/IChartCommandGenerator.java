/*******************************************************************************
 * Copyright (c) 2023, 2026 Lablicate GmbH.
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

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtchart.export.vector.model.PageSizeOption;
import org.eclipse.swtchart.extensions.core.ScrollableChart;
import org.eclipse.swtchart.vectorgraphics2d.intermediate.CommandSequence;

public interface IChartCommandGenerator {

	CommandSequence getCommandSequence(Shell shell, PageSizeOption pageSizeOption, ScrollableChart scrollableChart);

	CommandSequence getCommandSequence(PageSizeOption pageSizeOption, int indexAxisX, int indexAxisY, ScrollableChart scrollableChart);
}