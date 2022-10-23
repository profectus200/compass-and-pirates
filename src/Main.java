import java.util.*;

import static java.lang.Math.*;

/**
 * The main class consisting of everything
 */
public class Main {
    final static int INT_MAX = 1000000000; // constant responsible for max value

    static class Cell {
        private int x, y;
        private Cell parent;
        private int g, h;

        public Cell() {
        }

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
            this.g = INT_MAX;
            this.h = INT_MAX;
            this.parent = null;
        }

        public boolean isValid() {
            return (x >= 0 && x <= 8) && (y >= 0 && y <= 8);
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public Cell getParent() {
            return parent;
        }

        public void setParent(Cell parent) {
            this.parent = parent;
        }

        public int getG() {
            return g;
        }

        public void setG(int g) {
            this.g = g;
        }

        public int getH() {
            return h;
        }

        public void setH(int h) {
            this.h = h;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell that = (Cell) o;
            return x == that.x && y == that.y;
        }
    }


    static class Map {
        protected static char[][] map = new char[9][9];
        protected static int scenario;
        protected static Cell jackCell, chestCell, rockCell, tortugaCell, krakenCell, davyCell;


        protected static Cell[] attackCells;

        public Map() {
        }

        public void createEmptyMap() {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    map[i][j] = ' ';
                }
            }
        }

