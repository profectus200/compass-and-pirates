import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.lang.Math.*;

/**
 * Class which contains everything.
 */
public class Main {
    public static void main(String[] args) {
        new Solution();
    }
}

/**
 * Class which represents the cell on the map.
 */
class Cell {
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
     * Returns for x field.
     *
     * @return value of x field
     */
    public Integer getX() {
        return x;
    }

    /**
     * Sets for x field.
     *
     * @param x field
     */
    public void setX(Integer x) {
        this.x = x;
    }

    /**
     * Returns for y field.
     *
     * @return value of y field
     */
    public Integer getY() {
        return y;
    }

    /**
     * Sets for y field.
     *
     * @param y field
     */
    public void setY(Integer y) {
        this.y = y;
    }

    /**
     * Returns for parent field.
     *
     * @return value of parent field
     */
    public Cell getPrevious() {
        return previous;
    }

    /**
     * Sets for parent field.
     *
     * @param previous field
     */
    public void setPrevious(Cell previous) {
        this.previous = previous;
    }

    /**
     * Returns for g field.
     *
     * @return value of g field
     */
    public Integer getG() {
        return g;
    }

    /**
     * Sets for g field.
     *
     * @param g field
     */
    public void setG(Integer g) {
        this.g = g;
    }

    /**
     * Returns for h field.
     *
     * @return value of h field
     */
    public Integer getH() {
        return h;
    }

    /**
     * Sets for h field.
     *
     * @param h field
     */
    public void setH(Integer h) {
        this.h = h;
    }

    public boolean isKrakenKilled() {
        return isKrakenKilled;
    }

    public void setKrakenKilled(boolean krakenKilled) {
        isKrakenKilled = krakenKilled;
    }

    public boolean isHasRum() {
        return hasRum;
    }

    public void setHasRum(boolean hasRum) {
        this.hasRum = hasRum;
    }

}


class Map {
    private static Character[][] map = new Character[9][9];
    private static Integer scenario;
    private static Cell jackCell, chestCell, rockCell, tortugaCell, krakenCell, davyCell;


    protected static boolean isCorrupted = false;

    /**
     * Empty constructor.
     */
    public Map() {
    }


