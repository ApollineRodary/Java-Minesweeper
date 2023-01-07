package minesweeper.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import minesweeper.model.Board;
import minesweeper.model.Model;
import minesweeper.view.Frame;
import minesweeper.view.Panel;
import minesweeper.view.View;

public class Controller implements MouseListener {
    Model model;

    public static void start() {
        int rows = 20;
        int columns = 20;
        int mines = 30;

        Frame frame = new Frame(rows, columns);
        Board board = new Board(rows, columns, mines);
        new Controller(new View(frame), new Model(board));
    }

    private Controller(View view, Model model) {
        this.model = model;
        view.getFrame().getPanel().addMouseListener(this);
        model.getBoard().addPropertyChangeListener(view);
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
                model.getBoard().reveal(row, column);
                break;
            
            case MouseEvent.BUTTON3:
                model.getBoard().toggleFlag(row, column);
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}
}
