package day1;

import java.io.File;
import java.util.*;

public class HistorianHysteria {
    public static void main(String[] args) {
        //Probe input
        List<Integer> leftNumbers = new ArrayList<Integer>(Arrays.asList(3, 4, 2, 1, 3, 3));
        List<Integer> rightNumbers = new ArrayList<Integer>(Arrays.asList(4, 3, 5, 3, 9, 3));
        Collections.sort(leftNumbers);
        Collections.sort(rightNumbers);

        //Read the input file
        String inputTitle = "HistorianHysteriaInput.txt";
        List<Integer> input = readFile(inputTitle);
        List<Integer> leftSide = separateInputLocations(input, true);
        List<Integer> rightSide = separateInputLocations(input, false);
        Collections.sort(leftSide);
        Collections.sort(rightSide);

        //Solution
        System.out.println(getSumOfDistances(leftSide, rightSide));
    }

    //Have to sort the arrays
    //Have to pair up the numbers of the two list and calculate the distance between them
    public static int getSumOfDistances(List<Integer> leftNumbers, List<Integer> rightNumbers) {
        int sumOfDistances = 0;
        for (int i = 0; i < leftNumbers.size(); i++) {
            sumOfDistances += Math.abs(leftNumbers.get(i) - rightNumbers.get(i));
        }
        return sumOfDistances;
    }

    //Have to read the txt input file
    public static List<Integer> readFile(String input) {
        List<Integer> locations = new ArrayList<>();
        try {
            Scanner scan = new Scanner(new File(input));

            while (scan.hasNext()) {
                locations.add(scan.nextInt());
            }
            scan.close();
        } catch(Exception e) {
            System.out.println("Can not read file");
        }
        return locations;
    }

    //Need a method which separates the two lists
    public static List<Integer> separateInputLocations(List<Integer> locations, boolean direction) {
        List<Integer> oneSide = new ArrayList<Integer>();
        if(direction) {
            for (int i = 0; i < locations.size(); i+=2) {
                oneSide.add(locations.get(i));
            }
        } else {
            for (int i = 1; i < locations.size(); i+=2) {
                oneSide.add(locations.get(i));
            }
        }
        return oneSide;
    }
}
