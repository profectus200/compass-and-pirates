import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.lang.Math.*;


public class Main {
    final static int INT_MAX = 1000000000;

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
        protected static boolean isCorrupted = false;


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


        public void addActor(char name, int x, int y) {
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
    }


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
    }


    static class Path {
        private ArrayList<Cell> path;
        private int size;

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
                if (path.get(i).equals(cell)) {
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
        protected Path answer;
        protected boolean doesPathExist;
        protected Jack jack;

        public Algorithm() {
            this.doesPathExist = true;
            this.jack = new Jack(getScenario());
            this.answer = new Path();
            this.answer.add(this.jack.initCell);
        }

        public Path buildPath(Cell currentCell) {
            if (currentCell == null) {
                return null;
            }
            Path finalPath = new Path();
            while (currentCell.parent != null) {
                finalPath.add(currentCell);
                currentCell = currentCell.parent;

            }
            Collections.reverse(finalPath.path);
            return finalPath;
        }

        public void choosePath(Path[] paths) {
            Path bestPath = paths[0];
            if (paths[1].getSize() < paths[0].getSize())
                bestPath = paths[1];

            if (bestPath.size >= INT_MAX) {
                doesPathExist = false;
            } else {
                answer = bestPath;
            }
        }

        public Path getAnswer() {
            return answer;
        }

        public void setAnswer(Path answer) {
            this.answer = answer;
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


    static class AStar extends Algorithm {

        public AStar() {
            super();
            jack.makeStep(jack.initCell, getScenario());
            solve();
        }

        public void solve() {
            jack.currentCell = jack.initCell;
            Path[] paths = new Path[2];

            paths[0] = reachGoal(jack.currentCell, getTortugaCell());
            if (paths[0].size != INT_MAX) {
                jack.currentCell = getTortugaCell();
                paths[0].addPath(reachGoal(jack.currentCell, getChestCell()));
            }

            jack.currentCell = jack.initCell;
            jack = new Jack(getScenario());
            paths[1] = reachGoal(jack.currentCell, getChestCell());

            choosePath(paths);
        }


        public Path reachGoal(Cell initCell, Cell goal) {
            Path openPath = new Path();
            Path closedPath = new Path();
            jack.currentCell = initCell;
            jack.currentCell.g = 0;
            jack.currentCell.h = max(abs(goal.x - jack.currentCell.x), abs(goal.y - jack.currentCell.y));
            openPath.add(jack.currentCell);

            while (openPath.size != 0) {
                Cell leastCostStep = findCheapestStep(openPath.path);
                openPath.remove(leastCostStep);
                closedPath.add(leastCostStep);

                if (leastCostStep.equals(goal)) {
                    break;
                }

                jack.makeStep(leastCostStep, getScenario());
                ArrayList<Cell> successors = generateNeighbors(leastCostStep, goal);
                for (Cell successor : successors) {
                    if (closedPath.contains(successor.x, successor.y)) {
                        continue;
                    }
                    if (!openPath.contains(successor.x, successor.y) || successor.g + successor.h < findOldF(openPath.path, successor)) {
                        openPath.remove(successor);
                        openPath.add(successor);
                    }
                }
            }

            if (closedPath.path.get(closedPath.size - 1).equals(goal)) {
                return super.buildPath(closedPath.path.get(closedPath.size - 1));
            } else {
                Path ans = new Path();
                ans.size = INT_MAX;
                return ans;
            }
        }


        public int findOldF(ArrayList<Cell> list, Cell cell) {
            for (Cell listCell : list) {
                if (listCell.equals(cell)) {
                    return listCell.g + listCell.h;
                }
            }
            return -1;
        }


        public ArrayList<Cell> generateNeighbors(Cell father, Cell goal) {
            ArrayList<Cell> neighbors = new ArrayList<>();
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    Cell neighborCell = new Cell(father.x + i, father.y + j);
                    if (neighborCell.isValid() && (i != 0 || j != 0) &&
                            jack.jackMap[father.x + i][father.y + j] != 'X' &&
                            jack.jackMap[father.x + i][father.y + j] != 'Y' &&
                            jack.jackMap[father.x + i][father.y + j] != 'K' &&
                            jack.jackMap[father.x + i][father.y + j] != 'D' &&
                            jack.jackMap[father.x + i][father.y + j] != 'R') {
                        neighborCell.setParent(father);
                        computeHeuristics(neighborCell, goal);
                        neighbors.add(neighborCell);
                    }
                }
            }
            return neighbors;
        }


        public Cell findCheapestStep(ArrayList<Cell> openPath) {
            Cell cheapestStep = new Cell();
            int cheapestStepCost = INT_MAX;

            for (Cell cell : openPath) {
                if (cell.g + cell.h < cheapestStepCost ||
                        (cell.g + cell.h == cheapestStepCost && cell.g < cheapestStep.g)) {
                    cheapestStepCost = cell.g + cell.h;
                    cheapestStep = cell;
                }
            }
            return cheapestStep;
        }


        public void computeHeuristics(Cell currentCell, Cell goal) {
            int g = currentCell.parent.g + 1;
            int h = max(abs(goal.x - currentCell.x), abs(goal.y - currentCell.y));
            currentCell.g = g;
            currentCell.h = h;
        }
    }


    static class Backtracking extends Algorithm {
        private Cell globalCell;
        private boolean doesFound;
        private int minStepNumber;
        private int rec;


        public Backtracking() {
            super();
            this.rec = 0;
            this.minStepNumber = INT_MAX;
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
            Path currentPath;
            currentPath = cleanData();

            backTrack(jack.currentCell, getTortugaCell(), 0, currentPath);
            paths[0] = buildPath(globalCell);
            int tortugaMin = minStepNumber;
            jack.currentCell = getTortugaCell();
            currentPath = cleanData();
            backTrack(jack.currentCell, getChestCell(), 0, currentPath);
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
            backTrack(jack.currentCell, getChestCell(), 0, currentPath);
            paths[1] = buildPath(globalCell);
            if (paths[1] == null || (tmpPath1 != null && paths[1].path.get(0).equals(tmpPath1.path.get(0)))) {
                paths[1] = new Path();
                paths[1].setSize(INT_MAX);
            }
            choosePath(paths);
        }

        public void backTrack(Cell currentCell, Cell goal, int stepNumber, Path currentPath) {
            jack.makeStep(currentCell, getScenario());
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
            ArrayList<Cell> neighbors = generateNeighbors(currentCell, goal, currentPath);

            for (Cell neighbor : neighbors) {
                currentPath.add(neighbor);
                backTrack(neighbor, goal, stepNumber + 1, currentPath);
                currentPath.remove(neighbor);
            }
            jack.returnBack(currentCell, getScenario());
        }


        public ArrayList<Cell> generateNeighbors(Cell currentCell, Cell goal, Path currentPath) {
            ArrayList<Cell> neighbors = new ArrayList<>();

            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int x = currentCell.x + i;
                    int y = currentCell.y + j;
                    Cell curNeighbor = new Cell(x, y);
                    if ((i != 0 || j != 0) && curNeighbor.isValid()) {
                        if (!currentPath.contains(x, y) && jack.jackMap[x][y] != 'X' && jack.jackMap[x][y] != 'K'
                                && jack.jackMap[x][y] != 'D' && jack.jackMap[x][y] != 'Y' && jack.jackMap[x][y] != 'R') {
                            curNeighbor.setParent(currentCell);
                            curNeighbor.h = computeHeuristics(curNeighbor, goal);
                            neighbors.add(curNeighbor);
                        }
                    }
                }
            }
            neighbors.sort(Comparator.comparing(Cell::getH));
            return neighbors;
        }


        public int computeHeuristics(Cell cell, Cell goal) {
            return max(abs(cell.x - goal.x), abs(cell.y - goal.y));
        }
    }


    static class Output extends Map {

        public Output() {
            System.out.println("\nInput successfully entered\n##########  Map  ##########");
            System.out.println(outputMap('M', new StringBuilder()) + "\n");
        }


        public void finalOutput(String algorithm, boolean outcome, int stepCount, Path path, long time) {
            StringBuilder output = new StringBuilder("################  " + algorithm + "  ################\n");
            if (outcome) {
                output.append("Win\n");
                output.append(stepCount).append("\n");
                for (int i = 0; i < path.path.size(); i++) {
                    output.append("[").append(path.path.get(i).x).append(",").append(path.path.get(i).y).append("] ");
                }
                output.append("\n");
                drawPath(path);
                output = outputMap('P', output);
                output.append(time / 1000000).append("ms\n");
                cleanMap(path);
            } else {
                output.append("Lose\n");
            }
            System.out.println(output);
            fileOutput(output.toString(), algorithm);
        }


        public void drawPath(Path path) {
            getMap()[0][0] = '*';
            for (int i = 0; i < path.path.size(); i++) {
                int x = path.path.get(i).x;
                int y = path.path.get(i).y;
                getMap()[x][y] = '*';
            }
//            outputMap('P', algorithm);
//            cleanMap(path);
        }


        public void cleanMap(Path path) {
            for (int i = 0; i < path.path.size(); i++) {
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


        public StringBuilder outputMap(char type, StringBuilder output) {
            output.append("-------------------\n");
            output.append("  0 1 2 3 4 5 6 7 8\n");
            for (int i = 0; i < 9; i++) {
                for (int j = -1; j < 9; j++) {
                    if (j == -1) {
                        output.append(i).append(" ");
                    } else {
                        if ((type == 'P' && getMap()[i][j] == '*') || (type == 'M' && getMap()[i][j] != ' '))
                            output.append(getMap()[i][j]).append(" ");
                        else
                            output.append("- ");
                    }
                }
                output.append("\n");
            }
            output.append("-------------------\n");
            return output;
        }


        public void fileOutput(String output, String algorithm){
            if(algorithm.equals("Backtracking")){
                try(FileWriter writer = new FileWriter("outputBacktracking.txt", false)){
                    writer.write(output);
                } catch (IOException ex){
                    System.out.println(ex.getMessage());
                }
            } else if(algorithm.equals("A*")){
                try(FileWriter writer = new FileWriter("outputAStar.txt", false)){
                    writer.write(output);
                } catch (IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
    }


    static class Input extends Map {

        public Input() {
        }

        public void startInputReading() {
            while (true) {
                System.out.println("""
                        Welcome to the 'Compass and Pirates' game.
                        If you want to generate the map RANDOMLY, press R
                        If you want to generate the map MANUALLY, press M""");

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
            String tmpScenario = "";
            if (type.equals("C")) {
                System.out.println("Enter the scenario: ");
                Scanner scanner = new Scanner(System.in);
                tmpScenario = scanner.nextLine();
            } else {
                try (FileReader fr = new FileReader("input.txt")) {
                    BufferedReader reader = new BufferedReader(fr);
                    tmpScenario = reader.readLine();
                    tmpScenario = reader.readLine();
                    reader.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if (tmpScenario.equals("1") || tmpScenario.equals("2"))
                scenario = Integer.parseInt(tmpScenario);
            else {
                System.out.println("Wrong scenario!\n");
                isCorrupted = true;
            }
        }


        public void drawKrakenPerception(int x, int y) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (abs(i + j) == 1) {
                        Cell tmpCell = new Cell(x + i, y + j);
                        if (tmpCell.isValid() && Map.map[x + i][y + j] != 'Y' && Map.map[x + i][y + j] != 'R') {
                            getMap()[x + i][y + j] = 'X';
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
                        getMap()[x + i][y + j] = 'Y';
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


    static class RandomInput extends Input {
        public RandomInput() {
            System.out.println("You chose RANDOM world generation\n" +
                    "Please, select the scenario");
            super.enterScenario("C");
            generateMap();

            System.out.println("Harry: [" + getJackCell().y + "," + getJackCell().x + "]");
            System.out.println("Filch: [" + getKrakenCell().y + "," + getKrakenCell().x + "]");
            System.out.println("Cat: [" + getDavyCell().y + "," + getDavyCell().x + "]");
            System.out.println("Book: [" + getRockCell().y + "," + getRockCell().x + "]");
            System.out.println("Cloak: [" + getTortugaCell().y + "," + getTortugaCell().x + "]");
            System.out.println("Door: [" + getChestCell().y + "," + getChestCell().x + "]");
        }


        public void generateMap() {
            super.createEmptyMap();
            super.addActor('J', 0, 0);

            super.drawEnemy('D', placeRandEnemy('D'));
            super.drawEnemy('K', placeRandEnemy('K'));
            super.drawEnemy('R', placeRandEnemy('R'));

            placeRandObject('T');
            placeRandObject('C');
        }

        public void placeRandObject(char name) {
            Random random = new Random();
            int x = random.nextInt(9);
            int y = random.nextInt(9);
            while (Map.getMap()[x][y] != ' ') {
                x = random.nextInt(9);
                y = random.nextInt(9);
            }

            super.addActor(name, x, y);
        }

        public Cell placeRandEnemy(char enemy) {
            Random random = new Random();
            int x = random.nextInt(9);
            int y = random.nextInt(9);
            if (enemy == 'D') {
                while (x - 1 <= 0 && y - 1 <= 0) {
                    x = random.nextInt(9);
                    y = random.nextInt(9);
                }
            } else if (enemy == 'K') {
                while ((x == 0 && y == 0) || (x == 1 && y == 0) || (x == 0 && y == 1) || (x == davyCell.getX() && y == davyCell.getY())) {
                    x = random.nextInt(9);
                    y = random.nextInt(9);
                }
            } else if (enemy == 'R')
                while ((x == 0 && y == 0) || (x == davyCell.getX() && y == davyCell.getY())) {
                    x = random.nextInt(9);
                    y = random.nextInt(9);
                }
            return new Cell(x, y);
        }
    }


    static class ManualInput extends Input {

        public ManualInput() {
            String type;

            while (true) {
                System.out.println("""
                        You chose MANUAL world generation
                        If you want to generate the map from CONSOLE, press C
                        If you want to generate the map from FILE, press F""");

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

            super.createEmptyMap();
            enterCoords(type);
            if (!isCorrupted) {
                super.enterScenario(type);
            }
        }


        public void enterCoords(String type) {
            ArrayList<Integer> tmpCoords = new ArrayList<>();
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
                    tmpCoords.add(Integer.parseInt(String.valueOf(word.charAt(1))));
                    tmpCoords.add(Integer.parseInt(String.valueOf(word.charAt(3))));
                }

                checkObject('J', tmpCoords.get(0), tmpCoords.get(1));
                if (!isCorrupted) checkEnemy('D', tmpCoords.get(2), tmpCoords.get(3));
                if (!isCorrupted) checkEnemy('K', tmpCoords.get(4), tmpCoords.get(5));
                if (!isCorrupted) checkEnemy('R', tmpCoords.get(6), tmpCoords.get(7));
                if (!isCorrupted) checkObject('C', tmpCoords.get(8), tmpCoords.get(9));
                if (!isCorrupted) checkObject('T', tmpCoords.get(10), tmpCoords.get(11));
            } catch (Exception e) {
                isCorrupted = true;
                System.out.println("Wrong input style!");
            }
        }


        public void checkObject(char name, int x, int y) {
            if (!isValid(x, y) || Map.getMap()[x][y] != ' ' || (name == 'J' && (x != 0 || y != 0))) {
                invalidCoords();
                return;
            }

            super.addActor(name, x, y);
        }

        public void checkEnemy(char enemy, int x, int y) {
            if (enemy == 'D') {
                if (!isValid(x, y) || (x - 1 <= 0 && y - 1 <= 0)) {
                    invalidCoords();
                    return;
                }
            } else if (enemy == 'K') {
                if (!isValid(x, y) || (x == 0 && y == 0) || (x == 1 && y == 0) || (x == 0 && y == 1) || (x == davyCell.getX() && y == davyCell.getY())) {
                    invalidCoords();
                    return;
                }
            } else if (enemy == 'R')
                if (!isValid(x, y) || (x == 0 && y == 0) || (x == davyCell.getX() && y == davyCell.getY())) {
                    invalidCoords();
                    return;
                }
            drawEnemy(enemy, new Cell(x, y));
        }

        public boolean isValid(int x, int y) {
            return (x >= 0 && x <= 8) && (y >= 0 && y <= 8);
        }

        public void invalidCoords() {
            isCorrupted = true;
            System.out.println("Wrong input!\n(coordinates not in 0...8 range " +
                    "or some actors intersect with each others zones)");
        }
    }


    static class Solution {

        public Solution() {

            Input input = new Input();
            input.startInputReading();
            if (!Map.isCorrupted) {

                Output output = new Output();

                long startTime1 = System.nanoTime();
                Backtracking backtracking = new Backtracking();
                long endTime1 = System.nanoTime();
                output.finalOutput("Backtracking", backtracking.doesPathExist,
                        backtracking.answer.path.size(), backtracking.answer, endTime1 - startTime1);

                long startTime2 = System.nanoTime();
                AStar aStar = new AStar();
                long endTime2 = System.nanoTime();
                output.finalOutput("A*", aStar.doesPathExist, aStar.answer.path.size(), aStar.answer, endTime2 - startTime2);
            }
        }
    }

    public static void main(String[] args) {
        new Solution();
    }
}