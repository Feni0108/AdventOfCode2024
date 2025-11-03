package day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlutonianPebbles {

    public static void main(String[] args) {
        List<BigInteger> stonesList = readInput("day11input.txt");
        System.out.println(stonesList);

        List<BigInteger> result = allBlinks(24, stonesList);
        System.out.println(result);
        System.out.println(result.size());

        Map<BigInteger, BigInteger> stones = new HashMap<>();
        for (BigInteger stone : stonesList) {
            stones.put(stone, stones.getOrDefault(stone, BigInteger.ZERO).add(BigInteger.ONE));
        }

        // Simulate 75 blinks
        for (int blink = 0; blink < 75; blink++) {
            Map<BigInteger, BigInteger> nextStones = new HashMap<>();
            for (Map.Entry<BigInteger, BigInteger> entry : stones.entrySet()) {
                BigInteger value = entry.getKey();
                BigInteger count = entry.getValue();

                if (value.equals(BigInteger.ZERO)) {
                    // Rule 1: 0 -> 1
                    nextStones.put(BigInteger.ONE, nextStones.getOrDefault(BigInteger.ONE, BigInteger.ZERO).add(count));
                } else if (value.toString().length() % 2 == 0) {
                    // Rule 2: Split into two stones
                    String strValue = value.toString();
                    int mid = strValue.length() / 2;
                    BigInteger left = new BigInteger(strValue.substring(0, mid));
                    BigInteger right = new BigInteger(strValue.substring(mid));
                    nextStones.put(left, nextStones.getOrDefault(left, BigInteger.ZERO).add(count));
                    nextStones.put(right, nextStones.getOrDefault(right, BigInteger.ZERO).add(count));
                } else {
                    // Rule 3: Multiply by 2024
                    BigInteger newValue = value.multiply(BigInteger.valueOf(2024));
                    nextStones.put(newValue, nextStones.getOrDefault(newValue, BigInteger.ZERO).add(count));
                }
            }
            stones = nextStones;
        }

        // Sum up all stone counts
        BigInteger totalStones = stones.values().stream().reduce(BigInteger.ZERO, BigInteger::add);
        System.out.println(totalStones);
    }

    public static List<BigInteger> readInput(String fileName) {
        List<BigInteger> stones = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine(); // csak az első sort olvassuk

            if (line != null && !line.isBlank()) {
                String[] parts = line.trim().split("\\s+"); // szóközök mentén darabolás
                for (String p : parts) {
                    stones.add(new BigInteger(p)); // konvertálás számokká
                }
            }
        } catch (IOException e) {
            System.err.println("Hiba a fájl olvasása közben: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Nem sikerült számként értelmezni egy elemet: " + e.getMessage());
        }

        return stones;
    }

    public static BigInteger replace(BigInteger nul) {
        return BigInteger.valueOf(1);
    }

    public static List<BigInteger> split(BigInteger even) {
        List<BigInteger> newStones = new ArrayList<>();

        // Átalakítjuk Stringgé
        String s = String.valueOf(even);

        // Kettévágjuk
        int half = s.length() / 2;
        String left = s.substring(0, half);
        String right = s.substring(half);

        newStones.add(new BigInteger(left));
        newStones.add(new BigInteger(right));

        return newStones;
    }

    public static BigInteger multiply(BigInteger neither) {
        return neither.multiply(BigInteger.valueOf(2024));
    }

    public static List<BigInteger> logic(BigInteger stone) {
        List<BigInteger> result = new ArrayList<>();
        if (stone.equals(BigInteger.ZERO)) {
            result.add(replace(stone));
        } else if (String.valueOf(stone).length() % 2 == 0) {
            result.addAll(split(stone));
        } else {
            result.add(multiply(stone));
        }
        return result;
    }

    public static List<BigInteger> blink(List<BigInteger> stones) {
        List<BigInteger> newStones = new ArrayList<>();
        for (BigInteger stone:
             stones) {
            newStones.addAll(logic(stone));
        }
        return newStones;
    }

    public static List<BigInteger> allBlinks(int numberOfBlinks, List<BigInteger> stones) {
        List<BigInteger> afterABlink = blink(stones);
        int counter = numberOfBlinks;

        if (counter == 0) {
            return afterABlink;
        }

        counter--;

        return allBlinks(counter, afterABlink);
    }

    // 2ND PART
    /*
    *A kulcs itt az, amit előzőleg is említettem: nem tároljuk az összes egyes követ külön, hanem csak a mennyiségeket/összegeket.
    🔹 Megközelítés
        Minden “kő” típusú állapotot számmal jelölünk (BigInteger), de nem listában tartjuk mindegyiket.
        Ha a logika a következő:
                                 0 → 1
                                 páros számjegyű → split két részre
                                 páratlan → szorzás 2024-el
        → akkor csak azt kell tudnunk, hogy hány kő van adott értékkel.
        Ha a szám túl nagy, vagy sokszor duplázódik, használhatunk modulo-t (Day11 második rész tipikusan ezt várja, hogy a számok ne nőjenek végtelenre).
        Iteratív feldolgozás: minden blinknél frissítjük a kőállapotokat.
    * */
}
