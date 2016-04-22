package serie06;

import util.TPTester;

public final class TP06Test {
    private TP06Test() {
        // rien
    }
    public static void main(String[] args) {
        TPTester t = new TPTester(
                serie06.CoinTypesTest.class,
                serie06.DrinkTypesTest.class,
                serie06.StdStockTest.class,
                serie06.StdMoneyAmountTest.class,
                serie06.StdDrinksMachineModelTest.class
        );
        int exitValue = t.runAll();
        System.exit(exitValue);
    }
}
