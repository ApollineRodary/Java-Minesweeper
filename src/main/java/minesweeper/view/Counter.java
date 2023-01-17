package minesweeper.view;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Image;

public class Counter extends JPanel {
    protected static final int DIGIT_WIDTH = 30;
    protected static final int DIGIT_HEIGHT = 50;
    private boolean enabled = true;
    private int value = 0;

    public Counter(boolean enabled) {
        setPreferredSize(new Dimension(DIGIT_WIDTH*3, DIGIT_HEIGHT));
        this.enabled = enabled;
        if (enabled) {
            setValue(0);
        }
    }

    public void setValue(int n) {
        value = n;
        repaint();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (enabled) {
            g.drawImage(ImageLibrary.getImage(value/100), 0, 0, this);
            g.drawImage(ImageLibrary.getImage(value%100/10), DIGIT_WIDTH, 0, this);
            g.drawImage(ImageLibrary.getImage(value%10), DIGIT_WIDTH*2, 0, this);
        } else {
            Image image = ImageLibrary.getImage("counter_off");
            g.drawImage(image, 0, 0, this);
            g.drawImage(image, DIGIT_WIDTH, 0, this);
            g.drawImage(image, DIGIT_WIDTH*2, 0, this);
        }
    }
}
