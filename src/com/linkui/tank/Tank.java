package com.linkui.tank;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Tank {
	public static final int XSPEED=5;
	public static final int YSPEED=5;
	
	public static final int WIDTH = 50;
	public static final int HEIGHT = 50;
	
	public static Random r = new Random();
	private int step = r.nextInt(20)+5;
	private int oldX, oldY;
	
	TankClient tc = null;
	
	private boolean good;
	private boolean live = true;
	private int life = 100;
	private BloodBar bb = new BloodBar();
	
	private int x, y;
	private boolean bL = false, bU = false, bR = false, bD = false;
	
	private Direction dir = Direction.STOP;
	private Direction ptDir = Direction.D;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	
	//static code, run when the class is loaded.
	static {
		imgs.put("L", tk.getImage(Tank.class.getClassLoader().getResource("images/tankL.gif")));
		imgs.put("LU", tk.getImage(Tank.class.getClassLoader().getResource("images/tankLU.gif")));
		imgs.put("U", tk.getImage(Tank.class.getClassLoader().getResource("images/tankU.gif")));
		imgs.put("RU", tk.getImage(Tank.class.getClassLoader().getResource("images/tankRU.gif")));
		imgs.put("R", tk.getImage(Tank.class.getClassLoader().getResource("images/tankR.gif")));
		imgs.put("RD", tk.getImage(Tank.class.getClassLoader().getResource("images/tankRD.gif")));
		imgs.put("D", tk.getImage(Tank.class.getClassLoader().getResource("images/tankD.gif")));
		imgs.put("LD", tk.getImage(Tank.class.getClassLoader().getResource("images/tankLD.gif")));
	}
	
	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
	}
	
	public Tank(int x, int y, TankClient tc, boolean good, Direction dir){
		this(x,y,good);
		this.tc = tc;
		this.dir = dir;
	}
	
	public void draw(Graphics g){
		if(!live) {
			if(!good){
				tc.tanks.remove(this);
			}
			return;
		}
		Color c = g.getColor();
		if(good) bb.draw(g);
		g.setColor(c);
		move();
		
		switch (ptDir){
		case L:
			g.drawImage(imgs.get("L"), x, y, null);
			break;
		case LU:
			g.drawImage(imgs.get("LU"), x, y, null);
			break;
		case U:
			g.drawImage(imgs.get("U"), x, y, null);
			break;
		case RU:
			g.drawImage(imgs.get("RU"), x, y, null);
			break;
		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;
		case RD:
			g.drawImage(imgs.get("RD"), x, y, null);
			break;
		case D:
			g.drawImage(imgs.get("D"), x, y, null);
			break;
		case LD:
			g.drawImage(imgs.get("LD"), x, y, null);
			break;
		}
		
	}
	
	void move(){
		this.oldX = x;
		this.oldY = y;
		
		switch (dir){
		case L:
			x -=XSPEED;
			break;
		case LU:
			x -=XSPEED;
			y -=YSPEED;
			break;
		case U:
			y -=YSPEED;
			break;
		case RU:
			x +=XSPEED;
			y -=YSPEED;
			break;
		case R:
			x +=XSPEED;
			break;
		case RD:
			x +=XSPEED;
			y +=YSPEED;
			break;
		case D:
			y +=YSPEED;
			break;
		case LD:
			x -=XSPEED;
			y +=YSPEED;
			break;
		case STOP:
			break;
		}
		if(this.dir!=Direction.STOP) {
			this.ptDir = this.dir;
		}
		
		if(x<0) x=0;
		if(y<30) y=30;
		if(x+Tank.WIDTH>TankClient.GAME_WIDTH) x = TankClient.GAME_WIDTH-Tank.WIDTH;
		if(y+Tank.HEIGHT>TankClient.GAME_HEIGHT) y = TankClient.GAME_HEIGHT-Tank.HEIGHT;
		
		if(!good){
			Direction[] dirs = Direction.values();
			if(step==0){
				step = r.nextInt(20) + 5;
				int rn = r.nextInt(dirs.length);
				dir = dirs[rn];
			}
			step--;
			
			if(r.nextInt(40)>38) this.fire();
		}
	}
	
	public void stay(){
		x = oldX;
		y = oldY;
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		locateDirection();
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_SHIFT:
			this.live = true;
			this.life = 100;
			break;
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		case KeyEvent.VK_A :
			superFire();
			break;
		}
		locateDirection();
	}
	
	public Missile fire(){
		if(!live) return null;
		int x = this.x +Tank.WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
		Missile m = new Missile(x, y, ptDir,tc,good);
		tc.missiles.add(m);
		return m;
	}
	
	public Missile fire(Direction dir){
		if(!live) return null;
		int x = this.x +Tank.WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
		Missile m = new Missile(x, y, dir,tc,good);
		tc.missiles.add(m);
		return m;
	}
	
	public void superFire(){
		Direction[] dirs = Direction.values();
		for(int i =0; i<8; i++){
			fire(dirs[i]);
		}
	}
	
	void locateDirection(){
		if(bL && !bU && !bR && !bD) dir = Direction.L;
		else if(bL && bU && !bR && !bD) dir = Direction.LU;
		else if(!bL && bU && !bR && !bD) dir = Direction.U;
		else if(!bL && bU && bR && !bD) dir = Direction.RU;
		else if(!bL && !bU && bR && !bD) dir = Direction.R;
		else if(!bL && !bU && bR && bD) dir = Direction.RD;
		else if(!bL && !bU && !bR && bD) dir = Direction.D;
		else if(bL && !bU && !bR && bD) dir = Direction.LD;
		else if(!bL && !bU && !bR && !bD) dir = Direction.STOP;
	}
	
	public Rectangle getRect(){
		return new Rectangle(this.x, this.y, this.WIDTH, this.HEIGHT);
	}
	
	public void collidesWithWall(Wall w){
		if(this.live && this.getRect().intersects(w.getRect())){
			stay();
		}
	}
	
	public void collidesWithTanks(java.util.List<Tank> tanks){
		for (int i =0; i<tanks.size(); i++){
			Tank t = tanks.get(i);
			if(this != t){
				if(this.live && t.isLive() && this.getRect().intersects(t.getRect())){
					this.stay();
					t.stay();
				}
			}
		}
	}
	
	public boolean eatBlood(Blood b){
		if(this.live && this.life < 100 && b.isLive() && this.getRect().intersects(b.getRect())){
			life = 100;
			b.setLive(false);
			return true;
		}
		return false;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	
	public boolean isGood(){
		return good;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	
	class BloodBar {
		public void draw(Graphics g){
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y-15, WIDTH, 10);
			int w = WIDTH * life/100;
			g.fillRect(x, y-15, w, 10);
			g.setColor(c);
		}
	}
}
