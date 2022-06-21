package com.omg.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import com.omg.entities.Enemy;
import com.omg.entities.Entity;
import com.omg.entities.Player;
import com.omg.graph.Spritesheet;
import com.omg.graph.UI;
import com.omg.world.World;

public class Game extends Canvas implements Runnable, KeyListener,MouseListener{
	
	
	private static final long serialVersionUID = 1227254042505466843L;
	
	///Definindo parametros para janela
	public static JFrame frame;
	public static int WIDTH = 640;
	public static int HEIGHT = 700;
	public static int SCALE = 1;
	///Fim parametros para janela
	
	public static BufferedImage image;
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static Spritesheet spritesheet;
	public static Player player;
	public static Random rand;
	public static UI ui;
	public String level_now = "/map.png";
	private int level_number = 1;
	public static World world;
	private Thread thread;
	private boolean isRunning = true;
	public boolean restartGame = false;
	public boolean saveGame = false;
	public static int score = 0;
	public static int indexEnemy = 0;
	public static int fr = 0;
	public static int bf = 0;
	
	public static int maxlife = 3;
	


	
	public Game() {
		
		///Criação da Janela
		setPreferredSize(new Dimension(SCALE*WIDTH,SCALE*HEIGHT));
		inicia_frame();
		///
		addKeyListener(this);
		addMouseListener(this);
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_ARGB);
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		spritesheet = new Spritesheet("/text.png");
		player = new Player(0,0,0,0, spritesheet.getSprite(32, 0, 16, 16));
		entities.add(player);
		rand = new Random();
		world = new World("/map1.png");
		ui = new UI();
		
	}
	
	public void inicia_frame() {///Inicializa janela
		frame = new JFrame("Pacman");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
		
	public static void main(String args[]) {
		Game game = new Game();
		game.start();
	}
		
	public void tick() {
		if(bf > 300) {
			for(int i = 0; i < entities.size();i++) {
				Entity e = entities.get(i);
				e.tick();
			}
			if(maxlife > player.life) {
				//Perdeu uma vida
				maxlife = player.life;
				Sound.death.play();
				Game.player.setX(32);
				Game.player.setY(32);
			}
			if(indexEnemy < 3) {
				fr++;
				if((fr % 300) == 0) {
					this.enemies.get(indexEnemy).freeToGo = true;
					indexEnemy++;
				}
			}
			if(player.life ==  0) {
				Sound.death.play();
				World.worldRestart("/map1.png");
			}
		}else {
			bf++;
			if(bf == 1) {
				Sound.begining.play();
			}
		}
	}
	
	public void  render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g = bs.getDrawGraphics();
		world.render(g);
		for(int i = 0; i < entities.size();i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		ui.render(g);		
		if(restartGame){	
			this.restartGame = false;
			World.worldRestart(level_now);				
		}
		g.dispose();
		bs.show();
	}
	
	public void run() {
		
		//Implementação game looping
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		double timer = System.currentTimeMillis();
		int frames = 0;
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime();
			delta+=(now - lastTime)/ns;
			lastTime = now;
			
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				frames = 0;
				timer += 1000;
			}
		}//Fim implementação game looping
		
		stop();
}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;	
		}else if(e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}if(e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
		}else if(e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
			
		}else if(e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}if(e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		}else if(e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
