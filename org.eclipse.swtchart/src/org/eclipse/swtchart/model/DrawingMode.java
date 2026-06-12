/*******************************************************************************
 * Copyright (c) 2026 SWTChart project.
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
package org.eclipse.swtchart.model;

/**
 * Selects the line-drawing back-end.
 * CLASSIC uses SWT GC primitives (antialiasing, all line styles, full symbol types).
 * BRESENHAM uses a pixel-based Bresenham rasteriser for solid-line drawing;
 * area, symbols, labels and error bars fall back to the GC so feature parity is maintained.
 */
public enum DrawingMode {

	CLASSIC, //
	BRESENHAM; // Experimental
}