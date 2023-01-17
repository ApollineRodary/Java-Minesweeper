package minesweeper.view;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Dimension;

public class OptionsButton extends JButton {
    private static final ImageIcon SETTINGS_ICON = new ImageIcon(ImageLibrary.getImage("options_button"));

    public OptionsButton() {
        setIcon(SETTINGS_ICON);
        setPreferredSize(new Dimension(Counter.DIGIT_HEIGHT, Counter.DIGIT_HEIGHT));
    }
}
