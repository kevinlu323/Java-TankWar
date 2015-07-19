
import java.awt.*;

public class Explode {
	int x,y;
	private boolean live = true;
	
	int[] diameter = {4, 10, 15, 22, 30, 38, 49, 32, 20, 3};
	int step = 0;
	
	private TankClient tc;
	
	public Explode(int x, int y, TankClient tc){
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	public void draw(Graphics g){
		if(!live) return;
		
		if(step == diameter.length){
			step = 0;
			live = false;
			return;
		}
		
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x, y, diameter[step], diameter[step]);
		step++;
		g.setColor(c);
	}
}
