package minesweeper.view;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Frame extends JFrame {
    private Panel panel;
    private Counter mineCounter;

    public Frame(int rows, int columns) {
        setTitle("Minesweeper");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        mineCounter = new Counter();
        add(mineCounter);

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

    public Counter getMineCounter() {
        return mineCounter;
    }
}