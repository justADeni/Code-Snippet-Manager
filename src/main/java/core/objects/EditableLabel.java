package core.objects;

import com.formdev.flatlaf.extras.components.FlatButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;

public class EditableLabel extends JPanel {

    private final Snippet snippet;

    private final JLabel label;
    private final JTextField textField;

    public String getText() {
        return label.getText();
    }

    @Override
    public void setFont(Font font) {
        if (label != null) {
            label.setFont(font);
            textField.setFont(font);
        }
    }

    @Override
    public void setPreferredSize(Dimension dimension) {
        if (label != null) {
            label.setPreferredSize(dimension);
            textField.setPreferredSize(dimension);
        }
        super.setPreferredSize(dimension);
        super.setMaximumSize(dimension);
        super.setMinimumSize(dimension);
    }

    public EditableLabel(String text, Snippet snippet) {
        this.snippet = snippet;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weighty = 1.0;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.ipady = 20;
        gbc.ipadx = 120;
        gbc.insets = new Insets(0, 0, 0, 50);

        label = new JLabel(text);
        textField = new JTextField();
        textField.setVisible(false);

        add(label, gbc);
        add(textField, gbc);

        label.addMouseListener(new MouseAdapter() {
            boolean isAlreadyOneClick;
            final java.util.Timer timer = new java.util.Timer("doubleClickTimer", false);
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isAlreadyOneClick) {

                    // double click
                    textField.setText(label.getText());
                    label.setVisible(false);
                    textField.setVisible(true);
                    textField.requestFocus();
                    textField.selectAll();

                    isAlreadyOneClick = false;
                } else {
                    isAlreadyOneClick = true;
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            isAlreadyOneClick = false;
                        }
                    }, 500);
                }
            }
        });

        ActionListener finishEditing = e -> {
            String newText = textField.getText();
            if (newText.isBlank() || snippet.group.snippets.stream().anyMatch(s -> snippet.getName().equalsIgnoreCase(newText))) {
                add(new FadeToolTip("Error", this));
            } else {
                label.setText(textField.getText());
                snippet.detectCodeType();
            }
            textField.setVisible(false);
            label.setVisible(true);
        };

        // Handles ENTER and focus loss
        textField.addActionListener(finishEditing);
        textField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                finishEditing.actionPerformed(null);
            }
        });

        // ESC cancels edit
        textField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    textField.setText(getText());
                    finishEditing.actionPerformed(null);
                }
            }
        });
    }
}