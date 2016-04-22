package serie07;

import util.TPTester;

public final class TP07Test {
    private TP07Test() {
        // rien
    }
    public static void main(String[] args) {
        TPTester t = new TPTester(
                serie07.StdSlotModelTest.class,
                serie07.StdSwellingModelTest.class
        );
        int exitValue = t.runAll();
        System.exit(exitValue);
    }
}
