package core.objects;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.fonts.inter.FlatInterFont;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Snippet extends JPanel {

    public Group group;
    public String snippetName;
    public String snippetCode;

    private JPanel controlPanel;
    private JLabel nameLabel;
    private JButton copyButton;
    private Dimension buttonDimension = new Dimension(60, 35);

    private JTextArea snippetTextArea;

    private JPopupMenu popupMenu;

    public Snippet(Group group, String name) {
        this.snippetName = name;
        this.group = group;

        this.setLayout(new BorderLayout());
        this.setBackground(group.getBackground().brighter());
        this.setBorder(new EmptyBorder(0, 10, 10, 10));
        this.setPreferredSize(new Dimension(400, 200));
        this.putClientProperty(FlatClientProperties.STYLE, "arc : 20");

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenu.show(getSnippetPanel(), e.getX(), e.getY() + 30);
                }
            }
        });

        init();
    }

    public void init() {
        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        controlPanel.setPreferredSize(new Dimension(this.getPreferredSize().width, 70));

        nameLabel = new JLabel();
        nameLabel.setText(snippetName);
        nameLabel.setFont(new Font(FlatInterFont.FAMILY, Font.PLAIN, 20));
        nameLabel.setPreferredSize(new Dimension(360, 90));

        copyButton = getButton("Copy", new Color(46, 97, 234));
        copyButton.addActionListener(e -> {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

            StringSelection selection = new StringSelection(snippetCode);
            clipboard.setContents(selection, null);
        });

        snippetTextArea = new JTextArea();
        snippetTextArea.setPreferredSize(new Dimension(400, 200));


        controlPanel.add(nameLabel);
        controlPanel.add(copyButton);

        this.add(controlPanel, BorderLayout.NORTH);
        this.add(snippetTextArea, BorderLayout.CENTER);

        initPopupMenu();
    }

    public void initPopupMenu() {
        popupMenu = new JPopupMenu();

        JMenuItem edit = new JMenuItem("Edit");
        JMenuItem delete = new JMenuItem("Delete");

        edit.addActionListener(e -> {
            new EditableSnippet(this);
        });

        delete.addActionListener(e -> {

            int confirmed = JOptionPane.showConfirmDialog(null,
                    "Deleted snippets cannot be restored",
                    "Delete snippet?", JOptionPane.YES_NO_OPTION);

            if (confirmed == JOptionPane.YES_OPTION) {
                group.snippets.remove(this);
                group.remove(this);
            }
        });


        popupMenu.add(edit);
        popupMenu.add(delete);
    }

    public void updateGUI() {
        nameLabel.setText(snippetName);

        try {
            String[] lines = snippetCode.split("\n");
            StringBuilder preview = new StringBuilder();

            for (int i = 0; i < Math.min(4, lines.length); i++) {
                preview.append(lines[i]).append("\n");
            }

            if (lines.length > 4) {
                preview.append("...");
            }

            snippetTextArea.setText(preview.toString());
        } catch (NullPointerException e) {
            repaint();
        }

        this.revalidate();
    }

    public void highlight() {
        setBackground(new Color(73, 137, 220));
        controlPanel.setBackground(new Color(73, 137, 220));
    }

    public void restore() {
        setBackground(group.getBackground().brighter());
        controlPanel.setBackground(getBackground());
    }

    public JButton getButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setPreferredSize(buttonDimension);
        button.setMaximumSize(buttonDimension);
        button.setBackground(color);

        return button;
    }

    private JPanel getSnippetPanel() {
        return this;
    }

}
