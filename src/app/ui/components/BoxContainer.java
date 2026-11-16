package app.ui.components;

import app.ui.view.window.AppMainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

//* ********************************************************************************************************
// // TODO MAKE ABSTRACT -> Paint Component implements on different container Done but class Editor should used a generic BoxContainer add takke a static function as instanciate type the check on boar dhsould use ost geenric instancof
// TODO when selectig HOVER colors and resize only on NW SW NE SW cursor location
// ********************************************************************************************************/

public class BoxContainer extends JComponent{

    public static final int DEFAULT_WIDTH  = 60;
    public static final int DEFAULT_HEIGHT = 60;
    public static final int MIN_WIDTH  = 50;
    public static final int MIN_HEIGHT = 50;

    protected Color color = Color.BLACK;
    private boolean selected = false;

    public BoxContainer(JComponent parent)
    {
        super();
        this.setLayout(null);
        this.setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        this.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        this.select();

        this.color = AppMainWindow.getInstance().getUserData().getSelectedColor();

        parent.add(this);
    }

    public void select(){
        //this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        this.selected = true;
        repaint();
    }

    public void unselect(){
        //this.setBorder(null);
        this.selected = false;
        repaint();
    }

}