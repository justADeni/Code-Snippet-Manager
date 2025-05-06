package core.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.fonts.inter.FlatInterFont;
import core.App;
import core.objects.Group;
import core.objects.SimpleDocumentListener;
import core.objects.Snippet;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    private final App app;

    private JButton newGroup, addSnippet;
    private JTextField searchBar;

    public ControlPanel(App app) {
        this.app = app;
        this.setLayout(new FlowLayout());
    }

    public void init() {
        newGroup = getButton("New group");
        newGroup.addActionListener(e -> {
            app.tabbedPanel.addGroup("New Group", true);
            app.tabbedPanel.setSelectedIndex(app.tabbedPanel.getTabCount()-1);
        });

        addSnippet = getButton("New Snippet");
        addSnippet.addActionListener(e -> {
            Group selectedGroup = app.tabbedPanel.getSelectedGroup();
            selectedGroup.addSnippet("New Snippet", "", true);
        });

        searchBar = new JTextField();
        searchBar.setFont(new Font(FlatInterFont.FAMILY, Font.PLAIN, 16));
        searchBar.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search....");
        searchBar.putClientProperty(FlatClientProperties.STYLE, "arc : 10");
        searchBar.setPreferredSize(new Dimension(200, 30));
        searchBar.getDocument().addDocumentListener(new SimpleDocumentListener() {
            @Override
            public void updateText() {
                Group selectedGroup = app.tabbedPanel.getSelectedGroup();
                selectedGroup.snippets.forEach(snippet -> snippet.setVisible(
                        searchBar.getText().isBlank() ||
                                snippet.getName().contains(searchBar.getText())
                ));
            }
        });
    }

    public void addComponent() {
        this.add(searchBar);
        this.add(newGroup);
        this.add(addSnippet);

        app.add(this, BorderLayout.NORTH);
    }

    private JButton getButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font(FlatInterFont.FAMILY, Font.PLAIN, 16));
        button.setBackground(new Color(46, 97, 234));
        return button;
    }

}
