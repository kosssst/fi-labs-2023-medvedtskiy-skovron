import java.io.*;
import java.util.HashMap;
import java.util.Map;

// Война и мир

// calBigram 2 methods
// H1, H2

// bug # symbols

public class Tools {

    private final InputStream srcFile = new FileInputStream("cp_1/medvedtskyi-fi-04-skovron-fi-04-cp1/src/sourceText.txt");
    private final InputStream clearTxt;

    Tools() throws IOException {
        filter();
        clearTxt = new FileInputStream("cp_1/medvedtskyi-fi-04-skovron-fi-04-cp1/src/nFile.txt");
    }

    public void run() throws IOException {
        var map = calLetter(readFromInputStream(clearTxt).replaceAll("\n", ""));

        map.forEach((key, value) -> System.out.println(key + ":" + value));
    }

    private void filter() throws IOException {
        String temp = readFromInputStream(srcFile);

        temp = temp.replaceAll("ё", "e").replaceAll("ъ", "ь");
        temp = temp.replaceAll("[^^а-яА-Я]", "");
        temp = temp.toLowerCase();

        try {
            File file = new File("cp_1/medvedtskyi-fi-04-skovron-fi-04-cp1/src/nFile.txt");
            FileWriter writer = new FileWriter(file);
            writer.write(temp);
            writer.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

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

    private long calBiGram(String input){
        throw new UnsupportedOperationException();
    }

}
