package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import javax.swing.JLabel;

public abstract class LJLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	private String key;

	public LJLabel(String key, ILocalizationProvider lp) {
		this.key = key;
		this.setText(lp.getString(key));

		lp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				LJLabel.this.setText(lp.getString(LJLabel.this.key));
			}
		});
	}

}
