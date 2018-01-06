package utils;

import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class MyDialog {

	public static void showDialog (Frame root,String title,String msg)
	{
		
		
		
		JDialog dialog = new JDialog ( root,title);
		dialog.setSize(400,400);
		JLabel label = new JLabel();
		label.setText(msg);
		
		
		
		dialog.add(label);
		JButton cancel = new JButton("È¡Ïû");
		dialog.add(cancel);
		
		dialog.setVisible(true);
		
		
	}
	
	
}
