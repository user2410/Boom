package main;
import java.awt.Insets;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame{

	public Window(String title, int width, int height, Game game) {
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
		Insets is = getInsets();
		setSize(width + is.left + is.right, height + is.top + is.bottom);
	}
}
