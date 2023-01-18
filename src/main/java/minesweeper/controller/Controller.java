package minesweeper.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.Random;

import minesweeper.model.Cell;
import minesweeper.model.GameState;
import minesweeper.model.Model;
import minesweeper.model.Scoreboard;
import minesweeper.view.Panel;
import minesweeper.view.View;

public class Controller implements MouseListener, ActionListener, PropertyChangeListener {
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
    public final static int SCORE_ON_CELL_DISCOVERY = 1;
    public final static int SCORE_ON_DEFEAT = -5;
    public final static int SCORE_ON_CORRECT_FLAG = 5;

    public static void start() {
        /* Starts the application, creates a new model and a new view */
        View view = new View(getOptions());
        Model model = new Model();
        new Controller(view, model);
    }

    private Controller(View view, Model model) {
        this.model = model;
        this.view = view;
        model.startGame(getOptions(), this);
        view.addListener(this);
    }

    public void play() {
        /* Fired at each interaction by a player, checks for victory and updates the current player in multiplayer */
        model.getBoard().checkVictory();
        if (model.getIsMultiplayer()) {
            model.getMultiplayerGame().nextPlayer();
        }
    }

    public void restart() {
        /* Resets view and creates a new game */
        if (isHardcore) {
            mines = hardcoreMineCount();
        }
        model.startGame(getOptions(), this);
        view.startGame(getOptions());
    }

    private static int hardcoreMineCount() {
        /* Generates a random number of mines in hardcore mode */
        int minMines = (int) (rows*columns*HARDCORE_MODE_MIN_MINE_DENSITY);
        int maxMines = (int) (rows*columns*HARDCORE_MODE_MAX_MINE_DENSITY);
        return new Random().nextInt(minMines, maxMines);
    }

    private static Map<Option, Object> getOptions() {
        return Map.ofEntries(
            Map.entry(Option.ROWS, rows),
            Map.entry(Option.COLUMNS, columns),
            Map.entry(Option.MINES, mines),
            Map.entry(Option.HARDCORE_MODE, isHardcore),
            Map.entry(Option.MULTIPLAYER_MODE, isMultiplayer),
            Map.entry(Option.PLAYERS, players)
        );
    }

    /* Interactions with the main panel */
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
        int player = isMultiplayer?model.getMultiplayerGame().getCurrentPlayer():0;

        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                model.getBoard().reveal(row, column);
                play();
                break;
            
            case MouseEvent.BUTTON3:
                model.getBoard().toggleFlag(row, column, player);
                play();
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    /* Interactions with buttons */

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
    
    /* Notifications from the model */

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        switch (e.getPropertyName()) {
            case "knownCells": {
                // Update view
                IndexedPropertyChangeEvent ei = (IndexedPropertyChangeEvent) e;
                int row = ei.getIndex()/columns;
                int column = ei.getIndex()%columns;
                Cell cell = (Cell) ei.getNewValue();
                view.updateKnownCells(cell, row, column);
                
                // Update scores in multiplayer
                if (!isMultiplayer || model.getBoard().getGameState() != GameState.IN_GAME) {
                    break;
                }
                int currentPlayer = model.getMultiplayerGame().getCurrentPlayer();
                Scoreboard scoreboard = model.getMultiplayerGame().getScoreboard();
                switch (cell) {
                    case MINE, FLAGGED, HIDDEN, NEIGHBORS_0:
                        break;
                    default:
                        scoreboard.addScore(currentPlayer, SCORE_ON_CELL_DISCOVERY);
                        break;
                }
                break;
            }
            
            case "minesRemaining":
                view.updateRemainingMines((int) e.getNewValue());
                break;

            case "currentPlayer": {
                int player = (int) e.getNewValue();
                int score = model.getMultiplayerGame().getScoreboard().getScore(player);
                view.updateCurrentPlayer(player, score);
                break;
            }
            
            case "gameState":
                GameState state = (GameState) e.getNewValue();
                if (isMultiplayer && (state == GameState.DEFEAT || state == GameState.VICTORY)) {
                    Scoreboard scoreboard = model.getMultiplayerGame().getScoreboard();
                    if (state == GameState.DEFEAT) {
                        // Add penalty for clicking on a mine
                        int currentPlayer = model.getMultiplayerGame().getCurrentPlayer();
                        scoreboard.addScore(currentPlayer, SCORE_ON_DEFEAT);
                    }
                    
                    for (int row=0; row<rows; row++) {
                        for (int column=0; column<columns; column++) {
                            int player = model.getBoard().getFlagPlacer(row, column);
                            if (player >= 0) {
                                scoreboard.addScore(player, SCORE_ON_CORRECT_FLAG);
                            }
                        }
                    }
                    view.updateGameState(state, scoreboard);
                } else {
                    view.updateGameState(state);
                }
                break;
        }
    }
}
