package serie11;

import util.TPTester;

public final class TP11Test {
    private TP11Test() {
        // rien
    }
    public static void main(String[] args) {
        TPTester t = new TPTester(
                serie11.model.StdFilDocManagerTest.class,
                serie11.model.StdPetModelTest.class
        );
        int exitValue = t.runAll();
        System.exit(exitValue);
    }
}
