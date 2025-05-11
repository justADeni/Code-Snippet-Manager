package core.highlight;

import core.App;
import core.utils.RuntimeSource;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Theme;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class NamedThemeWrapper extends Theme {

    public final String name;
    // Unfortunately couldn't be inherited the 'normal way' due to inner
    // static classes and methods being necessary to instantiation of Theme.
    private Theme theme;

    public NamedThemeWrapper(String name) {
        super(new RSyntaxTextArea());
        this.name = name;
        try {
            switch (RuntimeSource.get()) {
                case JAR -> theme = Theme.load(App.class.getResourceAsStream("/" + name + ".xml"));
                case CLASSES -> {
                    String relativePath = UUID.randomUUID().toString();
                    Path currentPath = Paths.get(App.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                    String[] currentLocation = currentPath.toString().split(Pattern.quote(File.separator));
                    int i = currentLocation.length;
                    while (Files.notExists(Path.of(relativePath))) {
                        relativePath = Arrays
                                .stream(currentLocation, 0, i)
                                .collect(Collectors.joining("/"))
                                + "/src/main/resources/" + name + ".xml";

                        i--;
                    }
                    theme = Theme.load(new FileInputStream(relativePath));
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void apply(RSyntaxTextArea textArea) {
        theme.apply(textArea);
    }

}
