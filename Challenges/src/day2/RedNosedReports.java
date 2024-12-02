package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class RedNosedReports {
    public static void main(String[] args) {
        //Probe input
        List<Integer> level = new ArrayList<Integer>(Arrays.asList(8, 10, 11, 14, 23));

        //Input
        String inputTitle = "day2input.txt";

        System.out.println(isIncreasing(level));
        System.out.println(sumOfSafeLevels(readByLine(inputTitle)));
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
