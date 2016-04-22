package serie01.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import util.Contract;

public final class DBFactory {
    private DBFactory() {
       // rien 
    }
    
    public static CurrencyDB createInternalDB() {
        return new StdCurrencyDB();
    }
    
    public static CurrencyDB createLocalDB(File f) throws IOException {
        Contract.checkCondition(f != null);

        throw new UnsupportedOperationException();
    }
    
    public static CurrencyDB createRemoteDB(URL url) {
        Contract.checkCondition(url != null);

        throw new UnsupportedOperationException();
    }
}