//        public void addActor(char name, int x, int y, int perception) {
//            map[x][y] = name;
//            if (perception == 2) {
//                krakenCell = new Cell(x, y);
//            } else {
//                davyCell = new Cell(x, y);
//            }
//        }

        public void addActor(char name, int x, int y) {
            //fixme
            map[x][y] = name;
            switch (name) {
                case 'J' -> jackCell = new Cell(x, y);
                case 'R' -> rockCell = new Cell(x, y);
                case 'T' -> tortugaCell = new Cell(x, y);
                case 'C' -> chestCell = new Cell(x, y);
                case 'K' -> {
                    krakenCell = new Cell(x, y);
                    attackCells = new Cell[4];
                    attackCells[0] = new Cell(x + 1, y + 1);
                    attackCells[1] = new Cell(x - 1, y + 1);
                    attackCells[2] = new Cell(x + 1, y - 1);
                    attackCells[3] = new Cell(x - 1, y - 1);
                }
                case 'D' -> davyCell = new Cell(x, y);

            }
        }

        /**
         * Method to redraw enemies zone after removing cloak
         */

        public static char[][] getMap() {
            return map;
        }

        public static void setMap(char[][] map) {
            Map.map = map;
        }

        public static int getScenario() {
            return scenario;
        }

        public static void setScenario(int scenario) {
            Map.scenario = scenario;
        }

        public static Cell getJackCell() {
            return jackCell;
        }

        public static void setJackCell(Cell jackCell) {
            Map.jackCell = jackCell;
        }

        public static Cell getChestCell() {
            return chestCell;
        }

        public static void setChestCell(Cell chestCell) {
            Map.chestCell = chestCell;
        }

        public static Cell getRockCell() {
            return rockCell;
        }

        public static void setRockCell(Cell rockCell) {
            Map.rockCell = rockCell;
        }

        public static Cell getTortugaCell() {
            return tortugaCell;
        }

        public static void setTortugaCell(Cell tortugaCell) {
            Map.tortugaCell = tortugaCell;
        }

        public static Cell getKrakenCell() {
            return krakenCell;
        }

        public static void setKrakenCell(Cell krakenCell) {
            Map.krakenCell = krakenCell;
        }

        public static Cell getDavyCell() {
            return davyCell;
        }

        public static void setDavyCell(Cell davyCell) {
            Map.davyCell = davyCell;
        }

        public static Cell[] getAttackCells() {
            return attackCells;
        }

        public static void setAttackCells(Cell[] attackCells) {
            Map.attackCells = attackCells;
        }
    } // fixme kraken zone


    static class Jack {
        private Cell initCell, currentCell, chestCell, krakenKillCell;
        private boolean hasRum, isKrakenKilled;
        private final char[][] jackMap = new char[9][9];


        public Jack(int scenario) {
            initCell = Map.jackCell;
            initCell.g = 0;
            currentCell = initCell;
            chestCell = Map.chestCell;
            krakenKillCell = new Cell(-1, -1);
            hasRum = false;
            isKrakenKilled = false;
            makeStep(initCell, scenario);
        }


        public void makeStep(Cell cell, int scenario) {
            currentCell = cell;
            if (currentCell.equals(Map.tortugaCell)) { // found book
                hasRum = true;
            }
            if (hasRum && !isKrakenKilled) {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (abs(i) == 1 && abs(j) == 1 && (currentCell.x == Map.krakenCell.x + i) && (currentCell.y == Map.krakenCell.y + j)) {
                            removeKrakenZone();
                            isKrakenKilled = true;
                            krakenKillCell = currentCell;
                        }
                    }
                }
            }
            int x = currentCell.x, y = currentCell.y;
            for (int i = -2; i <= 2; i++) {
                for (int j = -2; j <= 2; j++) {
                    if ((scenario == 1 && (abs(i) > 1 || abs(j) > 1)) ||
                            (scenario == 2 && (abs(i) > 1 || abs(j) > 1) && (abs(i) + abs(j) > 2))) continue;
                    Cell tmpCell = new Cell(x + i, y + j);
                    if (tmpCell.isValid() &&
                            ((!isKrakenKilled && (Map.map[x + i][y + j] == 'X' || Map.map[x + i][y + j] == 'K'))
                                    || Map.map[x + i][y + j] == 'Y' || Map.map[x + i][y + j] == 'D'
                                    || Map.map[x + i][y + j] == 'R')) {
                        jackMap[x + i][y + j] = Map.map[x + i][y + j];
                    }
                }
            }
        }

        public void returnBack(Cell cell, int scenario) {
            if (krakenKillCell.equals(cell)) {
                addKrakenZone();
                isKrakenKilled = false;
                krakenKillCell = new Cell(-1, -1);
            }
            if (cell.equals(Map.tortugaCell)) {
                hasRum = false;
            }
            currentCell = cell.parent;
        }


        public void removeKrakenZone() {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (jackMap[i][j] == 'X' || jackMap[i][j] == 'K') {
                        jackMap[i][j] = 'M';
                    }
                }
            }
        }


        public void addKrakenZone() {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (jackMap[i][j] == 'M') {
                        jackMap[i][j] = Map.map[i][j];
                    }
                }
            }
        }

        public char[][] getJackMap() {
            return jackMap;
        }

        public Cell getInitCell() {
            return initCell;
        }

        public void setInitCell(Cell initCell) {
            this.initCell = initCell;
        }

        public Cell getCurrentCell() {
            return currentCell;
        }

        public void setCurrentCell(Cell currentCell) {
            this.currentCell = currentCell;
        }

        public Cell getChestCell() {
            return chestCell;
        }

        public void setChestCell(Cell chestCell) {
            this.chestCell = chestCell;
        }

        public boolean isHasRum() {
            return hasRum;
        }

        public void setHasRum(boolean hasRum) {
            this.hasRum = hasRum;
        }
    } // fixme


    static class Path {
        private ArrayList<Cell> path; // list with coordinates of steps inside path
        private int size; // size of path

        public Path() {
            path = new ArrayList<>();
            size = 0;
        }

        public void add(Cell cell) {
            path.add(cell);
            ++size;
        }

        public void addPath(Path newPath) {
            path.addAll(newPath.path);
            size += newPath.size;
        }

        public void remove(Cell cell) {
            for (int i = 0; i < size; i++) {
                if(path.get(i).equals(cell)){
                    path.remove(i);
                    --size;
                    break;
                }
            }
        }

        public boolean contains(int x, int y) {
            for (Cell cell : path) {
                if (cell.getX() == x && cell.getY() == y)
                    return true;
            }
            return false;
        }

        public ArrayList<Cell> getPath() {
            return path;
        }

        public void setPath(ArrayList<Cell> path) {
            this.path = path;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int newSize) {
            size = newSize;
        }

    }


    static class Algorithm extends Map {
        protected Path algoPath; // final path of algorithm
        protected boolean doesPathExist; // flag to check whether we can build path
        protected Jack jack; // instance of Harry for each algorithm

        public Algorithm(Jack jack) {
            this.doesPathExist = true;
            this.jack = jack;
            this.algoPath = new Path();
            this.algoPath.add(this.jack.initCell); // Path of algorithm starts with init position of harry
        }

        public Path buildPath(Cell currentCell) {
            if (currentCell == null) { // case when we haven't reached the goal
                return null;
            }

            Path finalPath = new Path();
            while (currentCell.parent != null) {
                finalPath.add(currentCell);
                currentCell = currentCell.parent; // going to initial cell

            }
            Collections.reverse(finalPath.path); // reversing path
            return finalPath;
        }

        public void choosePath(Path[] paths) {
            Path ansPath = paths[0];
            if (paths[1].getSize() < paths[0].getSize())
                ansPath = paths[1];

            if (ansPath.size >= INT_MAX) { // all paths are invalid
                doesPathExist = false;
            } else {
                algoPath = ansPath;
            }
        }

        public Path getAlgoPath() {
            return algoPath;
        }

        public void setAlgoPath(Path algoPath) {
            this.algoPath = algoPath;
        }

        public boolean isDoesPathExist() {
            return doesPathExist;
        }

        public void setDoesPathExist(boolean doesPathExist) {
            this.doesPathExist = doesPathExist;
        }

        public Jack getHarry() {
            return jack;
        }

        public void setHarry(Jack jack) {
            this.jack = jack;
        }
    }

    /**
     * A-star algorithm that goes to goal with some heuristics
     */
    static class AStar extends Algorithm {

        public AStar(Jack jack) {
            super(jack);
            jack = new Jack(Map.getScenario());
            jack.makeStep(jack.initCell, Map.getScenario()); // harry analyzes his 1st position
            solve(); // function to solve everything
        }

        public void solve() {
            // Harry -> Book -> Door
            jack.currentCell = jack.initCell;
            Path[] paths = new Path[2];

            paths[0] = reachGoal(jack.currentCell, getTortugaCell());
            if(paths[0].size != INT_MAX) {// Harry -> Cloak
                jack.currentCell = getTortugaCell();
                paths[0].addPath(reachGoal(jack.currentCell, getChestCell()));
            }

            jack.currentCell = jack.initCell;
            jack = new Jack(getScenario());
            paths[1] = reachGoal(jack.currentCell, getChestCell()); // Harry -> Book


           choosePath(paths);
        }


        public Path reachGoal(Cell initCell, Cell goal) {
            Path openPath = new Path();
            Path closedPath = new Path();
            jack.currentCell = initCell;
            jack.currentCell.g = 0;
            jack.currentCell.h = max(abs(goal.x - jack.currentCell.x), abs(goal.y - jack.currentCell.y));// fixme
            openPath.add(jack.currentCell); // starting point

            // loop until reach goal
            int ctr = 0; // counter to check infinite loop
            while (openPath.size != 0) {
                Cell leastCostStep = leastStep(openPath.path);
                openPath.remove(leastCostStep);
                closedPath.add(leastCostStep);// Remove step with least stepCost from openPath

                if (leastCostStep.equals(goal)) {
                    break;
                }
//                if (ctr++ > 5000000) { // case when we lose
//                    closedPath.setSize(INT_MAX);
//                    return closedPath;
//                }

                jack.makeStep(leastCostStep, getScenario()); // Harry makes a step
                // Generate probableStep's 8(or less) successors
                ArrayList<Cell> successors = generateSuccessors(leastCostStep, goal);
                for (Cell successor : successors) {
                    if (closedPath.contains(successor.x, successor.y)) {
//                        stepCost(leastCostStep, successor, goal, true); // computing f for successor
                        continue;
                    }
                    if (!openPath.contains(successor.x, successor.y) || successor.g + successor.h < findAndGetF(openPath.path, successor)) {
                        openPath.remove(successor);
                        openPath.add(successor);
                    }
                }
            }
            // delete info about parents
//            for (Cell cur : closedPath.path) {
//                cur.parent = null;
//            }
            if(closedPath.path.get(closedPath.size - 1).equals(goal)) {
                return super.buildPath(closedPath.path.get(closedPath.size - 1));
            }
            else{
                Path ans = new Path();
                ans.size = INT_MAX;
                return ans;
            }
        }


        public int findAndGetF(ArrayList<Cell> list, Cell cell) {
            for (Cell tmpCell : list) {
                if (tmpCell.equals(cell)) {
                    return tmpCell.g + tmpCell.h;
                }
            }
            return -1; // never should happen
        }

        /**
         * Method to generate probableStep's 8(or less) successors
         *
         * @param father probable step, parent of generated coords
         * @return list of successors
         */
        public ArrayList<Cell> generateSuccessors(Cell father, Cell goal) {
            ArrayList<Cell> successors = new ArrayList<>();
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    Cell tmpCell = new Cell(father.x + i, father.y + j);
                    if (tmpCell.isValid() && (i != 0 || j != 0) &&
                            jack.jackMap[father.x + i][father.y + j] != 'X' && // not take if next cell contains enemy
                            jack.jackMap[father.x + i][father.y + j] != 'Y' &&
                            jack.jackMap[father.x + i][father.y + j] != 'K' &&
                            jack.jackMap[father.x + i][father.y + j] != 'D' &&
                            jack.jackMap[father.x + i][father.y + j] != 'R') {
                        tmpCell.setParent(father);
                        computeStepCost(tmpCell, goal);
                        successors.add(tmpCell);
                    }
                }
            }
            return successors;
        }


        public Cell leastStep(ArrayList<Cell> openPath) {
            Cell probableStep = new Cell();
            int probableStepCost = INT_MAX;

            for (Cell step : openPath) {
                if (step.g + step.h < probableStepCost ||
                        (step.g + step.h == probableStepCost && step.g < probableStep.g)) {
                    probableStepCost = step.g + step.h;
                    probableStep = step;
                }
            }
            return probableStep;
        }


        public void computeStepCost(Cell currentCell, Cell goal) {
            int g = currentCell.parent.g + 1;
            int h = max(abs(goal.x - currentCell.x), abs(goal.y - currentCell.y));
            currentCell.g = g;
            currentCell.h = h;
        }
    }


    static class Backtracking extends Algorithm {
        private Cell globalCell;
        private final boolean[][] isVisited = new boolean[9][9];
        private boolean doesFound;
        private int minStepNumber = INT_MAX;
        int rec = 0;


        public Backtracking(Jack jack) {
            super(jack);
            solve();
        }


        public Path cleanData() {
            Path currentPath = new Path();
            currentPath.add(jack.currentCell);
            doesFound = false;
            minStepNumber = INT_MAX;
            rec = 0;
            return currentPath;
        }


        public void solve() {
            Path[] paths = new Path[2];
            jack.currentCell = jack.initCell;
            Path currentPath = new Path();
            currentPath = cleanData();

            backTrack(jack.currentCell, getTortugaCell(), 0, currentPath); // Book -> Door
            paths[0] = buildPath(globalCell);
            int tortugaMin = minStepNumber;
            jack.currentCell = getTortugaCell();
            currentPath = cleanData();
            backTrack(jack.currentCell, getChestCell(), 0, currentPath); // Book -> Door
            Path tmpPath1 = buildPath(globalCell);
            if (paths[0] == null || tmpPath1 == null || paths[0].path.get(0).equals(tmpPath1.path.get(0))) {
                paths[0] = new Path();
                paths[0].setSize(INT_MAX);
                doesFound = false;
                minStepNumber = INT_MAX;
            } else {
                paths[0].addPath(tmpPath1);
                doesFound = true;
                minStepNumber += tortugaMin;
            }

            jack = new Jack(scenario);
            jack.currentCell = jack.initCell;
            rec = 0;
            currentPath = new Path();
            currentPath.add(jack.currentCell);
            backTrack(jack.currentCell, getChestCell(), 0, currentPath); // Book -> Door
            paths[1] = buildPath(globalCell);
            if (paths[1] == null || (tmpPath1 != null && paths[1].path.get(0).equals(tmpPath1.path.get(0)))) {
                paths[1] = new Path();
                paths[1].setSize(INT_MAX);
            }
            choosePath(paths);
        }

        public void backTrack(Cell currentCell, Cell goal, int stepNumber, Path currentPath) {
            jack.makeStep(currentCell, getScenario()); // Harry made a step
            rec++;

            if (rec > 5000000 || (doesFound && stepNumber > minStepNumber)
                    || (!jack.isKrakenKilled && (getMap()[currentCell.x][currentCell.y] == 'X' || getMap()[currentCell.x][currentCell.y] == 'K'))
                    || getMap()[currentCell.x][currentCell.y] == 'Y' || getMap()[currentCell.x][currentCell.y] == 'D'
                    || getMap()[currentCell.x][currentCell.y] == 'R') {
                jack.returnBack(currentCell, getScenario());
                return;
            }

            if (currentCell.equals(goal)) {
                if (stepNumber < minStepNumber) {
                    globalCell = currentCell;
                    doesFound = true;
                    minStepNumber = stepNumber;
                }
                jack.returnBack(currentCell, getScenario());
                return;
            }
            ArrayList<Cell> neighbors = getNeighbors(currentCell, goal, currentPath);

            for (Cell neighbor : neighbors) {
                currentPath.add(neighbor);
                backTrack(neighbor, goal, stepNumber + 1, currentPath);
                currentPath.remove(neighbor);
            }
            jack.returnBack(currentCell, getScenario());
        }


        public ArrayList<Cell> getNeighbors(Cell currentCell, Cell goal, Path currentPath) {
            ArrayList<Cell> neighbors = new ArrayList<>();
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int curX = currentCell.x + i;
                    int curY = currentCell.y + j;
                    Cell curNeighbor = new Cell(curX, curY);
                    if ((i != 0 || j != 0) && curNeighbor.isValid()) {// not counting curStep as neighbor + validation check
                        if (!currentPath.contains(curX, curY) && jack.jackMap[curX][curY] != 'X' && jack.jackMap[curX][curY] != 'K'
                                && jack.jackMap[curX][curY] != 'D' && jack.jackMap[curX][curY] != 'Y' && jack.jackMap[curX][curY] != 'R') { // check whether coord is visited before + lose step
                            curNeighbor.setParent(currentCell);
                            curNeighbor.h = heuristics(curNeighbor, goal); // counting heuristics of each neighbor
                            neighbors.add(curNeighbor);
                        }
                    }
                }
            }
            neighbors.sort(Comparator.comparing(Cell::getH));
            return neighbors;
        }


        public int heuristics(Cell cell, Cell goal) {
            return max(abs(cell.x - goal.x), abs(cell.y - goal.y));
        }
    }

    /**
     * Class responsible for all output in our program
     */
    static class Output extends Map {
        /**
         * Constructor without params
         */
        public Output() {
            System.out.println("Input successfully entered\n##########  Here is the map:  ##########");
            outputMap();
        }

        /**
         * Method to output results/statistics after work of algo
         *
         * @param algoName  name of the algorithm
         * @param outcome   whether we win or lose
         * @param stepCount number of steps we took
         * @param path      path we go through
         * @param time      time algorithm took
         */
        public void finalOutput(String algoName, boolean outcome, int stepCount, Path path, long time) {
            System.out.println("\n\n################  " + algoName + "  ################");
            if (outcome) {
                System.out.println("We WIN!\nHarry successfully reached the door");
                System.out.println("Number of steps algorithm took: " + stepCount);
                drawPath(path);
                System.out.println("Final path: ");
                for (int i = 0; i < path.path.size(); i++) {
                    System.out.print("[" + path.path.get(i).x + "," + path.path.get(i).y + "] ");
                }
                System.out.println();
                System.out.println("Time of algorithm in nanoseconds: " + time);
            } else {
                System.out.println("Unfortunately we LOSE! T-T");
            }

        }

        /**
         * Method to draw stars showing steps we made
         *
         * @param path path we made
         */
        public void drawPath(Path path) {
            System.out.println("######  Here is the map with path  ######");
            for (int i = 0; i < path.path.size(); i++) { // start from 1 since 1st dot in initial position
                int x = path.path.get(i).x;
                int y = path.path.get(i).y;
                if (x == 0 && y == 0) {
                    continue;
                }
                getMap()[x][y] = '*';
            }
            outputMap();
            cleanMap(path); // clean map after drawing the path
        }

        /**
         * After running of one algo we draw there path, so we need to clean the
         * map for next algo since we have the only map for everything
         *
         * @param path path we made before to clean the map
         */
        public void cleanMap(Path path) {
            for (int i = 1; i < path.path.size(); i++) { // start from 1 since 1st dot in initial position
                int x = path.path.get(i).x;
                int y = path.path.get(i).y;
                getMap()[x][y] = ' ';
            }
            getMap()[getJackCell().x][getJackCell().y] = 'J';
            getMap()[getRockCell().x][getRockCell().y] = 'R';
            getMap()[getTortugaCell().x][getTortugaCell().y] = 'T';
            getMap()[getChestCell().x][getChestCell().y] = 'C';
            getMap()[getKrakenCell().x][getKrakenCell().y] = 'K';
            getMap()[getDavyCell().x][getDavyCell().y] = 'D';
        }

        /**
         * Method to output map properly
         */
        public void outputMap() {
            System.out.println("#\t#\t#\t#\t#\t#\t#\t#\t#\t#\t#\t");
            for (int i = 0; i < 9; i++) {
                for (int j = -1; j < 10; j++) {
                    if (j == -1 || j == 9) {
                        System.out.print("#\t");
                    } else {
                        if (getMap()[i][j] != 'X' && getMap()[i][j] != ' ' && getMap()[i][j] != '*') {
                            System.out.print(emoji(getMap()[i][j]) + "\t");
                        } else {
                            System.out.print(getMap()[i][j] + "\t");
                        }
                    }
                }
                System.out.println();
            }
            System.out.println("#\t#\t#\t#\t#\t#\t#\t#\t#\t#\t#\t");
        }

        /**
         * Customization of our objects/actors with emojis
         *
         * @param symbol char of object/actor
         * @return emoji in type string
         */
        public char emoji(char symbol) {
//            String ans = switch (symbol) {
//                case 'J' -> "🐺";
//                case 'B' -> "\uD83D\uDCD3";
//                case 'C' -> "\uD83E\uDDE5";
//                case 'D' -> "\uD83D\uDEAA";
//                case 'E' -> "\uD83E\uDD81";
//                default -> Character.toString(symbol);
//            };
            return symbol;
        }
    }

    /**
     * Class responsible for input in our program
     */
    static class Input extends Map {
        /**
         * Default constructor
         */
        public Input() {
        }

        /**
         * method to start input entering from user
         */
        public void startInputReading() {
            while (true) {
                System.out.println("""
                        Welcome to the 'Book Finding' game.
                        If you want to generate the map RANDOMLY, press R
                        If you want to generate the map MANUALLY, press M""");

                Scanner sc = new Scanner(System.in);
                String generationType = sc.nextLine();
                if (generationType.equals("R")) {
                    //RandomInput randomInput = new RandomInput();
                    break;
                } else if (generationType.equals("M")) {
                    ManualInput manualInput = new ManualInput();
                    break;
                } else {
                    System.out.println("Wrong input :( Please, try again");
                }
            }
        }

        /**
         * Method to enter scenario with handling all errors
         */
        public void enterScenario() {
            while (true) {
                Scanner sc = new Scanner(System.in);
                String tmpScenario;
                tmpScenario = sc.nextLine();
                if (tmpScenario.equals("1") || tmpScenario.equals("2")) {
                    scenario = Integer.parseInt(tmpScenario);
                    break;
                }
                System.out.println("Wrong input :( Please, try again");
            }
        }

        public void drawKrakenPerception(int x, int y) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (abs(i + j) == 1) {
                        Cell tmpCell = new Cell(x + i, y + j);
                        if (tmpCell.isValid() && Map.map[x + i][y + j] != 'Y' && Map.map[x + i][y + j] != 'R') {
                            getMap()[x + i][y + j] = 'X'; // zone
                        }
                    }
                }
            }
        }

        public void drawDavyPerception(int x, int y) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    Cell tmpCell = new Cell(x + i, y + j);
                    if (tmpCell.isValid()) {
                        getMap()[x + i][y + j] = 'Y'; // zone
                    }
                }
            }
        }

        public void drawEnemy(char name, Cell cell) {
            int x = cell.x, y = cell.y;
            if (name == 'K')
                drawKrakenPerception(x, y);
            else if (name == 'D')
                drawDavyPerception(x, y);
            super.addActor(name, x, y);
        }
    }

    /**
     * Class responsible for random input part
     */
