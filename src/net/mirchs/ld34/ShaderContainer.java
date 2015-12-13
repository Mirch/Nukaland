package net.mirchs.ld34;

import net.mirchs.dm8.graphics.Shader;

public class ShaderContainer {
	
	public static Shader PLAYER = new Shader("shaders/player.vert", "shaders/player.frag");
	public static Shader MOOMU = new Shader("shaders/moomu.vert", "shaders/moomu.frag");
	public static Shader PHAP = new Shader("shaders/player.vert", "shaders/player.frag");

	public static Shader SWORD = new Shader("shaders/sword.vert", "shaders/sword.frag");
	
	public static Shader TILE = new Shader("shaders/tile.vert", "shaders/tile.frag");
	public static Shader SWORDTILE = new Shader("shaders/tile.vert", "shaders/tile.frag");
	
	public static Shader SCREEN = new Shader("shaders/moomu.vert", "shaders/moomu.frag");
	
	public static Shader FADE = new Shader("shaders/fade.vert", "shaders/fade.frag");
	

}
