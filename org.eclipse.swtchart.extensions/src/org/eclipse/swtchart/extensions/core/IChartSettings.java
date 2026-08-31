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
package org.eclipse.swtchart.extensions.core;

import java.util.List;
import java.util.Set;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swtchart.extensions.events.IHandledEventProcessor;
import org.eclipse.swtchart.extensions.menu.IChartMenuEntry;

/**
 * Settings of a chart.
 * <p>
 * The settings are a passive value holder. Modifying them has no immediate effect, the
 * values are evaluated when the settings are handed over to
 * {@link ScrollableChart#applySettings(IChartSettings)}.
 */
public interface IChartSettings {

	/**
	 * Returns the registered menu entry of exactly the given class, or null if no such
	 * entry is registered. Subclasses of the given class are not matched.
	 */
	IChartMenuEntry getChartMenuEntryByClass(Class<?> clazz);

	/**
	 * Returns the registered event processor of exactly the given class, or null if no
	 * such processor is registered. Subclasses of the given class are not matched.
	 */
	IHandledEventProcessor getHandledEventProcessorByClass(Class<?> clazz);

	/**
	 * Sets whether a tooltip with the series value shall be displayed when hovering over a
	 * data point.
	 */
	void setEnableTooltips(boolean enable);

	/**
	 * Returns whether a tooltip with the series value is displayed when hovering over a
	 * data point.
	 */
	boolean isEnableTooltips();

	/**
	 * Returns whether the range selector is available.
	 */
	boolean isEnableRangeSelector();

	/**
	 * Sets whether the range selector shall be available. The range selector is the bar
	 * above the plot area that displays and edits the currently shown axis ranges. While it
	 * is available but hidden, a hint rectangle is drawn to point out that a double click
	 * opens it.
	 */
	void setEnableRangeSelector(boolean enableRangeSelector);

	/**
	 * Returns whether the range selector is shown when the chart is displayed for the first
	 * time.
	 */
	boolean isShowRangeSelectorInitially();

	/**
	 * Sets whether the range selector shall be shown when the chart is displayed for the
	 * first time. It is only shown if the range selector is enabled as well, see
	 * {@link #setEnableRangeSelector(boolean)}.
	 */
	void setShowRangeSelectorInitially(boolean showRangeSelectorInitially);

	/**
	 * Returns the index of the X axis that is preselected in the range selector.
	 */
	int getRangeSelectorDefaultAxisX();

	/**
	 * Sets the index of the X axis that shall be preselected in the range selector.
	 *
	 * @param rangeSelectorDefaultAxisX
	 *            0 selects the primary X axis, higher values select the secondary X axes in
	 *            the order they have been added
	 */
	void setRangeSelectorDefaultAxisX(int rangeSelectorDefaultAxisX);

	/**
	 * Returns the index of the Y axis that is preselected in the range selector.
	 */
	int getRangeSelectorDefaultAxisY();

	/**
	 * Sets the index of the Y axis that shall be preselected in the range selector.
	 *
	 * @param rangeSelectorDefaultAxisY
	 *            0 selects the primary Y axis, higher values select the secondary Y axes in
	 *            the order they have been added
	 */
	void setRangeSelectorDefaultAxisY(int rangeSelectorDefaultAxisY);

	/**
	 * Returns the color of the hint rectangle of the range selector.
	 */
	Color getColorHintRangeSelector();

	/**
	 * Sets the color of the hint rectangle that is drawn on top of the chart while the
	 * range selector is enabled but currently hidden.
	 */
	void setColorHintRangeSelector(Color colorHintRangeSelector);

	/**
	 * Returns whether the slider to scroll the Y axis is visible.
	 */
	boolean isVerticalSliderVisible();

	/**
	 * Sets whether the slider to scroll the Y axis shall be visible. A hidden slider is
	 * excluded from the layout and doesn't consume any space.
	 */
	void setVerticalSliderVisible(boolean verticalSliderVisible);

