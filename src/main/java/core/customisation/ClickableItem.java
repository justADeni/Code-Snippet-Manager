package core.customisation;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClickableItem extends JMenuItem {

    public ClickableItem(String name, MenuClickHandler menuClickHandler) {
        super(name);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                menuClickHandler.click(name);
            }
        });
    }

}
