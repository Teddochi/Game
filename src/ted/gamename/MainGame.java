package ted.gamename;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import ted.gamename.entity.mob.Player;
import ted.gamename.graphics.Screen;
import ted.gamename.input.Keyboard;
import ted.gamename.level.Level;
import ted.gamename.level.RandomLevel;
import ted.gamename.level.SpawnLevel;

public class MainGame extends Canvas implements Runnable{
	
	//Added Git Comment
	
	private static final long serialVersionUID = 1L;

	//Resolution
	public static int width = 300;
	public static int height = width / 16 * 9;
	public static int scale = 3;
	private static String gamename = "GameName";
	
	private Thread thread;
	private JFrame frame;
	private boolean running = false;
	private Screen screen;
	private Keyboard key;
	private Level level;
	private Player player;

	
	private BufferedImage image = new BufferedImage(width, 
										height, BufferedImage.TYPE_INT_RGB);
	
	//Pixels in the image
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
			
			
	public MainGame(){
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		
		frame = new JFrame();
		screen = new Screen(width, height);
		level = new SpawnLevel("/textures/Level.png");
		key = new Keyboard();
		addKeyListener(key);
		player = new Player(8 * 16, 8 * 16, key);
	}
	
	public synchronized void start(){
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public synchronized void stop(){
		running = false;
		try{
			thread.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public void run(){
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		
		while(running){
			long now = System.nanoTime();
			delta += (now-lastTime) / ns;
			lastTime = now;	
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				//Adds one second
				timer += 1000;
				frame.setTitle(gamename + "  |  " + updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	public void update(){
		key.update();
		player.update();

	}
	
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		int xScroll = player.x - (screen.width / 2);
		int yScroll = player.y - (screen.height / 2);
		level.render(xScroll, yScroll, screen);
		player.render(screen);
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		
		//Fills the canvas with black
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image,  0, 0, getWidth(), getHeight(), null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Verdana", 0, 50));
		//Removes old frame
		g.dispose();
		bs.show();
		
		
	
	}
	
	public static void main(String[] args){
		MainGame game = new MainGame();
		
		//Prevents graphic errors from window resizing
		game.frame.setResizable(false);
		
		game.frame.setTitle(gamename);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.start();
		
	}
	
}
