package core.objects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;

public abstract class EditableLabel extends JPanel {

    private final JLabel label;
    private final JTextField textField;

    @Override
    public String getName() {
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

    public EditableLabel(String text, int ipady, int ipadx, int rightInset) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weighty = 1.0;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.ipady = ipady;
        gbc.ipadx = ipadx;
        gbc.insets = new Insets(0, 0, 0, rightInset);

        label = new JLabel(text);
        label.setOpaque(true);
        label.setBackground(new Color(0, 0, 0, 0));

        textField = new JTextField();
        textField.setVisible(false);

        add(label, gbc);
        add(textField, gbc);

        addMouseListener(new MouseAdapter() {
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

        ActionListener finishEditHandler = e -> {
            switch (finishEdit(label.getText(), textField.getText())) {
                case LabelEditResult.Success ignored -> label.setText(textField.getText());
                case LabelEditResult.Error error -> JOptionPane.showMessageDialog(null, error.message(),
                        error.title(), JOptionPane.ERROR_MESSAGE);
            }
            textField.setVisible(false);
            label.setVisible(true);
        };
        textField.addActionListener(finishEditHandler);

        // ENTER and focus loss confirm edit
        textField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                // idk what but something already handles this case so avoid message dialog being thrown twice
                if (textField.getText().isBlank() && e.getCause() == FocusEvent.Cause.ACTIVATION)
                    return;

                finishEditHandler.actionPerformed(null);
            }
        });

        // ESC cancels edit
        textField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    textField.setText(getName());
                    finishEditHandler.actionPerformed(null);
                }
            }
        });
    }

    public abstract LabelEditResult finishEdit(String oldText, String newText);

    @Override
    public synchronized void addMouseListener(MouseListener l) {
        super.addMouseListener(l);
        label.addMouseListener(l);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EditableLabel that = (EditableLabel) o;
        return label.getText().equals(that.label.getText());
    }

    @Override
    public int hashCode() {
        return label.getText().hashCode();
    }
}