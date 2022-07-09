package main;

public class Main {

	public static void main(String[] args) {
		Game game = new Game();
		boolean init = game.init();
		if(init) {
			new Thread(game).start();
		}
	}

}
