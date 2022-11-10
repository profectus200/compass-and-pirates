package input;

import representation.Cell;
import representation.Map;

import java.util.Random;

/**
 * Class which represents random input.
 */
public class RandomInput extends Input {

    /**
     * Constructor for the input.RandomInput class.
     */
    public RandomInput() {
        System.out.println("You chose RANDOM world generation\n" +
                "Please, select the scenario");
        super.enterScenario("C");
        generateMap();

        System.out.println("representation.Jack: [" + Map.getJackCell().getX() + "," + Map.getJackCell().getY() + "]");
        System.out.println("Davy: [" + Map.getDavyCell().getX() + "," + Map.getDavyCell().getY() + "]");
        System.out.println("Kraken: [" + Map.getKrakenCell().getX() + "," + Map.getKrakenCell().getY() + "]");
        System.out.println("Rock: [" + Map.getRockCell().getX() + "," + Map.getRockCell().getY() + "]");
        System.out.println("Chest: [" + Map.getChestCell().getX() + "," + Map.getChestCell().getY() + "]");
        System.out.println("Tortuga: [" + Map.getTortugaCell().getX() + "," + Map.getTortugaCell().getY() + "]");
    }

    /**
     * Generates map.
     */
    public void generateMap() {
        Map.createEmptyMap();
        Map.addObject('J', 0, 0);

        Map.drawEnemy('D', placeRandEnemy('D'));
        Map.drawEnemy('K', placeRandEnemy('K'));
        Map.drawEnemy('R', placeRandEnemy('R'));

        placeRandObject('T');
        placeRandObject('C');
    }

    /**
     * Places random object on the map.
     *
     * @param name object's name
     */
    public void placeRandObject(char name) {
        Random random = new Random();
        int x = random.nextInt(9), y = random.nextInt(9);
        while (Map.getMap()[x][y] != ' ') {
            x = random.nextInt(9);
            y = random.nextInt(9);
        }

        Map.addObject(name, x, y);
    }

    /**
     * Places random enemy on the map.
     *
     * @param enemy enemy's name
     * @return enemy's cell
     */
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