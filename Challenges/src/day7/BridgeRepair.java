package day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BridgeRepair {

    public static List<Long> readTargets(String path) {
        List<Long> targets = new ArrayList<>();

        try {
            targets = Files.lines(Paths.get(path))
                    .map(line -> Long.parseLong(line.split(":")[0].trim()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Cannot read targets from file.");
        }

        return targets;
    }

    public static List<List<Long>> readNumbers(String path) {
        List<List<Long>> numbers = new ArrayList<>();

        try {
            numbers = Files.lines(Paths.get(path))
                    .map(line -> line.split(":")[1].trim()) // csak a számrész
                    .map(nums -> Arrays.stream(nums.split(" "))
                            .map(Long::parseLong)
                            .collect(Collectors.toList()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Cannot read numbers from file.");
        }

        return numbers;
    }

    public static boolean canReachTarget(long target, List<Long> nums, int index, long current) {
        if (index == nums.size()) {
            return current == target;
        }
        return canReachTarget(target, nums, index + 1, current + nums.get(index))
                || canReachTarget(target, nums, index + 1, current * nums.get(index))
                || canReachTarget(target, nums, index + 1, Long.parseLong("" + current + nums.get(index)));
    }

    public static void main(String[] args) {
        List<Long> targets = readTargets("day7input.txt");
        List<List<Long>> numbers = readNumbers("day7input.txt");

        Long sum = 0l;

        for (int i = 0; i < targets.size(); i++) {
            int index = 0;
            if (canReachTarget(targets.get(i), numbers.get(i), index, 0l)) {
                sum += targets.get(i);
            }
        }

        System.out.println(sum);
    }

}
