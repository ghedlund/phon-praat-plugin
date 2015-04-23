/*
 * phon-textgrid-plugin
 * Copyright (C) 2015, Gregory Hedlund <ghedlund@mun.ca>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ca.phon.plugins.praat;

import ca.phon.app.session.editor.EditorView;
import ca.phon.app.session.editor.SessionEditor;
import ca.phon.plugin.IPluginExtensionFactory;
import ca.phon.plugin.IPluginExtensionPoint;
import ca.phon.plugin.PhonPlugin;

@PhonPlugin(name="Text Grid",version="0.1",minPhonVersion="1.5.2")
public class TextGridViewExtPt implements IPluginExtensionPoint<EditorView> {

	@Override
	public Class<EditorView> getExtensionType() {
		return EditorView.class;
	}

	@Override
	public IPluginExtensionFactory<EditorView> getFactory() {
		return new TextGridViewExtPtFactory();
	}
	
	/**
	 * Internal factory.
	 */
	private class TextGridViewExtPtFactory implements IPluginExtensionFactory<EditorView> {

		@Override
		public EditorView createObject(Object... args) {
			final SessionEditor editor = SessionEditor.class.cast(args[0]);
			return new TextGridPanel(editor);
		}
		
	}

}