    /**
     * Creates empty map.
     */
    public static void createEmptyMap() {
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) map[i][j] = ' ';
        }
    }


    /**
     * Adds new object on the map.
     *
     * @param name character name
     * @param x    coordinate
     * @param y    coordinate
     */
    public static void addObject(char name, int x, int y) {
        map[x][y] = name;
        switch (name) {
            case 'J' -> jackCell = new Cell(x, y);
            case 'R' -> rockCell = new Cell(x, y);
            case 'T' -> tortugaCell = new Cell(x, y);
            case 'C' -> chestCell = new Cell(x, y);
            case 'K' -> krakenCell = new Cell(x, y);
            case 'D' -> davyCell = new Cell(x, y);

        }
    }


    /**
     * Returns map field.
     *
     * @return map field
     */
    public static Character[][] getMap() {
        return map;
    }

    /**
     * Sets map field.
     *
     * @param map field
     */
    public static void setMap(Character[][] map) {
        Map.map = map;
    }

    /**
     * Returns scenario field.
     *
     * @return scenario field
     */
    public static Integer getScenario() {
        return scenario;
    }

    /**
     * Sets scenario field.
     *
     * @param scenario field
     */
    public static void setScenario(Integer scenario) {
        Map.scenario = scenario;
    }

    /**
     * Returns jackCell field.
     *
     * @return jackCell field
     */
    public static Cell getJackCell() {
        return jackCell;
    }

    /**
     * Sets jackCell field.
     *
     * @param jackCell field
     */
    public static void setJackCell(Cell jackCell) {
        Map.jackCell = jackCell;
    }

    /**
     * Returns chestCell field.
     *
     * @return chestCell field
     */
    public static Cell getChestCell() {
        return chestCell;
    }

    /**
     * Sets chestCell field.
     *
     * @param chestCell field
     */
    public static void setChestCell(Cell chestCell) {
        Map.chestCell = chestCell;
    }

    /**
     * Returns rockCell field.
     *
     * @return rockCell field
     */
    public static Cell getRockCell() {
        return rockCell;
    }

    /**
     * Sets rockCell field.
     *
     * @param rockCell field
     */
    public static void setRockCell(Cell rockCell) {
        Map.rockCell = rockCell;
    }

    /**
     * Returns tortugaCell field.
     *
     * @return tortugaCell field
     */
    public static Cell getTortugaCell() {
        return tortugaCell;
    }

    /**
     * Sets tortugaCell field.
     *
     * @param tortugaCell field
     */
    public static void setTortugaCell(Cell tortugaCell) {
        Map.tortugaCell = tortugaCell;
    }

    /**
     * Returns krakenCell field.
     *
     * @return krakenCell field
     */
    public static Cell getKrakenCell() {
        return krakenCell;
    }

    /**
     * Sets krakenCell field.
     *
     * @param krakenCell field
     */
    public static void setKrakenCell(Cell krakenCell) {
        Map.krakenCell = krakenCell;
    }

    /**
     * Returns davyCell field.
     *
     * @return davyCell field
     */
    public static Cell getDavyCell() {
        return davyCell;
    }

    /**
     * Sets davyCell field.
     *
     * @param davyCell field
     */
    public static void setDavyCell(Cell davyCell) {
        Map.davyCell = davyCell;
    }

    public static boolean getIsCorrupted() {
        return isCorrupted;
    }

    public static void setIsCorrupted(boolean isCorrupted) {
        Map.isCorrupted = isCorrupted;
    }
}

/**
 * Represents Jack Sparrow.
 */
class Jack {
    private Cell initCell, currentCell, krakenKillCell;
    private Boolean hasRum;
    private Boolean isKrakenKilled;
    private final Character[][] jackMap;

    /**
     * Constructor for the Jack class.
     *
     * @param scenario of Jack's perception
     */
    public Jack(int scenario) {
        this.jackMap = new Character[9][9];
        this.initCell = Map.getJackCell();
        this.initCell.setG(0);
        this.currentCell = initCell;
        this.krakenKillCell = new Cell(-1, -1);
        this.hasRum = false;
        this.isKrakenKilled = false;
        makeStep(initCell, scenario);
        createEmptyMap();
    }

