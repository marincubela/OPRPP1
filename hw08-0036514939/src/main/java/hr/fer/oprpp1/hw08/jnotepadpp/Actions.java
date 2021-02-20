package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizationProvider;

public class Actions {
	@SuppressWarnings("unused")
	private ILocalizationProvider flp;
	@SuppressWarnings("unused")
	private JNotepadPP frame;
	private MultipleDocumentModel models;
	private String buffer;
	private Action openDocumentAction;
	private Action createNewDocumentAction;
	private Action saveDocumentAction;
	private Action saveAsDocumentAction;
	private Action closeDocumentAction;
	private Action exitAction;
	private Action statsAction;
	private Action cutAction;
	private Action copyAction;
	private Action pasteAction;
	private Action toUpperCaseAction;
	private Action toLowerCaseAction;
	private Action invertCaseAction;
	private Action hrAction;
	private Action enAction;
	private Action deAction;
	private Action fileAction;
	private Action editAction;
	private Action languageAction;
	private Action toolsAction;
	private Action ascendingAction;
	private Action descendingAction;
	private Action uniqueAction;

	public Actions(JNotepadPP frame, ILocalizationProvider flp) {
		this.flp = flp;
		this.frame = frame;
		models = frame.getModels();

		openDocumentAction = new LocalizableAction("open", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Open file");
				if (fc.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				File fileName = fc.getSelectedFile();
				Path filePath = fileName.toPath();

				if (!Files.isReadable(filePath)) {
					JOptionPane.showMessageDialog(frame, "Datoteka " + fileName.getAbsolutePath() + " ne postoji!",
							"Pogreška", JOptionPane.ERROR_MESSAGE);
					return;
				}

				try {
					models.loadDocument(filePath);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame,
							"Pogreška prilikom čitanja datoteke " + fileName.getAbsolutePath() + ".", "Pogreška",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		};

		createNewDocumentAction = new LocalizableAction("new", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				models.createNewDocument();
			}

		};

		saveDocumentAction = new LocalizableAction("save", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Path path = models.getCurrentDocument().getFilePath();

				if (path == null) {
					JFileChooser jfc = new JFileChooser() {
						private static final long serialVersionUID = 1L;

						public void approveSelection() {
							if (getSelectedFile().exists()) {
								if (JOptionPane.showConfirmDialog(frame,
										"Datoteka već postoji, bit će prepisana novom datotekom. Jeste li sigurni da to želite?") == JOptionPane.OK_OPTION) {
									super.approveSelection();
								}
							} else {
								super.approveSelection();
							}
						}

					};

					jfc.setDialogTitle("Save document");
					if (jfc.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
						JOptionPane.showMessageDialog(frame, "Ništa nije snimljeno.", "Upozorenje",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					path = jfc.getSelectedFile().toPath();
				}

				try {
					models.saveDocument(models.getCurrentDocument(), path);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame,
							"Pogreška prilikom zapisivanja datoteke " + path.toFile().getAbsolutePath()
									+ ".\nPažnja: nije jasno u kojem je stanju datoteka na disku!",
							"Pogreška", JOptionPane.ERROR_MESSAGE);
					return;
				}

				JOptionPane.showMessageDialog(frame, "Datoteka je snimljena.", "Informacija",
						JOptionPane.INFORMATION_MESSAGE);
			}

		};

		saveAsDocumentAction = new LocalizableAction("saveAs", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Path path = null;

				if (path == null) {
					JFileChooser jfc = new JFileChooser() {
						private static final long serialVersionUID = 1L;

						public void approveSelection() {
							if (getSelectedFile().exists()) {
								if (JOptionPane.showConfirmDialog(frame,
										"Datoteka već postoji, bit će prepisana novom datotekom. Jeste li sigurni da to želite?") == JOptionPane.OK_OPTION) {
									super.approveSelection();
								}
							} else {
								super.approveSelection();
							}
						}

					};
					jfc.setDialogTitle("Save as document");
					if (jfc.showDialog(frame, "Save as") != JFileChooser.APPROVE_OPTION) {
						JOptionPane.showMessageDialog(frame, "Ništa nije snimljeno.", "Upozorenje",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					path = jfc.getSelectedFile().toPath();
				}

				try {
					models.saveDocument(models.getCurrentDocument(), path);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame,
							"Pogreška prilikom zapisivanja datoteke " + path.toFile().getAbsolutePath()
									+ ".\nPažnja: nije jasno u kojem je stanju datoteka na disku!",
							"Pogreška", JOptionPane.ERROR_MESSAGE);
					return;
				}

				JOptionPane.showMessageDialog(frame, "Datoteka je snimljena.", "Informacija",
						JOptionPane.INFORMATION_MESSAGE);
			}

		};

		closeDocumentAction = new LocalizableAction("close", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				var model = models.getCurrentDocument();

				if (model == null) {
					JOptionPane.showMessageDialog(frame, "Nije moguće zatvoriti datoteku jer nijedna nije otvorena.");
					return;
				}

				if (model.isModified()) {
					int res = JOptionPane.showConfirmDialog(frame,
							"Datoteka nije spremljena. Jeste li sigurni da ju želite zatvoriti?");
					if (res != JOptionPane.OK_OPTION) {
						return;
					}
				}
				models.closeDocument(model);
			}

		};

		exitAction = new LocalizableAction("exit", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				for (SingleDocumentModel model : models) {
					if (model.isModified()) {
						String fileName = model.getFilePath() == null ? "unnamed"
								: model.getFilePath().getFileName().toString();
						String message = "Datoteka " + fileName + " nije spremljena. Želite li ju spremiti?";
						int res = JOptionPane.showConfirmDialog(frame, message, "Upozorenje",
								JOptionPane.YES_NO_CANCEL_OPTION);

						if (res == JOptionPane.YES_OPTION) {
							saveDocumentAction.actionPerformed(null);
						} else if (res == JOptionPane.CANCEL_OPTION) {
							return;
						}
					}
				}

				frame.getTimer().stop();
				frame.dispose();
			}
		};

