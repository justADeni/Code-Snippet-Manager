package core.objects;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.fonts.inter.FlatInterFont;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EditableSnippet extends JFrame {

    private Snippet snippet;

    private JPanel bottomPanel;

    private JTextField snippetNameField;
    private JTextArea snippetTextArea;
    private JScrollPane scrollPane;

    private JButton cancelButton, saveButton;

    public EditableSnippet(Snippet snippet) {
        this.snippet = snippet;

        this.setTitle("Edit Snippet");
        this.setSize(600, 500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        ((JPanel) this.getContentPane()).setBorder(new EmptyBorder(10, 30, 10, 30));

        init();
        addComponent();
    }

    public void init() {
        snippetNameField = new JTextField();
        snippetNameField.setPreferredSize(new Dimension(600, 40));
        snippetNameField.setFont(new Font(FlatInterFont.FAMILY, Font.PLAIN, 18));
        snippetNameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter snippet name");
        snippetNameField.setText(snippet.snippetName);

        snippetTextArea = new JTextArea();
        snippetTextArea.setFont(new Font(FlatInterFont.FAMILY, Font.PLAIN, 16));
        snippetTextArea.setText(snippet.snippetCode);

        scrollPane = new JScrollPane(snippetTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font(FlatInterFont.FAMILY, Font.PLAIN, 18));
        cancelButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        cancelButton.addActionListener(e -> {
            this.dispose();
        });

        saveButton = new JButton("Save");
        saveButton.setFont(new Font(FlatInterFont.FAMILY, Font.PLAIN, 18));
        saveButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        saveButton.setBackground(new Color(46, 97, 234));
        saveButton.addActionListener(e -> {
            snippet.snippetName = snippetNameField.getText();
            snippet.snippetCode = snippetTextArea.getText();

            snippet.updateGUI();

            this.dispose();
        });
    }

    public void addComponent() {
        bottomPanel.add(cancelButton);
        bottomPanel.add(saveButton);

        this.add(snippetNameField, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }
}