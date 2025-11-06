package day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GardenGroups {

    public static void main(String[] args) {
        List<List<Character>> grid = readInputGridAsList("day12sample.txt");

        // Ellenőrzés: kiírás
        for (List<Character> row : grid) {
            for (Character c : row) {
                System.out.print(c);
            }
            System.out.println();
        }

        int height = grid.size();
        int width = grid.get(0).size();

        boolean[][] visited = new boolean[height][width];

        /*for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (!visited[i][j]) {
                    Region oneRegion = explore(i, j, grid, visited, grid.get(i).get(j));
                    allRegions.add(oneRegion);
                }
            }
        }

        for (Region region:
             allRegions) {
            System.out.println(region.toString());
        }

        long costs = calculateCosts(allRegions);
        System.out.println(costs);*/

        // Példa: 0,0 koordinátától indulunk

        int finalCost = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (!visited[i][j]) {
                    Region oneRegion = new Region();
                    secondExplore(i, j, grid, visited, grid.get(i).get(j), oneRegion);
                    finalCost += oneRegion.calculatSideCosts();
                }
            }
        }

        System.out.println(finalCost);

        /*Region F = new Region();
        secondExplore(0, 9, grid, visited, grid.get(0).get(9), F);
        for (String dir : F.borders.keySet()) {
            System.out.println("Direction: " + dir);
                System.out.print("Side: ");
                for (int[] coord : F.borders.get(dir)) {
                    System.out.print("[" + coord[0] + "," + coord[1] + "] ");
                }
                System.out.println();
        }

        System.out.println(F.countSides());*/
    }

    static class Region {
        private int area;
        private int perimeter;
        Map<String, List<int[]>> borders;

        public Region(int area, int perimeter) {
            this.area = area;
            this.perimeter = perimeter;
            this.borders = new HashMap<>();
        }

        public Region() {
            this.area = 0;
            this.perimeter = 0;
            this.borders = new HashMap<>();
        }

        public void addBorder(String dir, int x, int y) {
            borders.computeIfAbsent(dir, k -> new ArrayList<>()).add(new int[]{x, y});
        }

        public String toString() {
            return "area: " + this.area + ", perimeter: " + this.perimeter;
        }

        public int countSides() {
            int sides = 0;
            for (String dir : borders.keySet()) {
                List<int[]> coords = borders.get(dir);
                Set<String> visitedCoords = new HashSet<>();
                for (int[] coord : coords) {
                    String key = coord[0] + "," + coord[1];
                    if (visitedCoords.contains(key)) continue;

                    // Új oldal
                    sides++;

                    // DFS jelleggel járjuk be a folytató koordinátákat
                    Deque<int[]> stack = new ArrayDeque<>();
                    stack.push(coord);

                    while (!stack.isEmpty()) {
                        int[] current = stack.pop();
                        String currKey = current[0] + "," + current[1];
                        if (visitedCoords.contains(currKey)) continue;
                        visitedCoords.add(currKey);

                        for (int[] neighbor : coords) {
                            String neighborKey = neighbor[0] + "," + neighbor[1];
                            if (visitedCoords.contains(neighborKey)) continue;

                            boolean isContinuous = false;
                            if ((dir.equals("N") || dir.equals("S")) && neighbor[0] == current[0] && Math.abs(neighbor[1] - current[1]) == 1) {
                                isContinuous = true;
                            } else if ((dir.equals("E") || dir.equals("W")) && neighbor[1] == current[1] && Math.abs(neighbor[0] - current[0]) == 1) {
                                isContinuous = true;
                            }

                            if (isContinuous) {
                                stack.push(neighbor);
                            }
                        }
                    }
                }
            }
            return sides;
        }

        public int calculatSideCosts() {
            System.out.println("Area: " + this.area + " * " + "Sides: " + this.countSides());
            return this.area * countSides();
        }
    }

    public static List<List<Character>> readInputGridAsList(String fileName) {
        List<List<Character>> grid = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    List<Character> row = new ArrayList<>();
                    for (char c : line.toCharArray()) {
                        row.add(c);
                    }
                    grid.add(row);
                }
            }
        } catch (IOException e) {
            System.err.println("Hiba a fájl olvasása közben: " + e.getMessage());
        }

        return grid;
    }

    public static Region explore(int x, int y, List<List<Character>> grid, boolean[][] visited, char target) {
        int rows = grid.size();
        int cols = grid.get(0).size();

        // 1️⃣ Kilépési feltételek
        if (x < 0 || x >= grid.size() || y < 0 || y >= grid.get(0).size()) {
            // Kívül vagyunk → perimeterhez járul hozzá 1
            return new Region(0, 1);
        }
        if (visited[x][y]) return new Region(0, 0);
        if (grid.get(x).get(y) != target) return new Region(0, 1);

        // 2️⃣ Jelöljük a mezőt látogatottnak
        visited[x][y] = true;

        // 3️⃣ Kezdjük a területet 1-el (magunk)
        int area = 1;
        int perimeter = 0;

        // Négy irányban vizsgálódunk
        int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int[] d : directions) {
            int nx = x + d[0];
            int ny = y + d[1];
            int[] neighbourCoordinate = new int[]{nx, ny};

            if (nx < 0 || ny < 0 || nx >= rows || ny >= cols || grid.get(nx).get(ny) != target) {
                perimeter++; // határ, szél vagy más karakter
            } else {
                Region r = explore(nx, ny, grid, visited, target);
                area += r.area;
                perimeter += r.perimeter;
            }
        }

        return new Region(area, perimeter);
    }

    public static long calculateCosts(List<Region> allRegions) {
        long costs = 0l;

        for (Region region:
             allRegions) {
            costs += region.area * region.perimeter;
        }

        return costs;
    }


    public static void secondExplore(int x, int y, List<List<Character>> grid, boolean[][] visited, char target, Region region) {
        int rows = grid.size();
        int cols = grid.get(0).size();

        // Kilépési feltételek
        if (x < 0 || y < 0 || x >= rows || y >= cols) {
            region.perimeter++;
            return;
        }
        if (visited[x][y]) return;
        if (grid.get(x).get(y) != target) {
            region.perimeter++;
            return;
        }

        // Jelöljük a mezőt látogatottnak
        visited[x][y] = true;
        region.area++;

        // Négy irány: Észak, Dél, Nyugat, Kelet
        int[][] directions = { {-1,0}, {1,0}, {0,-1}, {0,1} };
        String[] dirNames = { "N", "S", "W", "E" };

        for (int i = 0; i < directions.length; i++) {
            int nx = x + directions[i][0];
            int ny = y + directions[i][1];

            // Határ vagy más karakter
            if (nx < 0 || ny < 0 || nx >= rows || ny >= cols || grid.get(nx).get(ny) != target) {
                region.perimeter++;
                region.addBorder(dirNames[i], nx, ny);
            } else {
                // Rekurzív hívás ugyanazzal a Region objektummal
                secondExplore(nx, ny, grid, visited, target, region);
            }
        }
    }
}



