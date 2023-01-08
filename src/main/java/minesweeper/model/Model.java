package minesweeper.model;

import java.beans.PropertyChangeListener;

public class Model {
    private Board board;

    public void startGame(int rows, int columns, int mines, PropertyChangeListener listener) {
        board = new Board(rows, columns, mines, listener);
    }

    public Board getBoard() {
        return board;
    }
}
