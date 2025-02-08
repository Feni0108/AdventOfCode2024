package day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PrintQueue {

    public static List<List<Integer>> pairs = new ArrayList<>(List.of(
            new ArrayList<>(List.of(47, 53)),
            new ArrayList<>(List.of(97,13)),
            new ArrayList<>(List.of(97, 61)),
            new ArrayList<>(List.of(97, 47)),
            new ArrayList<>(List.of(75, 29)),
            new ArrayList<>(List.of(61, 13)),
            new ArrayList<>(List.of(75, 53)),
            new ArrayList<>(List.of(29, 13)),
            new ArrayList<>(List.of(97, 29)),
            new ArrayList<>(List.of(53, 29)),
            new ArrayList<>(List.of(61, 53)),
            new ArrayList<>(List.of(97, 53)),
            new ArrayList<>(List.of(61, 29)),
            new ArrayList<>(List.of(47, 13)),
            new ArrayList<>(List.of(75, 47)),
            new ArrayList<>(List.of(97, 75)),
            new ArrayList<>(List.of(47, 61)),
            new ArrayList<>(List.of(75, 61)),
            new ArrayList<>(List.of(47, 29)),
            new ArrayList<>(List.of(75, 13)),
            new ArrayList<>(List.of(53, 13))
    ));

    public static List<List<Integer>> updates = new ArrayList<>(List.of(
            new ArrayList<>(List.of(75,47,61,53,29)),
            new ArrayList<>(List.of(97,61,53,29,13)),
            new ArrayList<>(List.of(75,29,13)),
            new ArrayList<>(List.of(75,97,47,61,53)),
            new ArrayList<>(List.of(61,13,29)),
            new ArrayList<>(List.of(97,13,75,29,47))
    ));

    public static List<List<Integer>> realPairs = readFromInput("day5input.txt", "\\|");

    public static List<List<Integer>> realUpdates = readFromInput("day5input2.txt", ",");

    public static void main(String[] args) {
        System.out.println(getFirstPartResult(realPairs, realUpdates));

    }

    // It is a function to be able to read numbers from a file, separated from each other with a specific regex
    public static List<List<Integer>> readFromInput(String path, String regex) {
        List<List<Integer>> realPairs = new ArrayList<List<Integer>>();

        try {
            realPairs = Files.lines(Paths.get(path))
                    .map(line -> Arrays.stream(line.split(regex))
                            .map(Integer::valueOf)
                            .collect(Collectors.toList()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Can not read file");;
        }
        return realPairs;
    }

    // This is the function which ultimately solves the second part of this challenge
    // It only needs the two main source of input, the pairs and the update list
    // It checks if the order of one update is correct, and if it is not, then uses
    // a function which creates the correct order, and then only sums up these updates middle value
    public static int getFirstPartResult(List<List<Integer>> realPairs, List<List<Integer>> realUpdates) {
        List<List<Integer>> numbersWithPosition = new ArrayList<>();
        int result = 0;

        for (int i = 0; i < realUpdates.size(); i++) {
            for (int j = 0; j < realUpdates.get(i).size(); j++) {
                numbersWithPosition.add(getThePositionOfNumber(realPairs, realUpdates.get(i), realUpdates.get(i).get(j)));
            }
            if (!isTheUpdateCorrect(numbersWithPosition)) {
                List<Integer> corrected = calculatePosition(numbersWithPosition);
                result += corrected.get(corrected.size() / 2);
                corrected.clear();
            }
            numbersWithPosition.clear();

        }

        return result;
    }

    // This function gives back every number and it's position using the pairs matrix and one update list and one number
    // from the update list ---> [75, 3]. This function is used by the main function, and for every number in a list it
    // the solution looks like this for the third row of updates list: [[75, 3], [97, 4], [47, 2], [61, 1], [53, 0]].
    // The second number of every inside list is the position of that number where it should be.
    public static List<Integer> getThePositionOfNumber(List<List<Integer>> pairs, List<Integer> update, int number) {
        List<Integer> onePosition = new ArrayList<>();
        int frequency = 0;

        for (int i = 0; i < pairs.size(); i++) {
            if (pairs.get(i).get(0) == number && update.contains(pairs.get(i).get(1))) {
                frequency++;
            }
        }
        onePosition.add(number);
        onePosition.add(frequency);

        return onePosition;
    }

    // It is a function which checks if the provided list is in the correct order
    public static boolean isTheUpdateCorrect(List<List<Integer>> orders) {
        List<Integer> correct = new ArrayList<>();
        List<Integer> fromOrders = new ArrayList<>();

        for (int i = 0; i < orders.size(); i++) {
            fromOrders.add(orders.get(i).get(1));
        }

        for (int i = orders.size() - 1; i > -1; i--) {
            correct.add(i);
        }

        return fromOrders.equals(correct);
    }

    // This method gets the list which is made by the getThePositionOfNumber method, and returns the correct ordered
    // list of that provided false ordered list.
    public static List<Integer> calculatePosition(List<List<Integer>> numbersWithPositions) {
        List<Integer> orderedUpdate = new ArrayList<>();
        int max = numbersWithPositions.size() - 1;
        int index = 0;

        while (index < numbersWithPositions.size()) {
            if (numbersWithPositions.get(index).get(1) == max) {
                orderedUpdate.add(numbersWithPositions.get(index).get(0));
                max--;
                index = -1;
            }
            index++;
        }

        return orderedUpdate;
    }

}
