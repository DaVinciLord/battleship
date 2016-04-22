package serie11;

enum Item {
    NEW("Cr�er"),
    NEW_FROM_FILE("Cr�er � partir de..."),
    OPEN("Ouvrir..."),
    REOPEN("R�ouvrir"),
    SAVE("Sauvegarder"),
    SAVE_AS("Sauvegarder comme..."),
    CLOSE("Fermer"),
    CLEAR("Nettoyer"),
    QUIT("Quitter");
    
    private String label;

    Item(String lb) {
        label = lb;
    }
    
    public String getLabel() {
        return label;
    }
}
