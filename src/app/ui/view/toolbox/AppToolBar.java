package app.ui.view.toolbox;


import app.ui.view.window.AppMainWindow;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AppToolBar extends JToolBar implements MouseListener {

    public Color c = new Color(1f, 1f, 1f, 0.5f);
    public static Color DARK_GRAY = new Color(0.14f, 0.14f, 0.14f);
    protected ToolBox.ToolState current = ToolBox.ToolState.CREATE;


    public AppToolBar(){
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));//create grid layout frame
        this.addTool(ToolBox.ToolState.SELECT);
        this.addTool(ToolBox.ToolState.CREATE);
        this.addTool(ToolBox.ToolState.EDGE);
        this.addTool(ToolBox.ToolState.COLOR);
        this.addTool(ToolBox.ToolState.DELETE);
        this.addTool(ToolBox.ToolState.DELETEALL);
        this.setFloatable(false);
        this.setBackground(AppToolBar.DARK_GRAY);
        this.setBorder(initBorder());

    }

    private CompoundBorder initBorder() {
        Border shadow = BorderFactory.createMatteBorder(1, 1, 3, 3, new Color(0, 0, 0, 60));
        Border line   = BorderFactory.createLineBorder(new Color(70, 70, 70), 1);
        Border padding = BorderFactory.createEmptyBorder(8, 12, 8, 12);

        return BorderFactory.createCompoundBorder(
                shadow,
                BorderFactory.createCompoundBorder(line, padding)
        );
    }

    private void addTool(ToolBox.ToolState name){
        JButton b = new JButton(new ImageIcon(name.getIcon()));
        b.setBackground(null);
        b.setName(name.getName());
        b.addMouseListener(this);
        this.add(b);
    }

    public ToolBox.ToolState getCurrentTool() {
        return this.current;
    }
    public void setSelect() {
        this.current = ToolBox.ToolState.SELECT;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        String s = ((JButton)e.getSource()).getName();
        if(s.equals(ToolBox.ToolState.CREATE.getName())){
            this.current = ToolBox.ToolState.CREATE;
        }

        if(s.equals(ToolBox.ToolState.SELECT.getName())){
            this.current = ToolBox.ToolState.SELECT;
        }

        if(s.equals(ToolBox.ToolState.COLOR.getName())){
            this.current = ToolBox.ToolState.COLOR;
            Color color = JColorChooser.showDialog(this,"Select a color", Color.RED);
            if(color != null) {
                AppMainWindow.getInstance().getUserData().updateColorSelected(color);
            }
        }

        if(s.equals(ToolBox.ToolState.EDGE.getName())){
            this.current = ToolBox.ToolState.EDGE;
            AppMainWindow.getInstance().getEditorPanel().unselectAll();
        }

        if(s.equals(ToolBox.ToolState.DELETE.getName())){
            this.current = ToolBox.ToolState.DELETE;
            AppMainWindow.getInstance().getEditorPanel().delete();
        }

        if(s.equals(ToolBox.ToolState.DELETEALL.getName())){
            this.current = ToolBox.ToolState.DELETEALL;
            AppMainWindow.getInstance().getEditorPanel().deleteAll();
        }

        AppMainWindow.getInstance().getUserData().updateCurrentTool(this.current);
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}