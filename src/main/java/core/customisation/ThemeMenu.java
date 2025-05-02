package core.customisation;

import javax.swing.*;

public class ThemeMenu extends JMenu {

    public ThemeMenu() {
        super("Theme");
        add(new HighlightSubmenu());
        add(new GeneralThemeSubmenu());
    }

}
