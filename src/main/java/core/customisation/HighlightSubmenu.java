package core.customisation;

import core.highlight.HighlightTheme;
import core.highlight.ThemedSyntaxTextArea;
import core.io.SettingsManager;
import org.fife.ui.rsyntaxtextarea.Theme;

import javax.swing.*;
import java.io.IOException;

public class HighlightSubmenu extends JMenu implements MenuClickHandler {

    public HighlightSubmenu() {
        super("Highlight");
        this.add(new ClickableItem("Eclipse", this));
        this.add(new ClickableItem("Idea", this));
        this.add(new ClickableItem("VS", this));
        this.add(new ClickableItem("Monokai", this));
        this.add(new ClickableItem("Obsidian", this));
    }

    @Override
    public void click(String name) {
        Theme theme = HighlightTheme.load(name);
        ThemedSyntaxTextArea.setTheme(theme);
        SettingsManager.get().highlight = name;
    }

}
