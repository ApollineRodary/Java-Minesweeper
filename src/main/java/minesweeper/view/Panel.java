package minesweeper.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

import javax.swing.JPanel;

import minesweeper.board.Cell;

public class Panel extends JPanel {
    public static final int CELL_WIDTH = 25;
    public static final int CELL_HEIGHT = 25;
    
    private final int rows;
    private final int columns;

    private Cell[][] contents;

    private static Image loadImage(final String path) {
        URL url = Panel.class.getClassLoader().getResource(path);
        return Toolkit.getDefaultToolkit().getImage(url).getScaledInstance(CELL_WIDTH, CELL_HEIGHT, Image.SCALE_FAST);
    }

    private static final Map<Cell, Image> IMAGES = Map.ofEntries(
        Map.entry(Cell.MINE, loadImage("textures/cell/mine.png")),
        Map.entry(Cell.NEIGHBORS_0, loadImage("textures/cell/0_neighbors.png")),
        Map.entry(Cell.NEIGHBORS_1, loadImage("textures/cell/1_neighbor.png")),
        Map.entry(Cell.NEIGHBORS_2, loadImage("textures/cell/2_neighbors.png")),
        Map.entry(Cell.NEIGHBORS_3, loadImage("textures/cell/3_neighbors.png")),
        Map.entry(Cell.NEIGHBORS_4, loadImage("textures/cell/4_neighbors.png")),
        Map.entry(Cell.NEIGHBORS_5, loadImage("textures/cell/5_neighbors.png")),
        Map.entry(Cell.NEIGHBORS_6, loadImage("textures/cell/6_neighbors.png")),
        Map.entry(Cell.NEIGHBORS_7, loadImage("textures/cell/7_neighbors.png")),
        Map.entry(Cell.NEIGHBORS_8, loadImage("textures/cell/8_neighbors.png")),
        Map.entry(Cell.HIDDEN, loadImage("textures/cell/hidden.png")),
        Map.entry(Cell.FLAGGED, loadImage("textures/cell/flagged.png"))
    );

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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i=0; i<contents.length; i++) {
            Cell[] row = contents[i];
            for (int j=0; j<row.length; j++) {
                Cell cell = row[j];
                g.drawImage(IMAGES.get(cell), (j*CELL_WIDTH), (i*CELL_HEIGHT), this);
            }
        }
    }
}