package algorithms;

import representation.Cell;
import representation.Jack;
import representation.Map;
import representation.Path;

import java.util.Collections;

/**
 * Represents the solution algorithm.
 */
public class Algorithm {
    final static Integer INT_MAX = 1000000000;
    protected Path answer;
    protected Boolean isPathExist;
    protected Jack jack;

    /**
     * Constructor for the algorithms.Algorithm class.
     */
    public Algorithm() {
        this.isPathExist = true;
        this.jack = new Jack(Map.getScenario());
        this.answer = new Path();
        this.answer.add(this.jack.getInitCell());
    }

    /**
     * Builds the path from the current cell to the start cell.
     *
     * @param currentCell current cell
     * @return the path from the current cell to the start cell
     */
    public Path buildPath(Cell currentCell) {
        if (currentCell == null) {
            return null;
        }
        Path finalPath = new Path();
        while (currentCell.getPrevious() != null) {
            finalPath.add(currentCell);
            currentCell = currentCell.getPrevious();

        }
        Collections.reverse(finalPath.getPath());
        return finalPath;
    }

    /**
     * Chooses the most optimal path from the given.
     *
     * @param paths list of the given paths
     */
    public void choosePath(Path[] paths) {
        Path bestPath = paths[0];
        if (paths[1].getSize() < paths[0].getSize())
            bestPath = paths[1];

        if (bestPath.getSize() >= INT_MAX) {
            isPathExist = false;
        } else {
            answer = bestPath;
        }
    }

    /**
     * Returns the answer field.
     *
     * @return answer field
     */
    public Path getAnswer() {
        return answer;
    }

    /**
     * Sets the answer field.
     *
     * @param answer field
     */
    public void setAnswer(Path answer) {
        this.answer = answer;
    }

    /**
     * Returns the isPathExist field.
     *
     * @return isPathExist field
     */
    public boolean getIsPathExist() {
        return isPathExist;
    }

    /**
     * Sets the doesPathExist field.
     *
     * @param pathExist field
     */
    public void setIsPathExist(boolean pathExist) {
        this.isPathExist = pathExist;
    }

    /**
     * Returns the jack field.
     *
     * @return jack field
     */
    public Jack getJack() {
        return jack;
    }

    /**
     * Sets the jack field.
     *
     * @param jack field
     */
    public void setJack(Jack jack) {
        this.jack = jack;
    }
}
