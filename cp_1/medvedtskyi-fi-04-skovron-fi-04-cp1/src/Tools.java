import java.io.*;
import java.util.HashMap;
import java.util.Map;

// Война и мир

// calBigram 2 methods
// H1, H2

public class Tools {
    Tools() throws IOException {

    }

    public void run() throws IOException {
        Map<Character, Integer> map = calLetter(filter("cp_1/medvedtskyi-fi-04-skovron-fi-04-cp1/src/Война и мир all in.txt"));
        Map<Character, Integer> mapWithSpaces = calLetter(filterWithSpaces("cp_1/medvedtskyi-fi-04-skovron-fi-04-cp1/src/Война и мир all in.txt"));
        Map<Character, Double> mapFrequency = calLetterFrequency(map);
        Map<Character, Double> mapFrequencyWithSpaces = calLetterFrequency(mapWithSpaces);

        System.out.println("\nWithout spaces:");
        map.forEach((key, value) -> System.out.println(key + ":" + value));

        System.out.println("\nWith spaces:");
        mapWithSpaces.forEach((key, value) -> System.out.println(key + ":" + value));

        System.out.println("\nFrequency without spaces:");
        mapFrequency.forEach((key, value) -> System.out.println(key + ":" + (value*100.0) + "%"));

        System.out.println("\nFrequency with spaces:");
        mapFrequencyWithSpaces.forEach((key, value) -> System.out.println(key + ":" + (value*100.0) + "%"));
    }

    private String filter(String path) throws IOException {
        InputStream srcFile = new FileInputStream(path);
        String temp = readFromInputStream(srcFile);

        temp = temp.replaceAll("ё", "e").replaceAll("ъ", "ь");
        temp = temp.replaceAll("[^^а-яА-Я]", "");
        temp = temp.replaceAll("\n", "");
        temp = temp.toLowerCase();

        return temp;
    }

    private String filterWithSpaces(String path) throws IOException {
        InputStream srcFile = new FileInputStream(path);
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

    private Map<Character, Integer> calLetter(String input) {
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

    private Map<Character, Double> calLetterFrequency(Map<Character, Integer> map) {
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

    private long calBiGram(String input){
        throw new UnsupportedOperationException();
    }

}
