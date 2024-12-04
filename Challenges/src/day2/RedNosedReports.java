package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/*Brainstorming
*
* I have to delete every element from a list, and see if those two methods are true without those elements.
* Sum up the elements without them the methods were false, and if it is one element, then it is a safe level.
*
* Always create a new list in  a loop not containing the specific element
*
* */

public class RedNosedReports {
    public static void main(String[] args) {
        //Probe input
        List<Integer> level = new ArrayList<Integer>(Arrays.asList(2, 3, 5, 4, 6, 7));
        List<Integer> level2 = new ArrayList<Integer>(Arrays.asList(82, 84, 85, 87, 90, 92, 93, 91));

        //Input
        String inputTitle = "day2input.txt";

        //First puzzle solution
        System.out.println(isIncreasing(level2));
        System.out.println(sumOfSafeLevels(readByLine(inputTitle)));

        //Second puzzle solution
        //If only one level is problematic
        System.out.println(secondPuzzleResult(readByLine(inputTitle)));
    }

    public static int secondPuzzleResult(List<List<Integer>> allLevels) {
        int sum = 0;
        for (int i = 0; i < allLevels.size(); i++) {
            if (isDecreasing(allLevels.get(i)) || isIncreasing(allLevels.get(i))) {
                sum++;
            } else if(getFaultyLevels(allLevels.get(i))) {
                sum++;
            }
        }
        return sum;
    }


    public static boolean getFaultyLevels(List<Integer> level) {
        int sumOfFaultyLevels = 0;
        for (int i = 0; i < level.size(); i++) {
            List<Integer> newLevels = new ArrayList<>(level);
            newLevels.remove(i);

            if(isIncreasing(newLevels) || isDecreasing(newLevels)) {
                sumOfFaultyLevels++;
            }
        }

        if (sumOfFaultyLevels >= 1) {
            return true;
        }
        return false;
    }

    //If (isDecreasing || isDecreasing)

    //Need a method to check if the list is decreasing or increasing
    public static boolean isDecreasing(List<Integer> level) {
        for (int i = 0; i < level.size() - 1; i++) {
            if(level.get(i) < level.get(i + 1)) {
                return false;
            } else if (Math.abs(level.get(i) - level.get(i + 1)) > 3 || Math.abs(level.get(i) - level.get(i + 1)) < 1) {
                return false;
            }
        }
        return true;
    }

    //Separate method for increasing
    public static boolean isIncreasing(List<Integer> level) {
        for (int i = 0; i < level.size() - 1; i++) {
            if(level.get(i) > level.get(i + 1)) {
                return false;
            } else if (Math.abs(level.get(i) - level.get(i + 1)) > 3 || Math.abs(level.get(i) - level.get(i + 1)) < 1) {
                return false;
            }
        }
        return true;
    }

    //Sum up all the safe levels
    public static int sumOfSafeLevels(List<List<Integer>> allLevels) {
        int sum = 0;
        for (int i = 0; i < allLevels.size(); i++) {
            if (isDecreasing(allLevels.get(i)) || isIncreasing(allLevels.get(i))) {
                sum++;
            }
        }
        return sum;
    }

    //A method to read the input file
    public static List<List<Integer>> readByLine(String path) {
        List<List<Integer>> levels = new ArrayList<List<Integer>>();

        try {
            levels = Files.lines(Paths.get(path))
                            .map(line -> Arrays.stream(line.split(" "))
                            .map(Integer::valueOf)
                            .collect(Collectors.toList()))
                            .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Can not read file");;
        }
        return levels;
    }

}
