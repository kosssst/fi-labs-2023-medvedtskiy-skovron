import java.util.ArrayList;

public class LZR {
    private String Registry;
    private final ArrayList<Integer> Reccurent;

    public LZR(String Registry, ArrayList<Integer> Reccurent) {
        this.Registry = Registry;
        this.Reccurent = Reccurent;
    }

    public String getRegistry() {
        return Registry;
    }

    public ArrayList<Integer> getReccurent() {
        return Reccurent;
    }

    public char generate() {
        int result = Character.getNumericValue(Registry.charAt(0));
        int temp = 0;
        for (int i : Reccurent) {
            temp ^= Character.getNumericValue(Registry.charAt(i));
        }
        Registry = Registry.substring(1);
        Registry += Integer.toString(temp);
        return (char) (result + '0');
    }
}
