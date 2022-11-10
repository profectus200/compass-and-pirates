package solution;

import algorithms.AStar;
import algorithms.Backtracking;
import input.Input;
import output.Output;
import representation.Map;

/**
 * Class which represents the solution.
 */
public class Solution {

    /**
     * Method which solves everything.
     */
    public Solution() {
        Input input = new Input();
        input.startInputReading();
        if (!Map.getIsCorrupted()) {
            Output output = new Output();
            long startTime, endTime;

            startTime = System.nanoTime();
            Backtracking backtracking = new Backtracking();
            endTime = System.nanoTime();
            output.formOutput("algorithms.Backtracking", backtracking.getIsPathExist(), backtracking.getAnswer(), endTime - startTime);

            startTime = System.nanoTime();
            AStar aStar = new AStar();
            endTime = System.nanoTime();
            output.formOutput("A*", aStar.getIsPathExist(), aStar.getAnswer(), endTime - startTime);
        }
    }
}