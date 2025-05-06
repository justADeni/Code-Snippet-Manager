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
import java.util.TimerTask;

public class Group extends JPanel {

    private final GroupsTabbedPanel groupsTabbedPanel;
    public final EditableLabel nameLabel;

    public final ArrayList<Snippet> snippets;
    public final JScrollPane scrollPane;

    private final JTextField searchBar;

    public Group(GroupsTabbedPanel groupsTabbedPanel, String groupName) {
        this.groupsTabbedPanel = groupsTabbedPanel;
        final Group group = this;
        nameLabel = new EditableLabel(groupName, 0, 0, 0) {
            @Override
            public LabelEditResult finishEdit(String oldText, String newText) {
                if (newText.isBlank()) {
                    return new LabelEditResult.Error("Name blank", "Group name cannot be blank");
                } else if (groupsTabbedPanel.groups.stream().anyMatch(g -> g != group && g.getName().equalsIgnoreCase(newText))) {
                    return new LabelEditResult.Error("Name in use", "Group name is already taken");
                } else {
                    return new LabelEditResult.Success();
                }
            }
        };
        nameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                groupsTabbedPanel.setSelectedComponent(scrollPane);
            }
        });

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

    @Override
    public String getName() {
        return nameLabel.getName();
    }

    @Override
    public String toString() {
        return getName();
    }

}
