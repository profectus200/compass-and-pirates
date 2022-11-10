package representation;

import java.util.Objects;

/**
 * Class which represents the cell on the map.
 */
public class Cell {
    final static Integer INT_MAX = 1000000000;
    private Integer x, y, g, h;
    private Cell previous;
    private boolean isKrakenKilled, hasRum;

    /**
     * Empty constructor.
     */
    public Cell() {
    }

    /**
     * Standard constructor.
     *
     * @param x coordinate
     * @param y coordinate
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.g = INT_MAX;
        this.h = INT_MAX;
        isKrakenKilled = false;
        hasRum = false;
        this.previous = null;
    }

    /**
     * Checks if the current cell has the correct coordinates.
     *
     * @return true if the current cell is valid and false otherwise
     */
    public Boolean isValid() {
        return (x > -1 && x < 9) && (y > -1 && y < 9);
    }

    /**
     * Compares two cells.
     *
     * @param o another cell
     * @return true if cells are equal and false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell anotherCell = (Cell) o;
        return Objects.equals(x, anotherCell.x) && Objects.equals(y, anotherCell.y);
    }

    /**
     * Returns x field.
     *
     * @return value of x field
     */
    public Integer getX() {
        return x;
    }

    /**
     * Sets x field.
     *
     * @param x value
     */
    public void setX(Integer x) {
        this.x = x;
    }

    /**
     * Returns y field.
     *
     * @return value of y field
     */
    public Integer getY() {
        return y;
    }

    /**
     * Sets y field.
     *
     * @param y value
     */
    public void setY(Integer y) {
        this.y = y;
    }

    /**
     * Returns previous field.
     *
     * @return value of parent field
     */
    public Cell getPrevious() {
        return previous;
    }

    /**
     * Sets previous field.
     *
     * @param previous value
     */
    public void setPrevious(Cell previous) {
        this.previous = previous;
    }

    /**
     * Returns g field.
     *
     * @return value of g field
     */
    public Integer getG() {
        return g;
    }

    /**
     * Sets g field.
     *
     * @param g value
     */
    public void setG(Integer g) {
        this.g = g;
    }

    /**
     * Returns h field.
     *
     * @return value of h field
     */
    public Integer getH() {
        return h;
    }

    /**
     * Sets h field.
     *
     * @param h value
     */
    public void setH(Integer h) {
        this.h = h;
    }

    /**
     * Returns isKrakenKilled field.
     *
     * @return value of isKrakenKilled field
     */
    public boolean isKrakenKilled() {
        return isKrakenKilled;
    }

    /**
     * Sets isKrakenKilled field.
     *
     * @param krakenKilled value
     */
    public void setKrakenKilled(boolean krakenKilled) {
        isKrakenKilled = krakenKilled;
    }

    /**
     * Returns hasRum field.
     *
     * @return value of hasRum field
     */
    public boolean isHasRum() {
        return hasRum;
    }

    /**
     * Sets hasRum field.
     *
     * @param hasRum value
     */
    public void setHasRum(boolean hasRum) {
        this.hasRum = hasRum;
    }

}