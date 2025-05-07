package core.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.fonts.inter.FlatInterFont;
import core.App;
import core.objects.Group;
import core.objects.SimpleDocumentListener;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    private final App app;

    private JButton newGroup, deleteGroup, addSnippet;
    private JTextField searchBar;

    public ControlPanel(App app) {
        this.app = app;
        this.setLayout(new FlowLayout());
    }

    public void init() {
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
        newGroup = getButton("New Group");
        newGroup.addActionListener(e -> {
            app.tabbedPanel.addGroup("New Group", true);
            app.tabbedPanel.setSelectedIndex(app.tabbedPanel.getTabCount()-1);
            addSnippet.setVisible(true);
            deleteGroup.setVisible(true);
            searchBar.setVisible(true);
            redrawEverything();
        });

        deleteGroup = getButton("Delete Group", new Color(148, 24, 24));
        deleteGroup.addActionListener(e -> {
            app.tabbedPanel.getSelectedGroup().deleteGroup();
            addSnippet.setVisible(!app.tabbedPanel.groups.isEmpty());
            deleteGroup.setVisible(!app.tabbedPanel.groups.isEmpty());
            searchBar.setVisible(!app.tabbedPanel.groups.isEmpty());
            redrawEverything();
        });

        addSnippet = getButton("New Snippet");
        addSnippet.addActionListener(e -> {
            Group selectedGroup = app.tabbedPanel.getSelectedGroup();
            selectedGroup.addSnippet("New Snippet", "", true);
        });
        addSnippet.setVisible(!app.tabbedPanel.groups.isEmpty());
        deleteGroup.setVisible(!app.tabbedPanel.groups.isEmpty());
        searchBar.setVisible(!app.tabbedPanel.groups.isEmpty());
    }

    public void addComponent() {
        this.add(searchBar);
        this.add(newGroup);
        this.add(deleteGroup);
        this.add(addSnippet);

        app.add(this, BorderLayout.NORTH);
    }

    private JButton getButton(String text) {
        return getButton(text, new Color(46, 97, 234));
    }

    private JButton getButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font(FlatInterFont.FAMILY, Font.PLAIN, 16));
        button.setBackground(color);
        return button;
    }

    private void redrawEverything() {
        SwingUtilities.invokeLater(() -> {
            for (Window window : Window.getWindows()) {
                SwingUtilities.updateComponentTreeUI(window);
                window.invalidate();
                window.validate();
                window.repaint();
            }
        });
    }

}
