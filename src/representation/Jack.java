package representation;

import static java.lang.Math.abs;

/**
 * Represents representation.Jack Sparrow.
 */
public class Jack {
    private Cell initCell, currentCell;
    private char[][] jackMap;

    /**
     * Constructor for the representation.Jack class.
     *
     * @param scenario of representation.Jack's perception
     */
    public Jack(int scenario) {
        this.initCell = Map.getJackCell();
        this.initCell.setG(0);
        this.currentCell = initCell;
        createEmptyMap();
        makeStep(initCell, scenario);
    }

    /**
     * Creates empty representation.Jack's map.
     */
    public void createEmptyMap() {
        jackMap = new char[9][9];
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) jackMap[i][j] = ' ';
        }
    }

    /**
     * Makes step on the given cell.
     *
     * @param cell     on which representation.Jack makes step
     * @param scenario of representation.Jack's perception
     */
    public void makeStep(Cell cell, int scenario) {
        currentCell = cell;
        cell.setHasRum(cell.getPrevious() != null && cell.getPrevious().isHasRum());
        if (cell.getPrevious() != null && cell.getPrevious().isKrakenKilled()) {
            cell.setKrakenKilled(true);
            removeKrakenZone();
        } else {
            cell.setKrakenKilled(false);
            addKrakenZone();
        }
        if (currentCell.equals(Map.getTortugaCell())) cell.setHasRum(true);
        if (cell.isHasRum() && !cell.isKrakenKilled()) {
            for (int i = -1; i < 2; ++i) {
                for (int j = -1; j < 2; ++j) {
                    if (abs(i) == 1 && abs(j) == 1 && (currentCell.getX().equals(Map.getKrakenCell().getX() + i)) && (currentCell.getY().equals(Map.getKrakenCell().getY() + j))) {
                        removeKrakenZone();
                        cell.setKrakenKilled(true);
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
                        ((!cell.isKrakenKilled() && (Map.getMap()[x + i][y + j].equals('X') || Map.getMap()[x + i][y + j].equals('K')))
                                || Map.getMap()[x + i][y + j].equals('Y') || Map.getMap()[x + i][y + j].equals('D')
                                || Map.getMap()[x + i][y + j].equals('R'))) {
                    jackMap[x + i][y + j] = Map.getMap()[x + i][y + j];
                }
            }
        }
    }

    /**
     * Removes kraken zone form representation.Jack's map.
     */
    public void removeKrakenZone() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (jackMap[i][j] == 'X' || jackMap[i][j] == 'K') {
                    jackMap[i][j] = 'M';
                }
            }
        }
    }

    /**
     * Adds kraken zone on representation.Jack's map.
     */
    public void addKrakenZone() {
        for (int i = 0; i <= 8; ++i) {
            for (int j = 0; j <= 8; ++j) {
                if (jackMap[i][j] == 'M') {
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
    public char[][] getJackMap() {
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

}
