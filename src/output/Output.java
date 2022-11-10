package output;

import representation.Map;
import representation.Path;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Represents the output.
 */
public class Output {

    /**
     * Constructor for the output.
     */
    public Output() {
        System.out.println("\ninput.Input successfully entered\n##########  representation.Map  ##########");
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
            output.append("[0,0] ");
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
        Map.drawDavyPerception(Map.getDavyCell().getX(), Map.getDavyCell().getY());
        Map.drawKrakenPerception(Map.getKrakenCell().getX(), Map.getKrakenCell().getY());
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
        if (algorithm.equals("algorithms.Backtracking")) {
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

