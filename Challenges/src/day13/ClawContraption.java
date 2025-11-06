package day13;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClawContraption {
    public static void main(String[] args) {
        List<double[]> buttonsAndPrize = readInput("day13input.txt");

        /*List<double[]> buttonPushCounts = calculatePushes(buttonsAndPrize);

        int cost = calculateCost(buttonPushCounts);*/

        List<BigDecimal[]> bigPrizes = addABillionToPrize(buttonsAndPrize);
        List<BigDecimal[]> calculatedPushes = calculatePushesBigDecimals(bigPrizes);
        BigDecimal COST = calculateCostBigDecimal(calculatedPushes);


    }

    public static List<double[]> readInput(String fileName) {
        List<double[]> buttonsAndPrize = new ArrayList<>();

        Path path = Path.of(fileName); // <-- a fájl neve

        // reguláris kifejezés, ami az X+94, Y-12 mintát keresi
        Pattern pattern = Pattern.compile("X[=+]?(-?\\d+),\\s*Y[=+]?(-?\\d+)");

        try {
            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                Matcher m = pattern.matcher(line);
                if (m.find()) {
                    double x = Double.parseDouble(m.group(1));
                    double y = Double.parseDouble(m.group(2));
                    buttonsAndPrize.add(new double[]{x, y});
                }
            }

            /*// Kiíratás ellenőrzésképp
            for (double[] arr : buttonsAndPrize) {
                System.out.println(Arrays.toString(arr));
            }*/

        } catch (IOException e) {
            e.printStackTrace();
        }

        return buttonsAndPrize;
    }

    public static double[] cramerRule(double a1, double a2, double b1, double b2, double c1, double c2) {
        double[] results = new double[2];

        double D = a1 * b2 - a2 * b1;
        double a = (c1 * b2 - c2 * b1) / D;
        double b = (a1 * c2 - a2 * c1) / D;

        if ((a % 1 == 0) && (b % 1 == 0)) {
            results[0] = a;
            results[1] = b;
        }

        /*System.out.println("Push the A button " + a + " times.");
        System.out.println("Push the B button " + b + " times.");*/

        return results;
    }

    public static List<double[]> calculatePushes(List<double[]> buttonsAndPrizes) {
        List<double[]> buttonPushCounts = new ArrayList<>();

        int playBoxNumbers = 0;

        for (int i = 0; i < buttonsAndPrizes.size(); i += 3) {
            if(i >= buttonsAndPrizes.size()) {
                break;
            }

            playBoxNumbers++;

            double a1 = buttonsAndPrizes.get(i)[0];
            double a2 = buttonsAndPrizes.get(i)[1];
            double b1 = buttonsAndPrizes.get(i + 1)[0];
            double b2 = buttonsAndPrizes.get(i + 1)[1];
            double c1 = buttonsAndPrizes.get(i + 2)[0];
            double c2 = buttonsAndPrizes.get(i + 2)[1];

            double[] pushes = cramerRule(a1, a2, b1, b2, c1, c2);

            if ((pushes[0] != 0 && pushes[1] != 0) /*&& (pushes[0] <= 100 && pushes[1] <= 100)*/) {
                buttonPushCounts.add(pushes);
                System.out.println("The " + playBoxNumbers + ". playbox can win!");
                System.out.println("Push the A box: " + pushes[0] + " times.");
                System.out.println("Push the B box: " + pushes[1] + " times.");
            } else {
                System.out.println("You can not win with the " +  playBoxNumbers + ". playbox.");
            }
        }
        return buttonPushCounts;
    }

    public static int calculateCost(List<double[]> buttonPushCounts) {
        int cost = 0;

        for (double[] pushCounts:
                buttonPushCounts) {
            cost += pushCounts[0] * 3 +pushCounts[1];
        }

        System.out.println("You will need " + cost + " much coin to be able to win the most.");

        return cost;
    }

    public static BigDecimal[] cramerRuleBigDecimals(BigDecimal a1, BigDecimal a2, BigDecimal b1, BigDecimal b2, BigDecimal c1, BigDecimal c2) {
        BigDecimal[] results = new BigDecimal[2];

        BigDecimal D = a1.multiply(b2).subtract(a2.multiply(b1));
        BigDecimal a = (c1.multiply(b2).subtract(c2.multiply(b1))).divide(D, 20, RoundingMode.HALF_UP);
        BigDecimal b = (a1.multiply(c2).subtract(a2.multiply(c1))).divide(D, 20, RoundingMode.HALF_UP);

        if ((a.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0) && (b.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0)) {
            results[0] = a;
            results[1] = b;
        }

        /*System.out.println("Push the A button " + a + " times.");
        System.out.println("Push the B button " + b + " times.");*/

        return results;
    }

    public static List<BigDecimal[]> calculatePushesBigDecimals(List<BigDecimal[]> buttonsAndPrizes) {
        List<BigDecimal[]> buttonPushCounts = new ArrayList<>();

        int playBoxNumbers = 0;

        for (int i = 0; i < buttonsAndPrizes.size(); i += 3) {
            if(i >= buttonsAndPrizes.size()) {
                break;
            }

            playBoxNumbers++;

            BigDecimal a1 = buttonsAndPrizes.get(i)[0];
            BigDecimal a2 = buttonsAndPrizes.get(i)[1];
            BigDecimal b1 = buttonsAndPrizes.get(i + 1)[0];
            BigDecimal b2 = buttonsAndPrizes.get(i + 1)[1];
            BigDecimal c1 = buttonsAndPrizes.get(i + 2)[0];
            BigDecimal c2 = buttonsAndPrizes.get(i + 2)[1];

            BigDecimal[] pushes = cramerRuleBigDecimals(a1, a2, b1, b2, c1, c2);

            if (pushes[0] != null && pushes[1] != null &&
                    pushes[0].compareTo(BigDecimal.ZERO) != 0 &&
                    pushes[1].compareTo(BigDecimal.ZERO) != 0/*&& (pushes[0] <= 100 && pushes[1] <= 100)*/) {
                buttonPushCounts.add(pushes);
                System.out.println("The " + playBoxNumbers + ". playbox can win!");
                System.out.println("Push the A box: " + pushes[0].toString() + " times.");
                System.out.println("Push the B box: " + pushes[1].toString() + " times.");
            } else {
                System.out.println("You can not win with the " +  playBoxNumbers + ". playbox.");
            }
        }
        return buttonPushCounts;
    }

    public static List<BigDecimal[]> addABillionToPrize(List<double[]> buttonsAndPrize) {
        List<BigDecimal[]> buttonsAndPrizeBigDecimal = new ArrayList<>();

        for (int i = 0; i < buttonsAndPrize.size(); i++) {
            if ((i + 1) % 3 == 0) {
                BigDecimal baseX = BigDecimal.valueOf(buttonsAndPrize.get(i)[0]);
                BigDecimal baseY = BigDecimal.valueOf(buttonsAndPrize.get(i)[1]);
                BigDecimal offset = new BigDecimal("10000000000000"); // szövegesen, pontosan!

                BigDecimal[] prizes = new BigDecimal[] {
                        baseX.add(offset),
                        baseY.add(offset)
                };

                buttonsAndPrizeBigDecimal.add(prizes);
            } else {
                BigDecimal[] machines = new BigDecimal[] {BigDecimal.valueOf(buttonsAndPrize.get(i)[0]), BigDecimal.valueOf(buttonsAndPrize.get(i)[1])};
                buttonsAndPrizeBigDecimal.add(machines);
            }
        }

        for (BigDecimal[] bigPrizes:
             buttonsAndPrizeBigDecimal) {
            System.out.println(Arrays.toString(bigPrizes));
        }

        return buttonsAndPrizeBigDecimal;
    }

    public static BigDecimal calculateCostBigDecimal(List<BigDecimal[]> buttonPushCounts) {
        BigDecimal cost = BigDecimal.ZERO;
        BigDecimal THREE = BigDecimal.valueOf(3);

        for (BigDecimal[] pushCounts:
                buttonPushCounts) {
            cost = cost.add((pushCounts[0].multiply(THREE)).add(pushCounts[1])) ;
        }

        System.out.println("You will need " + cost + " much coin to be able to win the most.");

        return cost;
    }
}
