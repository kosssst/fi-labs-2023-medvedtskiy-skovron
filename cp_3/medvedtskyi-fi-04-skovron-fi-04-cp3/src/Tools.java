import java.io.*;
import java.util.Map;

public class Tools {

    private static final String ALPHABET = "абвгдежзийклмнопрстуфхцчшщъыьэюя";
    private static final String FILE_PATH = "cp_3/medvedtskyi-fi-04-skovron-fi-04-cp3/src/to_decode.txt";

    public void run() throws IOException{
        System.out.println(getText(FILE_PATH));
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

    private Map<Character, Integer> calculateBigrams(String text){
        throw new UnsupportedOperationException("to do bigrams calculation");
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