    public void createEmptyMap() {
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) jackMap[i][j] = ' ';
        }
    }

    /**
     * Makes step on the given cell.
     *
     * @param cell     on which Jack makes step
     * @param scenario of Jack's perception
     */
    public void makeStep(Cell cell, int scenario) {
        currentCell = cell;
        if (currentCell.equals(Map.getTortugaCell())) hasRum = true;
        if (hasRum && !isKrakenKilled) {
            for (int i = -1; i < 2; ++i) {
                for (int j = -1; j < 2; ++j) {
                    if (abs(i) == 1 && abs(j) == 1 && (currentCell.getX().equals(Map.getKrakenCell().getX() + i)) && (currentCell.getY().equals(Map.getKrakenCell().getY() + j))) {
                        removeKrakenZone();
                        isKrakenKilled = true;
                        krakenKillCell = currentCell;
                    }
                }
            }
        }
        int x = currentCell.getX(), y = currentCell.getY();
        for (int i = -2; i < 3; ++i) {
            for (int j = -2; j < 3; ++j) {
                if ((scenario == 1 && (abs(i) > 1 || abs(j) > 1)) ||
                        (scenario == 2 && (abs(i) > 1 || abs(j) > 1) && (abs(i) + abs(j) > 2))) continue;
                Cell tmpCell = new Cell(x + i, y + j);
                if (tmpCell.isValid() &&
                        ((!isKrakenKilled && (Map.getMap()[x + i][y + j].equals('X') || Map.getMap()[x + i][y + j].equals('K')))
                                || Map.getMap()[x + i][y + j].equals('Y') || Map.getMap()[x + i][y + j].equals('D')
                                || Map.getMap()[x + i][y + j].equals('R'))) {
                    jackMap[x + i][y + j] = Map.getMap()[x + i][y + j];
                }
            }
        }
    }

    /**
     * Returns from the given cell.
     *
     * @param cell     from which Jack returns
     * @param scenario of Jack's perception
     */
    public void returnBack(Cell cell, int scenario) {
        if (krakenKillCell.equals(cell)) {
            addKrakenZone();
            isKrakenKilled = false;
            krakenKillCell = new Cell(-1, -1);
        }
        if (cell.equals(Map.getTortugaCell())) {
            hasRum = false;
        }
        currentCell = cell.getPrevious();
    }

    /**
     * Removes kraken zone form Jack's map.
     */
    public void removeKrakenZone() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (jackMap[i][j].equals('X') || jackMap[i][j].equals('K')) {
                    jackMap[i][j] = 'M';
                }
            }
        }
    }


    /**
     * Adds kraken zone on Jack's map.
     */
    public void addKrakenZone() {
        for (int i = 0; i <= 8; ++i) {
            for (int j = 0; j <= 8; ++j) {
                if (jackMap[i][j].equals('M')) {
                    jackMap[i][j] = Map.getMap()[i][j];
                }
            }
        }
    }

    /**
     * Returns jackMap field.
     *
     * @return jackMap field
     */
    public Character[][] getJackMap() {
        return jackMap;
    }

    /**
     * Returns jackMap field.
     *
     * @return jackMap field
     */
    public Cell getInitCell() {
        return initCell;
    }

    /**
     * Sets initCell field.
     *
     * @param initCell field
     */
    public void setInitCell(Cell initCell) {
        this.initCell = initCell;
    }

    /**
     * Returns jackMap field.
     *
     * @return jackMap field
     */
    public Cell getCurrentCell() {
        return currentCell;
    }

    /**
     * Sets currentCell field.
     *
     * @param currentCell field
     */
    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    /**
     * Returns hasRum field.
     *
     * @return hasRum field
     */
    public Boolean getHasRum() {
        return hasRum;
    }

    /**
     * Sets hasRum field.
     *
     * @param hasRum field
     */
    public void setHasRum(boolean hasRum) {
        this.hasRum = hasRum;
    }

    public Boolean getKrakenKilled() {
        return isKrakenKilled;
    }

    public void setKrakenKilled(Boolean krakenKilled) {
        isKrakenKilled = krakenKilled;
    }
}

/**
 * Represents the path on the map.
 */
class Path {
    private ArrayList<Cell> path;
    private Integer size;

    /**
     * Constructor for the Path class.
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


/**
 * Represents the solution algorithm.
 */
class Algorithm {
    final static Integer INT_MAX = 1000000000;
    protected Path answer;
    protected Boolean isPathExist;
    protected Jack jack;

    /**
     * Constructor for the Algorithm class.
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

/**
 * Represents A* algorithm.
 */
class AStar extends Algorithm {

    /**
     * Constructor for the AStar algorithm.
     */
    public AStar() {
        super();
        jack.makeStep(jack.getInitCell(), Map.getScenario());
        solve();
    }

