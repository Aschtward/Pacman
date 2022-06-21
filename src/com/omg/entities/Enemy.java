package com.omg.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
import com.omg.main.Game;
import com.omg.main.Sound;
import com.omg.world.AStar;
import com.omg.world.Node;
import com.omg.world.Vector2i;
import com.omg.world.World;

public class Enemy extends Entity {

	public int speed = 1;
	private BufferedImage[] ani;
	private int direction = 0;
	public int eyeposX = 0;
	public int eyeposY = 0;
	public int skin = 0;
	public boolean beenEaten = false;
	public boolean ghostMode = false;
	public boolean freeToGo = false;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		ani = new BufferedImage[5];
		ani[0] = Game.spritesheet.getSprite((16*9), 0, 16, 16);//orange
		ani[1] = Game.spritesheet.getSprite((16*8), 0, 16, 16);//blue
		ani[2] = Game.spritesheet.getSprite(16, 16, 16, 16);//red
		ani[3] = Game.spritesheet.getSprite((16*2), 16, 16, 16);//eyes
		ani[4] = Game.spritesheet.getSprite(3*16, 16, 16, 16);//ghostMode
	}
	
	public  void followPath(List<Node> path) {
		if(path != null) {
			if(path.size() > 0) {
				Vector2i target = path.get(path.size()-1).tile;
				if(this.getX() <  target.x*World.tile_size) {
					this.setX(this.getX()+speed);
				}else if(this.getX() > target.x*World.tile_size) {
					this.setX(this.getX()-speed);
				}
				if(this.getY() < target.y*World.tile_size) {
					this.setY(this.getY()+speed);
				}else if(this.getY() > target.y*World.tile_size) {
					this.setY(this.getY()-speed);
				}
				if(this.getX() == target.x*World.tile_size && this.getY() == target.y*World.tile_size) {
					path.remove(path.size()-1);
				}
			}
		}
	}
	
	public void switchEyes() {
		if(Game.player.getX() > this.getX()) {
			if(eyeposX < 4){
				eyeposX+=1;
			}
		}else if(Game.player.getX() < this.getX()) {
			if(eyeposX > -4) {
				eyeposX-=1;
			}
		}
		if(Game.player.getY() > this.getY()) {
			if(eyeposY < 4) {
				eyeposY+=1;
			}
		}else if(Game.player.getY() < this.getY()) {
			if(eyeposY > -4) {
				eyeposY-= 1;
			}
		}
	}
	
	public void backUpRoutine() {
		Vector2i start = new Vector2i((int)(this.getX()/World.tile_size),(int)(this.getY()/World.tile_size));
		Vector2i end = new Vector2i(9,8);
		path =  AStar.findPath(Game.world, start, end);
		followPath(path);
	}
	
	public void restartEnemy() {
		speed = 1;
		ghostMode = false;
		beenEaten = false;
	}
	public void tick() {
		if(freeToGo) {
			if(path == null || path.size() == 0) {
				Vector2i start = new Vector2i((int)(this.getX()/World.tile_size),(int)(this.getY()/World.tile_size));
				Vector2i end = new Vector2i((int)(Game.player.getX()/World.tile_size),(int)(Game.player.getY()/World.tile_size));
				path =  AStar.findPath(Game.world, start, end);
			}
			if(Game.rand.nextInt(100) < 80) {
				followPath(path);
			}
			switchEyes();
			if(ghostMode && collided()) {
				this.backUpRoutine();
				if(!beenEaten) {
					Sound.eatghost.play();
					Game.score += 200;
					beenEaten = true;
				}
			}
			if(collided() && !ghostMode) {
				Game.player.life--;
			}
			if(this.getX() == 32*9 && this.getY() == 32*8 && ghostMode) {
				restartEnemy();
			}else if(!Game.player.state) {
				restartEnemy();
			}
		}

	}
	
	public void render(Graphics g) {
		if(ghostMode && !beenEaten) {
			g.drawImage(ani[4],this.getX(),this.getY(),World.tile_size,World.tile_size,null);
		}else {
			if(beenEaten) {
				g.drawImage(ani[3],this.getX()+eyeposX,this.getY()+eyeposY,World.tile_size,World.tile_size,null);
			}else {
				g.drawImage(ani[skin],this.getX(),this.getY(),World.tile_size,World.tile_size,null);	
				g.drawImage(ani[3],this.getX()+eyeposX,this.getY()+eyeposY,World.tile_size,World.tile_size,null);
			}
		}
	}

}
