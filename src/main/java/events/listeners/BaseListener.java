package events.listeners;
import events.NewRecordEvent;

public interface BaseListener {
    void celebrateNewRecord(NewRecordEvent newRecord);
}
