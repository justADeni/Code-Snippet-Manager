package core.customisation;

import core.io.SettingsManager;

import javax.swing.*;

public class GeneralThemeSubmenu extends JMenu implements MenuClickHandler {

    public GeneralThemeSubmenu() {
        super("General");
        add(new ClickableItem("Example", this));
    }

    @Override
    public void click(String name) {
        SettingsManager.get().general = name;
    }
}
