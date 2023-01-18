package minesweeper.view;

import javax.swing.JLabel;

public class PlayerLabel extends JLabel {
    public void setValue(int n) {
        setText("Now playing: Player " + (n+1));
    }
}