		statsAction = new LocalizableAction("stats", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (models.getCurrentDocument() == null) {
					return;
				}
				JTextArea editor = models.getCurrentDocument().getTextComponent();
				Document doc = editor.getDocument();
				int x, y, z;

				x = doc.getLength();
				y = editor.getText().replaceAll("\\s", "").length();
				z = (int) editor.getText().lines().count();

				JOptionPane.showMessageDialog(frame,
						"Your document has " + x + " characters, " + y + " non-blank characters and " + z + " lines.");
			}
		};

		cutAction = new LocalizableAction("cut", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (models.getCurrentDocument() == null) {
					return;
				}
				JTextArea editor = models.getCurrentDocument().getTextComponent();
				Document doc = editor.getDocument();

				int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
				if (len == 0) {
					return;
				}

				int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());

				try {
					buffer = doc.getText(offset, len);
					doc.remove(offset, len);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		};

		copyAction = new LocalizableAction("copy", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (models.getCurrentDocument() == null) {
					return;
				}
				JTextArea editor = models.getCurrentDocument().getTextComponent();
				Document doc = editor.getDocument();

				int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
				if (len == 0) {
					return;
				}

				int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());

				try {
					buffer = doc.getText(offset, len);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		};

		pasteAction = new LocalizableAction("paste", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (models.getCurrentDocument() == null) {
					return;
				}
				JTextArea editor = models.getCurrentDocument().getTextComponent();
				Document doc = editor.getDocument();

				try {
					doc.insertString(editor.getCaretPosition(), buffer, null);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		};

		toUpperCaseAction = new LocalizableAction("upper", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (models.getCurrentDocument() == null) {
					return;
				}
				JTextArea editor = models.getCurrentDocument().getTextComponent();
				Document doc = editor.getDocument();

				int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
				if (len == 0) {
					return;
				}

				int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());

				try {
					String text = doc.getText(offset, len).toUpperCase();
					doc.remove(offset, len);
					doc.insertString(offset, text, null);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		};

		toLowerCaseAction = new LocalizableAction("lower", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (models.getCurrentDocument() == null) {
					return;
				}
				JTextArea editor = models.getCurrentDocument().getTextComponent();
				Document doc = editor.getDocument();

				int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
				if (len == 0) {
					return;
				}

				int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());

				try {
					String text = doc.getText(offset, len).toLowerCase();
					doc.remove(offset, len);
					doc.insertString(offset, text, null);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		};

		invertCaseAction = new LocalizableAction("invert", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (models.getCurrentDocument() == null) {
					return;
				}
				JTextArea editor = models.getCurrentDocument().getTextComponent();
				Document doc = editor.getDocument();

				int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
				if (len == 0) {
					return;
				}

				int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());

				try {
					String text = invertCase(doc.getText(offset, len));
					doc.remove(offset, len);
					doc.insertString(offset, text, null);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		};

		hrAction = new LocalizableAction("hr", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("hr");
			}
		};

		deAction = new LocalizableAction("de", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("de");
			}
		};

		enAction = new LocalizableAction("en", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("en");
			}
		};

		fileAction = new LocalizableAction("file", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
			}
		};

		editAction = new LocalizableAction("edit", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
			}
		};

		languageAction = new LocalizableAction("language", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
			}
		};

		toolsAction = new LocalizableAction("tools", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
			}
		};

		ascendingAction = new LocalizableAction("asc", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				if (models.getCurrentDocument() == null) {
					return;
				}
				List<String> lines = new ArrayList<>();
				int min, max, offset, length;

				JTextArea editor = models.getCurrentDocument().getTextComponent();
				Document doc = editor.getDocument();
				Element root = doc.getDefaultRootElement();
				int dot = editor.getCaret().getDot();
				int mark = editor.getCaret().getMark();

				if (dot == mark) {
					return;
				}

				if (dot > mark) {
					min = root.getElementIndex(mark);
					max = root.getElementIndex(dot);
				} else {
					min = root.getElementIndex(dot);
					max = root.getElementIndex(mark);
				}
				try {
					for (int i = min; i <= max; i++) {
						offset = root.getElement(i).getStartOffset();
						length = root.getElement(i).getEndOffset() - offset - 1;
						lines.add(doc.getText(offset, length));
					}
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}

				lines.sort(Collator.getInstance(new Locale(flp.getCurrentLanguage())));

				try {
					offset = root.getElement(min).getStartOffset();
					length = root.getElement(max).getEndOffset() - offset;
					doc.remove(offset, length - 1);
					doc.insertString(offset, lines.stream().collect(Collectors.joining("\n")), null);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		};

		descendingAction = new LocalizableAction("desc", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				if (models.getCurrentDocument() == null) {
					return;
				}
				List<String> lines = new ArrayList<>();
				int min, max, offset, length;

				JTextArea editor = models.getCurrentDocument().getTextComponent();
				Document doc = editor.getDocument();
				Element root = doc.getDefaultRootElement();
				int dot = editor.getCaret().getDot();
				int mark = editor.getCaret().getMark();

				if (dot == mark) {
					return;
				}

				if (dot > mark) {
					min = root.getElementIndex(mark);
					max = root.getElementIndex(dot);
				} else {
					min = root.getElementIndex(dot);
					max = root.getElementIndex(mark);
				}
				try {
					for (int i = min; i <= max; i++) {
						offset = root.getElement(i).getStartOffset();
						length = root.getElement(i).getEndOffset() - offset - 1;
						lines.add(doc.getText(offset, length));
					}
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}

				lines.sort(Collator.getInstance(new Locale(flp.getCurrentLanguage())));
				Collections.reverse(lines);

				try {
					offset = root.getElement(min).getStartOffset();
					length = root.getElement(max).getEndOffset() - offset;
					doc.remove(offset, length - 1);
					doc.insertString(offset, lines.stream().collect(Collectors.joining("\n")), null);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		};

		uniqueAction = new LocalizableAction("unique", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				if (models.getCurrentDocument() == null) {
					return;
				}
				Set<String> lines = new LinkedHashSet<>();
				int offset, length;

				JTextArea editor = models.getCurrentDocument().getTextComponent();
				Document doc = editor.getDocument();
				Element root = doc.getDefaultRootElement();

				try {
					for (int i = 0; i < root.getElementCount(); i++) {
						offset = root.getElement(i).getStartOffset();
						length = root.getElement(i).getEndOffset() - offset - 1;
						lines.add(doc.getText(offset, length));
					}
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}

				try {
					offset = 0;
					length = doc.getLength();
					doc.remove(offset, length);
					doc.insertString(offset, lines.stream().collect(Collectors.joining("\n")), null);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		};

		createActions();
	}

	private String invertCase(String str) {
		String res = "";

		for (char c : str.toCharArray()) {
			if (Character.isUpperCase(c)) {
				res += Character.toLowerCase(c);
			} else {
				res += Character.toUpperCase(c);
			}
		}

		return res;
	}

	public Action getOpenDocumentAction() {
		return openDocumentAction;
	}

	public Action getCreateNewDocumentAction() {
		return createNewDocumentAction;
	}

	public Action getSaveDocumentAction() {
		return saveDocumentAction;
	}

	public Action getSaveAsDocumentAction() {
		return saveAsDocumentAction;
	}

	public Action getCloseDocumentAction() {
		return closeDocumentAction;
	}

	public Action getExitAction() {
		return exitAction;
	}

	public Action getStatsAction() {
		return statsAction;
	}

	public Action getCutAction() {
		return cutAction;
	}

	public Action getCopyAction() {
		return copyAction;
	}

	public Action getPasteAction() {
		return pasteAction;
	}

	public Action getToUpperCaseAction() {
		return toUpperCaseAction;
	}

	public Action getToLowerCaseAction() {
		return toLowerCaseAction;
	}

	public Action getInvertCaseAction() {
		return invertCaseAction;
	}

	public Action getHrAction() {
		return hrAction;
	}

	public Action getEnAction() {
		return enAction;
	}

	public Action getDeAction() {
		return deAction;
	}

	public Action getFileAction() {
		return fileAction;
	}

	public Action getEditAction() {
		return editAction;
	}

	public Action getLanguageAction() {
		return languageAction;
	}

	public Action getToolsAction() {
		return toolsAction;
	}

	public Action getAscendingAction() {
		return ascendingAction;
	}

	public Action getDescendingAction() {
		return descendingAction;
	}

	public Action getUniqueAction() {
		return uniqueAction;
	}

	private void createActions() {
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to open existing file from disk.");

		createNewDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		createNewDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		createNewDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to create new file.");

		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to save current file to disk.");

		saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		saveAsDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveAsDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to save current file to disk.");

		closeDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		closeDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		closeDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to close the program.");

		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		exitAction.putValue(Action.SHORT_DESCRIPTION, "Used to close the app.");

		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		cutAction.putValue(Action.SHORT_DESCRIPTION, "Used to cut selected text from document.");

		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copyAction.putValue(Action.SHORT_DESCRIPTION, "Used to copy selected text from document.");

		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		pasteAction.putValue(Action.SHORT_DESCRIPTION, "Used to paste copied text.");

		statsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		statsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		statsAction.putValue(Action.SHORT_DESCRIPTION, "Used to show statistical information.");

		toUpperCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		toUpperCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		toUpperCaseAction.putValue(Action.SHORT_DESCRIPTION, "Used to change text to upper case.");

		toLowerCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		toLowerCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
		toLowerCaseAction.putValue(Action.SHORT_DESCRIPTION, "Used to change text to lower case.");

		invertCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		invertCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		invertCaseAction.putValue(Action.SHORT_DESCRIPTION, "Used to invert case of text.");

		hrAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control H"));
		hrAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_H);
		hrAction.putValue(Action.SHORT_DESCRIPTION, "Used to change language to croatian.");

		deAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control D"));
		deAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		deAction.putValue(Action.SHORT_DESCRIPTION, "Used to change language to german.");

		enAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		enAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		enAction.putValue(Action.SHORT_DESCRIPTION, "Used to change language to english.");

		ascendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		ascendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		ascendingAction.putValue(Action.SHORT_DESCRIPTION, "Used to sort selected lines ascending.");

		descendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control R"));
		descendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		descendingAction.putValue(Action.SHORT_DESCRIPTION, "Used to sort selected lines descending.");

		uniqueAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		uniqueAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);
		uniqueAction.putValue(Action.SHORT_DESCRIPTION, "Used to remove duplicate lines");

	}
}
