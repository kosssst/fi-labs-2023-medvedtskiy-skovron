public class Geffe {
    private LZR L1;
    private LZR L2;
    private LZR L3;

    Geffe(LZR L1, LZR L2, LZR L3) {
        this.L1 = L1;
        this.L2 = L2;
        this.L3 = L3;
    }

    public int next() {
        int x = Character.getNumericValue(this.L1.generate());
        int y = Character.getNumericValue(this.L2.generate());
        int s = Character.getNumericValue(this.L3.generate());
        int z = (s & x) ^ ((1 ^ s) & y);
        return z;
    }
}