	/**
	 * Returns whether the slider to scroll the X axis is visible.
	 */
	boolean isHorizontalSliderVisible();

	/**
	 * Sets whether the slider to scroll the X axis shall be visible. A hidden slider is
	 * excluded from the layout and doesn't consume any space.
	 */
	void setHorizontalSliderVisible(boolean horizontalSliderVisible);

	/**
	 * Returns the text of the chart title.
	 */
	String getTitle();

	/**
	 * Sets the text of the chart title, displayed above the plot area.
	 */
	void setTitle(String title);

	/**
	 * Returns whether the chart title is displayed.
	 */
	boolean isTitleVisible();

	/**
	 * Sets whether the chart title shall be displayed.
	 */
	void setTitleVisible(boolean titleVisible);

	/**
	 * Returns the foreground color of the chart title.
	 */
	Color getTitleColor();

	/**
	 * Sets the foreground color of the chart title.
	 */
	void setTitleColor(Color titleColor);

	/**
	 * Returns the font of the chart title.
	 */
	Font getTitleFont();

	/**
	 * Sets the font of the chart title.
	 */
	void setTitleFont(Font titleFont);

	/**
	 * Returns whether the legend is displayed.
	 */
	boolean isLegendVisible();

	/**
	 * Sets whether the legend shall be displayed next to the plot area.
	 */
	void setLegendVisible(boolean legendVisible);

	/**
	 * Returns the edge of the chart the legend is attached to.
	 */
	int getLegendPosition();

	/**
	 * Sets the edge of the chart the legend shall be attached to.
	 *
	 * @param legendPosition
	 *            SWT.LEFT, SWT.RIGHT, SWT.TOP or SWT.BOTTOM
	 */
	void setLegendPosition(int legendPosition);

	/**
	 * Returns whether the extended legend is displayed.
	 */
	boolean isLegendExtendedVisible();

	/**
	 * Sets whether the extended legend shall be displayed. In contrast to the legend of the
	 * chart itself, this is a separate table next to the plot area that lists the series
	 * and allows to edit their settings.
	 */
	void setLegendExtendedVisible(boolean legendExtendedVisible);

	/**
	 * Returns the settings of the primary X axis. The returned instance is used by the
	 * chart, hence it can be modified in place to adjust the axis.
	 */
	IPrimaryAxisSettings getPrimaryAxisSettingsX();

	/**
	 * Returns the settings of the primary Y axis. The returned instance is used by the
	 * chart, hence it can be modified in place to adjust the axis.
	 */
	IPrimaryAxisSettings getPrimaryAxisSettingsY();

	/**
	 * Returns the modifiable list of the secondary X axes. Add an entry to create an
	 * additional X axis, the order of the list defines the order of the axes.
	 */
	List<ISecondaryAxisSettings> getSecondaryAxisSettingsListX();

	/**
	 * Returns the modifiable list of the secondary Y axes. Add an entry to create an
	 * additional Y axis, the order of the list defines the order of the axes.
	 */
	List<ISecondaryAxisSettings> getSecondaryAxisSettingsListY();

	/**
	 * Returns the orientation of the chart.
	 */
	int getOrientation();

	/**
	 * Sets the orientation of the chart. SWT.VERTICAL flips the chart, so that the X axis
	 * runs from top to bottom.
	 *
	 * @param orientation
	 *            SWT.HORIZONTAL or SWT.VERTICAL,
	 */
	void setOrientation(int orientation);

	/**
	 * Returns the background color of the composite that surrounds the chart.
	 */
	Color getBackground();

	/**
	 * Sets the background color of the composite that surrounds the chart, which also
	 * covers the sliders and the range selector. Null is ignored, so that the color can be
	 * left to the CSS styling.
	 */
	void setBackground(Color background);

	/**
	 * Returns the background color of the chart.
	 */
	Color getBackgroundChart();

