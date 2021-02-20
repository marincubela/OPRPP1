package hr.fer.oprpp1.hw08.jnotepadpp.localization;

public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	private ILocalizationProvider lp;
	private boolean connected;

	public LocalizationProviderBridge(ILocalizationProvider lp) {
		this.lp = lp;
		lp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				LocalizationProviderBridge.this.fire();
			}
		});
	}

	private ILocalizationListener l = new ILocalizationListener() {

		@Override
		public void localizationChanged() {

		}
	};

	public void disconnect() {
		if (!connected) {
			return;
		}
		this.removeLocalizationListener(l);
	}

	public void connect() {
		if (connected) {
			return;
		}

		this.addLocalizationListener(l);
	}

	@Override
	public String getString(String key) {
		return lp.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return lp.getCurrentLanguage();
	}
}