    /**
     * Solves the problem via AStar algorithm.
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
        jack.getCurrentCell().setH(max(abs(goal.getX() - jack.getCurrentCell().getX()), abs(goal.getY() - jack.getCurrentCell().getY())));
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
                        !jack.getJackMap()[cell.getX() + i][cell.getY() + j].equals('X') &&
                        !jack.getJackMap()[cell.getX() + i][cell.getY() + j].equals('Y') &&
                        !jack.getJackMap()[cell.getX() + i][cell.getY() + j].equals('K') &&
                        !jack.getJackMap()[cell.getX() + i][cell.getY() + j].equals('D') &&
                        !jack.getJackMap()[cell.getX() + i][cell.getY() + j].equals('R')) {
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
        int h = max(abs(goal.getX() - currentCell.getX()), abs(goal.getY() - currentCell.getY()));
        currentCell.setG(g);
        currentCell.setH(h);
    }
}

/**
 * Represents the Backtracking algorithm.
 */
class Backtracking extends Algorithm {
    private Cell globalCell;
    private boolean doesFound;
    private Integer minStepNumber;
    private Integer[][] minDistance;

    /**
     * Constructor for the Backtracking algorithm.
     */
    public Backtracking() {
        super();
        this.minStepNumber = INT_MAX;
        updateMinDistance();
        solve();
    }

    public void updateMinDistance(){
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

        if(stepNumber > minDistance[currentCell.getX()][currentCell.getY()]){
            jack.returnBack(currentCell, Map.getScenario());
            return;
        } else{
            minDistance[currentCell.getX()][currentCell.getY()] = stepNumber;
        }

        if ((doesFound && stepNumber > minStepNumber)
                || (!jack.getKrakenKilled() && (Map.getMap()[currentCell.getX()][currentCell.getY()] == 'X' || Map.getMap()[currentCell.getX()][currentCell.getY()] == 'K'))
                || Map.getMap()[currentCell.getX()][currentCell.getY()] == 'Y' || Map.getMap()[currentCell.getX()][currentCell.getY()] == 'D'
                || Map.getMap()[currentCell.getX()][currentCell.getY()] == 'R') {
            jack.returnBack(currentCell, Map.getScenario());
            return;
        }

        if (currentCell.equals(goal)) {
            if (stepNumber < minStepNumber) {
                globalCell = currentCell;
                doesFound = true;
                minStepNumber = stepNumber;
            }
            jack.returnBack(currentCell, Map.getScenario());
            return;
        }
        ArrayList<Cell> neighbors = generateNeighbors(currentCell, goal, currentPath);

        for (Cell neighbor : neighbors) {
            currentPath.add(neighbor);
            reachGoal(neighbor, goal, stepNumber + 1, currentPath);
            currentPath.remove(neighbor);
        }
        jack.returnBack(currentCell, Map.getScenario());
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
        return max(abs(cell.getX() - goal.getX()), abs(cell.getY() - goal.getY()));
    }
}

/**
 * Represents the output.
 */
class Output {

    /**
     * Constructor for the output.
     */
    public Output() {
        System.out.println("\nInput successfully entered\n##########  Map  ##########");
        System.out.println(outputMap('M', new StringBuilder()) + "\n");
    }

    /**
     * Sets the final output.
     *
     * @param algorithm name of the current algorithm
     * @param isFound   shows found we path or not
     * @param path      the found path
     * @param time      the time consumed
     */
    public void formOutput(String algorithm, boolean isFound, Path path, long time) {
        StringBuilder output = new StringBuilder("################  " + algorithm + "  ################\n");
        if (isFound) {
            output.append("Win\n");
            output.append(path.getPath().size()).append("\n");
            for (int i = 0; i < path.getPath().size(); i++) {
                output.append("[").append(path.getPath().get(i).getX()).append(",").append(path.getPath().get(i).getY()).append("] ");
            }
            output.append("\n");
            drawPath(path);
            output = outputMap('P', output);
            output.append(time / 1000000).append(" ms\n");
            cleanMap(path);
        } else {
            output.append("Lose\n");
        }
        System.out.println(output);
        fileOutput(output.toString(), algorithm);
    }

    /**
     * Draws the answer path on the map.
     *
     * @param path the path found
     */
    public void drawPath(Path path) {
        Map.getMap()[0][0] = '*';
        for (int i = 0; i < path.getPath().size(); ++i) {
            Integer x = path.getPath().get(i).getX(), y = path.getPath().get(i).getY();
            Map.getMap()[x][y] = '*';
        }
    }


