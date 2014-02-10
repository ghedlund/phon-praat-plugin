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
