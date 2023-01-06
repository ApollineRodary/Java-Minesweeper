package minesweeper.view;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Frame extends JFrame {
    private Panel panel;

    public Frame(int rows, int columns) {
        setTitle("Minesweeper");

        panel = new Panel(rows, columns);
        add(panel);

        setLocationRelativeTo(null);
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);;
    }

    public Panel getPanel() {
        return panel;
    }
}