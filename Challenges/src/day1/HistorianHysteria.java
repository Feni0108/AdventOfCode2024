package day1;

import java.util.Arrays;

public class HistorianHysteria {
    public static void main(String[] args) {
        //Probe input
        int[] leftNumbers = new int[]{3, 4, 2, 1, 3, 3};
        int[] rightNumbers = new int[]{4, 3, 5, 3, 9, 3};
        Arrays.sort(leftNumbers);
        Arrays.sort(rightNumbers);

        getSumOfDistances(leftNumbers, rightNumbers);
    }

    //Have to sort the arrays
    //Have to pair up the numbers of the two list and calculate the distance between them
    public static int getSumOfDistances(int[] leftNumbers, int[] rightNumbers) {
        int sumOfDistances = 0;
        for (int i = 0; i < leftNumbers.length; i++) {
            sumOfDistances += Math.abs(leftNumbers[i] - rightNumbers[i]);
            System.out.println(sumOfDistances);
        }
        return sumOfDistances;
    }
}
