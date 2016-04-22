package serie02;

import util.TPTester;

public final class TP02Test {
    private TP02Test() {
        // rien
    }
    public static void main(String[] args) {
        TPTester t = new TPTester(
                serie02.StdSplitManagerTest.class
        );
        int exitValue = t.runAll();
        System.exit(exitValue);
    }
}
