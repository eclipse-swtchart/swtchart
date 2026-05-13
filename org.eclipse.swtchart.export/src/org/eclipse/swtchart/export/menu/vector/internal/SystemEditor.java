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

import java.io.File;
import java.net.URL;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.swt.program.Program;

public class SystemEditor {

	/*
	 * Open the file in the default system editor.
	 */
	public static boolean open(File file) {

		boolean success = false;
		Program program = Program.findProgram(FilenameUtils.getExtension(file.getName()));
		if(program == null) {
			program = Program.findProgram("txt");
		}
		if(program != null) {
			program.execute(file.getAbsolutePath());
			success = true;
		}
		return success;
	}

	public static boolean browse(URL url) {

		boolean success = false;
		if(url != null) {
			Program.launch(url.toString());
			success = true;
		}
		return success;
	}
}