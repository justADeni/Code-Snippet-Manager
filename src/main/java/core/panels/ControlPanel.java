package core.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.fonts.inter.FlatInterFont;
import core.App;
import core.objects.Group;
import core.objects.Snippet;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class ControlPanel extends JPanel {

    private final App app;

    private JButton newGroup, addSnippet;
    private JTextField searchBar;
    private String searchString = "";

    public ControlPanel(App app) {
        this.app = app;
        this.setLayout(new FlowLayout());
    }

    public void init() {
        newGroup = getButton("New group");
        newGroup.addActionListener(e -> {
            String groupName = JOptionPane.showInputDialog("Enter group name", "Group");

            if (groupName.isBlank()) return;

            for (Group group : app.tabbedPanel.groups) {
                if (groupName.equals(group.groupName)) {
                    JOptionPane.showMessageDialog(null, "Group name is already taken",
                            "Name in use", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            app.tabbedPanel.addGroup(groupName);
        });

        addSnippet = getButton("Add snippet");
        addSnippet.addActionListener(e -> {
            Group selectedGroup = app.tabbedPanel.getSelectedGroup();

            if (selectedGroup == null || app.tabbedPanel.groups.isEmpty()) {
                JOptionPane.showMessageDialog(null, "You must create a group before creating a snippet");
                return;
            }

            String name = JOptionPane.showInputDialog("Snippet Name");

            if (name.isBlank()) return;


            for (Snippet snippet : selectedGroup.snippets) {
                if (name.equals(snippet.snippetName)) {
                    JOptionPane.showMessageDialog(null, "Snippet name is already taken",
                            "Name in use", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            selectedGroup.addSnippet(name);
        });

        searchBar = new JTextField();
        searchBar.setFont(new Font(FlatInterFont.FAMILY, Font.PLAIN, 16));
        searchBar.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search....");
        searchBar.putClientProperty(FlatClientProperties.STYLE, "arc : 10");
        searchBar.setPreferredSize(new Dimension(200, 30));

        searchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyTyped(e);
                Group selectedGroup = app.tabbedPanel.getSelectedGroup();

                searchString = searchBar.getText();

                for (Snippet snippet : selectedGroup.snippets) {
                    if (searchString.equals(snippet.snippetName)) {
                        System.out.println(snippet.snippetName + " found!");

                        snippet.highlight();

                        int y = snippet.getY();
                        selectedGroup.scrollPane.getVerticalScrollBar().setValue(y - 50);
                        System.out.println(y);

                    } else {
                        snippet.restore();
                    }
                }



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
