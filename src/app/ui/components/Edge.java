package app.ui.components;

import app.ui.view.window.AppEditorPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;

public class Edge extends JComponent {
    public final BoxContainer from;
    public final BoxContainer to;

    public Edge(BoxContainer from, BoxContainer to) {
        this.from = from;
        this.to = to;
    }


    public BoxContainer getFrom() { return from; }
    public BoxContainer getTo() { return to; }
    public boolean isSelfLoop() { return from == to; }




    public void drawSelfLoop(Graphics2D g2, double cx, double cy, double r, double directionAngle) {
        // 1. Define the size and offset of the loop circle
        double loopRadius = r * 0.65;              // Radius of the loop itself (adjust for bigger/smaller loop)
        double loopCenterDist = r + loopRadius * 0.65; // Distance from node center to loop center

        // Center of the loop circle
        double loopCx = cx + loopCenterDist * Math.cos(directionAngle);
        double loopCy = cy + loopCenterDist * Math.sin(directionAngle);

        // 2. Calculate the two exact intersection points between the node circle and loop circle
        // Using the law of cosines for two intersecting circles of radii r and loopRadius
        double d = loopCenterDist;
        double a = (r * r - loopRadius * loopRadius + d * d) / (2 * d);
        double h = Math.sqrt(Math.max(0, r * r - a * a));

        // Midpoint along the line between centers
        double midX = cx + a * Math.cos(directionAngle);
        double midY = cy + a * Math.sin(directionAngle);

        // Perpendicular vector
        double perpX = -Math.sin(directionAngle);
        double perpY = Math.cos(directionAngle);

        // Start and end points on the node's circumference
        double startX = midX - h * perpX;
        double startY = midY - h * perpY;
        double endX = midX + h * perpX;
        double endY = midY + h * perpY;

        // 3. Calculate Swing's Arc2D angles (in degrees, counter-clockwise from 3 o'clock)
        double startDeg = Math.toDegrees(Math.atan2(-(startY - loopCy), startX - loopCx));
        double endDeg = Math.toDegrees(Math.atan2(-(endY - loopCy), endX - loopCx));

        // Sweep clockwise around the outside of the loop (negative extent in Swing)
        double extentDeg = endDeg - startDeg;
        if (extentDeg > 0) {
            extentDeg -= 360;
        }

        // 4. Draw the circular arc
        Arc2D arc = new Arc2D.Double(
                loopCx - loopRadius,
                loopCy - loopRadius,
                loopRadius * 2,
                loopRadius * 2,
                startDeg,
                extentDeg,
                Arc2D.OPEN
        );
        g2.draw(arc);

        // 5. Calculate the exact tangent angle at (endX, endY) for the arrowhead
        // The tangent to a circle at a point is perpendicular (90 deg clockwise) to the radius vector
        double radiusAngle = Math.atan2(endY - loopCy, endX - loopCx);
        double arrowAngle = radiusAngle - Math.PI / 2;

        drawArrowHead(g2, endX, endY, arrowAngle);
    }



    public void drawArrowBetweenOvals(Graphics2D g2,
                                       double x1, double y1, double rx1, double ry1,
                                       double x2, double y2, double rx2, double ry2) {
        double angle = Math.atan2(y2 - y1, x2 - x1);

        // Exact point on border of oval 1 and oval 2 at 'angle'
        double startX = x1 + rx1 * Math.cos(angle);
        double startY = y1 + ry1 * Math.sin(angle);
        double endX = x2 - rx2 * Math.cos(angle);
        double endY = y2 - ry2 * Math.sin(angle);

        // Prevent drawing if ovals overlap
        if (Math.hypot(x2 - x1, y2 - y1) <= Math.hypot(startX - x1, startY - y1) + Math.hypot(x2 - endX, y2 - endY)) {
            return;
        }

        g2.draw(new java.awt.geom.Line2D.Double(startX, startY, endX, endY));
        drawArrowHead(g2, endX, endY, angle);
    }

    private void drawArrowHead(Graphics2D g2, double tipX, double tipY, double angle) {
        AffineTransform old = g2.getTransform();
        g2.translate(tipX, tipY);
        g2.rotate(angle);

        int arrowSize = 11;
        g2.fillPolygon(
                new int[]{0, -arrowSize, -arrowSize},
                new int[]{0, -arrowSize / 2, arrowSize / 2},
                3
        );
        g2.setTransform(old);
    }


}
