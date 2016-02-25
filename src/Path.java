import java.awt.Color;
import java.awt.Graphics;

public class Path {
	private int x;
	private int y;
	private int height;
	private int width;
	
	public Path(int x, int y, int height, int width){
		this.x  =x;
		this.y = y;
		this.height =height;
		this.width = width;
	}
	public void drawVerticle(Graphics g){
		g.setColor(Color.black);
		g.fillRect(x,y,width,height);		
	}
	public void drawHorizantal(Graphics g){
		g.setColor(Color.black);
		g.fillRect(x, y, height,width);
	}
}
