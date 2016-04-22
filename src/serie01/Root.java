package serie01;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import serie01.gui.Converter;
import serie01.util.Currency;
import serie01.util.DBFactory;

public final class Root {
    private Root() {
        // rien ici
    }
    private static void initDBType() {
        Object[] dbTypes = new Object[] {"Internal", "Local", "Remote"};
        boolean done = false;
        while (!done) {
            String s = (String) JOptionPane.showInputDialog(
                    null,
                    "Choissisez la source des données :",
                    "Création de la base de données",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    dbTypes,
                    "Internal");
            if (s != null) {
                if (s.equals(dbTypes[0])) {
                    done = true;
                    Currency.setDB(DBFactory.createInternalDB());
//                } else if (s.equals(dbTypes[1])) {
//                    JFileChooser jfc = new JFileChooser();
//                    int returnVal = jfc.showOpenDialog(null);
//                    if (returnVal == JFileChooser.APPROVE_OPTION) {
//                        done = true;
//                        File f = jfc.getSelectedFile();
//                        Currency.setDB(DBFactory.createLocalDB(f));
//                    }
//                } else if (s.equals(dbTypes[2])) {
//                    // A faire
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "L'accès à ce type de sources n'est pas implémenté",
                            "Connexion à une base de données",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            } else {
                System.out.println("pas de base de données, pas de jouet");
                System.exit(0);
            }
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initDBType();
                new Converter(5).display();
            }
        });
    }
}
