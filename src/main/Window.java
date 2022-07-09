package main;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame{

	public Window(String title, int width, int height, Game game) {
		setTitle(title);
		pack();
		setSize(width + getInsets().left + getInsets().right, height + getInsets().top + getInsets().bottom);
		
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setVisible(true);
		
		add(game);
	}

}
