import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Tools {

    private final char[] alphabet = null;

    Tools() {

    }

    public void run() {
        System.out.println(calLetter());
    }

    private void filter(){

    }

    private Map calLetter(){
        // throw new UnsupportedOperationException();
        Map<Character, Integer> letters = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter text: ");
        String input = scanner.nextLine();
        for (int i = 0; i < input.length(); i++){
            char c = input.charAt(i);
            if (!letters.containsKey(c)){
                letters.put(c,1);
            }else{
                int n = letters.get(c);
                letters.put(c, (n+1));
            }
        }
        scanner.close();
        return letters;
    }

    private long calBiGram(){
        throw new UnsupportedOperationException();
    }

}
