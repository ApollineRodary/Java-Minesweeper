package minesweeper.view;

import javax.swing.JLabel;

public class PlayerLabel extends JLabel {
    public void setPlayer(int player, int score) {
        setText("Now playing: Player " + (player+1) + " - " + score + " points");
    }
}
