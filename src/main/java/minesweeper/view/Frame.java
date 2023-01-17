package minesweeper.view;

import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Frame extends JFrame {
    private Panel panel;
    private Counter mineCounter;
    private RestartButton restartButton;
    private OptionsButton optionsButton;

    public Frame(int rows, int columns) {
        setTitle("Minesweeper");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Top bar for mine counter and restart button
        JPanel topBar = new JPanel();
        topBar.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        add(topBar);

        mineCounter = new Counter();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        topBar.add(mineCounter, gbc);

        optionsButton = new OptionsButton();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.gridx = 1;
        gbc.gridy = 0;
        topBar.add(optionsButton, gbc);

        restartButton = new RestartButton();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.25;
        gbc.gridx = 2;
        gbc.gridy = 0;
        topBar.add(restartButton);

        // Main panel
        panel = new Panel(rows, columns);
        add(panel);

        setLocationRelativeTo(null);
        pack();
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);;
    }

    public Panel getPanel() {
        return panel;
    }

    public Counter getMineCounter() {
        return mineCounter;
    }

    public RestartButton getRestartButton() {
        return restartButton;
    }

    public OptionsButton getOptionsButton() {
        return optionsButton;
    }
}