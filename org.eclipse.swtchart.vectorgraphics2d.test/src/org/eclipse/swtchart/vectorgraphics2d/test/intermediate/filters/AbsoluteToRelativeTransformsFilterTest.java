/*******************************************************************************
 * Copyright (c) 2010, 2026 VectorGraphics2D project.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Erich Seifert - initial API and implementation
 * Michael Seifert - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtchart.vectorgraphics2d.test.intermediate.filters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.awt.geom.AffineTransform;
import java.util.stream.StreamSupport;

import org.eclipse.swtchart.vectorgraphics2d.intermediate.CommandSequence;
import org.eclipse.swtchart.vectorgraphics2d.intermediate.MutableCommandSequence;
import org.eclipse.swtchart.vectorgraphics2d.intermediate.commands.Command;
import org.eclipse.swtchart.vectorgraphics2d.intermediate.commands.CreateCommand;
import org.eclipse.swtchart.vectorgraphics2d.intermediate.commands.DisposeCommand;
import org.eclipse.swtchart.vectorgraphics2d.intermediate.commands.SetTransformCommand;
import org.eclipse.swtchart.vectorgraphics2d.intermediate.commands.TransformCommand;
import org.eclipse.swtchart.vectorgraphics2d.intermediate.commands.TranslateCommand;
import org.eclipse.swtchart.vectorgraphics2d.intermediate.filters.AbsoluteToRelativeTransformsFilter;
import org.junit.jupiter.api.Test;

public class AbsoluteToRelativeTransformsFilterTest {

	@Test
	public void testSetTransformCommandReplaced() {

		AffineTransform absoluteTransform = new AffineTransform();
		absoluteTransform.rotate(42.0);
		absoluteTransform.translate(4.0, 2.0);
		CommandSequence commands = wrapCommands(new SetTransformCommand(absoluteTransform));
		AbsoluteToRelativeTransformsFilter filter = new AbsoluteToRelativeTransformsFilter(commands);
		assertFalse(StreamSupport.stream(filter.spliterator(), false).anyMatch(SetTransformCommand.class::isInstance));
	}

	@Test
	public void testAbsoluteAndRelativeTransformsIdentical() {

		AffineTransform absoluteTransform = new AffineTransform();
		absoluteTransform.rotate(42.0);
		absoluteTransform.translate(4.0, 2.0);
		CommandSequence commands = wrapCommands(new SetTransformCommand(absoluteTransform));
		AbsoluteToRelativeTransformsFilter filter = new AbsoluteToRelativeTransformsFilter(commands);
		filter.next();
		AffineTransform relativeTransform = ((TransformCommand)filter.next()).getValue();
		assertEquals(absoluteTransform, relativeTransform);
	}

	@Test
	public void testTranslateCorrect() {

		AffineTransform absoluteTransform = new AffineTransform();
		absoluteTransform.scale(2.0, 2.0);
		absoluteTransform.translate(4.2, 4.2); // (8.4, 8.4)
		CommandSequence commands = wrapCommands(new TranslateCommand(4.0, 2.0), new SetTransformCommand(absoluteTransform));
		AbsoluteToRelativeTransformsFilter filter = new AbsoluteToRelativeTransformsFilter(commands);
		TransformCommand transformCommand = null;
		while(filter.hasNext()) {
			Command<?> filteredCommand = filter.next();
			if(filteredCommand instanceof TransformCommand tCommand) {
				transformCommand = tCommand;
			}
		}
		AffineTransform relativeTransform = transformCommand.getValue();
		assertEquals(4.4, relativeTransform.getTranslateX());
		assertEquals(6.4, relativeTransform.getTranslateY());
	}

	@Test
	public void testRelativeTransformAfterDispose() {

		AffineTransform absoluteTransform = new AffineTransform();
		absoluteTransform.rotate(42.0);
		absoluteTransform.translate(4.0, 2.0);
		CommandSequence commands = wrapCommands(new CreateCommand(null), new TransformCommand(absoluteTransform), new DisposeCommand(null), new SetTransformCommand(absoluteTransform));
		AbsoluteToRelativeTransformsFilter filter = new AbsoluteToRelativeTransformsFilter(commands);
		TransformCommand lastTransformCommand = null;
		for(Command<?> filteredCommand : filter) {
			if(filteredCommand instanceof TransformCommand tCommand) {
				lastTransformCommand = tCommand;
			}
		}
		assertEquals(absoluteTransform, lastTransformCommand.getValue());
	}

	private CommandSequence wrapCommands(Command<?>... commands) {

		MutableCommandSequence commandSequence = new MutableCommandSequence();
		commandSequence.add(new CreateCommand(null));
		for(Command<?> command : commands) {
			commandSequence.add(command);
		}
		commandSequence.add(new DisposeCommand(null));
		return commandSequence;
	}
}
