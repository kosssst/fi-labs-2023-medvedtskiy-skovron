import java.io.*;
import java.util.*;

public class Tools {

    private static final String ALPHABET = "абвгдежзийклмнопрстуфхцчшщъыьэюя";
    private static final String FILE_PATH = "cp_3/medvedtskyi-fi-04-skovron-fi-04-cp3/src/to_decode.txt";

    public void run() throws IOException{
        System.out.println(calculateBigrams(getText(FILE_PATH)));
    }

    private int gcd(int a, int b){
        throw new UnsupportedOperationException("to do gcd calculation");
    }

    private int reverse(int a, int b){
        throw new UnsupportedOperationException("to do finding reverse element");
    }

    private int[] solveLinearComparisons(int[] a){
        throw new UnsupportedOperationException("to do solving linear comparisons");
    }

    private Map<String, Integer> calculateBigrams(String text){
        Map<String, Integer> bigram = new TreeMap<>();

        for (int i = 0; i < text.length() - 1; i += 2) {
            char c1 = text.charAt(i);
            char c2 = text.charAt(i + 1);
            String temp = c1 + String.valueOf(c2);

            if (!bigram.containsKey(temp)) {
                bigram.put(temp, 1);
            } else {
                int n = bigram.get(temp);
                bigram.put(temp, (n + 1));
            }
        }

        List<Map.Entry<String, Integer>> listForSort = new ArrayList<>(bigram.entrySet());
        listForSort.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        Map<String, Integer> sortedBigram = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : listForSort) {
            sortedBigram.put(entry.getKey(), entry.getValue());
        }

        return sortedBigram;
    }

    private String getText(String filepath) throws IOException {
        InputStream file = new FileInputStream(filepath);
        StringBuilder resultStringBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }

        String temp = resultStringBuilder.toString();
        temp = temp.replaceAll("\n", "");

        return temp;
    }

    private String decodeText(String text, String key){
        throw new UnsupportedOperationException("to do text decoding");
    }
}
