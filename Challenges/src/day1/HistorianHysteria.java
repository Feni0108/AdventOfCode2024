package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HistorianHysteria {
    public static void main(String[] args) {
        //Probe input
        List<Integer> leftNumbers = new ArrayList<Integer>(Arrays.asList(3, 4, 2, 1, 3, 3));
        List<Integer> rightNumbers = new ArrayList<Integer>(Arrays.asList(4, 3, 5, 3, 9, 3));
        Collections.sort(leftNumbers);
        Collections.sort(rightNumbers);

        getSumOfDistances(leftNumbers, rightNumbers);
        System.out.println(readFile("HistorianHysteriaInput.txt"));
    }

    //Have to sort the arrays
    //Have to pair up the numbers of the two list and calculate the distance between them
    public static int getSumOfDistances(List<Integer> leftNumbers, List<Integer> rightNumbers) {
        int sumOfDistances = 0;
        for (int i = 0; i < leftNumbers.size(); i++) {
            sumOfDistances += Math.abs(leftNumbers.get(i) - rightNumbers.get(i));
            System.out.println(sumOfDistances);
        }
        return sumOfDistances;
    }

    //Have to read the txt input file
    public static List<String> readFile(String input) {
        List<String> locations = new ArrayList<>();
        try {
            locations = Files.readAllLines(Paths.get(input));
        } catch (IOException e) {
            System.err.println("Can not read file");
        }
        return locations;
    }
}
