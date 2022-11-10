package input;

import representation.Cell;
import representation.Map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class which represents manual input.
 */
public class ManualInput extends Input {

    /**
     * Constructor for input.ManualInput.
     */
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

    /**
     * Enters object coords.
     *
     * @param type file or console
     */
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

    /**
     * Checks object validness.
     *
     * @param name enemy's name
     * @param x    coordinate
     * @param y    coordinate
     */
    public void checkObject(char name, int x, int y) {
        if (!isValid(x, y) || (Map.getMap()[x][y] != ' ' && !(x == 0 || y == 0)) || (name == 'J' && (x != 0 || y != 0))) {
            invalidCoords();
            return;
        }

        Map.addObject(name, x, y);
    }

    /**
     * Checks enemy's validness.
     *
     * @param enemy enemy's name
     * @param x     coordinate
     * @param y     coordinate
     */
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
        Map.drawEnemy(enemy, new Cell(x, y));
    }

    /**
     * Checks validness of coordinates.
     *
     * @param x coordinate
     * @param y coordinate
     * @return true if coords are valid and false otherwise
     */
    public boolean isValid(int x, int y) {
        return (x >= 0 && x <= 8) && (y >= 0 && y <= 8);
    }

    /**
     * Runs when coords are invalid
     */
    public void invalidCoords() {
        Map.setIsCorrupted(true);
        System.out.println("Wrong input!\n(coordinates not in 0...8 range " +
                "or some actors intersect with each others zones)");
    }
}
