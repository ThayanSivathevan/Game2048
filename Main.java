package game2048;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;






public class Main extends JFrame{
	Game2048 game;
	static BufferedImage tiles[]=new BufferedImage[11];
	static BufferedImage background;
	boolean newT=false;
	int width,height;
	final int widthMax=123,heightMax=123;
	static String loc="C:\\Users\\100707190\\eclipse-workspace\\projects\\src\\game2048\\";
	Timer animate;
	public static void main(String[] args) {
		new Main();
	}
	public Main() {
		this.setSize(590, 840);
		this.setResizable(false);
		this.setTitle("2048");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(new paintBoard());
		this.setLocationRelativeTo (null);
		this.setVisible(true);
		game= new Game2048();
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
		executor.scheduleAtFixedRate(new Run(), 0L, 2L, TimeUnit.MILLISECONDS);
		InitImages();
		repaint();
	}
	
	
	public static void InitImages(){
		int x=0;
		for(int i =2;i<=2048;i*=2) {
			String str="p"+Integer.toString(i)+".jpg";
			//File file1 = new File(Main.class.getResource(str).getFile());
			try {
				tiles[x] = ImageIO.read(new File(loc+str));
				x++;
			} catch (IOException e) {
				System.out.println(true);

			}
		}
	    //File file2 = new File(Main.class.getResource("background.png").getFile());
		try {
			background = ImageIO.read(new File(loc+"background.png"));
		} catch (IOException e) {
			System.out.println(true);

		}
	}
	
	public void continuation(boolean moved) {
		if(moved&&!game.check2048()) {
			if(game.checkEmpty())newT=game.createTile(false);
			if(game.checkLoss()) {
				game=new Game2048();
				System.out.println("You lost");
			}
		}
		if(game.check2048()) {
			System.out.println("You won");
			game=new Game2048();
		}
	}
	
	class Run implements Runnable{

		@Override
		public void run() {
			repaint();
			if(width==widthMax) {
				width=0;
				height=0;
				newT=false;
				game.setTile();
			}
			if(newT) {
				width+=1;
				height+=1;
			}
			
		}
		
	}
	
	
	
	
	
	class paintBoard extends JComponent{
		int xCoords[]= {22,162,300,439};
		int yCoords[]= {252,390,530,669};
		int scoreX=430,scoreY=70;
		public paintBoard() {
			this.setFocusable(true);
			this.addKeyListener(new KeyListener(){

				@Override
				public void keyPressed(KeyEvent e) {
					char m=Character.toLowerCase(e.getKeyChar());
					boolean moved = false;
					switch(m) {
					case 'w':
						moved=game.moveTiles(0);
						break;
					case 's':
						moved=game.moveTiles(1);
						break;
					case 'a':
						moved=game.moveTiles(2);
						break;
					case 'd':
						moved=game.moveTiles(3);
						break;
					}
					continuation(moved);
					repaint();
				}

				@Override
				public void keyReleased(KeyEvent arg0) {
					
					
				}

				@Override
				public void keyTyped(KeyEvent arg0) {
					
					
				}
				
			});
		}
		public void paint(Graphics g) {
			g.drawImage(background,0,0, null);
			drawScore(g);
			if(newT)animateNewTile(g,width,height,game.getTileX(),game.getTileY());
			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					int imgValue=(int)(Math.log(game.getTileValue(i,j)) / Math.log(2))-1;
					if(game.getTileValue(i,j)!=0&&(i!=game.getTileX()||j!=game.getTileY()))g.drawImage(tiles[imgValue], xCoords[i], yCoords[j], null);
					
				}
			}
		}
		
		public void drawScore(Graphics g) {
			Font trb = new Font("Clear Sans", Font.BOLD, 24);
			g.setFont(trb);
			g.setColor(Color.white);
			g.drawString(String.valueOf(game.getScore()),scoreX , scoreY);
		}
		
		public void animateNewTile(Graphics g,int width,int height,int x,int y) {
			int imgValue=(int)(Math.log(game.getTileValue(x,y)) / Math.log(2))-1;
			g.drawImage(tiles[imgValue],xCoords[x]+widthMax/2-width/2,yCoords[y]+heightMax/2-height/2,width,height,null);
		}
		
	}
}
