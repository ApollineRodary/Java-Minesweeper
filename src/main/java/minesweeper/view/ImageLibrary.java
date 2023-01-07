package minesweeper.view;

import java.awt.Image;
import java.net.URL;
import java.awt.Toolkit;
import java.util.Map;
import minesweeper.model.Cell;

public class ImageLibrary {
    private static Image loadImage(final String path) {
        URL url = Panel.class.getClassLoader().getResource(path);
        return Toolkit.getDefaultToolkit().getImage(url).getScaledInstance(Panel.CELL_WIDTH, Panel.CELL_HEIGHT, Image.SCALE_FAST);
    }

    private static final Map<Cell, Image> CELL_IMAGES = Map.ofEntries(
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

    private static final Map<String, Image> IMAGES = Map.ofEntries();

    public static final Image getImage(Cell cell) {
        return CELL_IMAGES.get(cell);
    }

    public static final Image getImage(String str) {
        return IMAGES.get(str);
    }
}
