package day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HoofIt {

    public static void main(String[] args) {
        List<List<Integer>> topographicMap = readInput("day10input.txt");
        System.out.println(topographicMap);
        List<int[]> trailheadCoordinates = getTrailheadCoordinates(topographicMap);

        /*Set<String> oneTrailheadCoordinateScoreCoordinates = getScoresOfATrailhead(topographicMap, trailheadCoordinates.get(0)[0], trailheadCoordinates.get(0)[1]);
        System.out.println(oneTrailheadCoordinateScoreCoordinates);
        System.out.println(oneTrailheadCoordinateScoreCoordinates.size());


        int sumScore = 0;
        for (int[] trailHead:
             trailheadCoordinates) {
            sumScore += getScoresOfATrailhead(topographicMap, trailHead[0], trailHead[1]).size();
        }

        System.out.println(sumScore);*/

        int sumRates = 0;
        for (int[] trailHead:
             trailheadCoordinates) {
            sumRates += getRatesOfATrailhead(topographicMap, trailHead[0], trailHead[1]);
        }
        System.out.println("\n" + sumRates);
    }

    public static List<List<Integer>> readInput(String fileName) {
        List<List<Integer>> map = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Integer> row = new ArrayList<>();
                for (char c : line.toCharArray()) {
                    if (Character.isDigit(c)) { // csak számjegyeket olvasunk
                        row.add(Character.getNumericValue(c));
                    }
                }
                map.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    public static List<int[]> getTrailheadCoordinates(List<List<Integer>> map) {
        List<int[]> trailheadCoordinates = new ArrayList<>();

        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                if (map.get(i).get(j) == 0) {
                    trailheadCoordinates.add(new int[]{i, j});
                }
            }
        }

        for (int[] coordinate:
             trailheadCoordinates) {
            System.out.print("(" + coordinate[0] + ", " + coordinate[1] + ")");
        }

        return trailheadCoordinates;
    }

    public static int[][] directions = {
            {0, 1},   // jobbra
            {0, -1},  // balra
            {1, 0},   // lefelé
            {-1, 0},  // felfelé
    };

    public static Set<String> getScoresOfATrailhead(List<List<Integer>> map, int trailheadRow, int trailheadCol) {
        //only starting from one trailhead
        int height = map.size();
        int width = map.get(0).size();
        int value = map.get(trailheadRow).get(trailheadCol);

        if (value == 9) {
            Set<String> s = new HashSet<>();
            s.add(trailheadRow + "," + trailheadCol);
            return s;
        }

        Set<String> scoreCoordinates = new HashSet<>();

        for (int[] dir:
             directions) {
            int nextRow = trailheadRow + dir[0];
            int nextCol = trailheadCol + dir[1];

            if (nextRow < 0 || nextRow >= width || nextCol < 0 || nextCol >= height) continue;

            int nextStep = map.get(nextRow).get(nextCol);

            if (nextStep == value + 1) {
                scoreCoordinates.addAll(getScoresOfATrailhead(map, nextRow, nextCol));
            }
        }

        return scoreCoordinates;
    }

    // 2ND PART

    public static int getRatesOfATrailhead(List<List<Integer>> map, int trailheadRow, int trailheadCol) {
        //only starting from one trailhead
        int height = map.size();
        int width = map.get(0).size();
        int value = map.get(trailheadRow).get(trailheadCol);
        int path = 0;

        if (value == 9) {
            return 1;
        }

        for (int[] dir:
                directions) {
            int nextRow = trailheadRow + dir[0];
            int nextCol = trailheadCol + dir[1];

            if (nextRow < 0 || nextRow >= width || nextCol < 0 || nextCol >= height) continue;

            int nextStep = map.get(nextRow).get(nextCol);

            if (nextStep == value + 1) {
                path += getRatesOfATrailhead(map, nextRow, nextCol);
            }
        }

        return path;
    }
}
