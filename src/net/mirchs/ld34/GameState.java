package net.mirchs.ld34;

public class GameState {
	
	public static final int MENU = 1;
	public static final int OPENING = 2;
	public static final int GAME = 3;

	public static final int WON = 4;
	public static final int WONLEVEL = 5;
	
	public static final int LOST = 6;
	public static final int HOWTO = 7;
	public static final int EXIT = 8;
	
	
	public static int state = 1;
	
	public static void changeState(int s) {
		state = s;
	}

}
