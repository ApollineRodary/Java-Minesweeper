package minesweeper.view;

import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import minesweeper.controller.Controller;
import minesweeper.controller.Option;
import minesweeper.model.Cell;
import minesweeper.model.GameState;

public class View implements PropertyChangeListener {
    private Frame frame;
    
    public View(Frame frame) {
        this.frame = frame;
    }
    public Frame getFrame() {
        return frame;
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        switch (e.getPropertyName()) {
            case "knownCells":
                IndexedPropertyChangeEvent ei = (IndexedPropertyChangeEvent) e;
                frame.getPanel().setContents((Cell) e.getNewValue(), ei.getIndex());
                frame.getPanel().repaint();
        
                switch ((Cell) e.getNewValue()) {
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
                break;
            
            case "minesRemaining":
                frame.getMineCounter().setValue((int) e.getNewValue());
                break;
            
            case "gameState":
                GameState state = (GameState) e.getNewValue();
                frame.getRestartButton().updateIcon(state);
                if (state==GameState.DEFEAT) announceDefeat();
                else if (state==GameState.VICTORY) announceVictory();
                break;
        }
    }

    private void announceDefeat() {
        SoundMixer.playSound(SoundEvent.REVEALED_MINE);
        JOptionPane.showMessageDialog(frame, "You lose!", "Defeat", JOptionPane.PLAIN_MESSAGE);
    }

    private void announceVictory() {
        JOptionPane.showMessageDialog(frame, "You win!", "Victory", JOptionPane.PLAIN_MESSAGE);
    }

    public Map<Option, Integer> openOptions(int mines, int rows, int columns) {
        JSpinner minesSpinner = new JSpinner(new SpinnerNumberModel(mines, Controller.MIN_MINES, Controller.MAX_MINES, 1));
        JSpinner rowsSpinner = new JSpinner(new SpinnerNumberModel(rows, Controller.MIN_ROWS, Controller.MAX_ROWS, 1));
        JSpinner columnsSpinner = new JSpinner(new SpinnerNumberModel(columns, Controller.MIN_COLUMNS, Controller.MAX_COLUMNS, 1));
        Object[] message = {"Mines : ", minesSpinner, "Rows : ", rowsSpinner, "Columns : ", columnsSpinner};
        
        int option = JOptionPane.showConfirmDialog(getFrame(), message, "Options", JOptionPane.OK_CANCEL_OPTION);

        // Exit if the operation was canceled by the user
        if (option != JOptionPane.OK_OPTION) {
            return null;
        }
        
        int newMines = (int) minesSpinner.getValue();
        int newRows = (int) rowsSpinner.getValue();
        int newColumns = (int) columnsSpinner.getValue();

        // Check that the number of mines does not exceed the number of cells
        if (newMines > newRows*newColumns) {
            JOptionPane.showMessageDialog(getFrame(), "Number of mines cannot be greater than number of cells", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Return new settings
        return Map.ofEntries(
            Map.entry(Option.MINES, newMines),
            Map.entry(Option.ROWS, newRows),
            Map.entry(Option.COLUMNS, newColumns)
        );
    }
}
