package app.util;

import javax.swing.*;
import java.net.URL;

public class Util {

	protected ImageIcon createImageIcon(String path, String description) {java.net.URL imgURL = getClass().getResource(path);
	    if (imgURL != null) {
			return new ImageIcon(imgURL, description);
	    } else {
			System.err.println("Couldn't find file: " + path);
			return null;
	    }
	}

	protected ImageIcon createImageIcon(URL imgURL, String description) {
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + imgURL.getPath());
			return null;
		}
	}


}
