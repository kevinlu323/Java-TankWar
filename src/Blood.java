
import java.awt.*;
import java.util.Random;

public class Blood {
	public static final int BLOOD_WIDTH = 15;
	public static final int BLOOD_HEIGHT = 15;
	
	int x, y;
	TankClient tc;
	private Random r = new Random();
	int delay=200;
	int reLiveDelay = 400;
	private boolean live = true;
	
	public Blood(){
		/*this.x = pos[0][0];
		this.y = pos[0][1];*/
		this.x = r.nextInt(TankClient.GAME_WIDTH - BLOOD_WIDTH);
		this.y = r.nextInt(TankClient.GAME_HEIGHT - BLOOD_HEIGHT);
	}
	
	public void draw(Graphics g){
		if(!live) {
			if(reLiveDelay-- == 0){
				live = true;
				reLiveDelay = 400;
			}
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.PINK);
		g.fillRect(x, y, BLOOD_WIDTH, BLOOD_HEIGHT);
		g.setColor(c);
		move();
		
	}
	
	public void move(){
		if(delay-- == 0){
			x = r.nextInt(TankClient.GAME_WIDTH - BLOOD_WIDTH);
			y = r.nextInt(TankClient.GAME_HEIGHT - BLOOD_HEIGHT);
			delay = 200;
		}
	}
	
	public Rectangle getRect(){
		return new Rectangle(x, y, BLOOD_WIDTH, BLOOD_HEIGHT);
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
}