	/**
	 * Sets the background color of the chart, which is the area that contains the title,
	 * the legend and the axes, but not the plot area.
	 */
	void setBackgroundChart(Color backgroundChart);

	/**
	 * Returns the background color of the plot area.
	 */
	Color getBackgroundPlotArea();

	/**
	 * Sets the background color of the plot area, which is the region enclosed by the axes
	 * where the series are drawn.
	 */
	void setBackgroundPlotArea(Color backgroundPlotArea);

	/**
	 * Returns whether the series are compressed before being drawn.
	 */
	boolean isEnableCompress();

	/**
	 * Sets whether the series shall be compressed before being drawn. Compression skips the
	 * data points that would be mapped to a pixel that is already occupied. Normally there
	 * is no reason to disable it, but it can be switched off to isolate drawing problems.
	 */
	void setEnableCompress(boolean enableCompress);

	/**
	 * Returns the restrictions that are applied when the user zooms or pans the chart, e.g.
	 * to keep the ranges anchored at zero or to limit a selection to one axis. The returned
	 * instance is used by the chart, hence it can be modified in place.
	 */
	RangeRestriction getRangeRestriction();

	/**
	 * Returns whether the position marker is drawn.
	 */
	boolean isShowPositionMarker();

	/**
	 * Sets whether the position marker shall be drawn. It is a crosshair in the plot area
	 * that follows the mouse cursor.
	 */
	void setShowPositionMarker(boolean showPositionMarker);

	/**
	 * Returns the color of the position marker.
	 */
	Color getColorPositionMarker();

	/**
	 * Sets the color of the position marker.
	 */
	void setColorPositionMarker(Color colorPositionMarker);

	/**
	 * Returns whether the plot center marker is drawn.
	 */
	boolean isShowPlotCenterMarker();

	/**
	 * Sets whether the plot center marker shall be drawn. It is a dashed vertical line at
	 * the horizontal center of the plot area.
	 */
	void setShowPlotCenterMarker(boolean showPlotCenterMarker);

	/**
	 * Returns the color of the plot center marker.
	 */
	Color getColorPlotCenterMarker();

	/**
	 * Sets the color of the plot center marker.
	 */
	void setColorPlotCenterMarker(Color colorPlotCenterMarker);

	/**
	 * Returns whether the legend marker is drawn.
	 */
	boolean isShowLegendMarker();

	/**
	 * Sets whether the legend marker shall be drawn. It is a small inline legend in the
	 * upper left corner of the plot area that reports the axis values at the current mouse
	 * position.
	 */
	void setShowLegendMarker(boolean showLegendMarker);

	/**
	 * Returns the color of the legend marker.
	 */
	Color getColorLegendMarker();

	/**
	 * Sets the color of the legend marker.
	 */
	void setColorLegendMarker(Color colorLegendMarker);

	/**
	 * Returns whether the axis zero marker is drawn.
	 */
	boolean isShowAxisZeroMarker();

	/**
	 * Sets whether the axis zero marker shall be drawn. It marks the zero point of the
	 * primary axes and is only visible if both ranges actually cross zero.
	 */
	void setShowAxisZeroMarker(boolean showAxisZeroMarker);

	/**
	 * Returns the color of the axis zero marker.
	 */
	Color getColorAxisZeroMarker();

	/**
	 * Sets the color of the axis zero marker.
	 */
	void setColorAxisZeroMarker(Color colorAxisZeroMarker);

	/**
	 * Returns whether the series label marker is drawn.
	 */
	boolean isShowSeriesLabelMarker();

	/**
	 * Sets whether the series label marker shall be drawn. It prints a label next to the
	 * data points of each visible series.
	 */
	void setShowSeriesLabelMarker(boolean showSeriesLabelMarker);

	/**
	 * Returns whether the series label marker prints the description instead of the id.
	 */
	boolean isUseSeriesLabelDescription();

