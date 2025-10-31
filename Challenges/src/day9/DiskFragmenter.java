package day9;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DiskFragmenter {

    static class Block {
        private int id;
        private int size;

        public Block(int id, int size) {
            this.id = id;
            this.size = size;
        }

        public int getId() {
            return id;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String toString() {
            return "[F" + id + ": " + size + "]";
        }
    }

    public static String readToString(String path) {
        String content = "";
        try {
            content = Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return content;
    }

    public static List<Integer> decode(String code) {
        List<Integer> decoded = new ArrayList<>();
        int index = 0;

        for (int i = 0; i < code.length(); i++) {
            int repeat = Character.getNumericValue(code.charAt(i));
            if (i % 2 == 0) {
                for (int j = 0; j < repeat; j++) {
                    decoded.add(index);
                }
                index++;
            } else {
                for (int j = 0; j < repeat; j++) {
                    decoded.add(-1);
                }
            }
        }

        return decoded;
    }

    public static List<Integer> defregment(List<Integer> decoded) {
        List<Integer> copy = new ArrayList<>(List.copyOf(decoded));
        int firstIndex = 0;
        int index = decoded.size() - 1;

        while (firstIndex != index) {
            while (copy.get(index) < 0) {
                index--;
            }
            if (copy.get(firstIndex) < 0 && copy.get(index) > 0) {
                int lastFile = copy.get(index);
                int firstFile = copy.get(firstIndex);
                copy.set(index, firstFile);
                copy.set(firstIndex, lastFile);
                index--;
            }
            firstIndex++;
        }

        copy.removeIf(n -> n < 0);
        return copy;
    }

    public static BigInteger checkSum(List<Integer> defregmented) {
        BigInteger sum = BigInteger.ZERO;

        for (int i = 0; i < defregmented.size(); i++) {
            sum = sum.add(BigInteger.valueOf(i).multiply(BigInteger.valueOf(defregmented.get(i))));
        }

        return sum;
    }

    // 2ND part

    public static List<Block> secondDecode(String code) {
        List<Block> decoded = new ArrayList<>();
        int index = 0;

        for (int i = 0; i < code.length(); i++) {
            if (i % 2 == 0) {
                decoded.add(new Block(index, Character.getNumericValue(code.charAt(i))));
                index++;
            } else {
                decoded.add(new Block(-1, Character.getNumericValue(code.charAt(i))));
            }
        }

        return decoded;
    }

    public static List<Block> defregmentBlocks(List<Block> decodedBlocks) {
        List<Block> blocks = new ArrayList<>(decodedBlocks);

        // Jobbról balra járjuk be a fájlokat
        for (int i = blocks.size() - 1; i >= 0; i--) {
            Block current = blocks.get(i);
            if (current.getId() == -1) continue; // üres blokk, ugrás

            // Megkeressük a legbalrább található üres blokkot, ami elég nagy
            for (int j = 0; j < i; j++) {
                Block free = blocks.get(j);

                if (free.getId() == -1 && free.getSize() >= current.getSize()) {
                    // 1️⃣ Ha nagyobb a szabad hely, "szétvágjuk"
                    int remaining = free.getSize() - current.getSize();

                    // Lecseréljük a szabad blokkot a fájlra
                    blocks.set(j, new Block(current.getId(), current.getSize()));

                    // Ha maradt hely, a fájl után új üres blokkot szúrunk be
                    if (remaining > 0) {
                        blocks.add(j + 1, new Block(-1, remaining));
                        i++; // mert a lista hosszabb lett
                    }

                    // 2️⃣ A régi helyen lévő fájlt üres blokkra cseréljük
                    blocks.set(i, new Block(-1, current.getSize()));

                    break; // nem kell tovább keresni
                }
            }
        }

        return blocks;
    }

    public static List<Integer> decodeBlocks(List<Block> defregmentedBlocks) {
        List<Integer> decodedBlocks = new ArrayList<>();

        for (int i = 0; i < defregmentedBlocks.size(); i++) {
            for (int j = 0; j < defregmentedBlocks.get(i).getSize(); j++) {
                decodedBlocks.add(defregmentedBlocks.get(i).getId());
            }
        }

        return decodedBlocks;
    }

    public static BigInteger checkSumBlocks(List<Integer> defregmentedBlocks) {
        BigInteger sum = BigInteger.ZERO;

        for (int i = 0; i < defregmentedBlocks.size(); i++) {
            if (defregmentedBlocks.get(i) > 0) {
                sum = sum.add(BigInteger.valueOf(i).multiply(BigInteger.valueOf(defregmentedBlocks.get(i))));
            }
        }

        return sum;
    }

    public static void main(String[] args) {
        String sample = readToString("day9sample.txt");
        //System.out.println(sample);
        List<Integer> decoded = decode(sample);
        //System.out.println(decoded);
        List<Integer> defregmented = defregment(decoded);
        //System.out.println(defregmented);
        BigInteger sum = checkSum(defregmented);
        System.out.println(sum);

        List<Block> secondDecoded = secondDecode(sample);
        //System.out.println(secondDecoded);
        List<Block> defragmentedBlocks = defregmentBlocks(secondDecoded);
        //System.out.println(defragmentedBlocks);
        List<Integer> decodedBlocks = decodeBlocks(defragmentedBlocks);
        //System.out.println(decodedBlocks);
        BigInteger sumBlocks = checkSumBlocks(decodedBlocks);
        System.out.println(sumBlocks);

    }
}
