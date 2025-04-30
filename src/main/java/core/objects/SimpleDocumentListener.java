package core.objects;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public abstract class SimpleDocumentListener implements DocumentListener {

    @Override
    public void insertUpdate(DocumentEvent e) {
        updateText();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updateText();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        updateText();
    }

    abstract void updateText();

}
