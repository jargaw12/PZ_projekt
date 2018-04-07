package events;

import java.util.EventObject;
import java.util.Locale;

public class ChangeLanguageEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    private Locale locale;

    public ChangeLanguageEvent(Object source,Locale locale) {
        super(source);
        this.locale=locale;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
