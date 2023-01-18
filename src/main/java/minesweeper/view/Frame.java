package minesweeper.view;

import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.util.Map;
import java.awt.GridBagConstraints;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import minesweeper.controller.Option;

public class Frame extends JFrame {
    private Panel panel;
    private Counter mineCounter;
    private PlayerLabel playerLabel;
    private RestartButton restartButton;
    private OptionsButton optionsButton;

    public Frame(Map<Option, Object> options) {
        setTitle("Minesweeper");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Top bar for mine counter and restart button
        JPanel topBar = new JPanel();
        topBar.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        add(topBar);

        mineCounter = new Counter(!(boolean) options.get(Option.HARDCORE_MODE));
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
        panel = new Panel((int) options.get(Option.ROWS), (int) options.get(Option.COLUMNS));
        add(panel);

        // Current player (if multiplayer)
        if ((boolean) options.get(Option.MULTIPLAYER_MODE)) {
            addPlayerLabel();
        }
        
        setLocationRelativeTo(null);
        pack();
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);;
    }

    public void addPlayerLabel() {
        if (playerLabel == null) {
            playerLabel = new PlayerLabel();
            add(playerLabel);
            revalidate();
        }
    }

    public void removePlayerLabel() {
        if (playerLabel != null) {
            remove(playerLabel);
            revalidate();
        }
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

    public PlayerLabel getPlayerLabel() {
        return playerLabel;
    }
}