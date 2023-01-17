package minesweeper.model;

import java.beans.PropertyChangeListener;

public class Model {
    private Board board;

    public void startGame(int rows, int columns, int mines, boolean isHardcore, PropertyChangeListener listener) {
        board = new Board(rows, columns, mines, isHardcore, listener);
    }

    public Board getBoard() {
        return board;
    }
}
