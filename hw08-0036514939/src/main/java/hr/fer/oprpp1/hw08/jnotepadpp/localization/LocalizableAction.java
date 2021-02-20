package hr.fer.oprpp1.hw08.jnotepadpp.localization;
import javax.swing.AbstractAction;

public abstract class LocalizableAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private String key;

	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.key = key;
		this.putValue(NAME, lp.getString(key));

		lp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				LocalizableAction.this.putValue(NAME, lp.getString(LocalizableAction.this.key));
			}
		});
	}
}
