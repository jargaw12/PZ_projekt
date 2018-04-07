package events;
import java.util.EventObject;

public class WeatherEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    String warning;
    public WeatherEvent(Object source, String warning) {
        super(source);
        this.warning=warning;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }
}
