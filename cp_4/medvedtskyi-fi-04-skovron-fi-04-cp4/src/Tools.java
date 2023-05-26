import org.apache.commons.math3.distribution.NormalDistribution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Tools {
    private final String Sequence = "10101101111000010011100110001011100011101001110101001001111011110001010100000100100111010110000110101001101011010110110011000001101001111010100000000111001001011100000110000011000100001000101100111010010000001010100110100111110101110001110101000111100101000110011110111100100111100100000111100001001110011101001110000101110000100010110101001100010001110101101111000000111111111000001100101001100011000101110110100010000000011101010000110010011010110110111001001100010101110010000110011010100011011100010110001010000011001110011001010011011000011100001111110010100110101011011111110110111010001111101000111100111111111101010011000101101000101000111111101000101110001001101011110010000001101010101110110010100011100001101000011000010111100100100000000110010111000011100000000010010001001000110101000000110000100111011011000100011000000011000111101000110011111100010000000111110111010101011010100011011001111110011011001100010101100110110100111010011011000011110011011011010011001001000101111001000110100111110100110011101100000010001000011011011010111110011000110101110001010001010111100010001101110101101000010101100011100011000010101000111101000110001101001110011110101110101111000011011110101011000110101111101000000111010010000000010111011000000011111111000011000010100010011011011001100001011001000111100011100101000110110100001101000100111111101111100111001000001011010001010110110010001100011100101011010001111001011011000010011101011001100100100101000111111000111110010010000100001101111011010010001010000011000111001001111110111001000000011110000001110001101001000000100101011111000001101000010101100011001001111111011011001100101101100011111010110100010101011001111011111100100101010001111111010010011111001000111011110001100001011110101000100010110110100111001001000010101000011100011110110010010011110100011111110100001001010000000011000000111101000000101001101111010110110010010010110111001000100101001010010110101011100110101000001000001000001000101010000101100110100000111001011100100100000001010100010000110011100111000110111111001001";
    private final ArrayList<Integer> L1Reccurent = new ArrayList<>(Arrays.asList(6, 4, 1, 0));
    private final ArrayList<Integer> L2Reccurent = new ArrayList<>(Arrays.asList(3, 0));
    private final ArrayList<Integer> L3Reccurent = new ArrayList<>(Arrays.asList(7, 5, 3, 2, 1, 0));
    private final int L1Length = 30;
    private final int L2Length = 31;
    private final double Alpha = 0.01;

    public void run() {
        TreeSet<String> L1Candidates = new TreeSet<>();
        TreeSet<String> L2Candidates = new TreeSet<>();

        double Betta1 = 1 / Math.pow(2, 30);
        double Betta2 = 1 / Math.pow(2, 31);

        NormalDistribution nd = new NormalDistribution(0, 1);
        double tAlpha = nd.inverseCumulativeProbability(1 - Alpha);
        double tBetta1 = nd.inverseCumulativeProbability(1 - Betta1);
        double tBetta2 = nd.inverseCumulativeProbability(1 - Betta2);

        int N1 = (int) Math.ceil(Math.pow((2 * tBetta1 + tAlpha * Math.sqrt(3)), 2));
        int N2 = (int) Math.ceil(Math.pow((2 * tBetta2 + tAlpha * Math.sqrt(3)), 2));

        int C1 = (int) Math.ceil((N1 + tAlpha * Math.sqrt(3 * N1)) / 4);
        int C2 = (int) Math.ceil((N2 + tAlpha * Math.sqrt(3 * N2)) / 4);

//        L1Candidates = Candidates("L1", L1Reccurent, N1, C1, L1Length, 16);
//        L2Candidates = Candidates("L2", L2Reccurent, N2, C2, L2Length, 16);
        // or
        L1Candidates =  new TreeSet<>(List.of("101000001010000000110000000010", "111000101010000110101101101010", "000000110000011110011001000010", "101011010111111000111010110001", "010011011000010111010001100000", "000011110111111111101001110000", "101101011010000101111101000110", "100101111101100000011011001100"));
        L2Candidates = getL2FromFile();

        System.out.println(Arrays.toString(L2Candidates.toArray()));
    }

    private TreeSet<String> getL2FromFile() {
        TreeSet<String> res = new TreeSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("cp_4/medvedtskyi-fi-04-skovron-fi-04-cp4/src/L2_candidates.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                res.add(line.replaceAll("\n", ""));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    private TreeSet<String> Candidates(String name, ArrayList<Integer> reccurent, int N, int C, int length, int threads) {
        System.out.println("Searching candidates for " + name + " ...");
        long start = System.currentTimeMillis();
        TreeSet<String> candidates = new TreeSet<>();
        ExecutorService exe2 = Executors.newFixedThreadPool(threads);

        int delta = (int) Math.pow(2, length) / threads;
        ArrayList<Integer> points = new ArrayList<>();
        points.add(0);
        for (int i = 0; i < threads; i++) {
            points.add(points.get(i) + delta);
        }

        ArrayList<Future<TreeSet<String>>> futures = new ArrayList<>();
        for (int i = 0; i < threads; i++) {
            int finalI = i;
            Future<TreeSet<String>> future = exe2.submit(() -> {
                return findCandidates(reccurent, points.get(finalI), points.get(finalI + 1), N, C, length);
            });
            futures.add(future);
        }

        for (Future<TreeSet<String>> future : futures) {
            try {
                candidates.addAll(future.get());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        exe2.shutdown();

        long time = System.currentTimeMillis() - start;
        int seconds = (int) time / 1000;
        int minutes = (int) seconds / 60;
        System.out.println("Time elapsed: " + minutes + "m " + (seconds - (minutes * 60)) + "s");

        return candidates;
    }

    private TreeSet<String> findCandidates(ArrayList<Integer> reccurent, int start, int end, int N, int C, int length) {
        TreeSet<String> candidates = new TreeSet<>();
        for (int i = start; i < end; i++) {
            String seq = Integer.toBinaryString((int) i);
            String seq1 = toNLength(seq, length);
            StringBuilder b1 = new StringBuilder();
            LZR L = new LZR(seq1, reccurent);
            for (int j = 0; j < N; j++) {
                b1.append(L.generate());
            }
            if (getR(b1.toString()) > C) continue;
            candidates.add(seq1);
            System.out.println(seq1);
        }
        return candidates;
    }

    private int getR(String sequence) {
        int R = 0;
        for (int i = 0; i < sequence.length(); i++) {
            R += ((int) sequence.charAt(i)) ^ ((int) Sequence.charAt(i));
        }
        return R;
    }

    private String toNLength(String sequence, int n) {
        StringBuilder sequenceBuilder = new StringBuilder(sequence);
        while (sequenceBuilder.length() < n) {
            sequenceBuilder.insert(0, "0");
        }
        return sequenceBuilder.toString();
    }
}
