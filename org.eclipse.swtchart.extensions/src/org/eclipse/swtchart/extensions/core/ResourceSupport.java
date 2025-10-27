/*******************************************************************************
 * Copyright (c) 2020, 2025 Lablicate GmbH.
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

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swtchart.Resources;

public class ResourceSupport extends Resources {

	public static final String ICON_SET_RANGE = "set_range.svg"; // $NON-NLS-1$
	public static final String ICON_RESTRICT_RANGE = "restrict_range.gif"; // $NON-NLS-1$
	public static final String ICON_HIDE = "hide.svg"; // $NON-NLS-1$
	public static final String ICON_RESET = "reset.svg"; // $NON-NLS-1$
	public static final String ICON_CHECKED = "checked.svg"; // $NON-NLS-1$
	public static final String ICON_UNCHECKED = "unchecked.svg"; // $NON-NLS-1$
	public static final String ICON_CHECK_ALL = "checkAll.svg"; // $NON-NLS-1$
	public static final String ICON_UNCHECK_ALL = "uncheckAll.svg"; // $NON-NLS-1$
	public static final String ICON_LEGEND = "legend.gif"; // $NON-NLS-1$
	public static final String ICON_SORT = "sort.svg"; // $NON-NLS-1$
	public static final String ICON_POSITION = "position.gif"; // $NON-NLS-1$
	public static final String ICON_SETTINGS = "preferences.gif"; // $NON-NLS-1$
	public static final String ICON_MAPPINGS = "mappings.svg"; // $NON-NLS-1$
	public static final String ICON_DELETE = "delete.svg"; // $NON-NLS-1$
	public static final String ICON_DELETE_ALL = "deleteAll.png"; // $NON-NLS-1$
	public static final String ARROW_LEFT = "arrowLeft.svg"; // $NON-NLS-1$
	public static final String ARROW_RIGHT = "arrowRight.svg"; // $NON-NLS-1$
	public static final String ARROW_UP = "arrowUp.svg"; // $NON-NLS-1$
	public static final String ARROW_DOWN = "arrowDown.svg"; // $NON-NLS-1$
	public static final String ICON_SERIES_MARKER = "seriesMarker.gif"; // $NON-NLS-1$
	public static final String ICON_IMPORT = "import.svg"; // $NON-NLS-1$
	public static final String ICON_EXPORT = "export.svg"; // $NON-NLS-1$
	public static final String ICON_RESET_SELECTED = "resetSelected.gif"; // $NON-NLS-1$
	public static final String ICON_RESET_ALL = "resetAll.gif"; // $NON-NLS-1$
	public static final String ICON_RESET_SELECTION = "reset-selection.gif"; // $NON-NLS-1$
	public static final String ICON_REDO = "redo.svg"; // $NON-NLS-1$
	public static final String ICON_UNDO = "undo.svg"; // $NON-NLS-1$
	public static final String ICON_COPY_CLIPBOARD = "copy-clipboard.svg"; // $NON-NLS-1$
	public static final String ICON_CSV = "csv.gif"; // $NON-NLS-1$
	public static final String ICON_FIGURE = "figure.gif"; // $NON-NLS-1$
	public static final String ICON_BITMAP = "bitmap.gif"; // $NON-NLS-1$
	public static final String ICON_PRINT = "print.svg"; // $NON-NLS-1$
	public static final String ICON_TEX = "tex.gif"; // $NON-NLS-1$
	public static final String ICON_R = "r.gif"; // $NON-NLS-1$
	public static final String ICON_TRANSFER = "transfer.png"; // $NON-NLS-1$
	public static final String ICON_SAVE = "save.svg"; // $NON-NLS-1$
	public static final String ICON_ADD = "add.svg"; // $NON-NLS-1$

	private static ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources());
	private static IPreferenceStore preferenceStore = null;
	private static ImageRegistry imageRegistry = null;

	private ResourceSupport() {

	}

	/**
	 * Returns the current preference store.
	 * 
	 * @return {@link IPreferenceStore}
	 */
	public static IPreferenceStore getPreferenceStore() {

		if(preferenceStore == null) {
			try {
				/*
				 * SWTChart may be used also in a non Eclipse context.
				 * Hence, a simple file preference store instead of a ScopedPreferenceStore is used.
				 */
				Path path = Paths.get(System.getProperty("user.home"), ".eclipse", "org.eclipse.swtchart.extensions", "chart.properties");
				if(!path.toFile().exists()) {
					Files.createDirectories(path.getParent());
					Files.createFile(path);
				}
				preferenceStore = new PreferenceStore(path.toAbsolutePath().toString());
				/*
				 * Load existing values.
				 */
				((PreferenceStore)preferenceStore).load();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return preferenceStore;
	}

	public static void savePreferenceStore() {

		IPreferenceStore preferenceStore = getPreferenceStore();
		if(preferenceStore instanceof PreferenceStore savablePreferenceStore) {
			try {
				savablePreferenceStore.save();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns the given image. There is no need to
	 * dispose this image. It's handled by the
	 * resource support.
	 * 
	 * @param key
	 * @return {@link Image}
	 */
	public static Image getImage(String key) {

		if(imageRegistry == null) {
			imageRegistry = initializeImageRegistry();
		}

		return resourceManager.createImageWithDefault(imageRegistry.getDescriptor(key));
	}
	
	/**
	 * Returns the given image disabled. There is no need to
	 * dispose this image. It's handled by the
	 * resource support.
	 * 
	 * @param key
	 * @return {@link Image}
	 */
	public static Image getImageDisabled(String key) {

		if(imageRegistry == null) {
			imageRegistry = initializeImageRegistry();
		}

		return resourceManager.createImageWithDefault(ImageDescriptor.createWithFlags(imageRegistry.getDescriptor(key), SWT.IMAGE_DISABLE));
	}

	@Override
	protected void finalize() throws Throwable {

		/*
		 * Images
		 */
		if(imageRegistry != null) {
			imageRegistry.dispose();
		}
		super.finalize();
	}

	private static ImageRegistry initializeImageRegistry() {

		if(getDisplay() == null) {
			throw new SWTException(SWT.ERROR_THREAD_INVALID_ACCESS);
		} else {
			imageRegistry = new ImageRegistry();
		}

		Set<String> imageSet = new HashSet<>();
		imageSet.add(ICON_SET_RANGE);
		imageSet.add(ICON_RESTRICT_RANGE);
		imageSet.add(ICON_HIDE);
		imageSet.add(ICON_RESET);
		imageSet.add(ICON_CHECKED);
		imageSet.add(ICON_UNCHECKED);
		imageSet.add(ICON_CHECK_ALL);
		imageSet.add(ICON_UNCHECK_ALL);
		imageSet.add(ICON_LEGEND);
		imageSet.add(ICON_SORT);
		imageSet.add(ICON_POSITION);
		imageSet.add(ICON_SETTINGS);
		imageSet.add(ICON_MAPPINGS);
		imageSet.add(ICON_DELETE);
		imageSet.add(ICON_DELETE_ALL);
		imageSet.add(ARROW_LEFT);
		imageSet.add(ARROW_RIGHT);
		imageSet.add(ARROW_UP);
		imageSet.add(ARROW_DOWN);
		imageSet.add(ICON_SERIES_MARKER);
		imageSet.add(ICON_IMPORT);
		imageSet.add(ICON_EXPORT);
		imageSet.add(ICON_RESET_SELECTED);
		imageSet.add(ICON_RESET_ALL);
		imageSet.add(ICON_RESET_SELECTION);
		imageSet.add(ICON_REDO);
		imageSet.add(ICON_UNDO);
		imageSet.add(ICON_COPY_CLIPBOARD);
		imageSet.add(ICON_CSV);
		imageSet.add(ICON_FIGURE);
		imageSet.add(ICON_BITMAP);
		imageSet.add(ICON_PRINT);
		imageSet.add(ICON_TEX);
		imageSet.add(ICON_R);
		imageSet.add(ICON_TRANSFER);
		imageSet.add(ICON_SAVE);
		imageSet.add(ICON_ADD);

		for(String image : imageSet) {
			imageRegistry.put(image, createImageDescriptor(image));
		}

		return imageRegistry;
	}

	/**
	 * Helps to create an image descriptor.
	 * 
	 * @param fileName
	 * @return ImageDescriptor
	 */
	private static ImageDescriptor createImageDescriptor(String fileName) {
		URL url;
		if (fileName.endsWith("svg")) {
			url = ResourceSupport.class.getResource("/resources/icons/svg/" + fileName);
		} else {
			url = ResourceSupport.class.getResource("/resources/icons/16x16/" + fileName);
		}
		return ImageDescriptor.createFromURL(url);
	}
}