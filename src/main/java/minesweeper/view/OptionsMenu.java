package minesweeper.view;

import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import minesweeper.controller.Controller;
import minesweeper.controller.Option;

public class OptionsMenu {
    public static Map<Option, Object> launch(Frame frame, int mines, int rows, int columns) {
        // Spinners for mines, rows and columns
        JSpinner minesSpinner = new JSpinner(new SpinnerNumberModel(mines, Controller.MIN_MINES, Controller.MAX_MINES, 1));
        JSpinner rowsSpinner = new JSpinner(new SpinnerNumberModel(rows, Controller.MIN_ROWS, Controller.MAX_ROWS, 1));
        JSpinner columnsSpinner = new JSpinner(new SpinnerNumberModel(columns, Controller.MIN_COLUMNS, Controller.MAX_COLUMNS, 1));
        
        // Checkbox for hardcore, and disabling of mines spinner when hardcore is enabled
        JCheckBox hardcoreModeCheckbox = new JCheckBox("Hardcore mode");
        hardcoreModeCheckbox.addItemListener(e -> {
            minesSpinner.setEnabled(!hardcoreModeCheckbox.isSelected());
        });

        // Checkbox for multiplayer, and spinner for players enabled when multiplayer is enabled
        JCheckBox multiplayerCheckbox = new JCheckBox("Multiplayer");
        JSpinner playersSpinner = new JSpinner(new SpinnerNumberModel(2, Controller.MULTIPLAYER_MIN_PLAYERS, Controller.MULTIPLAYER_MAX_PLAYERS, 1));
        playersSpinner.setEnabled(false);
        multiplayerCheckbox.addItemListener(e -> {
            playersSpinner.setEnabled(multiplayerCheckbox.isSelected());
        });

        Object[] message = {"Mines : ", minesSpinner, "Rows : ", rowsSpinner, "Columns : ", columnsSpinner, hardcoreModeCheckbox, multiplayerCheckbox, "Players: ", playersSpinner};
        
        int option = JOptionPane.showConfirmDialog(frame, message, "Options", JOptionPane.OK_CANCEL_OPTION);

        // Exit if the operation was canceled by the user
        if (option != JOptionPane.OK_OPTION) {
            return null;
        }
        
        int newMines = (int) minesSpinner.getValue();
        int newRows = (int) rowsSpinner.getValue();
        int newColumns = (int) columnsSpinner.getValue();
        boolean isHardcore = hardcoreModeCheckbox.isSelected();
        boolean isMultiplayer = multiplayerCheckbox.isSelected();
        int players = isMultiplayer?((int) playersSpinner.getValue()):1;

        // Check that the number of mines does not exceed the number of cells
        if (newMines > newRows*newColumns) {
            JOptionPane.showMessageDialog(frame, "Number of mines cannot be greater than number of cells", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Return new settings
        return Map.ofEntries(
            Map.entry(Option.MINES, newMines),
            Map.entry(Option.ROWS, newRows),
            Map.entry(Option.COLUMNS, newColumns),
            Map.entry(Option.HARDCORE_MODE, isHardcore),
            Map.entry(Option.MULTIPLAYER_MODE, isMultiplayer),
            Map.entry(Option.PLAYERS, players)
        );
    }
}
