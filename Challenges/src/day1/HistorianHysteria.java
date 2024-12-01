package day1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HistorianHysteria {
    public static void main(String[] args) {
        //Probe input
        List<Integer> leftNumbers = new ArrayList<>(Arrays.asList(3, 4, 2, 1, 3, 3));
        List<Integer> rightNumbers = new ArrayList<>(Arrays.asList(4, 3, 5, 3, 9, 3));
    }

    //Have to sort the to lists, and delete duplications
    public static List<Integer> sortAndDropDuplications(List<Integer> numbers) {
        Collections.sort(numbers);
        List<Integer> sortedAndDistinct = numbers.stream()
                .distinct()
                .collect(Collectors.toList());
        return sortedAndDistinct;
    }
}
