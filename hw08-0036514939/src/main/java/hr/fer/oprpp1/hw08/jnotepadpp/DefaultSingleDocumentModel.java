package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DefaultSingleDocumentModel implements SingleDocumentModel {
	private JTextArea editor;
	private Path path;
	private boolean modified;
	private List<SingleDocumentListener> listeners = new ArrayList<>();
	
	public DefaultSingleDocumentModel(Path path, String content) {
		setFilePath(path);
		editor = new JTextArea(content);
		setModified(false);
		editor.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
			}
		});
			
	}

	@Override
	public JTextArea getTextComponent() {
		return editor;
	}

	@Override
	public Path getFilePath() {
		return path;
	}

	@Override
	public void setFilePath(Path path) {
		this.path = path;
		notifyListenersPathUpdated();
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		notifyListenersModifyStatusUpdated();
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);		
	}
	
	private void notifyListenersModifyStatusUpdated() {
		for(var l : listeners) {
			l.documentModifyStatusUpdated(this);
		}
	}
	
	private void notifyListenersPathUpdated() {
		for(var l : listeners) {
			l.documentFilePathUpdated(this);
		}
	}

}
