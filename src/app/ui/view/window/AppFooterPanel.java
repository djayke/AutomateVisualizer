package app.ui.view.window;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;


// TODO mouse Listener bad spot add current tool getUserData
public class AppFooterPanel extends JPanel{
	private final static String Status = "Ready!";
	private final JLabel mouseLocation  = new JLabel(Status);;

	public AppFooterPanel() {
		super();
		this.setBackground(Color.DARK_GRAY);
		this.add(mouseLocation);
		this.mouseLocation.setForeground(new Color(248, 248, 255)); // Ghost White
		this.mouseLocation.setFont(new Font("SansSerif", Font.BOLD, 14)); // Ghost White
	}


    public void updateInfo(Point e){
		this.mouseLocation.setText("[ x:" + e.getX() + " | y:" + e.getY() + " ]");
		this.mouseLocation.addMouseListener(AppMainWindow.getInstance().getEditorPanel());

	}

}
