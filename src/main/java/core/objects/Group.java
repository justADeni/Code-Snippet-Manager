package core.objects;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.fonts.inter.FlatInterFont;
import core.panels.GroupsTabbedPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Group extends JPanel {

    private GroupsTabbedPanel groupsTabbedPanel;
    public String groupName;

    public ArrayList<Snippet> snippets;
    public JScrollPane scrollPane;

    private JPopupMenu popupMenu;

    private JTextField searchBar;

    public Group(GroupsTabbedPanel groupsTabbedPanel, String groupName) {
        this.groupsTabbedPanel = groupsTabbedPanel;
        this.groupName = groupName;
        this.setPreferredSize(new Dimension(400, 4000));
        this.setBackground(groupsTabbedPanel.getBackground().darker());
        this.setLayout(new FlowLayout());

        snippets = new ArrayList<>();

        scrollPane = new JScrollPane(this);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setValue(0);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        searchBar = new JTextField();
        searchBar.setFont(new Font(FlatInterFont.FAMILY, Font.PLAIN, 16));
        searchBar.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search....");

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenu.show(scrollPane, e.getX(), e.getY() + 10);
                }
            }
        });

        initPopupMenu();
    }

    public void initPopupMenu() {
        popupMenu = new JPopupMenu();

        JMenuItem deleteGroupItem = new JMenuItem("Delete Group");
        JMenuItem renameGroupItem = new JMenuItem("Rename Group");

        renameGroupItem.addActionListener(e -> renameGroup());
        deleteGroupItem.addActionListener(e -> deleteGroup());

        popupMenu.add(renameGroupItem);
        popupMenu.add(deleteGroupItem);
    }

    public void addSnippet(String name, String code) {
        Snippet snippet = new Snippet(this, name, code);

        snippets.add(snippet);
        this.add(snippet);

        revalidate();
    }

    public void deleteGroup() {
        int confirm = JOptionPane.showConfirmDialog(null,
                "Delete group?",
                "Deleted groups cannot be restored",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            groupsTabbedPanel.groups.remove(this);

            int index = groupsTabbedPanel.getSelectedIndex();
            groupsTabbedPanel.remove(index);
        }
    }

    public void renameGroup() {
        String newName = JOptionPane.showInputDialog("Enter group name");

        if (newName == null || newName.trim().isEmpty()) {
            return; // Cancel or empty input, do nothing
        }

        for (Group group : groupsTabbedPanel.groups) {
            if (newName.equals(group.groupName)) {
                JOptionPane.showMessageDialog(null, "Group name is already taken",
                        "Name in use", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        this.groupName = newName;

        int index = groupsTabbedPanel.getSelectedIndex();
        groupsTabbedPanel.setTitleAt(index, newName);
    }


    @Override
    public String toString() {
        return groupName;
    }

}
