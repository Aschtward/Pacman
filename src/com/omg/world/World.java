package com.omg.world;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import com.omg.entities.*;
import com.omg.graph.Spritesheet;
import com.omg.graph.UI;
import com.omg.main.Game;

public class World {
	
	public static Tile[] tiles;
	public static int width, height;
	public static int tile_size = 32;

	public World(String path) {
		try {
			BufferedImage mapa = ImageIO.read(getClass().getResource(path));
			int[] px = new int[mapa.getWidth() * mapa.getHeight()];
			width = mapa.getWidth();
			height = mapa.getHeight();
			tiles = new Tile[mapa.getWidth() * mapa.getHeight()];
			mapa.getRGB(0, 0, mapa.getWidth(), mapa.getHeight(), px ,0, mapa.getWidth());
			for(int i = 0; i <  mapa.getWidth(); i++) {
				
				for(int j = 0; j < mapa.getHeight();j++) {
					
						int pxAtual = px[i + (j*mapa.getWidth())];
						tiles[i + (j *width)] = new Floor(i*tile_size,j*tile_size,Tile.tile_floor);
						
						if(pxAtual == 0xFF000000) {
							tiles[i + (j *width)] = new Floor(i*tile_size,j*tile_size,Tile.tile_floor);
						}else if(pxAtual == 0xFFFFFFFF) {
							tiles[i + (j *width)] = new Wall(i*tile_size,j*tile_size,Tile.tile_wall);
						}else if(pxAtual == 0xFF0026FF) {
							Game.player.setX(i*tile_size);
							Game.player.setY(j*tile_size);
						}else if(pxAtual == 0xFFFF00B2) {
							tiles[i+(j*width)] = new Wall(i*tile_size,j*tile_size,Tile.tile_CornerDtoL);
						}else if(pxAtual == 0xFFFFDD00) {
							tiles[i+(j*width)] = new Wall(i*tile_size,j*tile_size,Tile.tile_sideBar);
						}else if(pxAtual == 0xFFFF1D00) {
							tiles[i+(j*width)] = new Wall(i*tile_size,j*tile_size,Tile.tile_CornerTtoR);
						}else if(pxAtual == 0xFFBBFF00) {
							tiles[i+(j*width)] = new Wall(i*tile_size,j*tile_size,Tile.tile_ConerTtoL);
						}else if(pxAtual == 0xFF04FF00) {
							tiles[i+(j*width)] = new Wall(i*tile_size,j*tile_size,Tile.tile_CornerDtoR);
						}else if(pxAtual == 0xFF7C5617) {
							tiles[i+(j*width)] = new Wall(i*tile_size,j*tile_size,Tile.tile_CornerBigTU);
						}else if(pxAtual == 0xFF7F3300) {
							tiles[i+(j*width)] = new Wall(i*tile_size,j*tile_size,Tile.tile_CornerBigTD);
						}else if(pxAtual == 0xFFFF0800){
							//Red enemy
							Enemy en = new Enemy(i*tile_size,j*tile_size,tile_size,tile_size,Entity.enemy);
							en.skin = 2;
							Game.entities.add(en);
							Game.enemies.add(en);
						}else if(pxAtual == 0xFFFF9000) {
							//Coin
							Coin coin = new Coin(i*tile_size,j*tile_size,tile_size,tile_size,Entity.coin);
							Game.entities.add(coin);
						}else if(pxAtual == 0xFF5F000F) {
							Fruit fruta = new Fruit(i*tile_size,j*tile_size,tile_size,tile_size,Entity.fruit);
							Game.entities.add(fruta);
						}else if(pxAtual == 0xFFFF5400){
							//Orange enemy
							Enemy en = new Enemy(i*tile_size,j*tile_size,tile_size,tile_size,Entity.enemy);
							en.skin= 0;
							Game.entities.add(en);
							Game.enemies.add(en);
						}else if(pxAtual == 0xFF00FFD0){
							//Blue enemy
							Enemy en = new Enemy(i*tile_size,j*tile_size,tile_size,tile_size,Entity.enemy);
							en.skin= 1;
							Game.entities.add(en);
							Game.enemies.add(en);
						}
					}
				}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void worldRestart(String path) {
		Game.entities.clear();
		Game.enemies.clear();
		Game.image = new BufferedImage(Game.WIDTH,Game.HEIGHT,BufferedImage.TYPE_INT_RGB);
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		Game.spritesheet = new Spritesheet("/text.png");
		Game.entities.add(Game.player);
		Game.player.life = 3;
		Game.bf = 0;
		Game.fr = 0;
		Game.indexEnemy = 0;
		Game.maxlife = 3;
		Game.score = 0;
		Game.player.state = false;
		Game.world = new World(path);
		Game.ui = new UI();
	}
	
	public static boolean isFree(int xnext, int ynext) {
		int x1 = xnext / tile_size;
		int y1 = ynext/ tile_size;
		
		int x2 = (xnext+tile_size-1) / tile_size;
		int y2 = ynext/ tile_size;
		
		int x3 = xnext / tile_size;
		int y3 = (ynext+tile_size-1)/ tile_size;
		
		int x4 = (xnext+tile_size-1) / tile_size;
		int y4 = (ynext+tile_size-1)/ tile_size;
		
		return !(tiles[x1+(y1*World.width)] instanceof Wall ||
				tiles[x2+(y2*World.width)] instanceof Wall ||
				tiles[x3+(y3*World.width)] instanceof Wall ||
				tiles[x4+(y4*World.width)] instanceof Wall );
	}
	
	public void render(Graphics g) {
		for(int i = 0; i <= (Game.WIDTH / tile_size) + 1; i++) {
			for(int j = 0; j <= (Game.HEIGHT / tile_size) + 1;  j++) {
				if(i < 0 || j < 0 || i >= width|| j >= height) {
					continue;
				}
				Tile tile = tiles[i+ (j*width)];
				tile.render(g);
			}
		}
	}

}
