package states;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class MyDialog extends JDialog {

	private JLabel jLabel;
	private JButton yesButton;
	private JButton noButton;
	private ImageIcon bg;
	
	public MyDialog() {
		setUndecorated(true);
		setSize(200, 200);
		setLocationRelativeTo(null);
		setVisible(false);
		
		jLabel = new JLabel("Return to Main Menu?");
		jLabel.setBounds(24, 63, 151, 21);
		jLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
		
		yesButton = new JButton("YES");
		yesButton.setBounds(48, 114, 51, 23);
		modifyButton(yesButton);
		
		noButton = new JButton("NO");
		noButton.setBounds(104, 114, 47, 23);
		modifyButton(noButton);
		
		getContentPane().setLayout(null);	
		getContentPane().add(jLabel);
		getContentPane().add(yesButton);
		getContentPane().add(noButton);
	}

	public JButton getYesButton() {
		return yesButton;
	}

	public void setYesButton(JButton yesButton) {
		this.yesButton = yesButton;
	}

	public JButton getNoButton() {
		return noButton;
	}

	public void setNoButton(JButton noButton) {
		this.noButton = noButton;
	}
	
	public void modifyButton(JButton button) {
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusable(false);
	}
}