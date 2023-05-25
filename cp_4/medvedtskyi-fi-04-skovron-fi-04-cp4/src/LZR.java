import java.util.ArrayList;

public class LZR {
    private final ArrayList<Byte> Registry;
    private final ArrayList<Integer> Reccurent;

    public LZR(ArrayList<Byte> Registry, ArrayList<Integer> Reccurent) {
        this.Registry = Registry;
        this.Reccurent = Reccurent;
    }

    public ArrayList<Byte> getRegistry() {
        return Registry;
    }

    public ArrayList<Integer> getReccurent() {
        return Reccurent;
    }
}
