package hr.fer.oprpp1.hw08.jnotepadpp.localization;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	private List<ILocalizationListener> listeners;

	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}

	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}

	public void fire() {
		for (var l : listeners) {
			l.localizationChanged();
		}
	}
}
