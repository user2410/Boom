package main;
import java.awt.Insets;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame{

	private int width;
	private int height;
	
	public Window(String title, int width, int height, Game game) {
		this.width = width;
		this.height = height;
		
		setTitle(title);
		pack();
		Insets is = getInsets();
		setSize(width + is.left + is.right, height + is.top + is.bottom);
		
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setVisible(true);
		
		add(game);
	}

	public void _resize(int width, int height) {
		this.width = width;
		this.height = height;
		Insets is = getInsets();
		setSize(width + is.left + is.right, height + is.top + is.bottom);
	}
	
	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public int getHeight() {
		return this.height;
	}
	
}
