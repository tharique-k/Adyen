package com.ecommapp.models;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Alert {
	public Alert() {
		JOptionPane optionPane = new JOptionPane("Time is up!",JOptionPane.ERROR_MESSAGE);
		JDialog dialog = optionPane.createDialog("Warning!");
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	}
}
