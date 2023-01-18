package minesweeper.model;

import java.beans.PropertyChangeListener;
import java.util.Map;

import minesweeper.controller.Option;

public class Model {
    private Board board;
    private MultiplayerGame multiplayerGame;
    private boolean isMultiplayer;

    public void startGame(Map<Option, Object> options, PropertyChangeListener listener) {
        board = new Board(options, listener);
        isMultiplayer = (boolean) options.get(Option.MULTIPLAYER_MODE);
        if (isMultiplayer) {
            multiplayerGame = new MultiplayerGame((int) options.get(Option.PLAYERS), listener);
        }
    }

    public Board getBoard() {
        return board;
    }

    public MultiplayerGame getMultiplayerGame() {
        return multiplayerGame;
    }

    public boolean getIsMultiplayer() {
        return isMultiplayer;
    }
}
