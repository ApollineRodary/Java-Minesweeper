package minesweeper.model;

import java.beans.PropertyChangeListener;
import java.util.Map;

import minesweeper.controller.Option;

public class Model {
    private Board board;

    public void startGame(Map<Option, Object> options, PropertyChangeListener listener) {
        board = new Board(options, listener);
    }

    public Board getBoard() {
        return board;
    }
}
