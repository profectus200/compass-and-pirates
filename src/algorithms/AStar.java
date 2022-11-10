package algorithms;

import representation.Cell;
import representation.Jack;
import representation.Map;
import representation.Path;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents A* algorithm.
 */
public class AStar extends Algorithm {

    /**
     * Constructor for the algorithms.AStar algorithm.
     */
    public AStar() {
        super();
        jack.makeStep(jack.getInitCell(), Map.getScenario());
        solve();
    }

    /**
     * Solves the problem via algorithms.AStar algorithm.
     */
    public void solve() {
        jack.setCurrentCell(jack.getInitCell());
        Path[] paths = new Path[2];

        paths[0] = reachGoal(jack.getCurrentCell(), Map.getTortugaCell());
        if (!Objects.equals(paths[0].getSize(), INT_MAX)) {
            jack = new Jack(Map.getScenario());
            jack.setCurrentCell(Map.getTortugaCell());
            paths[0].addPath(reachGoal(jack.getCurrentCell(), Map.getChestCell()));
        }

        jack = new Jack(Map.getScenario());
        jack.setCurrentCell(jack.getInitCell());
        paths[1] = reachGoal(jack.getCurrentCell(), Map.getChestCell());

        choosePath(paths);
    }


    /**
     * Finds path from the initial cell to the goal.
     *
     * @param initCell initial cell
     * @param goal     goal cell
     * @return optimum path
     */
    public Path reachGoal(Cell initCell, Cell goal) {
        Path openPath = new Path();
        Path closedPath = new Path();
        jack.setCurrentCell(initCell);
        jack.getCurrentCell().setG(0);
        jack.getCurrentCell().setH(Math.max(Math.abs(goal.getX() - jack.getCurrentCell().getX()), Math.abs(goal.getY() - jack.getCurrentCell().getY())));
        openPath.add(jack.getCurrentCell());

        while (openPath.getSize() != 0) {
            Cell leastCostStep = findCheapestStep(openPath.getPath());
            openPath.remove(leastCostStep);
            closedPath.add(leastCostStep);

            if (leastCostStep.equals(goal)) {
                break;
            }

            jack.makeStep(leastCostStep, Map.getScenario());
            ArrayList<Cell> neighbors = generateNeighbors(leastCostStep, goal);
            for (Cell neighbor : neighbors) {
                if (closedPath.contains(neighbor.getX(), neighbor.getY())) {
                    continue;
                }
                if (!openPath.contains(neighbor.getX(), neighbor.getY()) || neighbor.getG() + neighbor.getH() < findOldF(openPath.getPath(), neighbor)) {
                    openPath.remove(neighbor);
                    openPath.add(neighbor);
                }
            }
        }

        if (closedPath.getPath().get(closedPath.getSize() - 1).equals(goal)) {
            return super.buildPath(closedPath.getPath().get(closedPath.getSize() - 1));
        } else {
            Path ans = new Path();
            ans.setSize(INT_MAX);
            return ans;
        }
    }

    /**
     * Finds current F value for the cell in the list.
     *
     * @param list given list
     * @param cell given cell
     * @return current F value for the cell in the list
     */
    public int findOldF(ArrayList<Cell> list, Cell cell) {
        for (Cell listCell : list) {
            if (listCell.equals(cell)) return listCell.getG() + listCell.getH();
        }
        return -1;
    }

    /**
     * Generates available neighbors of the given cell.
     *
     * @param cell given cell
     * @param goal goal cell
     * @return available neighbors of the given cell
     */
    public ArrayList<Cell> generateNeighbors(Cell cell, Cell goal) {
        ArrayList<Cell> neighbors = new ArrayList<>();
        for (int i = -1; i < 2; ++i) {
            for (int j = -1; j < 2; ++j) {
                Cell neighborCell = new Cell(cell.getX() + i, cell.getY() + j);
                if (neighborCell.isValid() && (i != 0 || j != 0) &&
                        !(jack.getJackMap()[cell.getX() + i][cell.getY() + j] == 'X') &&
                        !(jack.getJackMap()[cell.getX() + i][cell.getY() + j] == 'Y') &&
                        !(jack.getJackMap()[cell.getX() + i][cell.getY() + j] == 'K') &&
                        !(jack.getJackMap()[cell.getX() + i][cell.getY() + j] == 'D') &&
                        !(jack.getJackMap()[cell.getX() + i][cell.getY() + j] == 'R')) {
                    neighborCell.setPrevious(cell);
                    computeHeuristics(neighborCell, goal);
                    neighbors.add(neighborCell);
                }
            }
        }
        return neighbors;
    }

    /**
     * Finds the cheapest step.
     *
     * @param openPath the list of available cells
     * @return the cheapest cell
     */
    public Cell findCheapestStep(ArrayList<Cell> openPath) {
        Cell cheapestStep = new Cell();
        int cheapestStepCost = INT_MAX;

        for (Cell cell : openPath) {
            if (cell.getG() + cell.getH() < cheapestStepCost ||
                    (cell.getG() + cell.getH() == cheapestStepCost && cell.getG() < cheapestStep.getG())) {
                cheapestStepCost = cell.getG() + cell.getH();
                cheapestStep = cell;
            }
        }
        return cheapestStep;
    }

    /**
     * Computes heuristics for the given cell.
     *
     * @param currentCell given cell
     * @param goal        goal cell
     */
    public void computeHeuristics(Cell currentCell, Cell goal) {
        int g = currentCell.getPrevious().getG() + 1;
        int h = Math.max(Math.abs(goal.getX() - currentCell.getX()), Math.abs(goal.getY() - currentCell.getY()));
        currentCell.setG(g);
        currentCell.setH(h);
    }
}