    /**
     * Cleans the map after path drawing.
     *
     * @param path the path found
     */
    public void cleanMap(Path path) {
        for (int i = 0; i < path.getPath().size(); ++i) {
            Integer x = path.getPath().get(i).getX(), y = path.getPath().get(i).getY();
            Map.getMap()[x][y] = ' ';
        }
        Map.getMap()[Map.getJackCell().getX()][Map.getJackCell().getY()] = 'J';
        Map.getMap()[Map.getRockCell().getX()][Map.getRockCell().getY()] = 'R';
        Map.getMap()[Map.getTortugaCell().getX()][Map.getTortugaCell().getY()] = 'T';
        Map.getMap()[Map.getChestCell().getX()][Map.getChestCell().getY()] = 'C';
        Map.getMap()[Map.getKrakenCell().getX()][Map.getKrakenCell().getY()] = 'K';
        Map.getMap()[Map.getDavyCell().getX()][Map.getDavyCell().getY()] = 'D';
    }

    /**
     * Forms the map output.
     *
     * @param type   type of the map output
     * @param output formed already output string
     * @return updated output string
     */
    public StringBuilder outputMap(char type, StringBuilder output) {
        output.append("-------------------\n");
        output.append("  0 1 2 3 4 5 6 7 8\n");
        for (int i = 0; i <= 8; ++i) {
            for (int j = -1; j <= 8; ++j) {
                if (j == -1) {
                    output.append(i).append(" ");
                } else {
                    if ((type == 'P' && Map.getMap()[i][j] == '*') || (type == 'M' && Map.getMap()[i][j] != ' '))
                        output.append(Map.getMap()[i][j]).append(" ");
                    else
                        output.append("- ");
                }
            }
            output.append("\n");
        }
        output.append("-------------------\n");
        return output;
    }

