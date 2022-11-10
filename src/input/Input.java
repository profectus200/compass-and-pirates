package input;

import representation.Map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Represents the input.
 */
public class Input {

    /**
     * Constructor for the input.Input class.
     */
    public Input() {
    }


    /**
     * Starts input reading.
     */
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


    /**
     * Enters the scenario.
     *
     * @param type console or file
     */
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
}