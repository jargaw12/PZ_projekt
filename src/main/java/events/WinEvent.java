package events;

import panels.game.Player;

import java.util.EventObject;
import java.util.Locale;

public class WinEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    private Locale locale;
    private Player winner;
    public WinEvent(Object source, Player winner, Locale locale) {
        super(source);
        this.locale=locale;
        this.winner=winner;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
