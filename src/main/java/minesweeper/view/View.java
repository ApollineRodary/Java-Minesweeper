package minesweeper.view;

import java.util.Map;

import javax.swing.JOptionPane;

import minesweeper.controller.Controller;
import minesweeper.controller.Option;
import minesweeper.model.Cell;
import minesweeper.model.GameState;
import minesweeper.model.Scoreboard;

public class View {
    private Frame frame;
    
    public View(Map<Option, Object> options) {
        this.frame = new Frame(options);
    }

    public Frame getFrame() {
        return frame;
    }

    public void startGame(Map<Option, Object> options) {
        /* Updates the view at the beginning of a game */
        frame.getPanel().reset((int) options.get(Option.ROWS), (int) options.get(Option.COLUMNS));
        frame.getMineCounter().setEnabled(!(boolean) options.get(Option.HARDCORE_MODE));
        if ((boolean) options.get(Option.MULTIPLAYER_MODE)) {
            frame.addPlayerLabel();
            frame.getPlayerLabel().setPlayer(0, 0);
        } else {
            frame.removePlayerLabel();
        }
        frame.pack();
    }

    public void addListener(Controller controller) {
        /* Sets controller as listener for interactions with buttons and with the panel */
        frame.getPanel().addMouseListener(controller);
        frame.getRestartButton().addActionListener(controller);
        frame.getOptionsButton().addActionListener(controller);
    }

    public void updateKnownCells(Cell cell, int row, int column) {
        frame.getPanel().setContents(cell, row, column);
        frame.getPanel().repaint();

        switch (cell) {
            case FLAGGED:
                SoundMixer.playSound(SoundEvent.PLACED_FLAG);
                break;
            case HIDDEN:
                SoundMixer.playSound(SoundEvent.REMOVED_FLAG);
                break;
            case MINE:
            case FALSE_FLAG:
                break;
            default:
                SoundMixer.playSound(SoundEvent.REVEALED_CELL);
                break;
        }
    }

    public void updateRemainingMines(int mines) {
        frame.getMineCounter().setValue(mines);
    }

    public void updateCurrentPlayer(int player, int score) {
        frame.getPlayerLabel().setPlayer(player, score);
    }

    public void updateGameState(GameState state) {
        frame.getRestartButton().updateIcon(state);
        if (state==GameState.DEFEAT) {
            announceDefeat();
        } else if (state==GameState.VICTORY) {
            announceVictory();
        }
    }

    public void updateGameState(GameState state, Scoreboard scoreboard) {
        frame.getRestartButton().updateIcon(state);
        if (state==GameState.DEFEAT || state==GameState.VICTORY) {
            announceScores(scoreboard);
        }
    }

    private void announceDefeat() {
        SoundMixer.playSound(SoundEvent.REVEALED_MINE);
        JOptionPane.showMessageDialog(frame, "You lose!", "Defeat", JOptionPane.PLAIN_MESSAGE);
    }

    private void announceVictory() {
        JOptionPane.showMessageDialog(frame, "You win!", "Victory", JOptionPane.PLAIN_MESSAGE);
    }

    private void announceScores(Scoreboard scoreboard) {
        int players = scoreboard.getPlayers();
        Object[] message = new Object[players+2];
        message[0] = "End of game!";
        for (int player=0; player<scoreboard.getPlayers(); player++) {
            message[player+1] = "Player " + (player+1) + ": " + scoreboard.getScore(player);
        }
        message[players+1] = "\nWinner: Player " + (scoreboard.getWinner()+1);
        JOptionPane.showMessageDialog(frame, message, "Scores", JOptionPane.PLAIN_MESSAGE);
    }

    public Map<Option, Object> openOptions(int mines, int rows, int columns) {
        return OptionsMenu.launch(frame, mines, rows, columns);
    }
}
