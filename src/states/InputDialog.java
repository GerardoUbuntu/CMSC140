package states;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class InputDialog extends JDialog {

	private JLabel jLabel;
	private JButton okButton;
	private JButton cancelButton;
	private JTextField input;
	private ImageIcon bg;
	
	public InputDialog(String message) {
		setUndecorated(true);
		setSize(200, 200);
		setLocationRelativeTo(null);
		setVisible(false);
		jLabel = new JLabel("Message");
		jLabel.setBounds(24, 63, 151, 21);
		jLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
		
		input = new JTextField(20);
		input.setBounds(15,100,151,21);
				
		okButton = new JButton("OK");
		okButton.setBounds(48, 114, 51, 23);
		cancelButton = new JButton("CANCEL");
		cancelButton.setBounds(104, 114, 47, 23);
		getContentPane().setLayout(null);
	
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

	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}
	
	public void modifyButton(JButton button) {
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusable(false);
	}
}