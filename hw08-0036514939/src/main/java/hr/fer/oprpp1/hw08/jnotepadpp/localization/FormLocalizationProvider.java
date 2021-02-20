package hr.fer.oprpp1.hw08.jnotepadpp.localization;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class FormLocalizationProvider extends LocalizationProviderBridge {

	public FormLocalizationProvider(ILocalizationProvider lp, JFrame frame) {
		super(lp);

		frame.addWindowListener(wa);
	}

	private WindowAdapter wa = new WindowAdapter() {

		@Override
		public void windowOpened(WindowEvent e) {
			FormLocalizationProvider.this.connect();
		}

		@Override
		public void windowClosed(WindowEvent e) {
			FormLocalizationProvider.this.disconnect();
		}
	};

}
