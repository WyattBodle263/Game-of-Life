import java.util.HashSet;
import java.util.Objects;

class Cell {
    int x, y;
    boolean alive = true;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setAlive(boolean alive){
        this.alive = alive;
    }

    public boolean isAlive(){
        return this.alive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public static boolean applyRules(Cell cell, HashSet<Cell> cells, int cellSize) {
        int liveNeighbours = countLiveNeighbours(cell, cells, cellSize);

        if (cell.isAlive()) {
            if (liveNeighbours < 2 || liveNeighbours > 3) {
                return false; // Cell dies due to underpopulation or overpopulation
            }
            return true; // Cell lives on to the next generation
        } else {
            if (liveNeighbours == 3) {
                return true; // Dead cell becomes alive due to reproduction
            }
            return false; // Dead cell remains dead
        }
    }

    private static int countLiveNeighbours(Cell cell, HashSet<Cell> cells, int cellSize) {
        int neighbourCount = 0;
        for (Cell otherCell : cells) {
            if (otherCell.isAlive() && isNeighbour(cell, otherCell, cellSize)) {
                neighbourCount++;
            }
        }
        return neighbourCount;
    }

    private static boolean isNeighbour(Cell cell1, Cell cell2, int cellSize) {
        int dx = Math.abs(cell1.x - cell2.x);
        int dy = Math.abs(cell1.y - cell2.y);
        return (dx == cellSize && dy == 0) || (dx == 0 && dy == cellSize);
    }
}
