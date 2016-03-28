import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GPSPanel extends JPanel {
	private int [][] graph;
	ArrayList<Node> nodes = new ArrayList<Node>();
	ArrayList<Path> paths = new ArrayList<Path>();
	static int space;
	static int radius;
	public int  totalNodes;
	static int pathHeight;
	static int pathWidth;
	static int enemySpeed;
	int currentNode = 10;
	
	
	static {
		radius = 20;
		space = 50;
		pathHeight = 50;
		pathWidth = 10;
		enemySpeed = 1000;
		
	}
	
	public GPSPanel(int [][] graph) {
		this.graph = graph;
	}
	
	public void makeObjects() {
		totalNodes = graph.length;
		Thread nodeThread;

		for(int j = 0; j <graph.length/5;j++){
			for(int i = 0; i<5; i++){
				nodes.add(new Node(j*space,i*space,radius,Color.black,this));
			}
		}
		for(int j =0; j <graph.length; j++){
			for(int i = 0; i<graph.length;i++){
				if(graph[j][i]>0&&i==j+1){
					paths.add(new Path((j/5*space)+(radius/3),(j%5*space)+(radius/2), pathHeight, pathWidth));
				}
				 else if(graph[j][i]>0&& i+1 == j){
					paths.add(new Path((j/5*space)+(radius/3),((j-1)%5*space)+(radius/2),pathHeight,pathWidth));
				 }
				
				else if(graph[j][i]>0&& i== j+5){
					paths.add(new Path(j/5*space+radius/2, j%5*space+radius/3, pathWidth,pathHeight));
				}	 
				else if(graph[j][i]>0&& i==j-5){
					paths.add(new Path((j-5)/5*space+radius/2,j%5*space+radius/3, pathWidth,pathHeight));
				}	 
			}
		}
		nodeThread = new Thread(nodes.get(0));
		nodeThread.start();
	
	}
	public void enemyMovement(int currentNode){
		ArrayList<Integer> enemyMove;
		GraphAlgorithms g = new GraphAlgorithms();
	

		enemyMove = g.processGraph(graph,9,currentNode);
		System.out.println(enemyMove.size());
		
		try{
			nodes.get(enemyMove.get(0)).toggleEnemy();
			Thread.sleep(500);
			for(int  i =1; i <enemyMove.size(); i ++){
				nodes.get(enemyMove.get(i-1)).toggleOff();
				
				nodes.get(enemyMove.get(i)).toggleEnemy();
				Thread.sleep(enemySpeed);
				
			}
			
		}catch (InterruptedException caught){
			System.out.println("APP stopped");
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i =0; i<nodes.size();i++){
			nodes.get(i).draw(g);
		}
		for(int i =0; i<paths.size(); i++){
			paths.get(i).drawVerticle(g);
		}
		nodes.get(currentNode).toggleAvatar();
	}
	
	public int moveNode(char keyChar) {
		if(keyChar == 'w'){
			
			nodes.get(currentNode).toggleOff();
			nodes.get(currentNode-1).toggleAvatar();
			currentNode --;
		}
		else if(keyChar == 's'){
			nodes.get(currentNode).toggleOff();
			nodes.get(currentNode+1).toggleAvatar();
			currentNode ++;
		}
		else if(keyChar == 'a'){
			nodes.get(currentNode).toggleOff();
			nodes.get(currentNode-5).toggleAvatar();
			currentNode -= 5;
		}
		else if(keyChar == 'd'){
			nodes.get(currentNode).toggleOff();
			nodes.get(currentNode+5).toggleAvatar();
			currentNode += 5;
			
			
		}
		else{
			System.out.println("Out of Bounds");
		}
		return currentNode;
	
	}
	
}
	
