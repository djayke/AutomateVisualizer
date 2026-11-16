package app.ui.view.window;

import app.model.UserStateData;
import app.ui.components.BoxContainer;
import app.ui.components.CircleBox;
import app.ui.components.Edge;
import app.ui.view.toolbox.ToolBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.CubicCurve2D;
import java.util.ArrayList;
import java.util.Arrays;


public class AppEditorPanel extends JPanel implements MouseListener, MouseMotionListener {

    //* ********************************************************************************************************
    // TODO STATE Pattern?
    // ********************************************************************************************************/

    private static final int DRAG = 0;
    private static final int NORTH_EAST = 1;
    private static final int NORTH_WEST = 2;
    private static final int SOUTH_EAST = 3;
    private static final int SOUTH_WEST = 4;
    private static final int NORTH = 5;
    private static final int SOUTH = 6;
    private static final int EAST = 7;
    private static final int WEST = 8;


    //
    public record StructEventMetaData(JComponent current, Point pointBiais) {}
    private StructEventMetaData eventMouse ;

    //
    private final ArrayList<Edge> edges = new ArrayList<>();
    private BoxContainer edgeStartNode = null;

    //
    private ArrayList<CircleBox> comp = new ArrayList(); // components child for selector windowx when draggin
    private ArrayList<CircleBox> sel_comp = new ArrayList(); // selected components with draggin box
    private int sel_x = 0;
    private int sel_y = 0;
    private JPanel sel = new JPanel(); // Panel for slecting when draggin a square in screem
    private boolean selector = false;

    //
    private int cursorType = DRAG;


    //* ********************************************************************************************************
    // TODO void init function
    // ********************************************************************************************************/

    public AppEditorPanel(){
        super();
        this.setLayout(null);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setBackground(Color.white);
        sel.setSize(0,0);
        sel.setBackground(new Color(0f,0f,1f,.2f));
        sel.setBorder(BorderFactory.createLineBorder(new Color(0f,0f,1f,.5f),2) );
        this.add(sel);
        this.setVisible(true);
    }

    public void delete() {
        edges.removeIf(edge -> sel_comp.contains(edge.from) || sel_comp.contains(edge.to));
        comp.removeAll(sel_comp);
        for (CircleBox b : sel_comp) {
            this.remove(b);
        }
        this.sel_comp.clear();
        this.revalidate();
        this.repaint();
    }

    public void deleteAll() {
        edges.clear();
        for (CircleBox b : comp) {
            this.remove(b);
        }
        this.comp.clear();
        this.sel_comp.clear();
        this.revalidate();
        this.repaint();
    }

    public void select() {

    }

    public void unselectAll(){
        sel_comp.forEach(BoxContainer::unselect);
        sel_comp.clear();
    }

    public void paint(Color c){
        for(CircleBox b:sel_comp) {
            b.unselect();
        }
        this.sel_comp.clear();
        this.repaint();
        AppMainWindow.getInstance().getToolBar().setSelect();
    }


    private void addEdge(BoxContainer clickedBox){
        edges.add( new Edge(edgeStartNode, clickedBox) ); // Works for both different boxes AND self-loops!
        edgeStartNode.unselect();
        edgeStartNode = null;
        clickedBox.unselect();
        repaint();
    }

    private void addbox(int x, int y) {
        CircleBox b = new CircleBox(this);
        b.addMouseListener(this);
        b.addMouseMotionListener(this);
        b.setLocation(x-(b.getWidth()/2),y-(b.getHeight()/2));
        comp.add(b);
        this.repaint();
    }



    //* ********************************************************************************************************
    // TODO extraore clalcul generiqie
    // ********************************************************************************************************/


