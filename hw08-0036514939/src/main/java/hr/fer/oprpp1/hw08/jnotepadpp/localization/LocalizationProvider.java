package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {
	public static final LocalizationProvider INSTANCE = new LocalizationProvider();

	private String language;
	private ResourceBundle bundle;

	private LocalizationProvider() {
		this.setLanguage("en");
	}

	public static LocalizationProvider getInstance() {
		return INSTANCE;
	}

	public void setLanguage(String language) {
		this.language = language;
		this.bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.localization.prijevodi",
				Locale.forLanguageTag(this.language));
		fire();
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}
}
