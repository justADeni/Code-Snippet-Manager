package core.customisation;

import core.App;

import javax.swing.*;

public class ThemeMenu extends JMenu {

    public ThemeMenu(App app) {
        super("Theme");
        add(new HighlightSubmenu());
        add(new GeneralThemeSubmenu(app));
    }

}
