package representation;

import java.util.ArrayList;

/**
 * Represents the path on the map.
 */
public class Path {
    private ArrayList<Cell> path;
    private Integer size;

    /**
     * Constructor for the representation.Path class.
     */
    public Path() {
        path = new ArrayList<>();
        size = 0;
    }

    /**
     * Adds new cell to the path.
     *
     * @param cell new cell
     */
    public void add(Cell cell) {
        path.add(cell);
        size++;
    }

    /**
     * Adds another path to the current one.
     *
     * @param newPath added path
     */
    public void addPath(Path newPath) {
        path.addAll(newPath.path);
        size += newPath.size;
    }

    /**
     * Removes cell from the current path.
     *
     * @param cell to be removed
     */
    public void remove(Cell cell) {
        for (int i = 0; i < size; i++) {
            if (path.get(i).equals(cell)) {
                path.remove(i);
                --size;
                break;
            }
        }
    }

    /**
     * Checks if the current path contains the cell with (x, y) coordinates.
     *
     * @param x coordinate
     * @param y coordinate
     * @return true if the current path contains the cell with (x, y) coordinates and false otherwise
     */
    public Boolean contains(int x, int y) {
        for (Cell cell : path) {
            if (cell.getX().equals(x) && cell.getY().equals(y)) return true;
        }
        return false;
    }

    /**
     * Returns the path field.
     *
     * @return path value
     */
    public ArrayList<Cell> getPath() {
        return path;
    }

    /**
     * Sets the path field.
     *
     * @param path field
     */
    public void setPath(ArrayList<Cell> path) {
        this.path = path;
    }

    /**
     * Returns the size field.
     *
     * @return size field
     */
    public Integer getSize() {
        return size;
    }

    /**
     * Sets the size field.
     *
     * @param size field
     */
    public void setSize(int size) {
        this.size = size;
    }

}
