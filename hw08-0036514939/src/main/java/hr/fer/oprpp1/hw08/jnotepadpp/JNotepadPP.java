package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.text.Document;
import javax.swing.text.Element;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizationProvider;

public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;
	private DefaultMultipleDocumentModel models;
	private FormLocalizationProvider flp;
	private Actions actions;

	private JPanel statusBar;
	private int length;
	private int ln;
	private int col;
	private int sel;
	private JLabel jtf1;
	private JLabel jtf2;
	private JLabel jtf3;
	private JMenuItem toLower;
	private JMenuItem toUpper;
	private JMenuItem invert;

	private Timer timer;

	public JNotepadPP() {
		super();
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				actions.getExitAction().actionPerformed(null);
			}
		});
		setLocation(0, 0);
		setSize(600, 600);
		setTitle("JNotepad++");

		initGUI();
	}

	private void initGUI() {
		models = new DefaultMultipleDocumentModel();
		actions = new Actions(this, flp);

		statusBar = new JPanel(new GridLayout(1, 3));
		statusBar.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.gray));
		jtf1 = new JLabel("length:0");
		jtf1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.gray));
		jtf2 = new JLabel("Ln:1    Col:1    Sel:0");
		jtf2.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.gray));
		jtf3 = new JLabel("Sat");
		jtf3.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));

		timer = new Timer(500, l -> {
			jtf3.setText(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
		});
		timer.start();

		statusBar.add(jtf1);
		statusBar.add(jtf2);
		statusBar.add(jtf3);

		// Make adapter
		models.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {
				// Do nothing
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
				if (model == null) {
					return;
				}
				model.getTextComponent().getCaret().addChangeListener(l -> {
					updateStatusBar(model);
					updateEnabled(model);
				});
			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				JNotepadPP.this.setTitle();
				if (currentModel != null) {
					currentModel.getTextComponent().getCaret().addChangeListener(l -> {
						updateStatusBar(currentModel);
						updateEnabled(currentModel);
					});
				}
				updateStatusBar(currentModel);
				updateEnabled(currentModel);
			}
		});

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(models, BorderLayout.CENTER);
		panel.add(statusBar, BorderLayout.PAGE_END);

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panel, BorderLayout.CENTER);

		createMenu();
		createToolBar();

		models.createNewDocument();
	}

	private void updateStatusBar(SingleDocumentModel model) {
		if (model == null) {
			ln = col = 1;
			sel = length = 0;
		} else {
			JTextArea editor = model.getTextComponent();
			Document doc = model.getTextComponent().getDocument();
			Element root = doc.getDefaultRootElement();
			ln = root.getElementIndex(editor.getCaret().getDot()) + 1;
			col = editor.getCaret().getDot() - root.getElement(ln - 1).getStartOffset() + 1;
			sel = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			length = doc.getLength();
		}
		jtf1.setText("length:" + length);
		jtf2.setText("Ln:" + ln + "    Col:" + col + "    Sel:" + sel);
	}

	private void updateEnabled(SingleDocumentModel model) {
		if (model == null || sel == 0) {
			toUpper.setEnabled(false);
			toLower.setEnabled(false);
			invert.setEnabled(false);
		} else {
			toUpper.setEnabled(true);
			toLower.setEnabled(true);
			invert.setEnabled(true);
		}
	}

	private void setTitle() {
		if (models.getNumberOfDocuments() <= 0) {
			JNotepadPP.this.setTitle("JNotepad++");
		} else if (models.getSelectedIndex() >= 0) {
			if (models.getDocument(models.getSelectedIndex()).getFilePath() == null) {
				JNotepadPP.this.setTitle("(unnamed) - JNotepad++");
			} else {
				JNotepadPP.this.setTitle(
						models.getDocument(models.getSelectedIndex()).getFilePath().toAbsolutePath().toString()
								+ " - JNotepad++");
			}
		}
	}

	public MultipleDocumentModel getModels() {
		return this.models;
	}

	public Timer getTimer() {
		return this.timer;
	}

	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu(actions.getFileAction());
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(actions.getCreateNewDocumentAction()));
		fileMenu.add(new JMenuItem(actions.getOpenDocumentAction()));
		fileMenu.add(new JMenuItem(actions.getSaveDocumentAction()));
		fileMenu.add(new JMenuItem(actions.getSaveAsDocumentAction()));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(actions.getCloseDocumentAction()));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(actions.getExitAction()));

		JMenu editMenu = new JMenu(actions.getEditAction());
		menuBar.add(editMenu);

		editMenu.add(new JMenuItem(actions.getCopyAction()));
		editMenu.add(new JMenuItem(actions.getCutAction()));
		editMenu.add(new JMenuItem(actions.getPasteAction()));
		editMenu.add(new JMenuItem(actions.getStatsAction()));

		JMenu toolsMenu = new JMenu(actions.getToolsAction());
		menuBar.add(toolsMenu);

		toUpper = new JMenuItem(actions.getToUpperCaseAction());
		toolsMenu.add(toUpper);
		toLower = new JMenuItem(actions.getToLowerCaseAction());
		toolsMenu.add(toLower);
		invert = new JMenuItem(actions.getInvertCaseAction());
		toolsMenu.add(invert);

		JMenu sortMenu = new JMenu("Sort");
		toolsMenu.add(sortMenu);

		sortMenu.add(new JMenuItem(actions.getAscendingAction()));
		sortMenu.add(new JMenuItem(actions.getDescendingAction()));

		toolsMenu.add(new JMenuItem(actions.getUniqueAction()));

		JMenu languageMenu = new JMenu(actions.getLanguageAction());
		menuBar.add(languageMenu);

		languageMenu.add(new JMenuItem(actions.getHrAction()));
		languageMenu.add(new JMenuItem(actions.getEnAction()));
		languageMenu.add(new JMenuItem(actions.getDeAction()));

		this.setJMenuBar(menuBar);
	}

	private void createToolBar() {
		JToolBar toolBar = new JToolBar("Alati");
		toolBar.setFloatable(true);

		toolBar.add(new JButton(actions.getOpenDocumentAction()));
		toolBar.add(new JButton(actions.getCreateNewDocumentAction()));
		toolBar.add(new JButton(actions.getSaveDocumentAction()));
		toolBar.add(new JButton(actions.getSaveAsDocumentAction()));
		toolBar.addSeparator();
		toolBar.add(new JButton(actions.getCutAction()));
		toolBar.add(new JButton(actions.getCopyAction()));
		toolBar.add(new JButton(actions.getPasteAction()));
		toolBar.add(new JButton(actions.getStatsAction()));
		toolBar.addSeparator();
		toolBar.add(new JButton(actions.getCloseDocumentAction()));
		toolBar.addSeparator();
		toolBar.add(new JButton(actions.getExitAction()));

		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new JNotepadPP().setVisible(true);
			}
		});
	}
}
