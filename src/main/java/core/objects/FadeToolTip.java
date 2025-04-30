package core.objects;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class FadeToolTip extends JLabel {

    private Timer fadeTimer;
    private int alpha = 255; // Full opacity

    public FadeToolTip(String text, Component relativeTo) {
        int x = relativeTo.getX() + relativeTo.getWidth();
        int y = relativeTo.getY() + relativeTo.getHeight();
        setText(text);
        FontMetrics fm = getFontMetrics(getFont());
        Rectangle2D bounds = fm.getStringBounds(text, getGraphics());
        int width = (int) bounds.getWidth() + 80;
        int height = 100;
        setMinimumSize(new Dimension(width, height));
        setLocation(x, y);
        setOpaque(false); // Set tooltip to be transparent for fading effect
        setBackground(new Color(210, 43, 43, alpha)); // Set initial background with full opacity
        fadeTimer = new Timer(100, e -> {
            if (alpha > 0) {
                alpha -= 5; // Decrease opacity
                setBackground(new Color(255, 255, 255, alpha)); // Update background opacity
                repaint(); // Repaint to apply changes
            } else {
                fadeTimer.stop(); // Stop fading when alpha reaches 0
                setVisible(false);
                getParent().remove(this);
            }
        });
        fadeTimer.setRepeats(true);
        fadeTimer.start();
        setVisible(true);
    }
}
