package core.highlight;

import core.io.SettingsManager;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Theme;

public class HighlightThemeManager extends Theme {

    public HighlightThemeManager(RSyntaxTextArea textArea) {
        super(textArea);
    }

    private static NamedThemeWrapper theme;

    public static Theme get() {
        String name = SettingsManager.get().highlight;
        if (theme == null || !theme.name.equals(name))
            theme = new NamedThemeWrapper(name);

        return theme;
    }

}
