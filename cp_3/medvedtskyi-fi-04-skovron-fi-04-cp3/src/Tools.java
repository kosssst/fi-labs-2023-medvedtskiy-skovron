import java.io.*;
import java.util.*;

public class Tools {

    private static final String ALPHABET = "абвгдежзийклмнопрстуфхцчшщъыьэюя";
//    private static final String ALPHABET = "абвггдеєжзиіїйклмнопрстуфхцчшщьюя";
    private static final String FILE_PATH = "cp_3/medvedtskyi-fi-04-skovron-fi-04-cp3/src/to_decode.txt";
    private final List<String> BiGrams = new ArrayList<>(Arrays.asList("то", "ст", "на", "ов", "ал"));

    public void run() throws Exception {
        String text = getText(FILE_PATH);
        List<String> mostPopularBiGrams = getFirst5Elements(calculateBigrams(text));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                try {
                    String key = findKey(mostPopularBiGrams.get(i), BiGrams.get(j));
                    System.out.println("\nKey:\n" + key + "\nText:\n" + decrypt(text, key));
                } catch (Exception e) {
//                    System.out.println("exception in " + BiGrams.get(j) + " -> " + mostPopularBiGrams.get(i));
//                    System.out.println(e);
                }
            }
        }
//        String key = findKey("нк", "ов");
//        String d = decrypt(text, key);
    }

    private int gcd(int a, int b) {
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

    private int reverse(int a, int b) throws Exception {
        List<Integer> q = new ArrayList<>();
        int r1 = b;
        int r2 = a;
        int r3 = 1;

        while (r3 != 0) {
            q.add(r1 / r2);
            r3 = r1 % r2;
            r1 = r2;
            r2 = r3;
        }
        if (r1 != 1) {
            throw new Exception("gcd of " + a + " and " + b + " is not 1");
        }
        q.remove(q.size() - 1);
        List<Integer> u = new ArrayList<>(Arrays.asList(1, 0));
        List<Integer> v = new ArrayList<>(Arrays.asList(0, 1));
        for (Integer integer : q) {
            u.add(u.get(u.size() - 2) - u.get(u.size() - 1) * integer);
            v.add(v.get(v.size() - 2) - v.get(v.size() - 1) * integer);
        }

        if (v.get(v.size() - 1) >= 0){
            return v.get(v.size() - 1);
        }else{
            return v.get(v.size() - 1) + b;
        }
    }

    private Map<String, Integer> calculateBigrams(String text) {
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

    private String findKey(String biGramInEncryptedText, String biGramFromLanguage) throws Exception {
        int y = (ALPHABET.indexOf(biGramInEncryptedText.charAt(0)) - ALPHABET.indexOf(biGramInEncryptedText.charAt(1)) + ALPHABET.length()) % ALPHABET.length();
        int x = (ALPHABET.indexOf(biGramFromLanguage.charAt(0)) - ALPHABET.indexOf(biGramFromLanguage.charAt(1)) + ALPHABET.length()) % ALPHABET.length();
            if (gcd(x, ALPHABET.length()) != 1) {
            if (y % gcd(x, ALPHABET.length()) == 0) {
                int d = gcd(x, ALPHABET.length());
                x /= d;
                y /= d;
            } else {
                throw new Exception("Has no solutions");
            }
        }
        int a = (y * reverse(x, ALPHABET.length())) % ALPHABET.length();
        int b = (ALPHABET.indexOf(biGramInEncryptedText.charAt(0)) - (a * ALPHABET.indexOf(biGramFromLanguage.charAt(0))) + (a * ALPHABET.length())) % ALPHABET.length();
        return ("" + ALPHABET.charAt(a) + ALPHABET.charAt(b));
    }

    private int findSolution(int a, int b) throws Exception {
        if (gcd(a, ALPHABET.length()) != 1) {
            if (b % gcd(a, ALPHABET.length()) == 0) {
                int d = gcd(a, ALPHABET.length());
                a /= d;
                b /= d;
            } else {
                throw new Exception("Has no solutions");
            }
        }
        return (b * reverse(a, ALPHABET.length())) % ALPHABET.length();
    }

    private String decrypt(String text, String key) throws Exception {
        int a = ALPHABET.indexOf(key.charAt(0));
        int b = ALPHABET.indexOf((key.charAt(1)));
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            builder.append(ALPHABET.charAt(findSolution(a, (ALPHABET.indexOf(text.charAt(i)) - b + ALPHABET.length()) % ALPHABET.length())));
        }
        return builder.toString();
    }

    private List<String> getFirst5Elements(Map<String, Integer> map) {
        int numOfMaps = 0;
        List<String> bigrams = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (numOfMaps == 5) break;
            bigrams.add(entry.getKey());
            numOfMaps++;
        }
        return bigrams;
    }
}
