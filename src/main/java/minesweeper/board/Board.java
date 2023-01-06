package minesweeper.board;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Board {
    private final PropertyChangeSupport propertyChangeSupport;
    private static final Cell[] COUNT_TO_CONTENTS = {Cell.NEIGHBORS_0, Cell.NEIGHBORS_1, Cell.NEIGHBORS_2, Cell.NEIGHBORS_3, Cell.NEIGHBORS_4, Cell.NEIGHBORS_5, Cell.NEIGHBORS_6, Cell.NEIGHBORS_7, Cell.NEIGHBORS_8};
    
    private final Cell[][] knownCells;
    private final boolean[][] mines;
    private final int[][] adjacentMineCount;
    public final int rows;
    public final int columns;

    public Board(int rows, int columns, int mines) {
        propertyChangeSupport = new PropertyChangeSupport(this);

        this.rows = rows;
        this.columns = columns;
        
        // Initialise known cells
        knownCells = new Cell[rows][columns];
        for (int row=0; row<rows; row++) {
            for (int column=0; column<columns; column++) {
                knownCells[row][column] = Cell.HIDDEN;
            }
        }

        // Generate random mines
        this.mines = new boolean[rows][columns];
        List<int[]> positionCandidates = new ArrayList<>();
        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                positionCandidates.add(new int[] {i, j});
            }
        }
        Collections.shuffle(positionCandidates, new Random());
        List<int[]> minePositions = positionCandidates.subList(0, mines);
        for (int[] minePosition: minePositions) {
            int row = minePosition[0];
            int column = minePosition[1];
            this.mines[row][column] = true;
        }

        // Count neighbors for each cell
        adjacentMineCount = new int[rows][columns];
        for (int row=0; row<rows; row++) {
            for (int column=0; column<columns; column++) {
                adjacentMineCount[row][column] = countNeighbors(row, column);
            }
        }
    }

    private int countNeighbors(int row, int column) {
        int count=0;

        int startRow = Math.max(0, row-1);
        int endRow = Math.min(row+1, rows-1);
        int startColumn = Math.max(0, column-1);
        int endColumn = Math.min(column+1, columns-1);

        for (int i=startRow; i<=endRow; i++) {
            for (int j=startColumn; j<=endColumn; j++) {
                if (mines[i][j]) count++;
            }
        }
        return count;
    }
    
    public void reveal(int row, int column) {
        Cell cell;
        if (mines[row][column]) cell = Cell.MINE;
        else cell = COUNT_TO_CONTENTS[adjacentMineCount[row][column]];

        knownCells[row][column] = cell;
        propertyChangeSupport.fireIndexedPropertyChange("knownCells", row*columns + column, Cell.HIDDEN, cell);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
}