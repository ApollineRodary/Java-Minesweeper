package minesweeper.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import minesweeper.board.Board;
import minesweeper.frame.Frame;
import minesweeper.frame.Panel;

public class Controller implements MouseListener {
    Board board;

    public static void start() {
        int rows = 20;
        int columns = 20;

        new Controller(new Frame(rows, columns), new Board(rows, columns, 30));
    }

    private Controller(Frame frame, Board board) {
        this.board = board;
        frame.getPanel().addMouseListener(this);
        board.addPropertyChangeListener(frame);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    
    @Override
    public void mouseEntered(MouseEvent e) {}
    
    @Override
    public void mouseExited(MouseEvent e) {}
    
    @Override
    public void mousePressed(MouseEvent e) {
        int row = e.getY() / Panel.CELL_HEIGHT;
        int column = e.getX() / Panel.CELL_WIDTH;
        board.reveal(row, column);
    }

    @Override
    public void mouseReleased(MouseEvent e) {}
}
