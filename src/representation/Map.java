package representation;

import static java.lang.Math.abs;

/**
 * Class which represents the game map.
 */
public class Map {
    private static Character[][] map = new Character[9][9];
    private static Integer scenario;
    private static Cell jackCell, chestCell, rockCell, tortugaCell, krakenCell, davyCell;
    private static boolean isCorrupted = false;

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
     * Draws kraken perception on the map.
     *
     * @param x coordinate
     * @param y coordinate
     */
    public static void drawKrakenPerception(int x, int y) {
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

    /**
     * Draws Davy perception on the map.
     *
     * @param x coordinate
     * @param y coordinate
     */
    public static void drawDavyPerception(int x, int y) {
        for (int i = -1; i < 2; ++i) {
            for (int j = -1; j < 2; ++j) {
                Cell neighborCell = new Cell(x + i, y + j);
                if (neighborCell.isValid()) Map.getMap()[x + i][y + j] = 'Y';
            }
        }
    }

    /**
     * Draws enemy on the map.
     *
     * @param name enemy's name
     * @param cell enemy's cell
     */
    public static void drawEnemy(char name, Cell cell) {
        int x = cell.getX(), y = cell.getY();
        if (name == 'K')
            drawKrakenPerception(x, y);
        else if (name == 'D')
            drawDavyPerception(x, y);
        Map.addObject(name, x, y);
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
     * @param map value
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
     * @param scenario value
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
     * @param jackCell value
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
     * @param chestCell value
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
     * @param rockCell value
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
     * @param tortugaCell value
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
     * @param krakenCell value
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
     * @param davyCell value
     */
    public static void setDavyCell(Cell davyCell) {
        Map.davyCell = davyCell;
    }

    /**
     * Returns isCorrupted field.
     *
     * @return isCorrupted value
     */
    public static boolean getIsCorrupted() {
        return isCorrupted;
    }

    /**
     * Sets isCorrupted field.
     *
     * @param isCorrupted value
     */
    public static void setIsCorrupted(boolean isCorrupted) {
        Map.isCorrupted = isCorrupted;
    }
}