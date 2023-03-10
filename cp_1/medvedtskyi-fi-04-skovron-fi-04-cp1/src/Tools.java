import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

// Война и мир

public class Tools {
    private final static String FILE_PATH = "cp_1/medvedtskyi-fi-04-skovron-fi-04-cp1/src/Война и мир all in.txt";

    Map<Character, Integer> mapMonoGram = calGram(filter());
    Map<Character, Integer> mapWithSpacesMonoGram = calGram(filterWithSpaces());

    Map<String, Integer> mapBiGram = calBiGram(filter());
    Map<String, Integer> mapBiGramCross = calBiGramCross(filter());

    Map<Character, Double> mapFrequencyMonoGram = calGramFrequency(mapMonoGram);
    Map<Character, Double> mapFrequencyWithSpacesMonoGram = calGramFrequency(mapWithSpacesMonoGram);

    // bi Frequency

    Tools() throws IOException {

    }

    public void run() throws IOException {
        System.out.println("\nWithout spaces:");
        //mapMonoGram.forEach((key, value) -> System.out.println(key + ":" + value));

        System.out.println("\nWith spaces:");
        //mapWithSpacesMonoGram.forEach((key, value) -> System.out.println(key + ":" + value));

        System.out.println("\nFrequency without spaces:");
        //mapFrequency.forEach((key, value) -> System.out.println(key + ":" + (value*100.0) + "%"));

        System.out.println("\nFrequency with spaces:");
        //mapFrequencyWithSpaces.forEach((key, value) -> System.out.println(key + ":" + (value*100.0) + "%"));

        System.out.println("\nShow biGram no crossing");
        //mapBiGram.forEach((key, value) -> System.out.println(key + ":" + value));

        System.out.println("\nShow biGram crossing");
        //mapBiGramCross.forEach((key, value) -> System.out.println(key + ":" + value));

        System.out.println("\n Entropy MonoGram with space");
        //System.out.println(entropyMonoGramSpace());

        System.out.println("\n Entropy MonoGram without space");
        //System.out.println(entropyMonoGramNoSpace());

        System.out.println("\n Entropy BiGram with space");
        System.out.println(entropyBiGramSpace());

        System.out.println("\n Entropy BiGram without space");
        System.out.println(entropyBiGramNoSpace());

        System.out.println();
    }

    private String filter() throws IOException {
        InputStream srcFile = new FileInputStream(Tools.FILE_PATH);
        String temp = readFromInputStream(srcFile);

        temp = temp.replaceAll("ё", "e").replaceAll("ъ", "ь");
        temp = temp.replaceAll("[^^а-яА-Я]", "");
        temp = temp.replaceAll("\n", "");
        temp = temp.toLowerCase();

        return temp;
    }

    private String filterWithSpaces() throws IOException {
        InputStream srcFile = new FileInputStream(Tools.FILE_PATH);
        String temp = readFromInputStream(srcFile);

        temp = temp.replaceAll("ё", "e").replaceAll("ъ", "ь");
        temp = temp.replaceAll("[^^а-яА-Я\\s]", "");
        temp = temp.replaceAll("\\s+", " ");
        temp = temp.replaceAll("\n", "");
        temp = temp.toLowerCase();

        return temp;
    }

    private String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    private Map<Character, Integer> calGram(String input) {
        Map<Character, Integer> letters = new HashMap<>();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (!letters.containsKey(c)) {
                letters.put(c, 1);
            } else {
                int n = letters.get(c);
                letters.put(c, (n + 1));
            }
        }

        return letters;
    }

    private Map<Character, Double> calGramFrequency(Map<Character, Integer> map) {
        Map<Character, Double> frequency = new HashMap<>();
        int letterSummary = 0;
        for (Integer value : map.values()) {
            letterSummary += value;
        }
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            Character key = entry.getKey();
            Integer value = entry.getValue();
            double percentage = ((double) value)/((double) letterSummary);
            frequency.put(key, percentage);
        }
        return frequency;
    }

    private Map<String, Integer> calBiGram(String input){
        Map<String, Integer> bigram = new TreeMap<>();

        for(int i = 0; i < input.length() - 1; i+=2) {
            char c1 = input.charAt(i);
            char c2 = input.charAt(i + 1);
            String temp = c1 + String.valueOf(c2);

            if (!bigram.containsKey(temp)) {
                bigram.put(temp, 1);
            } else {
                int n = bigram.get(temp);
                bigram.put(temp, (n + 1));
            }
        }

        return bigram;
    }

    private Map<String, Integer> calBiGramCross(String input){
        Map<String, Integer> bigram = new TreeMap<>();

        for(int i = 0; i < input.length() - 1; i++) {
            char c1 = input.charAt(i);
            char c2 = input.charAt(i + 1);
            String temp = c1 + String.valueOf(c2);

            if (!bigram.containsKey(temp)) {
                bigram.put(temp, 1);
            } else {
                int n = bigram.get(temp);
                bigram.put(temp, (n + 1));
            }
        }

        return bigram;
    }

    private double entropyMonoGramNoSpace(){
        double entropy = 0;
        double[] freq = mapFrequencyMonoGram.values().stream().mapToDouble(Double::doubleValue).toArray();

        for (double i : freq){
            entropy += i * Math.log(i);
        }

        return -entropy;
    }

    private double entropyMonoGramSpace(){
        double entropy = 0;
        double[] freq = mapFrequencyWithSpacesMonoGram.values().stream().mapToDouble(Double::doubleValue).toArray();

        for (double i : freq){
            entropy += i * Math.log(i);
        }

        return -entropy;
    }

    private double entropyBiGramNoSpace() {
        double entropy = 0;
        double[] freq = null;

        for (double i : freq){
            entropy += i * Math.log(i);
        }

        return -entropy;
    }

    private double entropyBiGramSpace() {
        double entropy = 0;
        double[] freq = null;

        for (double i : freq){
            entropy += i * Math.log(i);
        }

        return -entropy;
    }
}
