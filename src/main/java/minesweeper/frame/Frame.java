package minesweeper.frame;

import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import minesweeper.board.Cell;

public class Frame extends JFrame implements PropertyChangeListener {
    private Panel panel;

    public Frame(int rows, int columns) {
        setTitle("Minesweeper");

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
    
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        IndexedPropertyChangeEvent ei = (IndexedPropertyChangeEvent) e;
        panel.setContents((Cell) e.getNewValue(), ei.getIndex());
        panel.repaint();
    }
}