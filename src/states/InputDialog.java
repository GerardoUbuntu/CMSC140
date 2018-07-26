package states;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Imageloader.Assets;

public class InputDialog extends JDialog {

	private JLabel jLabel;
	private JButton okButton;
	private JButton cancelButton;
	private JTextField input;
	private ImageIcon bg;
	
	public InputDialog() {
		setUndecorated(true);
		setSize(300, 150);
		setLocationRelativeTo(null);
		setVisible(false);
		jLabel = new JLabel();
		jLabel.setBounds(40, 30, 350, 50);
		jLabel.setFont(Assets.chillerSmall);
		
		input = new JTextField(20);
		input.setBounds(26,70,250,25);
				
		okButton = new JButton(new ImageIcon(getClass().getResource("/images/ok.png")));
		okButton.setRolloverIcon(new ImageIcon(getClass().getResource("/images/ok1.png")));
		okButton.setBounds(60, 114, 51, 23);
		modifyButton(okButton);
		
		cancelButton = new JButton(new ImageIcon(getClass().getResource("/images/cancel.png")));
		cancelButton.setRolloverIcon(new ImageIcon(getClass().getResource("/images/cancel1.png")));
		cancelButton.setBounds(134, 107, 100, 40);
		modifyButton(cancelButton);
		
		getContentPane().setLayout(null);
	
		
		getContentPane().setBackground(Color.LIGHT_GRAY);
		getContentPane().add(jLabel);
		getContentPane().add(input);
		getContentPane().add(okButton);
		getContentPane().add(cancelButton);
	}

	public JButton getOkButton() {
		return okButton;
	}

	public void setYesButton(JButton okButton) {
		this.okButton = okButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}
	
	public JLabel getLabel() {
		return jLabel;
	}
	
	public JTextField getTextField() {
		return input;
	}
	
	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}
	
	public void modifyButton(JButton button) {
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusable(false);
	}
	
	public void setBack() {
		
	}
}