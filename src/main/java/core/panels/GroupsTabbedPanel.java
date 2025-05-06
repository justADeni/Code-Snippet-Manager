package core.panels;

import core.App;
import core.objects.EditableLabel;
import core.objects.Group;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.OptionalInt;

public class GroupsTabbedPanel extends JTabbedPane {

    private final App app;

    public ArrayList<Group> groups;
    private Group selectedGroup;

    public GroupsTabbedPanel(App app) {
        this.app = app;
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        this.addChangeListener(e -> {
            try {
                int index = this.getSelectedIndex();
                final EditableLabel nameLabel = (EditableLabel) this.getTabComponentAt(index);
                groups.stream()
                        .filter(group -> group.getNameLabel().equals(nameLabel))
                        .findFirst()
                        .ifPresent(group -> selectedGroup = group);

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

    public void addGroup(String groupName, boolean newlyCreated) {
        Group group = new Group(this, groupName, newlyCreated);

        groups.add(group);

        this.addTab(null, group.scrollPane);
        this.setTabComponentAt(this.indexOfComponent(group.scrollPane), group.getNameLabel());
        revalidate();
    }

    public Group getSelectedGroup() {
        if (selectedGroup == null && !groups.isEmpty()) {
            selectedGroup = groups.getFirst();
            setSelectedComponent(selectedGroup.scrollPane);
        }

        return selectedGroup;
    }

}
