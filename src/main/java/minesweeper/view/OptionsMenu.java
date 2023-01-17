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
        JSpinner minesSpinner = new JSpinner(new SpinnerNumberModel(mines, Controller.MIN_MINES, Controller.MAX_MINES, 1));
        JSpinner rowsSpinner = new JSpinner(new SpinnerNumberModel(rows, Controller.MIN_ROWS, Controller.MAX_ROWS, 1));
        JSpinner columnsSpinner = new JSpinner(new SpinnerNumberModel(columns, Controller.MIN_COLUMNS, Controller.MAX_COLUMNS, 1));
        JCheckBox hardcoreModeCheckbox = new JCheckBox("Hardcore mode");
        hardcoreModeCheckbox.addItemListener(e -> {
            minesSpinner.setEnabled(!hardcoreModeCheckbox.isSelected());
        });

        Object[] message = {"Mines : ", minesSpinner, "Rows : ", rowsSpinner, "Columns : ", columnsSpinner, hardcoreModeCheckbox};
        
        int option = JOptionPane.showConfirmDialog(frame, message, "Options", JOptionPane.OK_CANCEL_OPTION);

        // Exit if the operation was canceled by the user
        if (option != JOptionPane.OK_OPTION) {
            return null;
        }
        
        int newMines = (int) minesSpinner.getValue();
        int newRows = (int) rowsSpinner.getValue();
        int newColumns = (int) columnsSpinner.getValue();
        boolean hardcoreMode = hardcoreModeCheckbox.isSelected();

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
            Map.entry(Option.HARDCORE_MODE, hardcoreMode)
        );

    }
}
