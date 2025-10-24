package day6;

import java.util.*;
import java.nio.file.*;
import java.io.IOException;

public class GuardGallivant {
    public static String[] guardMap = {
    "....#.....",
    ".........#",
    "..........",
    "..#.......",
    ".......#..",
    "..........",
    ".#..^.....",
    "........#.",
    "#.........",
    "......#..."
    };

    public static Map<Character, int[]> directions = new HashMap<>();

    public static char currentDir = '^';

    public static Set<String> allLocations = new HashSet<>();

    public static Integer[] findStartingCoordinate(String[] guardMap) {
        Integer[] startingCoordinate = new Integer[2];
        for (int row = 0; row < guardMap.length; row++) {
            for (int col = 0; col < guardMap[row].length(); col++) {
                if (guardMap[row].charAt(col) == '^') {
                    startingCoordinate[0] = row;
                    startingCoordinate[1] = col;
                }
            }
        }
        return startingCoordinate;
    }

    public static Integer[] calculateNextCoordinate(Integer[] actualCoordinate, char dirSign) {
        return new Integer[]{actualCoordinate[0] + directions.get(dirSign)[0], actualCoordinate[1] + directions.get(dirSign)[1]};
    }

    public static boolean checkNextStep(String[] map, Integer[] actualCoordinate, char dirSign) {
        Integer[] nextCoordinate = calculateNextCoordinate(actualCoordinate, dirSign);

        if(nextCoordinate[0] < 0 || nextCoordinate[0] >= map.length ||
            nextCoordinate[1] < 0 || nextCoordinate[1] >= map[0].length()) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean turnRight(String[] map, Integer[] actualCoordinate, char dirSign) {
        Integer[] nextCoordinate = calculateNextCoordinate(actualCoordinate, dirSign);

        if (map[nextCoordinate[0]].charAt(nextCoordinate[1]) == '#') {
            turnDirSigns(dirSign);
            return true;
        }
        return false;
    }

    public static void turnDirSigns(char dirSign) {
        switch (dirSign) {
            case '^': currentDir = '>';
            break;
            case '>': currentDir = 'v';
            break;
            case 'v': currentDir = '<';
            break;
            case '<': currentDir = '^';
            break;
        }
    }

    public static List<String> readInput(String path) {
        List<String> map = new ArrayList<String>();

        try {
            map = Files.lines(Path.of(path)).toList();
        } catch (IOException e) {
            System.out.println("Can not read file");;
        }
        return map;
    }

    //2ND PART

    public static void addOrTakeAwayBarricade(String[] map, Integer[] coordinate, char changeTo) {
        String newLine = map[coordinate[0]].substring(0, coordinate[1]) + changeTo + map[coordinate[0]].substring(coordinate[1] + 1);
        map[coordinate[0]] = newLine;
    }

    public static void main(String[] args) {
        directions.put('>', new int[]{0, 1});
        directions.put('<', new int[]{0, -1});
        directions.put('v', new int[]{1, 0});
        directions.put('^', new int[]{-1, 0});

        //It is useful to find the working directory to save your file to
        //System.out.println("Current working dir: " + System.getProperty("user.dir"));
        /*List<String> map = readInput("day6input.txt");
        String[] mapString = map.toArray(new String[0]);

        Integer[] initialCoordinate = findStartingCoordinate(mapString);
        Integer[] currentCoordinate = initialCoordinate;
        List<Integer[]> coordinationList = new ArrayList<>();

        String startCoordinates = "(" + currentCoordinate[0] + ", " + currentCoordinate[1] + ")";
        allLocations.add(startCoordinates);

        while(checkNextStep(mapString, currentCoordinate, currentDir)) {
            if (!turnRight(mapString, currentCoordinate, currentDir)) {
                currentCoordinate = calculateNextCoordinate(currentCoordinate, currentDir);
                String coordinates = "(" + currentCoordinate[0] + ", " + currentCoordinate[1] + ")";
                allLocations.add(coordinates);
                coordinationList.add(currentCoordinate);
            }
        }

        System.out.println(allLocations.size());*/


        //2ND PART

        List<String> map = readInput("day6input.txt");
        String[] mapString = map.toArray(new String[0]);

        Integer[] initialCoordinate = findStartingCoordinate(mapString);
        Integer[] currentCoordinate = initialCoordinate;
        List<Integer[]> coordinationList = new ArrayList<>();
        coordinationList.add(currentCoordinate);

        String startCoordinates = "(" + currentCoordinate[0] + ", " + currentCoordinate[1] + ")";
        allLocations.add(startCoordinates);

        while(checkNextStep(mapString, currentCoordinate, currentDir)) {
            if (!turnRight(mapString, currentCoordinate, currentDir)) {
                currentCoordinate = calculateNextCoordinate(currentCoordinate, currentDir);
                String coordinates = "(" + currentCoordinate[0] + ", " + currentCoordinate[1] + ")";
                if(allLocations.add(coordinates)) {
                    coordinationList.add(currentCoordinate);
                }
            }
        }

        System.out.println(allLocations.size());

        int countLoops = 0;

        for (Integer[] coordinate:
             coordinationList) {

            currentDir = '^';
            currentCoordinate = Arrays.copyOf(initialCoordinate, 2);;

            if (coordinate[0] == initialCoordinate[0] && coordinate[1] == initialCoordinate[1]) {
                continue;
            }

            String[] mapCopy = Arrays.copyOf(mapString, mapString.length);

            addOrTakeAwayBarricade(mapCopy, coordinate, '#');
            Set<String> newLocationList = new HashSet<>();
            String startState = currentCoordinate[0] + "," + currentCoordinate[1] + "," + currentDir;
            newLocationList.add(startState);

            while(checkNextStep(mapCopy, currentCoordinate, currentDir)) {
                if (!turnRight(mapCopy, currentCoordinate, currentDir)) {
                    currentCoordinate = calculateNextCoordinate(currentCoordinate, currentDir);
                    String state = currentCoordinate[0] + "," + currentCoordinate[1] + "," + currentDir;;
                    if(!newLocationList.add(state)){
                        countLoops++;
                        break;
                    }
                }
            }

            addOrTakeAwayBarricade(mapCopy, coordinate, '.');
        }

        System.out.println(countLoops);
    }
}