package com.linkui.tank;

import java.awt.*;

public class Explode {
	int x,y;
	private boolean live = true;
	
	int[] diameter = {4, 10, 15, 22, 30, 38, 49, 32, 20, 3};
	int step = 0;
	
	private TankClient tc;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private static Image[] imgs = {
		tk.getImage(Explode.class.getClassLoader().getResource("images/0.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/8.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/9.gif"))
	};
	
	private static boolean init = false;
	
	public Explode(int x, int y, TankClient tc){
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	public void draw(Graphics g){
		//to resolve the 1st explosion not draw issue.
		if(!init){
			for (int i = 0; i < imgs.length; i++) {
				g.drawImage(imgs[i], -100, -100, null);
			}
		}
		if(!live) {
			tc.explodes.remove(this);
			return;
		}
		
		if(step == imgs.length){
			step = 0;
			live = false;
			return;
		}
		
		g.drawImage(imgs[step], x, y, null);
		step++;
	}
}
