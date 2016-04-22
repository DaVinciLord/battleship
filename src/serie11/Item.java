package serie11;

enum Item {
    NEW("Créer"),
    NEW_FROM_FILE("Créer à partir de..."),
    OPEN("Ouvrir..."),
    REOPEN("Réouvrir"),
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