//    static class RandomInput extends Input {
//        /**
//         * Constructor without params
//         */
//        public RandomInput() {
//            System.out.println("You chose RANDOM world generation\n" +
//                    "Please, select the scenario");
//            super.enterScenario();
//            generateMap();
//
//            System.out.println("Harry: [" + getJackCell().y + "," + getJackCell().x + "]");
//            System.out.println("Filch: [" + getKrakenCell().y + "," + getKrakenCell().x + "]");
//            System.out.println("Cat: [" + getDavyCell().y + "," + getDavyCell().x + "]");
//            System.out.println("Book: [" + getRockCell().y + "," + getRockCell().x + "]");
//            System.out.println("Cloak: [" + getTortugaCell().y + "," + getTortugaCell().x + "]");
//            System.out.println("Door: [" + getChestCell().y + "," + getChestCell().x + "]");
//        }
//
//        /**
//         * Method to generate random map without collisions and bugs
//         */
//        public void generateMap() {
//            super.createEmptyMap();
//            // place Mrs Norris (little enemy) without intersecting with others
//            super.drawEnemy('K', placeRandEnemy(1));
//            // place Argus Filch (big Enemy) without intersecting with others
//            super.drawEnemy('D', placeRandEnemy(1));
//            super.drawEnemy('R', placeRandEnemy(0));
//            Random random = new Random();
//            int x = random.nextInt(9);
//            int y = random.nextInt(9); // x, y coordinates
//            super.addActor('C', x, y); // place Cloak in random place
//            while (getMap()[x][y] != ' ') {
//                x = random.nextInt(9);
//                y = random.nextInt(9);
//            }
//            super.addActor('B', x, y); // place Book in random place
//            while (getMap()[x][y] != ' ') {
//                x = random.nextInt(9);
//                y = random.nextInt(9);
//            }
//            super.addActor('D', x, y); // place Door in random place
//            while (getMap()[x][y] != ' ') {
//                x = random.nextInt(9);
//                y = random.nextInt(9);
//            }
//            super.addActor('H', 0, 0); // place Harry at the left bottom
//        }
//
//        /**
//         * Method to place enemy without intersection
//         * with objects/actors and perception zone
//         *
//         * @param perception size of perception zone
//         * @return generated coordinate of placed enemy
//         */
//        public Cell placeRandEnemy(int perception) {
//            Random random = new Random();
//            int x = random.nextInt(9);
//            int y = random.nextInt(9);
//            while (x - perception <= 0 && y - perception <= 0) { // generate till enemy stops intersecting with Harry (0;0)
//                x = random.nextInt(9);
//                y = random.nextInt(9);
//            }
//            return new Cell(x, y);
//        }
//    }

    /**
     * Class responsible for manual input part
     */
    static class ManualInput extends Input {
        /**
         * Default constructor without parameters
         */
        public ManualInput() {
            System.out.println("""
                    You chose MANUAL world generation
                    Please, enter the characters' coordinates (Harry, Argus Filch, Mrs Norris, Book, Invisibility Cloak, Exit) in the form '[x1,y1], [x2, y2], ...'
                    And select the scenario (1 or 2)""");
            super.createEmptyMap();
            enterCoords();
            super.enterScenario();
        }

        /**
         * Method to handle manually entered coordinates
         */
        public void enterCoords() {
            ArrayList<Integer> tmpCoords = new ArrayList<>();
            while (true) {
                try {
                    Scanner sc = new Scanner(System.in);
                    String str = sc.nextLine();
                    String[] words = str.split(" ");
                    for (String word : words) {
                        tmpCoords.add(Integer.parseInt(String.valueOf(word.charAt(1))));
                        tmpCoords.add(Integer.parseInt(String.valueOf(word.charAt(3))));
                    }
                    if (checkCoords(tmpCoords)) {
                        Cell enemyCoord = new Cell(tmpCoords.get(2), tmpCoords.get(3));
                        super.drawEnemy('D', enemyCoord); // Place big Enemy on the map
                        enemyCoord = new Cell(tmpCoords.get(4), tmpCoords.get(5));
                        super.drawEnemy('K', enemyCoord);
                        enemyCoord = new Cell(tmpCoords.get(6), tmpCoords.get(7));
                        super.drawEnemy('R', enemyCoord);// Place small enemy on the map
                        super.addActor('C', tmpCoords.get(8), tmpCoords.get(9)); // Place Door on the map
                        super.addActor('T', tmpCoords.get(10), tmpCoords.get(11)); // Place Book on the map
                        super.addActor('J', tmpCoords.get(0), tmpCoords.get(1)); // Place Harry on the map

                        break;
                    } else { // semantically wrong input
                        System.out.println("Wrong input!\n(coordinates not in 0...8 range " +
                                "or some actors intersect with each others zones or Harry is not in [0,0])");
                    }

                } catch (Exception e) { // syntactically wrong input
                    System.out.println("Wrong input T-T, please try again");
                }
            }
        }

        /**
         * fixme
         * Method to check whether we have prohibited intersections
         *
         * @param coords list of coordinates of objects/actors
         * @return TRUE if no intersections and FALSE otherwise
         */
        public boolean checkCoords(ArrayList<Integer> coords) {
            if ((coords.get(6).equals(coords.get(10)) && coords.get(7).equals(coords.get(11))) || // B != E
                    (coords.get(0) != 0 || coords.get(1) != 0)) { // Harry is not in [0,0]
                return false;
            }
            return checkEnemy(coords.get(2), coords.get(3), 2) && // check E
                    checkEnemy(coords.get(4), coords.get(5), 2); // check e
        }

        /**
         * Method to check whether we have something prohibited in our perception zone
         *
         * @param x          X-coordinate
         * @param y          Y-coordinate
         * @param perception perception zone size
         * @return TRUE is nothing prohibited in perception zone and FALSE otherwise
         */
        public boolean checkEnemy(int x, int y, int perception) {
            for (int i = -perception; i <= perception; i++) {
                for (int j = -perception; j <= perception; j++) {
                    Cell tmpCoord = new Cell(x + i, y + j);
                    if (tmpCoord.isValid() && // check out of bound error
                            (getMap()[x + i][y + j] != ' ' && getMap()[x + i][y + j] != 'X'
                                    && getMap()[x + i][y + j] != 'E' && getMap()[x + i][y + j] != 'D')) {
                        return false; // something in perception zone
                    }
                }
            }
            return true;
        }
    }

    /**
     * Class that calls all methods above to input/solve/output our task with
     */
    static class Solution {
        /**
         * Constructor where everything is made
         */
        public Solution() {
            // Input
            Input input = new Input();
            input.startInputReading();

            // Initial Output
            Output output = new Output();
            // Algorithms
            // BackTracking
            Jack jack = new Jack(Map.scenario);
            long startTime1 = System.nanoTime();
            Backtracking backtracking = new Backtracking(jack);
            long endTime1 = System.nanoTime();
            output.finalOutput("Backtracking", backtracking.doesPathExist,
                    backtracking.algoPath.path.size(), backtracking.algoPath, endTime1 - startTime1);
            if (jack.hasRum) { // redraw perception zones of enemies
//                Map.addKrakenZone();
                jack.setHasRum(false);
            }
            // A*
            Jack jack2 = new Jack(Map.scenario);
            jack2.currentCell = jack2.initCell; // place harry at the beginning to run another algorithm
            long startTime2 = System.nanoTime();
            AStar aStar = new AStar(jack2);
            long endTime2 = System.nanoTime();
            output.finalOutput("A*", aStar.doesPathExist, aStar.algoPath.path.size(), aStar.algoPath, endTime2 - startTime2);
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution(); // making instance of class that does everything
    }
}