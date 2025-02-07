package day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CeresSearch {
    public static String[] grid = {
            "MMMSXXMASM",
            "MSAMXMSMSA",
            "AMXSXMAAMM",
            "MSAMASMSMX",
            "XMASAMXAMM",
            "XXAMMXXAMA",
            "SMSMSASXSS",
            "SAXAMASAAA",
            "MAMMMXMMMM",
            "MXMXAXMASX"
    };
    public static int gridRowLength = grid.length;
    public static int gridColumnLength = grid[0].length();

    // Irányvektorok: (sor irány, oszlop irány)
    public static int[][] directions = {
            {0, 1},   // jobbra
            {0, -1},  // balra
            {1, 0},   // lefelé
            {-1, 0},  // felfelé
            {1, 1},   // jobb alsó átló
            {1, -1},  // bal alsó átló
            {-1, 1},  // jobb felső átló
            {-1, -1}  // bal felső átló
    };

    public static List<String> inputGrid = readGridFromInput("day4input.txt");

    public static int inputGridRowLength = inputGrid.size();
    public static int inputGridColumnLength = inputGrid.get(0).length();

    public static void main(String[] args) {
        System.out.println("All Xmas occurrences in the example:");

        List<int[]> coordinatesX = findXs(inputGrid);
        int result = 0;

        for (int i = 0; i < coordinatesX.size(); i++) {
            result += getTheNumberOfXmasesStartingFromASpecificCoordinate(coordinatesX.get(i), directions);
        }

        System.out.println(result);

        System.out.println(inputGrid);
    }

    // We need a method which finds all the X character coordinates and saves it in a list
    public static List<int[]> findXs(List<String> grid) {
        List<int[]> coordinatesX = new ArrayList<int[]>();

        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).length(); j++) {
                int[] oneXCoordinate = new int[2];
                if (grid.get(i).charAt(j) == 'X') {
                    oneXCoordinate[0] = i;
                    oneXCoordinate[1] = j;
                    coordinatesX.add(oneXCoordinate);
                }
            }
        }

        return coordinatesX;
    }

    // This method gets a coordinate of an X character, and the direction matrix. It checks first, if direction is
    // possible to reach the end of the word, check the limitations of the matrix. If a direction is possible to go
    // then it uses the matchingWordInADirection function, and if that gives back a true value, then the counter
    // increases.
    public static int getTheNumberOfXmasesStartingFromASpecificCoordinate(int[] actualCoordinate, int[][] directions) {
        int x = actualCoordinate[0];
        int y = actualCoordinate[1];
        int countXmas = 0;

        for (int i = 0; i < directions.length; i++) {
            if (x + directions[i][0] * 3 >= 0 && x + directions[i][0] * 3 < inputGridRowLength && y + directions[i][1] * 3 >= 0 && y + directions[i][1] * 3 < inputGridColumnLength) {

                if (matchingWordInADirection(actualCoordinate, directions[i][0], directions[i][1], 1)) {
                    countXmas++;
                }
            }
        }
        return countXmas;
    }

    // This method gets a coordinate, X and Y directions and a round variable, to be able to solve this as
    // a recursive function, to be able to ease the complexity. It goes through all the coordinates in the
    // specific direction, till it reaches the last character of the word we have to search. If one of the
    // character in the grid doesn't match, it returns false. If all the characters are matching, the
    // method return true.
    public static boolean matchingWordInADirection(int[] coordinate, int xDir, int yDir, int round) {
        String word = "XMAS";

        int x = coordinate[0];
        int y = coordinate[1];
        if (round == word.length()) {
            return true;
        }
        if (round < word.length() && inputGrid.get(x + xDir * round).charAt(y + yDir * round) != word.charAt(round)) {
            return false;
        }
        return matchingWordInADirection(coordinate, xDir, yDir, round + 1);
    }

    public static List<String> readGridFromInput(String path) {
        List<String> rows = new ArrayList<String>();

        try {
            rows = Files.lines(Path.of(path)).toList();
        } catch (IOException e) {
            System.out.println("Can not read file");;
        }
        return rows;
    }
}
