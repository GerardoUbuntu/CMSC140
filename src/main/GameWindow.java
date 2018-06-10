package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;


public class GameWindow {
	
	private JFrame frame;
	private String title;
	private int width,height;
	private Canvas canvas;
	private JLayeredPane layeredPane;

	public GameWindow(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		createWindow();
	}
	
	private void createWindow(){
		frame = new JFrame(title);
		layeredPane = new JLayeredPane();
	    layeredPane.setPreferredSize(new Dimension(width, height));
	    layeredPane.setLayout(null);
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);
//		ImagePanel imagePanel = new ImagePanel( new ImageIcon(getClass().getResource("/images/menubg.gif")).getImage());
//		imagePanel.setBounds(0,0, width, height);
		canvas.setBounds(0,0, width, height);
//		layeredPane.add(imagePanel, 1);
		layeredPane.add(canvas, 0);
		frame.add(layeredPane);
	
		frame.pack();
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
				
   	}
	
	public Canvas getCanvas(){
		return canvas;
	}
	
	public JFrame getFrame(){
		return frame;
	}
}
