package events;

import java.util.EventObject;

public class NewRecordEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public NewRecordEvent(Object source) {
        super(source);
    }
}