	/**
	 * Sets whether the series label marker shall print the description of the series
	 * instead of its id.
	 */
	void setUseSeriesLabelDescription(boolean useSeriesLabelDescription);

	/**
	 * Returns the color of the series label marker.
	 */
	Color getColorSeriesLabelMarker();

	/**
	 * Sets the color of the series label marker.
	 */
	void setColorSeriesLabelMarker(Color colorSeriesLabelMarker);

	/**
	 * Returns whether the context menu is created.
	 */
	boolean isCreateMenu();

	/**
	 * Sets whether the context menu shall be created from the registered menu entries. If
	 * set to false, an already existing menu is disposed.
	 */
	void setCreateMenu(boolean createMenu);

	/**
	 * Adds an entry to the context menu. A set of default entries is registered already,
	 * see {@link #clearMenuEntries()} to remove them.
	 */
	void addMenuEntry(IChartMenuEntry menuEntry);

	/**
	 * Removes the given entry from the context menu.
	 */
	void removeMenuEntry(IChartMenuEntry menuEntry);

	/**
	 * Returns an unmodifiable view of the registered menu entries. Use
	 * {@link #addMenuEntry(IChartMenuEntry)} and
	 * {@link #removeMenuEntry(IChartMenuEntry)} to modify them.
	 */
	Set<IChartMenuEntry> getMenuEntries();

	/**
	 * Returns the registered menu entry whose {@link IChartMenuEntry#getName()} matches the
	 * given name, or null if there is no such entry.
	 */
	IChartMenuEntry getChartMenuEntry(String name);

	/**
	 * Removes all entries from the context menu, including the default entries.
	 */
	void clearMenuEntries();

	/**
	 * Returns whether the data of the selected series can be shifted.
	 */
	boolean isSupportDataShift();

	/**
	 * Sets whether the data of the selected series can be shifted by dragging it with the
	 * mouse.
	 */
	void setSupportDataShift(boolean supportDataShift);

	/**
	 * Adds a processor that handles mouse and keyboard events of the chart. A set of
	 * default processors is registered already, see
	 * {@link #clearHandledEventProcessors()} to remove them.
	 */
	void addHandledEventProcessor(IHandledEventProcessor handledEventProcessor);

	/**
	 * Removes the given event processor.
	 */
	void removeHandledEventProcessor(IHandledEventProcessor handledEventProcessor);

	/**
	 * Returns an unmodifiable view of the registered event processors. Use
	 * {@link #addHandledEventProcessor(IHandledEventProcessor)} and
	 * {@link #removeHandledEventProcessor(IHandledEventProcessor)} to modify them.
	 */
	Set<IHandledEventProcessor> getHandledEventProcessors();

	/**
	 * Removes all event processors, including the default processors.
	 */
	void clearHandledEventProcessors();

	/**
	 * Returns whether the plot area is buffered while a selection is performed.
	 */
	boolean isBufferSelection();

	/**
	 * Sets whether the plot area shall be buffered while a selection is performed. If
	 * enabled, the series are captured into an image once and that image is displayed
	 * instead of redrawing the series on every mouse move.
	 */
	void setBufferSelection(boolean bufferSelection);

	/**
	 * Returns whether zoom operations without data in the selection are suppressed.
	 */
	boolean isPreventAccidentalZoom();

	/**
	 * Sets whether zoom operations shall be suppressed if the selected rectangle doesn't
	 * contain any data point. This avoids zooming into an empty region on an accidental
	 * mouse drag.
	 */
	void setPreventAccidentalZoom(boolean preventAccidentalZoom);

	/**
	 * Returns whether the plot area is maximized.
	 */
	boolean isPlotAreaMaximized();

	/**
	 * Sets whether the plot area shall be maximized. If enabled, the margins and the
	 * spacing of the surrounding composites are removed and the title, the legend, the
	 * sliders and the range selector are hidden.
	 */
	void setPlotAreaMaximized(boolean plotAreaMaximized);
}