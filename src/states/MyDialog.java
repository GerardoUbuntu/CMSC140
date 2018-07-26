package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Imageloader.Assets;

public class MyDialog extends JDialog {

	private JLabel jLabel;
	private JButton yesButton;
	private JButton noButton;
	private ImageIcon bg;
	
	public MyDialog(JFrame frame) {
		setUndecorated(true);
		setSize(350, 150);
		setVisible(false);
		
		jLabel = new JLabel();
		jLabel.setBounds(20, 30, 350, 60);
		jLabel.setFont(Assets.chillerBig);
		
		yesButton = new JButton(new ImageIcon(getClass().getResource("/images/ok.png")));
		yesButton.setRolloverIcon(new ImageIcon(getClass().getResource("/images/ok1.png")));
		yesButton.setBounds(150, 114, 51, 23);
		modifyButton(yesButton);
			
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		getContentPane().add(jLabel);
		getContentPane().add(yesButton);
		setLocationRelativeTo(frame);
	}

	public JButton getYesButton() {
		return yesButton;
	}

	public JLabel getLabel() {
		return jLabel;
	}
	
	public void setYesButton(JButton yesButton) {
		this.yesButton = yesButton;
	}
	
	public void modifyButton(JButton button) {
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusable(false);
	}
}