package core.customisation;

import core.App;
import core.highlight.GeneralTheme;
import core.io.SettingsManager;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLookAndFeel;

public class GeneralThemeSubmenu extends JMenu implements MenuClickHandler {

    private final App app;

    public GeneralThemeSubmenu(App app) {
        super("General");
        this.app = app;
        add(new ClickableItem("Arc", this));
        add(new ClickableItem("Light Flat", this));
        add(new ClickableItem("Light Solarized", this));
        add(new ClickableItem("Arc Dark", this));
        add(new ClickableItem("One Dark", this));
        add(new ClickableItem("Nord", this));
        add(new ClickableItem("Monokai", this));
        add(new ClickableItem("Mac Dark", this));
    }

    @Override
    public void click(String name) {
        SettingsManager.get().general = name;
        SwingUtilities.invokeLater(() -> {
            BasicLookAndFeel theme = GeneralTheme.load();
            try {
                UIManager.setLookAndFeel(theme);
                SwingUtilities.updateComponentTreeUI(app);
                app.invalidate();
                app.validate();
                app.repaint();
            } catch (UnsupportedLookAndFeelException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
