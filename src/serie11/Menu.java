package serie11;

import java.util.EnumMap;
import java.util.Map;

enum Menu {
    FILE("Fichier"),
    EDIT("Édition"),
    QUIT("Quitter");
    
    private String label;

    Menu(String lb) {
        label = lb;
    }

    public String getLabel() {
        return label;
    }
    
    static final Map<Menu, Item[]> MENU_STRUCT;
    static {
        MENU_STRUCT = new EnumMap<Menu, Item[]>(Menu.class);
        MENU_STRUCT.put(Menu.FILE, new Item[] {
                Item.NEW, Item.NEW_FROM_FILE,
                null,
                Item.OPEN, Item.REOPEN,
                null,
                Item.SAVE, Item.SAVE_AS,
                null,
                Item.CLOSE });
        MENU_STRUCT.put(Menu.EDIT, new Item[] { Item.CLEAR });
        MENU_STRUCT.put(Menu.QUIT, new Item[] { Item.QUIT });
    }
}