    @Override
    public void mouseDragged(MouseEvent e) {
        if(e.getSource() instanceof AppEditorPanel){
            //left to right & up to down
            if(e.getX()>sel_x && e.getY()>sel_y){
                sel.setLocation(sel_x,sel_y);
                sel.setSize(e.getX()-sel_x,e.getY()-sel_y);
            }
            //left to right & down to up
            if(e.getX()>sel_x && e.getY()<sel_y){
                sel.setLocation(e.getX()-sel.getWidth(),e.getY());
                sel.setSize(e.getX()-sel_x,sel_y-e.getY());
            }
            //right to left & down to up
            if(e.getX()<sel_x && e.getY()<sel_y){
                sel.setLocation(e.getX(),e.getY());
                sel.setSize(sel_x-e.getX(),sel_y-e.getY());
            }
            //right to left & up to down
            if(e.getX()<sel_x && e.getY()>sel_y){
                sel.setLocation(e.getX(),sel_y);
                sel.setSize(sel_x-e.getX(),e.getY()-sel_y);
            }
        }

        if (e.getSource() instanceof BoxContainer) {
            if(eventMouse != null){
                if(e.getSource() == eventMouse.current) {
                    switch(cursorType){
                        case DRAG:
                            int delta_x = e.getX()+eventMouse.current.getX()-eventMouse.pointBiais.x;
                            int delta_y = e.getY()+eventMouse.current.getY()-eventMouse.pointBiais.y;
                            eventMouse.current.setLocation(delta_x, delta_y);
                            break;
                        case SOUTH:
                            eventMouse.current.setSize(eventMouse.current.getWidth(),e.getY());
                            break;
                        case NORTH:
                            eventMouse.current.setSize(eventMouse.current.getWidth(),eventMouse.current.getHeight() - e.getY());
                            eventMouse.current.setLocation(eventMouse.current.getX(), eventMouse.current.getY()+e.getY());
                            break;
                        case EAST:
                            eventMouse.current.setSize(e.getX(),eventMouse.current.getHeight());
                            break;
                        case WEST:
                            eventMouse.current.setSize(eventMouse.current.getWidth()-e.getX(),eventMouse.current.getHeight());
                            eventMouse.current.setLocation(eventMouse.current.getX()+e.getX(),eventMouse.current.getY());
                            break;
                        case NORTH_EAST:
                            eventMouse.current.setSize(e.getX(), eventMouse.current.getHeight()-e.getY());
                            eventMouse.current.setLocation(eventMouse.current.getX(), eventMouse.current.getY()+e.getY());
                            break;
                        case SOUTH_EAST:
                            eventMouse.current.setSize(e.getX(),e.getY());
                            break;
                        case NORTH_WEST:
                            eventMouse.current.setSize(eventMouse.current.getWidth()-e.getX(), eventMouse.current.getHeight()-e.getY());
                            eventMouse.current.setLocation(eventMouse.current.getX()+e.getX(), eventMouse.current.getY()+e.getY());
                            break;
                        case SOUTH_WEST:
                            eventMouse.current.setSize(eventMouse.current.getWidth()-e.getX(), e.getY());
                            eventMouse.current.setLocation(eventMouse.current.getX()+e.getX(),eventMouse.current.getY());
                            break;
                        default:
                            break;
                    }
                    this.repaint(); // <-- Forces arrows to update smoothly while dragging/resizing
                }
            }
        };

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (e.getSource() instanceof AppEditorPanel)    AppMainWindow.getInstance().getBottomPage().updateInfo(e.getPoint());   // eviter dedoublement d'instance des views access statique entre eux via le singleton du framae


        if (e.getSource() instanceof BoxContainer obj) {
            if((e.getY() > (obj.getHeight()-5)) && (e.getX() < 5)){
                setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
                cursorType=SOUTH_WEST;
            }

            else if((e.getY() > (obj.getHeight()-5)) && (e.getX() > obj.getWidth()-5)){
                setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
                cursorType=SOUTH_EAST;
            }

            else if((e.getY() < 5) && (e.getX() < 5)){
                setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
                cursorType=NORTH_WEST;
            }

            else if((e.getY() < 5) && (e.getX() > (obj.getWidth())-5)){
                setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
                cursorType=NORTH_EAST;
            }

            else if(e.getY() > (obj.getHeight()-5)){
                setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
                cursorType=SOUTH;
            }

            else if(e.getX() < 5){
                setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
                cursorType=WEST;
            }

            else if(e.getX() > (obj.getWidth()-5)){
                setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                cursorType=EAST;
            }

            else if(e.getY() < 5){
                setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                cursorType=NORTH;
            }

            else{
                setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                cursorType=DRAG;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() instanceof AppEditorPanel) {
            if (selector) {
                for (CircleBox b : sel_comp) {
                    b.unselect();
                    comp.removeAll(sel_comp);
                    this.remove(b);
                    this.sel_comp.clear();
                }
                selector = false;
            }
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource() instanceof AppEditorPanel) {
            sel_x = e.getX();
            sel_y = e.getY();
            sel.setLocation(sel_x, sel_y);
        }
        else if(e.getSource() instanceof BoxContainer){
            if(AppMainWindow.getInstance().getToolBar().getCurrentTool().equals(ToolBox.ToolState.EDGE)){



            } else {
                for (BoxContainer b : comp)
                    b.unselect();

                eventMouse = new StructEventMetaData( (BoxContainer) e.getSource(), e.getPoint() );
                ((BoxContainer) eventMouse.current()).select();
                sel_comp.clear();
                sel_comp.add((CircleBox) eventMouse.current());
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(AppMainWindow.getInstance().getToolBar().getCurrentTool().equals(ToolBox.ToolState.SELECT)){
            for(CircleBox b:comp)
            {
                if(b.getX()>sel.getX() && b.getX()<sel_x+sel.getWidth() &&
                        b.getY()>sel.getY() && b.getY()<sel_y+sel.getHeight()){
                    b.select();
                    sel_comp.add(b);
                    this.selector = true;
                }
            }
        }
        if(AppMainWindow.getInstance().getToolBar().getCurrentTool().equals(ToolBox.ToolState.CREATE)){
            if(e.getSource() instanceof AppEditorPanel) {
                this.addbox(sel_x, sel_y);
            }
        }
        if (AppMainWindow.getInstance().getToolBar().getCurrentTool().equals(ToolBox.ToolState.EDGE)) {
            if (e.getSource() instanceof BoxContainer clickedBox) {
                if (edgeStartNode == null) {
                    edgeStartNode = clickedBox;
                    clickedBox.select();
                } else {
                    this.addEdge(clickedBox);
                }
            }
        }

        sel.setSize(0,0);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof BoxContainer) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }

    //* ********************************************************************************************************
    // TODO Couplage mal fait mettre dans class edge
    // ********************************************************************************************************/


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        for (Edge edge : edges) {
            BoxContainer from = edge.from;
            BoxContainer to = edge.to;

            // Center of 'from' circle/oval
            double rx1 = from.getWidth() / 2.0;
            double ry1 = from.getHeight() / 2.0;
            double cx1 = from.getX() + rx1;
            double cy1 = from.getY() + ry1;

            if (edge.isSelfLoop()) {
                // Use top radius (ry1) so self-loop sits cleanly on top edge even if resized
                    edge.drawSelfLoop(g2, cx1, cy1, ry1, -Math.PI / 2);
            } else {
                double rx2 = to.getWidth() / 2.0;
                double ry2 = to.getHeight() / 2.0;
                double cx2 = to.getX() + rx2;
                double cy2 = to.getY() + ry2;
                    edge.drawArrowBetweenOvals(g2, cx1, cy1, rx1, ry1, cx2, cy2, rx2, ry2);
            }
        }

        g2.dispose();
    }


}