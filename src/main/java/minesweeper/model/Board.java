package minesweeper.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import minesweeper.controller.Option;

public class Board {
    private final PropertyChangeSupport propertyChangeSupport;
    private static final Cell[] COUNT_TO_CONTENTS = {Cell.NEIGHBORS_0, Cell.NEIGHBORS_1, Cell.NEIGHBORS_2, Cell.NEIGHBORS_3, Cell.NEIGHBORS_4, Cell.NEIGHBORS_5, Cell.NEIGHBORS_6, Cell.NEIGHBORS_7, Cell.NEIGHBORS_8};
    
    private final Cell[][] knownCells;          // The contents of cells discovered by the player
    private final boolean[][] mines;            // Where the mines are
    private final int[][] adjacentMineCount;    // How many mines are adjacent to each cell
    private final int[][] flagPlacers;          // Which player placed each flag: -1 if no flag has been placed or if the flag is misplaced
    public final int rows;
    public final int columns;
    public final boolean isHardcore;
    private GameState gameState;
    private int minesRemaining;                 // How many more flags can be placed

    public Board(Map<Option, Object> options, PropertyChangeListener listener) {
        propertyChangeSupport = new PropertyChangeSupport(this);
        propertyChangeSupport.addPropertyChangeListener(listener);
        
        rows = (int) options.get(Option.ROWS);
        columns = (int) options.get(Option.COLUMNS);
        isHardcore = (boolean) options.get(Option.HARDCORE_MODE);
        int mineCount = (int) options.get(Option.MINES);
        
        // Initialise known cells
        knownCells = new Cell[rows][columns];
        flagPlacers = new int[rows][columns];
        for (int row=0; row<rows; row++) {
            for (int column=0; column<columns; column++) {
                knownCells[row][column] = Cell.HIDDEN;
                flagPlacers[row][column] = -1;
            }
        }

        // Generate random mines
        mines = new boolean[rows][columns];
        List<int[]> positionCandidates = new ArrayList<>();
        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                positionCandidates.add(new int[] {i, j});
            }
        }
        Collections.shuffle(positionCandidates, new Random());
        List<int[]> minePositions = positionCandidates.subList(0, mineCount);
        for (int[] minePosition: minePositions) {
            int row = minePosition[0];
            int column = minePosition[1];
            mines[row][column] = true;
        }
        setMinesRemaining(mineCount);

        // Count neighbors for each cell
        adjacentMineCount = new int[rows][columns];
        for (int row=0; row<rows; row++) {
            for (int column=0; column<columns; column++) {
                adjacentMineCount[row][column] = countNeighbors(row, column);
            }
        }
        
        gameState = GameState.IN_GAME;
        propertyChangeSupport.firePropertyChange("gameState", null, GameState.IN_GAME);
    }

    public GameState getGameState() {
        return gameState;
    }

    private int countNeighbors(int row, int column) {
        /* Returns the number of mines surrounding a cell */
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
        /* Reveals an individual cell */

        // If the cell is flagged, unflag and exit
        if (knownCells[row][column] == Cell.FLAGGED) {
            unflag(row, column);
            return;
        }

        // If the cell is not hidden, ignore and exit
        if (knownCells[row][column] != Cell.HIDDEN) return;

        // Determine cell contents
        Cell cell;
        if (mines[row][column]) cell = Cell.MINE;
        else cell = COUNT_TO_CONTENTS[adjacentMineCount[row][column]];
        
        // Update known cell contents
        knownCells[row][column] = cell;
        
        // Notify controller
        propertyChangeSupport.fireIndexedPropertyChange("knownCells", row*columns + column, Cell.HIDDEN, cell);

        // If the current cell is not a mine and has no adjacent mines, reveal adjacent cells
        if (cell==Cell.NEIGHBORS_0) revealNeighbors(row, column);

        // If the current cell is a mine, end the game
        else if (cell==Cell.MINE) lose();
    }

    private void revealNeighbors(int row, int column) {
        /* Reveals cells adjacent to the current cell */

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

    private void revealWholeBoard() {
        /* Reveals the whole board upon defeat */

        for (int row=0; row<rows; row++) {
            for (int column=0; column<columns; column++) {
                if (knownCells[row][column] != Cell.HIDDEN && knownCells[row][column] != Cell.FLAGGED) continue;
                
                Cell previousCell = knownCells[row][column];

                Cell cell;
                if (mines[row][column]) cell = Cell.MINE;
                else if (previousCell==Cell.FLAGGED && !mines[row][column]) cell = Cell.FALSE_FLAG;
                else cell = COUNT_TO_CONTENTS[adjacentMineCount[row][column]];

                knownCells[row][column] = cell;
                propertyChangeSupport.fireIndexedPropertyChange("knownCells", row*columns + column, previousCell, cell);
            }
        }
    }

    private void lose() {
        /* Reveals the whole board and prevents further interactions with the board */
        gameState = GameState.DEFEAT;
        revealWholeBoard();
        propertyChangeSupport.firePropertyChange("gameState", GameState.IN_GAME, GameState.DEFEAT);
    }

    public void toggleFlag(int row, int column, int player) {
        /* Flags the current cell if it is hidden, unflags it if it is already flagged */
        if (knownCells[row][column] == Cell.HIDDEN) flag(row, column, player);
        else if (knownCells[row][column] == Cell.FLAGGED) unflag(row, column);
    }

    private void flag(int row, int column, int player) {
        /* Flags the current cell, and saves the player who placed the flag */

        // Prevent placing more flags than there are mines, if the mine counter is known
        if (!isHardcore && minesRemaining==0) return;

        knownCells[row][column] = Cell.FLAGGED;

        if (mines[row][column]) {
            flagPlacers[row][column] = player;
        }
        propertyChangeSupport.fireIndexedPropertyChange("knownCells", row*columns + column, Cell.HIDDEN, Cell.FLAGGED);
        setMinesRemaining(minesRemaining-1);
    }
    
    private void unflag(int row, int column) {
        /* Unflags the current cell */
        knownCells[row][column] = Cell.HIDDEN;
        flagPlacers[row][column] = -1;
        propertyChangeSupport.fireIndexedPropertyChange("knownCells", row*columns + column, Cell.FLAGGED, Cell.HIDDEN);
        setMinesRemaining(minesRemaining+1);
    }

    public int getFlagPlacer(int row, int column) {
        /* Returns the ID of the player who placed the flag at the current cell
         * Returns -1 if the current cell is not flagged
         */
        return flagPlacers[row][column];
    }

    private void setMinesRemaining(int n) {
        /* Updates the number of mines the player can place */
        if (!isHardcore) {
            propertyChangeSupport.firePropertyChange("minesRemaining", minesRemaining, n);
        }
        minesRemaining = n;
    }

    public void checkVictory() {
        /* Checks if the board is fully revealed, and if so notifies the controller and prevents further interactions with the board */

        // Check if the game is already over, or if there are flags left to place
        if (minesRemaining>0) return;
        if (gameState != GameState.IN_GAME) return;

        // Check if there are hidden cells
        for (int row=0; row<rows; row++) {
            for (int column=0; column<columns; column++) {
                if (knownCells[row][column]==Cell.HIDDEN) return;
            }
        }

        // Otherwise, the board has been completed
        gameState = GameState.VICTORY;
        propertyChangeSupport.firePropertyChange("gameState", GameState.IN_GAME, GameState.VICTORY);
    }
}