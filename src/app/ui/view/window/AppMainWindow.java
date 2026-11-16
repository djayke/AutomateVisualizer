package app.ui.view.window;

import app.model.UserStateData;
import app.ui.view.layout.Layout;
import app.ui.view.menu.AppMenuBar;
import app.ui.view.toolbox.AppToolBar;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AppMainWindow extends JFrame
{
	// instance private
	private static AppMainWindow singleton = null;
	private static final String DEFAULT_TITLE = "DFA editor";
	private static final Dimension DEFAULT_SIZE = new Dimension(1000,800);
	
    private static final AppMenuBar menu = new AppMenuBar();
	private static final AppToolBar toolbox = new AppToolBar();
    private static final AppEditorPanel canvas = new AppEditorPanel();
    private static final AppFooterPanel bottom = new AppFooterPanel();

	private UserStateData metadata = new UserStateData();


	//get instance static 
	public static AppMainWindow getInstance() {
		if (singleton == null)
				AppMainWindow.singleton = new AppMainWindow();
		return AppMainWindow.singleton;
	}

	// private constructor
	private AppMainWindow()
	{
	    super();
	    this.setTitle(DEFAULT_TITLE);
	    this.setMinimumSize(DEFAULT_SIZE);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setJMenuBar(menu);
	    this.setVisible(true);
	    this.add(new Layout.LayoutBuilder(new JPanel())
	    		.setSouth(bottom)
	    		.setWest(toolbox)
	    		.setCenter(canvas)
	    		.build());
	}

    public AppEditorPanel getEditorPanel() { return canvas; }
    public AppFooterPanel getBottomPage() { return bottom; }
    public AppToolBar getToolBar() { return toolbox; }
    public UserStateData getUserData() { return metadata; }

}
