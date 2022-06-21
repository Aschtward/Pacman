package com.omg.entities;

import java.awt.image.BufferedImage;

import com.omg.main.Game;
import com.omg.main.Sound;

public class Fruit extends Entity{

	public Fruit(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
	}
	
	public void tick() {
		if(this.collided()) {
			Game.entities.remove(this);
			Game.score+= 10;
			Game.player.state = true;
			Sound.eathfruit.play();
		}
	}

}
