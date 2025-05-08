package core.objects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;

/**
 * @author <a href="https://github.com/hccampos">Hugo Campos</a>
 */
public class SmoothScrollPane extends JScrollPane {
    private static final int FPS = 50;
    private static final int MILLIS_PER_FRAME = 1000 / FPS;

    private static final double STEP = 0.0004;
    private static final double MAX_FORCE = 0.08;
    private static final double PSEUDO_FRICTION = 0.9;
    private static final double ACC_REDUCTION_FACTOR = 0.6;
    private static final double SPEED_THRESHOLD = 0.001;

    private double _velocity = 0.0;
    private double _force = 0.0;
    private long _lastUpdate = 0;
    private long _lastScroll = 0;

    /**
     * Constructor for our SmoothScrollPane.
     */
    public SmoothScrollPane(Component view) {
        super(view);
        Timer _timer = new Timer(MILLIS_PER_FRAME, e -> update());
        _timer.start();
    }

    /**
     * Updates the velocity, force and acceleration acting on the scroll offset and then updates
     * the scroll offset according to these parameters.
     */
    private void update() {
        if (_lastUpdate == 0) {
            _lastUpdate = System.nanoTime();
            return;
        }

        long currentTime = System.nanoTime();
        double elapsedMillis = (currentTime - _lastUpdate) * 1.0e-6;
        _lastUpdate = currentTime;

        double exponent = elapsedMillis / 16.0;

        _force *= Math.pow(ACC_REDUCTION_FACTOR, exponent);
        _velocity += _force * elapsedMillis;
        _velocity *= Math.pow(PSEUDO_FRICTION, exponent);

        double speed = Math.abs(_velocity);
        if (speed >= SPEED_THRESHOLD) {
            Point currentPosition = viewport.getViewPosition();
            int newY = Math.max(0, (int)Math.round(currentPosition.y + _velocity * elapsedMillis));
            viewport.setViewPosition(new Point(currentPosition.x, newY));
        } else {
            _velocity = 0.0;
        }
    }

    /**
     * Function which is called when the mouse wheel is rotated.
     *
     * @param e
     *      The object which contains the information of the event.
     */
    @Override
    protected void processMouseWheelEvent(MouseWheelEvent e) {
        if (_lastScroll == 0) {
            _lastScroll = System.nanoTime();
            return;
        }

        long currentTime = System.nanoTime();
        double elapsedMillis = (currentTime - _lastScroll) * 1.0e-6;
        _lastScroll = currentTime;

        if (elapsedMillis == 0) { return; }

        double wheelDelta = dampen(e.getPreciseWheelRotation());
        boolean sameDirection = _velocity * wheelDelta >= 0;

        if (sameDirection) {
            double currentStep = wheelDelta * STEP;
            _force += currentStep + currentStep / (elapsedMillis * 0.0007);

            // Limit the magnitude of the force to MAX_FORCE.
            double forceMagnitude = Math.abs(_force);
            if (forceMagnitude > MAX_FORCE) { _force *= MAX_FORCE / forceMagnitude; }
        } else {
            _force = 0;
            _velocity = 0;
        }
        update();
    }

    /**
     * Dampens values above 1 or below -1. This is mostly just for trackpad
     * @param x precise wheel rotation
     * @return dampened output
     */
    private double dampen(double x) {
        if (Math.abs(x) <= 1) return x;
        return Math.signum(x) * (1 + 0.1 * (Math.abs(x) - 1));
    }
}
