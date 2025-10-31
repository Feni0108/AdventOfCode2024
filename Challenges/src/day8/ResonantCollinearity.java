package day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ResonantCollinearity {

    public static int rows = 0;
    public static int columns = 0;

    public static Map<Character, List<int[]>> readAntennaPositions(String path) {
        Map<Character, List<int[]>> antennaMap = new HashMap<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            rows = lines.size();
            columns = lines.get(0).length();

            for (int row = 0; row < lines.size(); row++) {
                String line = lines.get(row);
                for (int col = 0; col < line.length(); col++) {
                    char c = line.charAt(col);
                    if (c != '.') {
                        antennaMap
                                .computeIfAbsent(c, k -> new ArrayList<>())
                                .add(new int[]{row, col});
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return antennaMap;
    }

    public static Set<String> findAntinodes(List<int[]> coordinates, int rows, int cols) {
        Set<String> antinodes = new HashSet<>();

        for (int i = 0; i < coordinates.size(); i++) {
            for (int j = i + 1; j < coordinates.size(); j++) {
                int[] A1 = coordinates.get(i);
                int[] A2 = coordinates.get(j);

                int dx = A2[0] - A1[0];
                int dy = A2[1] - A1[1];

                int[] antinode1 = {A1[0] - dx, A1[1] - dy};
                int[] antinode2 = {A2[0] + dx, A2[1] + dy};

                if (antinode1[0] >= 0 && antinode1[0] < rows &&
                        antinode1[1] >= 0 && antinode1[1] < cols) {
                    antinodes.add(antinode1[0] + "," + antinode1[1]);
                }

                if (antinode2[0] >= 0 && antinode2[0] < rows &&
                        antinode2[1] >= 0 && antinode2[1] < cols) {
                    antinodes.add(antinode2[0] + "," + antinode2[1]);
                }
            }
        }

        return antinodes;
    }

    public static Set<String> findResonantAntinodes(List<int[]> antennas, int rows, int cols) {
        Set<String> antinodes = new HashSet<>();

        // minden antenna önmaga is antinode
        for (int[] antenna : antennas) {
            antinodes.add(antenna[0] + "," + antenna[1]);
        }

        for (int i = 0; i < antennas.size(); i++) {
            for (int j = i + 1; j < antennas.size(); j++) {
                int[] A1 = antennas.get(i);
                int[] A2 = antennas.get(j);

                int dx = A2[0] - A1[0];
                int dy = A2[1] - A1[1];

                // irány 1 – A1 felől A2 irányába
                int x = A1[0], y = A1[1];
                while (x >= 0 && x < rows && y >= 0 && y < cols) {
                    antinodes.add(x + "," + y);
                    x += dx;
                    y += dy;
                }

                // irány 2 – A2 felől A1 irányába
                x = A2[0];
                y = A2[1];
                while (x >= 0 && x < rows && y >= 0 && y < cols) {
                    antinodes.add(x + "," + y);
                    x -= dx;
                    y -= dy;
                }
            }
        }

        return antinodes;
    }

    public static void main(String[] args) {

        Map<Character, List<int[]>> antennas = readAntennaPositions("day8input.txt");

/*        for (var entry : antennas.entrySet()) {
            System.out.println(entry.getKey() + " -> " +
                    entry.getValue().stream()
                            .map(coords -> "(" + coords[0] + "," + coords[1] + ")")
                            .toList());
        }*/


        Set<String> allAntinodes = new HashSet<>();

        for (List<int[]> antenna : antennas.values()) {
            allAntinodes.addAll(findAntinodes(antenna, rows, columns));
        }

        System.out.println(allAntinodes.size());

        Set<String> allResonantAntinodes = new HashSet<>();

        for (List<int[]> antenna : antennas.values()) {
            allResonantAntinodes.addAll(findResonantAntinodes(antenna, rows, columns));
        }

        System.out.println(allResonantAntinodes.size());

    }
}