    /**
     * Writes output to the file.
     *
     * @param output    output string
     * @param algorithm name of the algorithm
     */
    public void fileOutput(String output, String algorithm) {
        if (algorithm.equals("Backtracking")) {
            try (FileWriter writer = new FileWriter("outputBacktracking.txt", false)) {
                writer.write(output);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } else if (algorithm.equals("A*")) {
            try (FileWriter writer = new FileWriter("outputAStar.txt", false)) {
                writer.write(output);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}

/**
 * Represents the input.
 */
class Input {

    /**
     * Constructor for the Input class.
     */
    public Input() {
    }


    public void startInputReading() {
        while (true) {
            System.out.println("""
                    Welcome to the 'Compass and Pirates' game.
                    If you want to input the data RANDOMLY, press R
                    If you want to input the data MANUALLY, press M""");

            Scanner scanner = new Scanner(System.in);
            String generationType = scanner.nextLine();
            if (generationType.equals("R")) {
                new RandomInput();
                break;
            } else if (generationType.equals("M")) {
                new ManualInput();
                break;
            } else {
                System.out.println("Wrong input :( Please, try again");
            }
        }
    }


    public void enterScenario(String type) {
        String input = "";
        if (type.equals("C")) {
            System.out.println("Enter the scenario: ");
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
        } else {
            try (FileReader fr = new FileReader("input.txt")) {
                BufferedReader reader = new BufferedReader(fr);
                input = reader.readLine();
                input = reader.readLine();
                reader.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (input.equals("1") || input.equals("2"))
            Map.setScenario(Integer.parseInt(input));
        else {
            System.out.println("Wrong scenario!\n");
            Map.setIsCorrupted(true);
        }
    }


    public void drawKrakenPerception(int x, int y) {
        for (int i = -1; i < 2; ++i) {
            for (int j = -1; j < 2; ++j) {
                if (abs(i + j) == 1) {
                    Cell neighborCell = new Cell(x + i, y + j);
                    if (neighborCell.isValid() && Map.getMap()[x + i][y + j] != 'Y' && Map.getMap()[x + i][y + j] != 'R')
                        Map.getMap()[x + i][y + j] = 'X';
                }
            }
        }
    }

    public void drawDavyPerception(int x, int y) {
        for (int i = -1; i < 2; ++i) {
            for (int j = -1; j < 2; ++j) {
                Cell neighborCell = new Cell(x + i, y + j);
                if (neighborCell.isValid()) Map.getMap()[x + i][y + j] = 'Y';
            }
        }
    }

    public void drawEnemy(char name, Cell cell) {
        int x = cell.getX(), y = cell.getY();
        if (name == 'K')
            drawKrakenPerception(x, y);
        else if (name == 'D')
            drawDavyPerception(x, y);
        Map.addObject(name, x, y);
    }
}


class RandomInput extends Input {
    public RandomInput() {
        System.out.println("You chose RANDOM world generation\n" +
                "Please, select the scenario");
        super.enterScenario("C");
        generateMap();

        System.out.println("Jack: [" + Map.getJackCell().getX() + "," + Map.getJackCell().getY() + "]");
        System.out.println("Davy: [" + Map.getDavyCell().getX() + "," + Map.getDavyCell().getY() + "]");
        System.out.println("Kraken: [" + Map.getKrakenCell().getX() + "," + Map.getKrakenCell().getY() + "]");
        System.out.println("Rock: [" + Map.getRockCell().getX() + "," + Map.getRockCell().getY() + "]");
        System.out.println("Chest: [" + Map.getChestCell().getX() + "," + Map.getChestCell().getY() + "]");
        System.out.println("Tortuga: [" + Map.getTortugaCell().getX() + "," + Map.getTortugaCell().getY() + "]");
    }


    public void generateMap() {
        Map.createEmptyMap();
        Map.addObject('J', 0, 0);

        super.drawEnemy('D', placeRandEnemy('D'));
        super.drawEnemy('K', placeRandEnemy('K'));
        super.drawEnemy('R', placeRandEnemy('R'));

        placeRandObject('T');
        placeRandObject('C');
    }

    public void placeRandObject(char name) {
        Random random = new Random();
        int x = random.nextInt(9), y = random.nextInt(9);
        while (Map.getMap()[x][y] != ' ') {
            x = random.nextInt(9);
            y = random.nextInt(9);
        }

        Map.addObject(name, x, y);
    }

    public Cell placeRandEnemy(char enemy) {
        Random random = new Random();
        int x = random.nextInt(9), y = random.nextInt(9);
        if (enemy == 'D') {
            while (x - 1 <= 0 && y - 1 <= 0) {
                x = random.nextInt(9);
                y = random.nextInt(9);
            }
        } else if (enemy == 'K') {
            while ((x == 0 && y == 0) || (x == 1 && y == 0) || (x == 0 && y == 1) || (x == Map.getDavyCell().getX() && y == Map.getDavyCell().getY())) {
                x = random.nextInt(9);
                y = random.nextInt(9);
            }
        } else if (enemy == 'R')
            while ((x == 0 && y == 0) || (x == Map.getDavyCell().getX() && y == Map.getDavyCell().getY())) {
                x = random.nextInt(9);
                y = random.nextInt(9);
            }
        return new Cell(x, y);
    }
}


class ManualInput extends Input {

    public ManualInput() {
        String type;

        while (true) {
            System.out.println("""
                    You chose MANUAL world generation
                    If you want to input the data from CONSOLE, press C
                    If you want to input the data from FILE, press F""");

            Scanner scanner = new Scanner(System.in);
            String source = scanner.nextLine();
            if (source.equals("C")) {
                type = source;
                System.out.println("""
                        You chose MANUAL world generation from CONSOLE
                        Input coordinates of all object:""");
                break;
            } else if (source.equals("F")) {
                type = source;
                System.out.println("You chose MANUAL world generation from FILE");
                break;
            } else {
                System.out.println("Wrong source name :( Please, try again");
            }
        }

        Map.createEmptyMap();
        enterCoords(type);
        if (!Map.getIsCorrupted()) {
            super.enterScenario(type);
        }
    }


    public void enterCoords(String type) {
        ArrayList<Integer> objectsCoordinates = new ArrayList<>();
        try {
            String inputString;
            if (type.equals("C")) {
                Scanner scanner = new Scanner(System.in);
                inputString = scanner.nextLine();
            } else {
                FileReader fr = new FileReader("input.txt");
                BufferedReader reader = new BufferedReader(fr);
                inputString = reader.readLine();
                fr.close();
                reader.close();
            }

            String[] words = inputString.split(" ");
            for (String word : words) {
                objectsCoordinates.add(Integer.parseInt(String.valueOf(word.charAt(1))));
                objectsCoordinates.add(Integer.parseInt(String.valueOf(word.charAt(3))));
            }

            checkObject('J', objectsCoordinates.get(0), objectsCoordinates.get(1));
            if (!Map.getIsCorrupted()) checkEnemy('D', objectsCoordinates.get(2), objectsCoordinates.get(3));
            if (!Map.getIsCorrupted()) checkEnemy('K', objectsCoordinates.get(4), objectsCoordinates.get(5));
            if (!Map.getIsCorrupted()) checkEnemy('R', objectsCoordinates.get(6), objectsCoordinates.get(7));
            if (!Map.getIsCorrupted()) checkObject('C', objectsCoordinates.get(8), objectsCoordinates.get(9));
            if (!Map.getIsCorrupted()) checkObject('T', objectsCoordinates.get(10), objectsCoordinates.get(11));
        } catch (Exception e) {
            Map.setIsCorrupted(true);
            System.out.println("Wrong input style!");
        }
    }


    public void checkObject(char name, int x, int y) {
        if (!isValid(x, y) || Map.getMap()[x][y] != ' ' || (name == 'J' && (x != 0 || y != 0))) {
            invalidCoords();
            return;
        }

        Map.addObject(name, x, y);
    }

    public void checkEnemy(char enemy, int x, int y) {
        if (enemy == 'D') {
            if (!isValid(x, y) || (x - 1 <= 0 && y - 1 <= 0)) {
                invalidCoords();
                return;
            }
        } else if (enemy == 'K') {
            if (!isValid(x, y) || (x == 0 && y == 0) || (x == 1 && y == 0) || (x == 0 && y == 1) || (x == Map.getDavyCell().getX() && y == Map.getDavyCell().getY())) {
                invalidCoords();
                return;
            }
        } else if (enemy == 'R')
            if (!isValid(x, y) || (x == 0 && y == 0) || (x == Map.getDavyCell().getX() && y == Map.getDavyCell().getY())) {
                invalidCoords();
                return;
            }
        drawEnemy(enemy, new Cell(x, y));
    }

    public boolean isValid(int x, int y) {
        return (x >= 0 && x <= 8) && (y >= 0 && y <= 8);
    }

    public void invalidCoords() {
        Map.setIsCorrupted(true);
        System.out.println("Wrong input!\n(coordinates not in 0...8 range " +
                "or some actors intersect with each others zones)");
    }
}


class Solution {

    public Solution() {

        Input input = new Input();
        input.startInputReading();
        if (!Map.isCorrupted) {
            Output output = new Output();
            long startTime, endTime;

            startTime = System.nanoTime();
            Backtracking backtracking = new Backtracking();
            endTime = System.nanoTime();
            output.formOutput("Backtracking", backtracking.isPathExist, backtracking.answer, endTime - startTime);

            startTime = System.nanoTime();
            AStar aStar = new AStar();
            endTime = System.nanoTime();
            output.formOutput("A*", aStar.isPathExist, aStar.answer, endTime - startTime);
        }
    }
}