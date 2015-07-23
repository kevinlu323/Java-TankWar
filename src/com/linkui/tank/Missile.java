package com.linkui.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Missile {
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;
	
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	int x, y;
	Direction dir;
	
	private boolean live = true;
	private TankClient tc;
	private boolean good;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	
	//static code, run when the class is loaded.
	static {
		imgs.put("L", tk.getImage(Missile.class.getClassLoader().getResource("images/missileL.gif")));
		imgs.put("LU", tk.getImage(Missile.class.getClassLoader().getResource("images/missileLU.gif")));
		imgs.put("U", tk.getImage(Missile.class.getClassLoader().getResource("images/missileU.gif")));
		imgs.put("RU", tk.getImage(Missile.class.getClassLoader().getResource("images/missileRU.gif")));
		imgs.put("R", tk.getImage(Missile.class.getClassLoader().getResource("images/missileR.gif")));
		imgs.put("RD", tk.getImage(Missile.class.getClassLoader().getResource("images/missileRD.gif")));
		imgs.put("D", tk.getImage(Missile.class.getClassLoader().getResource("images/missileD.gif")));
		imgs.put("LD", tk.getImage(Missile.class.getClassLoader().getResource("images/missileLD.gif")));
	}
	
	public Missile(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Missile(int x, int y, Direction dir, TankClient tc, boolean good){
		this(x,y,dir);
		this.tc=tc;
		this.good = good;
	}
	
	public void draw(Graphics g){
		if(!live){
			tc.missiles.remove(this);
			return;
		}
		/*Color c = g.getColor();
		if(good) g.setColor(Color.RED);
		else g.setColor(Color.BLACK);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);*/
		move();
		
		switch (dir){
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

	private void move() {
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
		}
		
		if(x<0|| y<0|| x>TankClient.GAME_WIDTH|| y>TankClient.GAME_HEIGHT){
			live=false;
		}
	}

	public boolean isLive() {
		return live;
	}
	
	public Rectangle getRect(){
		return new Rectangle(this.x, this.y, Missile.WIDTH, Missile.HEIGHT);
	}
	
	public boolean hitTank(Tank t){
		if(this.live && this.getRect().intersects(t.getRect())&& t.isLive()&& this.good != t.isGood()){
			if(t.isGood()){
				t.setLife(t.getLife()-20);
				if(t.getLife()<=0){
					t.setLive(false);
				}
			}
			else {
				t.setLive(false);
			}
			this.live=false;
			Explode e = new Explode(this.x, this.y, tc);
			tc.explodes.add(e);
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(List<Tank> tanks){
		for(int i=0; i<tanks.size(); i++){
			if(this.hitTank(tanks.get(i))){
				return true;
			}
		}
		return false;
	}
	
	public boolean hitWall(Wall w){
		if(this.live&& this.getRect().intersects(w.getRect())){
			this.live = false;
			return true;
		}
		return false;
	}
	
}
