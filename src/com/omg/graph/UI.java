package com.omg.graph;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.omg.main.Game;
import com.omg.world.World;


public class UI {
	
	private BufferedImage standart = Game.spritesheet.getSprite(64, 0, 16, 16);
	
	public void render(Graphics g) {
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString("Score: "+Game.score,10, 670);
		
		for(int i = 0; i < Game.player.life;i++) {
			g.drawImage(standart,500 + (i*40),640,World.tile_size,World.tile_size,null);
		}
	}
}
