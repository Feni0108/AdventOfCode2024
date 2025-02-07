package day3;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MullItOver {
    public static void main(String[] args) {
        String inputPath = "day3input.txt";
        String testInput = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";
        Pattern pattern = Pattern.compile("mul\\([0-9]{1,3},[0-9]{1,3}\\)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(testInput);
        while(matcher.find()) {
            System.out.println(matcher.group());
        }

        //First puzzle
        String wholeMemory = readToString(inputPath);
        List<String> withoutCorruption = ignoreCorruptedMemory(wholeMemory);
        List<Integer> multiplications = getMultiplications(withoutCorruption);
        int sum = multiplications.stream()
                        .reduce(0, Integer::sum);
        System.out.println(sum);

        //Second puzzle
        List<Integer> dos = findDosIndexes(wholeMemory);
        List<Integer> donts = findDontsIndexes(wholeMemory);
        System.out.println(dos);
        System.out.println(donts);
    }

    //Parse string numbers and make multiplications
    public static List<Integer> getMultiplications(List<String> withoutCorrupted) {
        List<Integer> multiplications = new ArrayList<>();
        for (String str : withoutCorrupted) {
            String firstNumber = "";
            String secondNumber = "";
            boolean change = false;
            for (char c : str.toCharArray()) {
                if (c == ',') {
                    change = true;
                } else if (!change && Character.isDigit(c)) {
                    firstNumber += c;
                } else if (change && Character.isDigit(c)) {
                    secondNumber += c;
                }
            }
            multiplications.add(Integer.parseInt(firstNumber) * Integer.parseInt(secondNumber));
        }
        return multiplications;
    }

    //Get only the patterns in a list
    public static List<String> ignoreCorruptedMemory(String memory) {
        List<String> withoutCorruptedMemory = new ArrayList<>();
        Pattern pattern = Pattern.compile("mul\\([0-9]{1,3},[0-9]{1,3}\\)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(memory);
        while(matcher.find()) {
            withoutCorruptedMemory.add(matcher.group());
        }
        return withoutCorruptedMemory;
    }

    //Second puzzle method only gets methods after do() and avoids method after don't()
    public static List<Integer> findDosIndexes(String memory) {
        List<Integer> dosIndexes = new ArrayList<>();
        Pattern pattern = Pattern.compile("do\\(\\)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(memory);
        while(matcher.find()) {
            dosIndexes.add(matcher.start());
        }
        return dosIndexes;
    }

    //Second puzzle method only gets methods after do() and avoids method after don't()
    public static List<Integer> findDontsIndexes(String memory) {
        List<Integer> dontsIndexes = new ArrayList<>();
        Pattern pattern = Pattern.compile("don't\\(\\)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(memory);
        while(matcher.find()) {
            dontsIndexes.add(matcher.start());
        }
        return dontsIndexes;
    }

    //Read file to a string
    public static String readToString(String path) {
        String memory = "";
        try {
            FileReader reader = new FileReader(path);
            int data = 0;
            while ((data = reader.read()) != -1) {
                memory += (char)data;
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error");
        }
        return memory;
    }
}
