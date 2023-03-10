package minesweeper.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MultiplayerGame {
    private final PropertyChangeSupport propertyChangeSupport;
    private final int players;
    private Scoreboard scoreboard;
    private int currentPlayer;

    public MultiplayerGame(int players, PropertyChangeListener listener) {
        propertyChangeSupport = new PropertyChangeSupport(this);
        propertyChangeSupport.addPropertyChangeListener(listener);
        this.players = players;
        currentPlayer = 0;
        
        scoreboard = new Scoreboard(players);
    }

    public void nextPlayer() {
        /* Updates the current player */
        int newPlayer = currentPlayer;
        newPlayer++;
        if (newPlayer==players) {
            newPlayer = 0;
        }
        propertyChangeSupport.firePropertyChange("currentPlayer", currentPlayer, newPlayer);
        currentPlayer = newPlayer;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
