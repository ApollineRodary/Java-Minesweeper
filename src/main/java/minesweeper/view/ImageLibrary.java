package minesweeper.view;

import java.awt.Image;
import java.net.URL;
import java.awt.Toolkit;
import java.util.Map;
import minesweeper.model.Cell;

public class ImageLibrary {
    private static Image loadImage(final String path) {
        URL url = ImageLibrary.class.getClassLoader().getResource(path);
        return Toolkit.getDefaultToolkit().getImage(url);
    }

    private static final Map<Cell, Image> CELL_IMAGES = Map.ofEntries(
        Map.entry(Cell.MINE, loadImage("textures/cell/mine.png").getScaledInstance(Panel.CELL_WIDTH, Panel.CELL_HEIGHT, Image.SCALE_FAST)),
        Map.entry(Cell.NEIGHBORS_0, loadImage("textures/cell/0_neighbors.png").getScaledInstance(Panel.CELL_WIDTH, Panel.CELL_HEIGHT, Image.SCALE_FAST)),
        Map.entry(Cell.NEIGHBORS_1, loadImage("textures/cell/1_neighbor.png").getScaledInstance(Panel.CELL_WIDTH, Panel.CELL_HEIGHT, Image.SCALE_FAST)),
        Map.entry(Cell.NEIGHBORS_2, loadImage("textures/cell/2_neighbors.png").getScaledInstance(Panel.CELL_WIDTH, Panel.CELL_HEIGHT, Image.SCALE_FAST)),
        Map.entry(Cell.NEIGHBORS_3, loadImage("textures/cell/3_neighbors.png").getScaledInstance(Panel.CELL_WIDTH, Panel.CELL_HEIGHT, Image.SCALE_FAST)),
        Map.entry(Cell.NEIGHBORS_4, loadImage("textures/cell/4_neighbors.png").getScaledInstance(Panel.CELL_WIDTH, Panel.CELL_HEIGHT, Image.SCALE_FAST)),
        Map.entry(Cell.NEIGHBORS_5, loadImage("textures/cell/5_neighbors.png").getScaledInstance(Panel.CELL_WIDTH, Panel.CELL_HEIGHT, Image.SCALE_FAST)),
        Map.entry(Cell.NEIGHBORS_6, loadImage("textures/cell/6_neighbors.png").getScaledInstance(Panel.CELL_WIDTH, Panel.CELL_HEIGHT, Image.SCALE_FAST)),
        Map.entry(Cell.NEIGHBORS_7, loadImage("textures/cell/7_neighbors.png").getScaledInstance(Panel.CELL_WIDTH, Panel.CELL_HEIGHT, Image.SCALE_FAST)),
        Map.entry(Cell.NEIGHBORS_8, loadImage("textures/cell/8_neighbors.png").getScaledInstance(Panel.CELL_WIDTH, Panel.CELL_HEIGHT, Image.SCALE_FAST)),
        Map.entry(Cell.HIDDEN, loadImage("textures/cell/hidden.png").getScaledInstance(Panel.CELL_WIDTH, Panel.CELL_HEIGHT, Image.SCALE_FAST)),
        Map.entry(Cell.FLAGGED, loadImage("textures/cell/flagged.png").getScaledInstance(Panel.CELL_WIDTH, Panel.CELL_HEIGHT, Image.SCALE_FAST))
    );

    private static final Map<String, Image> IMAGES = Map.ofEntries(
        Map.entry("restart_button", loadImage("textures/ui/restart_button.png"))
    );

    private static final Map<Integer, Image> NUM_IMAGES = Map.ofEntries(
        Map.entry(0, loadImage("textures/number/0.png")),
        Map.entry(1, loadImage("textures/number/1.png")),
        Map.entry(2, loadImage("textures/number/2.png")),
        Map.entry(3, loadImage("textures/number/3.png")),
        Map.entry(4, loadImage("textures/number/4.png")),
        Map.entry(5, loadImage("textures/number/5.png")),
        Map.entry(6, loadImage("textures/number/6.png")),
        Map.entry(7, loadImage("textures/number/7.png")),
        Map.entry(8, loadImage("textures/number/8.png")),
        Map.entry(9, loadImage("textures/number/9.png"))
    );

    public static Image getImage(Cell cell) {
        return CELL_IMAGES.get(cell);
    }

    public static Image getImage(String str) {
        return IMAGES.get(str);
    }

    public static Image getImage(int n) {
        return NUM_IMAGES.get(n);
    }
}
