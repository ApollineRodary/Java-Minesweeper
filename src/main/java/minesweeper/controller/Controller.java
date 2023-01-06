package minesweeper.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import minesweeper.board.Board;
import minesweeper.view.Frame;
import minesweeper.view.Panel;
import minesweeper.view.View;

public class Controller implements MouseListener {
    Board board;

    public static void start() {
        int rows = 20;
        int columns = 20;

        Frame frame = new Frame(rows, columns);
        new Controller(new View(frame), new Board(rows, columns, 30));
    }

    private Controller(View view, Board board) {
        this.board = board;
        view.getFrame().getPanel().addMouseListener(this);
        board.addPropertyChangeListener(view);
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

        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                board.reveal(row, column);
                break;
            
            case MouseEvent.BUTTON3:
                board.toggleFlag(row, column);
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}
}
