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
        /* Reveal an individual cell */

        // If the cell is flagged, unflag and exit
        if (knownCells[row][column] == Cell.FLAGGED) {
            unflag(row, column);
            return;
        }

        // Determine cell contents
        Cell cell;
        if (mines[row][column]) cell = Cell.MINE;
        else cell = COUNT_TO_CONTENTS[adjacentMineCount[row][column]];
        
        // Update known cell contents
        knownCells[row][column] = cell;
        
        // Notify frame
        propertyChangeSupport.fireIndexedPropertyChange("knownCells", row*columns + column, Cell.HIDDEN, cell);

        // If the current cell is not a mine and has no adjacent mines, reveal adjacent cells
        if (cell==Cell.NEIGHBORS_0) revealNeighbors(row, column);
    }

    private void revealNeighbors(int row, int column) {
        /* Reveal cells adjacent to the current cell */

        // Get list of positions of adjacent cells
        List <int[]> neighborPositions = new ArrayList<>();
        if (row>0) {
            if (column>0) neighborPositions.add(new int[]{row-1, column-1});
            neighborPositions.add(new int[]{row-1, column});
            if (column<columns-1) neighborPositions.add(new int[]{row-1, column+1});
        }
        if (column>0) neighborPositions.add(new int[]{row, column-1});
        if (column<columns-1) neighborPositions.add(new int[]{row, column+1});
        if (row<rows-1) {
            if (column>0) neighborPositions.add(new int[]{row+1, column-1});
            neighborPositions.add(new int[]{row+1, column});
            if (column<columns-1) neighborPositions.add(new int[]{row+1, column+1});
        }

        // For each adjacent cell that is hidden, reveal it
        for (int[] neighborPosition: neighborPositions) {
            row = neighborPosition[0];
            column = neighborPosition[1];
            if (knownCells[row][column] == Cell.HIDDEN) reveal(row, column);
        }
    }

    public void toggleFlag(int row, int column) {
        if (knownCells[row][column] == Cell.HIDDEN) flag(row, column);
        else if (knownCells[row][column] == Cell.FLAGGED) unflag(row, column);
    }
    private void flag(int row, int column) {
        knownCells[row][column] = Cell.FLAGGED;
        propertyChangeSupport.fireIndexedPropertyChange("knownCells", row*columns + column, Cell.HIDDEN, Cell.FLAGGED);
    }
    private void unflag(int row, int column) {
        knownCells[row][column] = Cell.HIDDEN;
        propertyChangeSupport.fireIndexedPropertyChange("knownCells", row*columns + column, Cell.FLAGGED, Cell.HIDDEN);
    }


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
}