package minesweeper.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.JPanel;

import minesweeper.model.Cell;

public class Panel extends JPanel {
    public static final int CELL_WIDTH = 25;
    public static final int CELL_HEIGHT = 25;
    
    private final int rows;
    private final int columns;

    private Cell[][] contents;

    public Panel(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        contents = new Cell[rows][columns];
        for(int i=0; i<rows; i++) Arrays.fill(contents[i], Cell.HIDDEN);
        setPreferredSize(new Dimension(Panel.CELL_WIDTH*this.columns, Panel.CELL_HEIGHT*this.rows));
    }

    public void setContents(Cell contents, int pos) {
        int row = pos/columns;
        int column = pos%columns;
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
}