package minesweeper.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.Random;

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
    private static boolean isHardcore = false;
    private static boolean isMultiplayer = false;
    private static int players = 1;

    public final static int MIN_ROWS = 5;
    public final static int MAX_ROWS = 40;
    public final static int MIN_COLUMNS = 5;
    public final static int MAX_COLUMNS = 60;
    public final static int MIN_MINES = 5;
    public final static int MAX_MINES = 200;
    public final static double HARDCORE_MODE_MIN_MINE_DENSITY = 0.15;
    public final static double HARDCORE_MODE_MAX_MINE_DENSITY = 0.30;
    public final static int MULTIPLAYER_MIN_PLAYERS = 2;
    public final static int MULTIPLAYER_MAX_PLAYERS = 10;

    public static void start() {
        View view = new View(new Frame(rows, columns, isHardcore));
        Model model = new Model();
        new Controller(view, model);
    }

    public void restart() {
        if (isHardcore) {
            mines = hardcoreMineCount();
        }
        model.startGame(getOptions(), view);
        view.getFrame().getPanel().reset(rows, columns);
        view.getFrame().getMineCounter().setEnabled(!isHardcore);
        view.getFrame().pack();
    }

    private Controller(View view, Model model) {
        this.model = model;
        this.view = view;
        model.startGame(getOptions(), view);
        view.getFrame().getPanel().addMouseListener(this);
        view.getFrame().getRestartButton().addActionListener(this);
        view.getFrame().getOptionsButton().addActionListener(this);
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
            restart();
        } else if (e.getSource() == view.getFrame().getOptionsButton()) {
            // Open options menu; leave current options as defaults but do not reveal the number of mines in hardcore
            Map<Option, Object> options = view.openOptions(isHardcore?MIN_MINES:mines, rows, columns);
            
            if (options == null) {
                // If the operation was canceled by the user, do not start a new game
                return;
            }
            
            rows = (int) options.get(Option.ROWS);
            columns = (int) options.get(Option.COLUMNS);
            mines = (int) options.get(Option.MINES);
            isHardcore = (boolean) options.get(Option.HARDCORE_MODE);
            isMultiplayer = (boolean) options.get(Option.MULTIPLAYER_MODE);
            players = isMultiplayer?((int) options.get(Option.PLAYERS)):1;

            restart();
        }
    }

    private int hardcoreMineCount() {
        int minMines = (int) (rows*columns*HARDCORE_MODE_MIN_MINE_DENSITY);
        int maxMines = (int) (rows*columns*HARDCORE_MODE_MAX_MINE_DENSITY);
        return new Random().nextInt(minMines, maxMines);
    }

    private Map<Option, Object> getOptions() {
        return Map.ofEntries(
            Map.entry(Option.ROWS, rows),
            Map.entry(Option.COLUMNS, columns),
            Map.entry(Option.MINES, mines),
            Map.entry(Option.HARDCORE_MODE, isHardcore),
            Map.entry(Option.MULTIPLAYER_MODE, isMultiplayer),
            Map.entry(Option.PLAYERS, players)
        );
    }
}
