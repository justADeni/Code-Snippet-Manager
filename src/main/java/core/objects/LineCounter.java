package core.objects;

import core.highlight.ThemedSyntaxTextArea;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class LineCounter extends ThemedSyntaxTextArea {

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg.darker());
    }

    public LineCounter() {
        super("1");
        setEditable(false);
        setFocusable(false);
        setOpaque(true);
        setHighlightCurrentLine(false);
    }

    // This is ugly but it wouldn't work any other way
    public void render(int lines) {
        setMinimumSize(null);
        setMaximumSize(null);
        StringBuilder lineText = new StringBuilder();
        for (int i = 1; i <= lines; i++) {
            lineText.append(i).append("\n");
        }
        this.setText(lineText.toString());
        FontMetrics fm = this.getFontMetrics(this.getFont());
        Rectangle2D bounds = fm.getStringBounds(Integer.toString(lines), getGraphics());
        int width = (int) Math.ceil(Math.max(18, bounds.getWidth() * 2));
        Dimension dimension = new Dimension(width, Integer.MAX_VALUE);
        setMinimumSize(dimension);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
    }

}
