package core.highlight;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Theme;

import java.io.IOException;

public class HighlightTheme extends Theme {

    public HighlightTheme(RSyntaxTextArea textArea) {
        super(textArea);
    }

    public static Theme load(String name) {
        try {
            return Theme.load(HighlightTheme.class.getResourceAsStream("/highlight/" + name + ".xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
