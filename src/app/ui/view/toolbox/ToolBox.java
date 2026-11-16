package app.ui.view.toolbox;

import java.net.URL;

// TODO CLEAN UP USELESS TOOL ADD ANOTHER TYOE

public class ToolBox {

    public enum ToolState {
        SELECT("Select", ToolBox.class.getResource("/hand_touch3.png")),
        CREATE("Create", ToolBox.class.getResource("/bomb.png")),
        EDGE("Edge", ToolBox.class.getResource("/screwdriver.png")),
        COLOR("Color", ToolBox.class.getResource("/gif.png")),
        DELETE("Delete", ToolBox.class.getResource("/delete.gif")),
        DELETEALL("Delete All", ToolBox.class.getResource("/chainsaw.png"));

        private final String name;
        private final URL icon;

        private ToolState(String name, URL icon) {
            this.name = name;
            this.icon = icon;
        }

        public String getName()  {return this.name;}
        public URL getIcon()     {return this.icon;}
    }
}
