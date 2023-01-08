package minesweeper.model;

public class Model {
    private Board board;

    public void startGame(int rows, int columns, int mines) {
        board = new Board(rows, columns, mines);
    }

    public Board getBoard() {
        return board;
    }
}
