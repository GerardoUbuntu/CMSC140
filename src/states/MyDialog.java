package states;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JWindow;

public class MyDialog extends JDialog{
	
	public MyDialog(JFrame frame,String message) {	
	     super(frame);
	     setLayout(new FlowLayout());
		
		 setUndecorated(true);
		 setSize(300,100);
		
//		 toFront();
		
		 JLabel label = new JLabel("message");   
	     JTextField input = new JTextField(10);
	     JButton button  = new JButton("OK");
	     setBounds(250,128,300,100);
	     add(label);
		 add(input);
		 add(button);

    }
}
