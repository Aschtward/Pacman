package com.omg.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.omg.main.Game;
import com.omg.world.World;

public class Player extends Entity{
	//Variáveis de direção
	public boolean right, left, up, down;
	public int dirSide = 0;
	public int speed = 2;
	
	//Variáveis de render
	private BufferedImage[] rightPlayer, leftPlayer;
	private BufferedImage standart;
	
	//Variáveis proprias do player
	public int life = 3;
	
	//Variáveis de animação
	public int frames = 0;
	public int maxFrames = 10;
	public boolean standartAni = false;
	public int gmode = 0;
	public int gmodeTime = 600;
	
	public boolean state = false;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		rightPlayer = new BufferedImage[2];
		leftPlayer = new BufferedImage[2];
		
		//Carregando sprites do spritesheet do game
		rightPlayer[0] = Game.spritesheet.getSprite(32, 0, 16, 16);
		rightPlayer[1] = Game.spritesheet.getSprite(48, 0, 16, 16);
		leftPlayer[0] = Game.spritesheet.getSprite(64, 0, 16, 16);
		leftPlayer[1] = Game.spritesheet.getSprite(0, 16, 16, 16);
		standart = Game.spritesheet.getSprite(80, 16, 16, 16);
	}
	
	public void tick() {
		//Manipulando direção que o player irá
		if(getX() > 600){
			this.setX(32);
		}else if(this.getX() < 2) {
			this.setX(590);
		}
		if(getY() > 606){
			this.setY(32);
		}else if(this.getY() < 32) {
			this.setY(600);
		}else if(right && World.isFree(getX()+speed, getY())) {
			setX(getX() + speed);
			dirSide = 0;
		}
		if(left && World.isFree(getX()-speed, getY())) {
			setX(getX() - speed);
			dirSide = 1;
		}
		if(up && World.isFree(getX(), getY()-speed)) {
			setY(getY() - speed);
			dirSide = 2;
		}
		if(down && World.isFree(getX(), getY()+speed)) {
			setY(getY() + speed);
			dirSide = 3;
		}
		//Animação de abrir e fechar a boca
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			if(standartAni) {
				standartAni = false;
			}else {
				standartAni = true;
			}
		}
		if(state) {
			if(gmode == 0) {
				for(Enemy e : Game.enemies) {
					e.ghostMode = true;
				}
			}
			gmode++;
			if(gmode == gmodeTime) {
				state = false;
				gmode = 0;
			}
		}
	}
	
	public void render(Graphics g) {
		//Renderizando animação
		if(standartAni) {
			g.drawImage(standart,this.getX(),this.getY(),World.tile_size,World.tile_size,null);
		}else if(dirSide == 3) {
				g.drawImage(rightPlayer[0],this.getX(),this.getY(),World.tile_size,World.tile_size,null);
		}else if(dirSide == 1) {
			g.drawImage(leftPlayer[1],this.getX(),this.getY(),World.tile_size,World.tile_size,null);
		}
		else if(dirSide == 0){
			g.drawImage(leftPlayer[0],this.getX(),this.getY(),World.tile_size,World.tile_size,null);
		}else if(dirSide == 2) {
			g.drawImage(rightPlayer[1],this.getX(),this.getY(),World.tile_size,World.tile_size,null);
		}else {
			g.drawImage(rightPlayer[0],this.getX(),this.getY(),World.tile_size,World.tile_size,null);
		}		
	}
}
