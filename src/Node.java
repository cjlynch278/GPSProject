import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class Node implements Runnable {
	private int x;
	private int y;
	private int radius;
	private Color color;
	private JPanel panel;
	
	public Node(int x, int y, int radius, Color color, JPanel panel){
		this.x  =x;
		this.y = y;
		this.radius = radius;
		this.color = color;
		this.panel = panel;
	}
	public void draw(Graphics g){
		g.setColor(color);
		g.fillOval(x,y,radius,radius);
	}
	public void setX(int x){
		this.x= x;
	}
	public void toggleEnemy(){
		color = Color.red;
	}
	public void toggleOff(){
		color = Color.black;
	}
	public void toggleAvatar(){
		color = Color.green;
		
	}
	public void run() {
		try {
			while (true) {
				Thread.sleep(10);
				panel.repaint();
			}
		} catch (InterruptedException caught) {
			System.out.println("Node Error.");
		}
	}
}
