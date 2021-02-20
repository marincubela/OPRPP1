package hr.fer.oprpp1.hw08.jnotepadpp;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
	private static final long serialVersionUID = 1L;

	private List<SingleDocumentModel> documents;
	private SingleDocumentModel currentDocument;
	private List<MultipleDocumentListener> listeners;

	public DefaultMultipleDocumentModel() {
		super();
		documents = new ArrayList<>();
		listeners = new ArrayList<>();

		this.addChangeListener(l -> {
			var previousModel = this.getCurrentDocument();
			if (this.getSelectedIndex() > -1) {
				currentDocument = getDocument(getSelectedIndex());
			} else {
				currentDocument = null;
			}

			notifyListenersDocumentChanged(previousModel, currentDocument);
		});
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		currentDocument = new DefaultSingleDocumentModel(null, "");
		currentDocument.addSingleDocumentListener(new SingleDocumentListener() {

			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				ImageIcon icon = model.isModified() ? getRedDiskette() : getGreenDiskette();
				DefaultMultipleDocumentModel.this.setIconAt(documents.indexOf(model), icon);
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				String title = model.getFilePath().getFileName().toString();
				title = title == null ? "unnamed" : title;
				DefaultMultipleDocumentModel.this.setTitleAt(documents.indexOf(model), title);
				notifyListenersDocumentChanged(model, model);
			}
		});

		documents.add(currentDocument);

		this.addTab("unnamed", getRedDiskette(), new JPanel().add(new JScrollPane(currentDocument.getTextComponent())));
		this.setSelectedIndex(documents.indexOf(currentDocument));

		notifyListenersAdd(currentDocument);
		return currentDocument;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		if (path == null) {
			throw new NullPointerException("Given path can't be null " + path);
		}

		if (documents.stream().anyMatch(d -> path.equals(d.getFilePath()))) {
			SingleDocumentModel doc = documents.stream().filter(d -> d.getFilePath().equals(path)).findFirst().get();
			this.setSelectedIndex(documents.indexOf(doc));

			currentDocument = doc;
			return doc;
		}

		if (!Files.isReadable(path)) {
			throw new IllegalArgumentException("Datoteka " + path.toAbsolutePath() + "is not readable!");
		}
		byte[] okteti;
		try {
			okteti = Files.readAllBytes(path);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Pogreška prilikom čitanja datoteke " + path.toAbsolutePath() + ".");
		}

		String content = new String(okteti, StandardCharsets.UTF_8);

		SingleDocumentModel doc = new DefaultSingleDocumentModel(path, content);
		documents.add(doc);
		this.addTab(path.getFileName().toString(), getGreenDiskette(), doc.getTextComponent());
		this.setSelectedIndex(documents.indexOf(doc));

		currentDocument = doc;
		currentDocument.addSingleDocumentListener(new SingleDocumentListener() {

			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				ImageIcon icon = model.isModified() ? getRedDiskette() : getGreenDiskette();
				DefaultMultipleDocumentModel.this.setIconAt(documents.indexOf(model), icon);
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				String title = model.getFilePath().getFileName().toString();
				title = title == null ? "unnamed" : title;
				DefaultMultipleDocumentModel.this.setTitleAt(documents.indexOf(model), title);
			}
		});
		return doc;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		Path path = newPath == null ? model.getFilePath() : newPath;
		boolean alreadyOpen = documents.stream().anyMatch(d -> path.equals(d.getFilePath()) && !d.equals(model));

		if (alreadyOpen) {
			throw new IllegalArgumentException("Već otvorena datoteka");
		}

		byte[] podatci = getCurrentDocument().getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(path, podatci);
		} catch (IOException e1) {
			throw new IllegalArgumentException("Pogreška prilikom zapisivanja datoteke!");
		}

		getCurrentDocument().setFilePath(path);
		getCurrentDocument().setModified(false);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = documents.indexOf(model);
		this.remove(index);
		documents.remove(model);
		if (index >= 1) {
			this.currentDocument = this.getDocument(index - 1);
			this.setSelectedIndex(index - 1);
		} else if (documents.size() > 0) {
			this.currentDocument = this.getDocument(0);
			this.setSelectedIndex(0);
		} else {
			this.createNewDocument();
		}
		notifyListenersRemove(model);

	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}

	private ImageIcon getGreenDiskette() {
		byte[] bytes = null;

		try (InputStream is = this.getClass().getResourceAsStream("icons/greenDisk.png")) {
			if (is == null) {
				throw new IllegalArgumentException("Ne valja nesto");
			}

			bytes = is.readAllBytes();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ImageIcon(bytes);
	}

	private ImageIcon getRedDiskette() {
		byte[] bytes = null;

		try (InputStream is = this.getClass().getResourceAsStream("icons/redDisk.png")) {
			if (is == null) {
				throw new IllegalArgumentException("Ne valja nesto");
			}

			bytes = is.readAllBytes();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ImageIcon(bytes);
	}

	public void notifyListenersDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
		for (var l : listeners) {
			l.currentDocumentChanged(previousModel, currentModel);
		}
	}

	private void notifyListenersAdd(SingleDocumentModel model) {
		for (var l : listeners) {
			l.documentAdded(model);
		}
	}

	private void notifyListenersRemove(SingleDocumentModel model) {
		for (var l : listeners) {
			l.documentRemoved(model);
		}
	}
}
