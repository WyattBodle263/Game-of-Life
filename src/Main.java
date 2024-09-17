import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class Main {
    private static final Integer OPEN_AREA = 800;
    public static final Integer LEGEND_HEIGHT = 20;

    public static void main(String[] args) {
        beginVisual();
    }

    private static void beginVisual() { // Class constants for open area in graphics (Do NOT change)
        int panelWidth = OPEN_AREA + LEGEND_HEIGHT;
        int panelHeight = OPEN_AREA + LEGEND_HEIGHT;

        DrawingPanel panel = initializePanel(panelWidth, panelHeight, Color.WHITE);
        Graphics g = panel.getGraphics();

        // 1) Create Static Drawings
        outputStaticGraphicsToGraph(g, panelWidth, panelHeight);

        // 2) Run Game
        runGame(g, panelWidth, panelHeight, panel);
    }

    // Outputs Static data
    private static void outputStaticGraphicsToGraph(Graphics g, int panelWidth, int panelHeight) {
        // Black Lines
        g.setColor(Color.BLACK);
        g.drawLine(LEGEND_HEIGHT, LEGEND_HEIGHT, panelWidth, LEGEND_HEIGHT);
        g.drawLine(LEGEND_HEIGHT, LEGEND_HEIGHT, LEGEND_HEIGHT, panelHeight);

        // Legend Numbers
        // Horizontal
        for (int i = 1; i < 10; i += 1) {
            g.drawString(String.valueOf(i), LEGEND_HEIGHT + (i * OPEN_AREA / 10), LEGEND_HEIGHT / 2);
        }

        // Vertical
        for (int i = 1; i < 10; i += 1) {
            g.drawString(String.valueOf(i), LEGEND_HEIGHT / 2, LEGEND_HEIGHT + (i * OPEN_AREA / 10));
        }
    }

    // Runs the Game of Life simulation
    private static void runGame(Graphics g, int panelWidth, int panelHeight, DrawingPanel panel) {
        int cellSize = OPEN_AREA / 100;
        HashSet<Cell> cells = new HashSet<>();
        Random random = new Random();

        // Initialize cells with random positions
        for (int i = 0; i <= 10000; i++) {
            int x = random.nextInt(OPEN_AREA / cellSize) * cellSize + LEGEND_HEIGHT;
            int y = random.nextInt(OPEN_AREA / cellSize) * cellSize + LEGEND_HEIGHT;
            cells.add(new Cell(x, y));
        }

        // Draw initial cells
        drawVariableGraphics(cells, g, cellSize);
        panel.clear();

        // Simulation loop until no changes between generations
        HashSet<Cell> previousGeneration;
        boolean hasChanges;
        do {
            previousGeneration = new HashSet<>(cells);
            HashSet<Cell> newCells = new HashSet<>();

            // Apply Game of Life rules to get the next generation
            for (Cell cell : cells) {
                boolean shouldBeAlive = Cell.applyRules(cell, cells, cellSize);
                if (shouldBeAlive) {
                    newCells.add(new Cell(cell.x, cell.y));
                }
            }

            // Check if there are changes
            hasChanges = !newCells.equals(previousGeneration);

            // Clear the panel and draw the new state
            panel.clear();
            drawVariableGraphics(newCells, g, cellSize);

            // Update the cells set to the new generation
            cells = newCells;

            // Pause for a short time to visualize the changes
            try {
                Thread.sleep(500); // Adjust delay as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } while (hasChanges);
    }

    private static void drawVariableGraphics(HashSet<Cell> cells, Graphics g, int cellSize) {
        g.setColor(Color.GREEN);
        for (Cell thisCell : cells) {
            g.fillRect(thisCell.x, thisCell.y, cellSize, cellSize);
        }
    }

    // Calls an instance of the drawing panel to make graphics easier
    private static DrawingPanel initializePanel(int width, int height, Color bgColor) {
        // Create the drawing panel object
        DrawingPanel panel = new DrawingPanel(width, height);

        // Set background color
        panel.setBackground(bgColor);

        // Return the panel object
        return panel;
    }
}
