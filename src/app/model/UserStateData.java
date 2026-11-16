package app.model;

import app.ui.view.toolbox.ToolBox;

import java.awt.*;

public class UserStateData {

    private final Color DEFAULT_COLOR = new Color(1f,0f,0f,.5f);

    private Color toolBoxColorSelected;
    private ToolBox.ToolState currentTool;


    public UserStateData(){
        this.toolBoxColorSelected = DEFAULT_COLOR;
        this.currentTool = ToolBox.ToolState.CREATE;
    }

    public UserStateData(Color _colorSelected, ToolBox.ToolState _current){
        this.toolBoxColorSelected = _colorSelected;
        this.currentTool = _current;
    }

    public void updateColorSelected(Color _c)           { this.toolBoxColorSelected = new Color( _c.getRed() / 255 , _c.getGreen() / 255, _c.getBlue() / 255, .5f ); }
    public void updateCurrentTool(ToolBox.ToolState _s) { this.currentTool = _s; }
    public void updateSelectedComponent()               {}
    public void updateMouseLocation()                   {}

    public Color getSelectedColor()                     { return this.toolBoxColorSelected; }
}
