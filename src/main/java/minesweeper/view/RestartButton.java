package minesweeper.view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Dimension;

public class RestartButton extends JButton {
    public RestartButton() {
        setIcon(new ImageIcon(ImageLibrary.getImage("restart_button")));
        setPreferredSize(new Dimension(Counter.DIGIT_HEIGHT, Counter.DIGIT_HEIGHT));
    }
}
