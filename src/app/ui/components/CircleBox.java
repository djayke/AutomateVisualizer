package app.ui.components;

import app.ui.view.toolbox.AppToolBar;
import app.ui.view.window.AppMainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.CubicCurve2D;


public class CircleBox extends BoxContainer {

    public CircleBox(JComponent parent) {
        super(parent);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        // Enable smooth edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2.setColor(this.color);

        // Draw rounded box
        g2.fillOval(0, 0, getWidth(), getHeight());
        g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // Use the CircleBox's bounds in parent coordinates!
//        int cx = this.getX() + this.getWidth() / 2;
//        int cy = this.getY() + this.getHeight() / 2;
//        int radius = this.getWidth() / 2;

        // Draw oval
        g2.drawOval(0, 0, getWidth(), getHeight());

        //drawArrowBetweenCircles(g2, this.getX(), this.getY(), this.getWidth(), this.getX(), this.getY(), this.getHeight());
//        g2.setColor(Color.BLACK);
//        drawSelfLoop(g2, cx, cy, radius, -Math.PI / 2);

        g2.dispose();
    }

}