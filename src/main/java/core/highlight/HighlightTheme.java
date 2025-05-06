package core.highlight;

import core.io.SettingsManager;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Theme;

import java.io.IOException;

public class HighlightTheme extends Theme {

    public HighlightTheme(RSyntaxTextArea textArea) {
        super(textArea);
    }

    private static String themeName;
    private static Theme theme;

    public static Theme get() {
        String name = SettingsManager.get().highlight;
        if (themeName != null && themeName.equals(name))
            return theme;

        try {
            theme = Theme.load(HighlightTheme.class.getResourceAsStream("/" + name + ".xml"));
            themeName = name;
            return theme;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
