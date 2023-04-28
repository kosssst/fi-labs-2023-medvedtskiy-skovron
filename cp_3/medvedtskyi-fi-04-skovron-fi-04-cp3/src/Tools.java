import java.io.*;
import java.util.*;

public class Tools {

    private static final String ALPHABET = "абвгдежзийклмнопрстуфхцчшщъыьэюя";
    private static final String FILE_PATH = "cp_3/medvedtskyi-fi-04-skovron-fi-04-cp3/src/to_decode.txt";

    public void run() throws IOException{
//        System.out.println(calculateBigrams(getText(FILE_PATH)));
        System.out.println(gcd(23, 12));
        System.out.println(gcd(24, 12));
        System.out.println(gcd(20, 12));
        System.out.println(gcd(21, 12));
        System.out.println(gcd(25, 12));
        System.out.println(gcd(30, 12));
    }

    private int gcd(int a, int b){
        int d = 1;

        while ((a % 2 == 0) && (b % 2 == 0)) {
            a /= 2;
            b /= 2;
            d *= 2;
        }

        while (a % 2 == 0) {
            a /= 2;
        }

        while (b != 0) {
            while (b % 2 == 0) {
                b /= 2;
            }
            if (a > b) {
                int temp = a;
                a = b;
                b = temp - a;
            }else{
                b -= a;
            }
        }

        d *= a;
        return d;
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
