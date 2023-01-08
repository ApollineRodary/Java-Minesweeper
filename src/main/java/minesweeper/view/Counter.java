package minesweeper.view;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;

public class Counter extends JPanel {
    private static final int DIGIT_WIDTH = 30;
    private static final int DIGIT_HEIGHT = 50;
    private int value = 0;

    public Counter() {
        super();
        setPreferredSize(new Dimension(DIGIT_WIDTH*3, DIGIT_HEIGHT));
        setValue(0);
    }

    public void setValue(int n) {
        value = n;
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ImageLibrary.getImage(value/100), 0, 0, this);
        g.drawImage(ImageLibrary.getImage(value%100/10), DIGIT_WIDTH, 0, this);
        g.drawImage(ImageLibrary.getImage(value%10), DIGIT_WIDTH*2, 0, this);
    }
}
