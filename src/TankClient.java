import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class TankClient extends Frame{

	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	Tank myTank = new Tank(400,500,this,true, Tank.Direction.STOP);
	Blood b = new Blood();
	
	List<Missile> missiles = new ArrayList<Missile>();
	List<Explode> explodes = new ArrayList<Explode>();
	List<Tank> tanks = new ArrayList<Tank>();
	
	Wall w1 = new Wall(250, 200, 300, 20, this);
	Wall w2 = new Wall(150, 300, 20, 200, this);
	
	Image offScreenImage = null;

	public void paint(Graphics g) {
		g.drawString("missiles count: " + missiles.size(), 10, 50);
		g.drawString("explodes count: " + explodes.size(), 10, 70);
		g.drawString("tanks    count: " + tanks.size(), 10, 90);
		g.drawString("myTank    life: " + myTank.getLife(), 10, 110);
		
		for(int i=0; i<tanks.size(); i++){
			Tank t = tanks.get(i);
			t.collidesWithWall(w1);
			t.collidesWithWall(w2);
			t.collidesWithTanks(tanks);
			t.draw(g);
		}
		for(int i=0;i<missiles.size();i++){
			Missile m =missiles.get(i);
			//if(!m.isLive()) missiles.remove(m); //Way to remove dead missiles
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitWall(w1);
			m.hitWall(w2);
			m.draw(g);
		}
		
		for(int i =0; i<explodes.size(); i++){
			Explode e = explodes.get(i);
			e.draw(g);
		}
		
		myTank.draw(g);
		b.draw(g);
		myTank.collidesWithWall(w1);
		myTank.collidesWithWall(w2);
		myTank.collidesWithTanks(tanks);
		myTank.eatBlood(b);
		w1.draw(g);
		w2.draw(g);
	}
	public void update(Graphics g) { //double buffer to avoid blinking
		if(offScreenImage == null){
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);//re-paint background
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	public void launchFrame(){
		for(int i =0; i< 15; i++){
			tanks.add(new Tank((50 +40*(i+1)), 50, this,false, Tank.Direction.D));
		}
		this.setLocation(400, 300);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setTitle("Tank War");
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}			
		});
		this.setResizable(false);
		this.setBackground(Color.GREEN);
		this.addKeyListener(new KeyMonitor());
		setVisible(true);
		new Thread(new PaintThread()).start();
	}
	public static void main(String[] args){
		TankClient tc = new TankClient();
		tc.launchFrame();
	}
	
	private class PaintThread implements Runnable{
		public void run(){
			while(true){
				repaint();
				try {
					Thread.sleep(17);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private class KeyMonitor extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
			if(e.getKeyCode() == KeyEvent.VK_R){
				for(int i =0; i< 15; i++){
					tanks.add(new Tank((50 +40*(i+1)), 50, TankClient.this,false, Tank.Direction.D));
				}
			}
		}
	}
}
