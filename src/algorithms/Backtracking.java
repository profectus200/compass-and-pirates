package algorithms;

import representation.Cell;
import representation.Jack;
import representation.Map;
import representation.Path;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Represents the algorithms.Backtracking algorithm.
 */
public class Backtracking extends Algorithm {
    private Cell globalCell;
    private boolean doesFound;
    private Integer minStepNumber;
    private Integer[][] minDistance;

    /**
     * Constructor for the algorithms.Backtracking algorithm.
     */
    public Backtracking() {
        super();
        this.minStepNumber = INT_MAX;
        updateMinDistance();
        solve();
    }

    public void updateMinDistance() {
        minDistance = new Integer[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.minDistance[i][j] = INT_MAX;
            }
        }
    }

    /**
     * Cleans data before the next backtracking algorithm call.
     *
     * @return new path
     */
    public Path cleanData() {
        Path currentPath = new Path();
        currentPath.add(jack.getCurrentCell());
        updateMinDistance();
        doesFound = false;
        minStepNumber = INT_MAX;
        globalCell = null;
        return currentPath;
    }

    /**
     * Solves the problem via backtracking.
     */
    public void solve() {
        Path[] paths = new Path[2];
        jack.setCurrentCell(jack.getInitCell());
        Path currentPath;
        currentPath = cleanData();

        reachGoal(jack.getCurrentCell(), Map.getTortugaCell(), 0, currentPath);
        paths[0] = buildPath(globalCell);
        int tortugaMin = minStepNumber;

        jack = new Jack(Map.getScenario());
        jack.setCurrentCell(Map.getTortugaCell());
        currentPath = cleanData();
        reachGoal(jack.getCurrentCell(), Map.getChestCell(), 0, currentPath);
        Path tmpPath1 = buildPath(globalCell);
        if (paths[0] == null || tmpPath1 == null) {
            paths[0] = new Path();
            paths[0].setSize(INT_MAX);
            doesFound = false;
            minStepNumber = INT_MAX;
        } else {
            paths[0].addPath(tmpPath1);
            doesFound = true;
            minStepNumber += tortugaMin;
        }

        jack = new Jack(Map.getScenario());
        updateMinDistance();
        globalCell = null;
        jack.setCurrentCell(jack.getInitCell());
        currentPath = new Path();
        currentPath.add(jack.getCurrentCell());
        reachGoal(jack.getCurrentCell(), Map.getChestCell(), 0, currentPath);
        paths[1] = buildPath(globalCell);
        if (paths[1] == null) {
            paths[1] = new Path();
            paths[1].setSize(INT_MAX);
        }
        choosePath(paths);
    }

    /**
     * Reaches the goal from the current cell.
     *
     * @param currentCell current cell
     * @param goal        goal cell
     * @param stepNumber  current step number
     * @param currentPath current path
     */
    public void reachGoal(Cell currentCell, Cell goal, int stepNumber, Path currentPath) {
        jack.makeStep(currentCell, Map.getScenario());

        if (stepNumber > minDistance[currentCell.getX()][currentCell.getY()]) {
            return;
        } else {
            minDistance[currentCell.getX()][currentCell.getY()] = stepNumber;
        }

        if ((doesFound && stepNumber > minStepNumber)
                || (!currentCell.isKrakenKilled() && (Map.getMap()[currentCell.getX()][currentCell.getY()] == 'X' || Map.getMap()[currentCell.getX()][currentCell.getY()] == 'K'))
                || Map.getMap()[currentCell.getX()][currentCell.getY()] == 'Y' || Map.getMap()[currentCell.getX()][currentCell.getY()] == 'D'
                || Map.getMap()[currentCell.getX()][currentCell.getY()] == 'R') {
            return;
        }

        if (currentCell.equals(goal)) {
            if (stepNumber < minStepNumber) {
                globalCell = currentCell;
                doesFound = true;
                minStepNumber = stepNumber;
            }
            return;
        }
        ArrayList<Cell> neighbors = generateNeighbors(currentCell, goal, currentPath);

        for (Cell neighbor : neighbors) {
            currentPath.add(neighbor);
            reachGoal(neighbor, goal, stepNumber + 1, currentPath);
            currentPath.remove(neighbor);
        }
    }

    /**
     * Generates all available neighbors for the given cell.
     *
     * @param currentCell given cell
     * @param goal        goal cell
     * @param currentPath current path
     * @return generated neighbors for the given cell
     */
    public ArrayList<Cell> generateNeighbors(Cell currentCell, Cell goal, Path currentPath) {
        ArrayList<Cell> neighbors = new ArrayList<>();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int x = currentCell.getX() + i;
                int y = currentCell.getY() + j;
                Cell curNeighbor = new Cell(x, y);
                if ((i != 0 || j != 0) && curNeighbor.isValid()) {
                    if (!currentPath.contains(x, y) && jack.getJackMap()[x][y] != 'X' && jack.getJackMap()[x][y] != 'K'
                            && jack.getJackMap()[x][y] != 'D' && jack.getJackMap()[x][y] != 'Y' && jack.getJackMap()[x][y] != 'R') {
                        curNeighbor.setPrevious(currentCell);
                        curNeighbor.setH(computeHeuristics(curNeighbor, goal));
                        neighbors.add(curNeighbor);
                    }
                }
            }
        }
        neighbors.sort(Comparator.comparing(Cell::getH));
        return neighbors;
    }


    /**
     * Computes heuristics for the given cell.
     *
     * @param cell given cell
     * @param goal goal cell
     * @return computed heuristics
     */
    public int computeHeuristics(Cell cell, Cell goal) {
        return Math.max(Math.abs(cell.getX() - goal.getX()), Math.abs(cell.getY() - goal.getY()));
    }
}
