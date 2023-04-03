import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Tools {
    private static final String symbols = "абвгдежзийклмнопрстуфхцчшщъыьэюя";
    private static final String FILE_PATH_TO_ENCODE = "cp_2/medvedtskyi-fi-04-skovron-fi-04-cp2/src/windows_license.txt";
    private static final String FILE_PATH_TO_DECODE = "cp_2/medvedtskyi-fi-04-skovron-fi-04-cp2/src/to_decode.txt";
    private static final int keyLength = 17;

    public Tools(){

    }

    public void run() throws IOException {
//        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
//        System.setOut(out);
//
//        String key2 = generateKey(2);
//        String key3 = generateKey(3);
//        String key4 = generateKey(4);
//        String key5 = generateKey(5);
//        String key10 = generateKey(10);
//
//        String openText = filter(FILE_PATH_TO_ENCODE);
//        String encodedByKey2 = encode(openText, key2);
//        String encodedByKey3 = encode(openText, key3);
//        String encodedByKey4 = encode(openText, key4);
//        String encodedByKey5 = encode(openText, key5);
//        String encodedByKey10 = encode(openText, key10);
//
        String EncodedText = filter(FILE_PATH_TO_DECODE);
//
//        System.out.println("Ключ 1: " + key2);
//        System.out.println("Ключ 2: " + key3);
//        System.out.println("Ключ 3: " + key4);
//        System.out.println("Ключ 4: " + key5);
//        System.out.println("Ключ 5: " + key10);
//        System.out.println();
//
//        System.out.println(openText);
//        System.out.println(encodedByKey2);
//        System.out.println(encodedByKey3);
//        System.out.println(encodedByKey4);
//        System.out.println(encodedByKey5);
//        System.out.println(encodedByKey10);
//        System.out.println();
//
//        System.out.println("I = " + calculateI(openText));
//        System.out.println("I = " + calculateI(encodedByKey2));
//        System.out.println("I = " + calculateI(encodedByKey3));
//        System.out.println("I = " + calculateI(encodedByKey5));
//        System.out.println("I = " + calculateI(encodedByKey4));
//        System.out.println("I = " + calculateI(encodedByKey10));
//        System.out.println();
//
//        System.out.println("Length of key:");
//        calculateKeyLength(EncodedText).forEach((key, value) -> System.out.println(key + ": " + value));
//        out.close();
        System.out.println(findKey(EncodedText));
    }

    private String filter(String filePath) throws IOException {
        InputStream srcFile = new FileInputStream(filePath);
        String temp = readFromInputStream(srcFile);

        temp = temp.replaceAll("ё", "e");
        temp = temp.replaceAll("[^^а-яА-Я]", "");
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

    private String encode(String text, String key) {
        int indexInKey = 0;
        StringBuilder builder = new StringBuilder(text.length());

        for (int i = 0; i < text.length(); i++) {
            builder.append(symbols.charAt(((symbols.indexOf(text.charAt(i)) + 1) + (symbols.indexOf(key.charAt(indexInKey)) + 1) - 1) % 32));
            indexInKey += 1;
            indexInKey = indexInKey % key.length();
        }

        return builder.toString();
    }

    private String generateKey(Integer length) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(symbols.length());
            builder.append(symbols.charAt(index));
        }

        return builder.toString();
    }

    private float calculateI(String text) {
        Map<Character, Integer> letters = calculateLetters(text);
        float sum = 0;
        for (Integer value : letters.values()) {
            sum += ((float)value * ((float)value - 1));
        }
        return (float) (sum / (text.length() * (text.length() - 1)));
    }

    private Map<Character, Integer> calculateLetters(String text) {
        Map<Character, Integer> letters = new HashMap<>();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (!letters.containsKey(c)) {
                letters.put(c, 1);
            } else {
                int n = letters.get(c);
                letters.put(c, (n + 1));
            }
        }

        return letters;
    }

    private Map<Integer, Float> calculateKeyLength(String text) {
        Map<Integer, Float> r = new HashMap<>();
        for (int i = 2; i <= 30; i++) {
            float sum = 0;
            StringBuilder[] builders = new StringBuilder[i];
            for (int j = 0; j < i; j++) {
                builders[j] = new StringBuilder((text.length() / i));
            }
            int k = 0;
            while(k < text.length()) {
                builders[(k % i)].append(text.charAt(k));
                k++;
            }
            for (int j = 0; j < i; j++) {
                sum += calculateI(builders[j].toString());
            }
            r.put(i, (sum / i));
        }
        return r;
    }

    private String findKey(String text) {
        StringBuilder[] rows = separateText(text);
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < keyLength; i++) {
            key.append(symbols.charAt((symbols.indexOf(max(calcFrequency(rows[i].toString()))) - symbols.indexOf('о') + 32) % 32));
        }
        return key.toString();
    }
    private Map<Character, Double> calcFrequency(String text) {
        Map<Character, Integer> characters = initEmptyMap();
        Map<Character, Double> frequency = new HashMap<>();
        for (int i = 0; i < text.length(); i++) {
            characters.put(text.charAt(i), characters.get(text.charAt(i)) + 1);
        }
        characters.forEach((key, value) -> frequency.put(key, ((double)value / (double)text.length())));
        return frequency;
    }
    private StringBuilder[] separateText(String text) {
        StringBuilder[] rows = new StringBuilder[keyLength];
        for (int i = 0; i < keyLength; i++) {
            rows[i] = new StringBuilder();
        }
        int k = 0;
        while(k < text.length()) {
            rows[(k % keyLength)].append(text.charAt(k));
            k++;
        }
        return rows;
    }
    private Map<Character, Integer> initEmptyMap() {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < symbols.length(); i++) {
            map.put(symbols.charAt(i), 0);
        }
        return map;
    }
    private Character max(Map<Character, Double> frequency) {
        char cMax = '-';
        double dMax = 0;
        for (Map.Entry<Character, Double> entry : frequency.entrySet()) {
            if (entry.getValue() > dMax) {
                dMax = entry.getValue();
                cMax = entry.getKey();
            }
        }
        return cMax;
    }
 }
