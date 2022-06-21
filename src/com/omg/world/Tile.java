package com.omg.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.omg.main.Game;

public class Tile {
	
	public static BufferedImage tile_floor =  Game.spritesheet.getSprite(0,0,16,16);
	public static BufferedImage tile_wall =  Game.spritesheet.getSprite(16,0,16,16);
	public static BufferedImage tile_CornerDtoL =  Game.spritesheet.getSprite(0,32,16,16);
	public static BufferedImage tile_CornerDtoR =  Game.spritesheet.getSprite(0,48,16,16);
	public static BufferedImage tile_sideBar =  Game.spritesheet.getSprite(16,48,16,16);
	public static BufferedImage tile_CornerTtoR =  Game.spritesheet.getSprite(32,48,16,16);
	public static BufferedImage tile_ConerTtoL =  Game.spritesheet.getSprite(48,48,16,16);
	public static BufferedImage tile_CornerBigTD =  Game.spritesheet.getSprite(64,48,16,16);
	public static BufferedImage tile_CornerBigTU =  Game.spritesheet.getSprite(80,48,16,16);
	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite,x ,y,World.tile_size,World.tile_size,null);
	}
}
