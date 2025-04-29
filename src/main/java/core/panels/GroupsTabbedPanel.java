package core.panels;

import core.App;
import core.objects.Group;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class GroupsTabbedPanel extends JTabbedPane {

    private final App app;

    public ArrayList<Group> groups;
    private Group selectedGroup;

    public GroupsTabbedPanel(App app) {
        this.app = app;
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        this.addChangeListener(e -> {
            try {
                int selectedIndex = this.getSelectedIndex();
                String selectedTitle = this.getTitleAt(selectedIndex);

                for (Group group : groups) {
                    if (selectedTitle.equals(group.groupName)) {
                        selectedGroup = group;
                        break;
                    }
                }
            } catch (IndexOutOfBoundsException ex) {
                repaint();
                revalidate();
            }
        });
    }

    public void init() {
        groups = new ArrayList<>(5);
    }

    public void addComponent() {
        app.add(this, BorderLayout.CENTER);
    }

    public void addGroup(String groupName) {
        Group group = new Group(this, groupName);
        groups.add(group);

        this.addTab(groupName, group.scrollPane);
        revalidate();
    }

    public Group getSelectedGroup() {
        return selectedGroup;
    }

}
