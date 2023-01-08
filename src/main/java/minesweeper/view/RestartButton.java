package minesweeper.view;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import minesweeper.model.GameState;

import java.awt.Dimension;

public class RestartButton extends JButton {
    private static final ImageIcon IN_GAME_ICON = new ImageIcon(ImageLibrary.getImage("restart_button"));
    private static final ImageIcon DEFEAT_ICON = new ImageIcon(ImageLibrary.getImage("restart_button_defeat"));
    private static final ImageIcon VICTORY_ICON = new ImageIcon(ImageLibrary.getImage("restart_button_victory"));

    public RestartButton() {
        setIcon(IN_GAME_ICON);
        setPreferredSize(new Dimension(Counter.DIGIT_HEIGHT, Counter.DIGIT_HEIGHT));
    }

    public void updateIcon(GameState s) {
        switch(s) {
            case IN_GAME:
                setIcon(IN_GAME_ICON);
                break;
            
            case DEFEAT:
                setIcon(DEFEAT_ICON);
                break;
            
            case VICTORY:
                setIcon(VICTORY_ICON);
                break;
        }
    }
}
