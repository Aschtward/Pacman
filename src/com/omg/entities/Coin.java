package com.omg.entities;

import java.awt.image.BufferedImage;

import com.omg.main.Game;
import com.omg.main.Sound;

public class Coin extends Entity{

	public Coin(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public void tick() {
		if(this.collided()) {
			Game.entities.remove(this);
			Game.score+= 5;
			Sound.chomp.play();
		}
	}

}
