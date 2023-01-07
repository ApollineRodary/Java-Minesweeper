package minesweeper.model;

public class Model {
    Board board;

    public Model(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }
}
