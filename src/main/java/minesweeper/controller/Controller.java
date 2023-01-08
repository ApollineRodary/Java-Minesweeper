package minesweeper.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import minesweeper.model.GameState;
import minesweeper.model.Model;
import minesweeper.view.Frame;
import minesweeper.view.Panel;
import minesweeper.view.View;

public class Controller implements MouseListener, ActionListener {
    private Model model;
    private View view;

    private static int rows = 20;
    private static int columns = 20;
    private static int mines = 30;

    public static void start() {
        View view = new View(new Frame(rows, columns));
        Model model = new Model();
        new Controller(view, model);
    }

    private Controller(View view, Model model) {
        this.model = model;
        this.view = view;
        model.startGame(rows, columns, mines, view);
        view.getFrame().getPanel().addMouseListener(this);
        view.getFrame().getRestartButton().addActionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    
    @Override
    public void mouseEntered(MouseEvent e) {}
    
    @Override
    public void mouseExited(MouseEvent e) {}
    
    @Override
    public void mousePressed(MouseEvent e) {
        if (model.getBoard().getGameState() != GameState.IN_GAME) return;

        int row = e.getY() / Panel.CELL_HEIGHT;
        int column = e.getX() / Panel.CELL_WIDTH;

        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                model.getBoard().reveal(row, column);
                model.getBoard().checkVictory();
                break;
            
            case MouseEvent.BUTTON3:
                model.getBoard().toggleFlag(row, column);
                model.getBoard().checkVictory();
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getFrame().getRestartButton()) {
            model.startGame(rows, columns, mines, view);
            view.getFrame().getPanel().reset();
        }
    }
}
