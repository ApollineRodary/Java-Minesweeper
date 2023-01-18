package minesweeper.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MultiplayerGame {
    private final PropertyChangeSupport propertyChangeSupport;
    private final int players;
    private int currentPlayer;

    public MultiplayerGame(int players, PropertyChangeListener listener) {
        propertyChangeSupport = new PropertyChangeSupport(this);
        propertyChangeSupport.addPropertyChangeListener(listener);
        this.players = players;
        currentPlayer = 0;
    }

    public void nextPlayer() {
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
}
