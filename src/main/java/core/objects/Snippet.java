package core.objects;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.fonts.inter.FlatInterFont;
import core.highlight.Detector;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.geom.Rectangle2D;

public class Snippet extends JPanel {

    public Group group;

    private JPanel controlPanel;
    private EditableLabel nameLabel;
    private JButton copyButton;
    private JButton deleteButton;
    private JToggleButton editToggle;
    private final Dimension buttonDimension = new Dimension(80, 35);

    private RSyntaxTextArea snippetTextArea;
    private final Dimension rolledSize = new Dimension(400, 200);
    private LineCounter lineCounter;

    private final Detector detector = new Detector();

    public Snippet(Group group, String snippetName, String snippetCode) {
        this.group = group;

        this.setLayout(new BorderLayout());
        this.setBackground(group.getBackground().brighter());
        this.setBorder(new EmptyBorder(0, 10, 10, 10));
        this.setPreferredSize(rolledSize);
        this.putClientProperty(FlatClientProperties.STYLE, "arc : 20");

        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        controlPanel.setPreferredSize(new Dimension(this.getPreferredSize().width, 70));

        nameLabel = new EditableLabel(snippetName, this);
        nameLabel.setFont(new Font(FlatInterFont.FAMILY, Font.PLAIN, 20));
        nameLabel.setPreferredSize(new Dimension(160, 90));

        copyButton = new JButton("Copy");
        copyButton.setPreferredSize(buttonDimension);
        copyButton.setMaximumSize(buttonDimension);
        copyButton.setBackground(new Color(46, 97, 234));
        copyButton.addActionListener(e -> {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

            StringSelection selection = new StringSelection(getText());
            clipboard.setContents(selection, null);
        });

        deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(buttonDimension);
        deleteButton.setMaximumSize(buttonDimension);
        deleteButton.setBackground(new Color(148, 24, 24));
        deleteButton.addActionListener(e -> deleteSnippet());

        editToggle = new JToggleButton("Edit");
        editToggle.setPreferredSize(buttonDimension);
        editToggle.setMaximumSize(buttonDimension);
        editToggle.setBackground(new Color(176, 122, 5));
        editToggle.addActionListener(e -> {
            if (editToggle.isSelected()) {
                editToggle.setText("Shrink");
                snippetTextArea.setEditable(true);
            } else {
                editToggle.setText("Edit");
                snippetTextArea.setEditable(false);
            }
            resize();
            revalidate();
        });

        snippetTextArea = new RSyntaxTextArea(snippetCode);
        snippetTextArea.setEditable(false);
        snippetTextArea.setAutoIndentEnabled(true);
        detectCodeType();
        snippetTextArea.setPreferredSize(rolledSize);
        snippetTextArea.setBackground(new Color(23, 23, 23, 226));
        snippetTextArea.setCurrentLineHighlightColor(new Color(28, 28, 28, 226));
        snippetTextArea.setForeground(new Color(236, 236, 236));
        snippetTextArea.getDocument().addDocumentListener(new SimpleDocumentListener() {
            @Override
            public void updateText() {
                snippetTextArea.revalidate();
                lineCounter.render(snippetTextArea.getLineCount());
                lineCounter.revalidate();
                revalidate();
                resize();
            }
        });

        lineCounter = new LineCounter();
        lineCounter.setBackground(new Color(52, 52, 52, 226));
        lineCounter.setFont(snippetTextArea.getFont());
        lineCounter.render(snippetTextArea.getLineCount());

        JPanel textAreasHolder = new JPanel();
        textAreasHolder.setLayout(new BoxLayout(textAreasHolder, BoxLayout.X_AXIS));
        textAreasHolder.add(lineCounter);
        textAreasHolder.add(snippetTextArea);

        controlPanel.add(nameLabel);
        controlPanel.add(deleteButton);
        controlPanel.add(copyButton);
        controlPanel.add(editToggle);

        this.add(controlPanel, BorderLayout.NORTH);
        this.add(textAreasHolder, BorderLayout.CENTER);
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getText() {
        return snippetTextArea.getText();
    }

    public void highlight() {
        setBackground(new Color(73, 137, 220));
        controlPanel.setBackground(new Color(73, 137, 220));
    }

    public void restore() {
        setBackground(group.getBackground().brighter());
        controlPanel.setBackground(getBackground());
    }

    private void deleteSnippet() {
        int confirmed = !getText().isBlank() ? JOptionPane.showConfirmDialog(null,
                "Deleted snippets cannot be restored",
                "Delete snippet?", JOptionPane.YES_NO_OPTION) : JOptionPane.YES_OPTION;

        if (confirmed == JOptionPane.YES_OPTION) {
            group.snippets.remove(this);
            group.remove(this);
            group.revalidate();
            group.repaint();
        }

    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        if (snippetTextArea != null) {
            snippetTextArea.setPreferredSize(rolledSize);
        }
        super.setPreferredSize(preferredSize);
    }

    private String longestLine() {
        String[] lines = getText().split("\\r?\\n");
        String longestLine = "";
        for (String line : lines) {
            if (line.length() > longestLine.length()) {
                longestLine = line;
            }
        }
        return longestLine;
    }

    private boolean editMode() {
        return editToggle.isSelected();
    }

    private void resize() {
        if (editMode()) {
            FontMetrics fm = snippetTextArea.getFontMetrics(snippetTextArea.getFont());
            Rectangle2D bounds = fm.getStringBounds(longestLine(), getGraphics());
            int width = Math.max((int) bounds.getWidth() + 80 + lineCounter.getWidth(), rolledSize.width);
            int height = Math.max(snippetTextArea.getLineHeight() * snippetTextArea.getLineCount() + 100, rolledSize.height);
            setPreferredSize(new Dimension(width,height));
        } else {
            setPreferredSize(rolledSize);
        }
    }

    public void detectCodeType() {
        snippetTextArea.setSyntaxEditingStyle(detector.get(getName()));
    }

}
