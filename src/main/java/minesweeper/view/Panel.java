package minesweeper.view;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import minesweeper.model.Cell;

public class Panel extends JPanel {
    public static final int CELL_WIDTH = 25;
    public static final int CELL_HEIGHT = 25;

    private Cell[][] contents;

    public Panel(int rows, int columns) {
        reset(rows, columns);
    }

    public void setContents(Cell contents, int row, int column) {
        this.contents[row][column] = contents;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i=0; i<contents.length; i++) {
            Cell[] row = contents[i];
            for (int j=0; j<row.length; j++) {
                Cell cell = row[j];
                g.drawImage(ImageLibrary.getImage(cell), (j*CELL_WIDTH), (i*CELL_HEIGHT), this);
            }
        }
    }

    public void reset(int rows, int columns) {
        setPreferredSize(new Dimension(Panel.CELL_WIDTH*columns, Panel.CELL_HEIGHT*rows));
        revalidate();
        contents = new Cell[rows][columns];
        for (int row=0; row<rows; row++) {
            for (int column=0; column<columns; column++) {
                contents[row][column] = Cell.HIDDEN;
                repaint();
            }
        }
    }
}